package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.repositories.DataObjectsRepository;
import com.rychkov.rds.repositories.DataTypesRepository;
import com.rychkov.rds.repositories.LifeCyclesRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class DataServiceImplTest {
    @Autowired
    private DataService dataService;
    @Autowired
    private DataObjectsRepository dataObjectsRepository;
    @Autowired
    private DataTypesRepository dataTypesRepository;
    @Autowired
    private LifeCyclesRepository lifeCyclesRepository;
    private DataType webDataType;
    private DataType mobileDataType;
    private LifeCycle lifeCycle;
    private Date pastDate;
    private Date closestDate;
    private Date futureDate;

    @BeforeEach
    public void init() {
        webDataType = DataType.builder().name("Web").build();
        mobileDataType = DataType.builder().name("Mobile").build();
        webDataType = dataTypesRepository.save(webDataType);
        mobileDataType = dataTypesRepository.save(mobileDataType);

        lifeCycle = LifeCycle.builder().level(1).name("First").build();
        lifeCycle = lifeCyclesRepository.save(lifeCycle);

        pastDate = Date.valueOf("2020-01-01");
        closestDate = Date.valueOf(LocalDate.now().plusDays(1));
        futureDate = Date.valueOf(LocalDate.now().plusMonths(1));

        List<DataObject> dataObjects = new ArrayList<>();

        dataObjects.add(DataObject.builder().dataType(webDataType).lifeCycle(lifeCycle).validTill(pastDate).dataContent("Past Web data").build());
        dataObjects.add(DataObject.builder().dataType(webDataType).lifeCycle(lifeCycle).validTill(closestDate).dataContent("Closest Web data").build());
        dataObjects.add(DataObject.builder().dataType(webDataType).lifeCycle(lifeCycle).validTill(futureDate).dataContent("Future Web data").build());

        dataObjects.add(DataObject.builder().dataType(mobileDataType).lifeCycle(lifeCycle).validTill(pastDate).dataContent("Past Mobile data").build());
        dataObjects.add(DataObject.builder().dataType(mobileDataType).lifeCycle(lifeCycle).validTill(closestDate).dataContent("Closest Mobile data").build());
        dataObjects.add(DataObject.builder().dataType(mobileDataType).lifeCycle(lifeCycle).validTill(futureDate).dataContent("Future Mobile data").build());

        dataObjectsRepository.saveAll(dataObjects);
    }

    @Test
    void saveNewDataObject() {
        DataObject dataObject = dataService.saveNewDataObject(
                DataObjectDto
                        .builder()
                        .dataContent("Test")
                        .dataType(webDataType.getId())
                        .lifeCycle(lifeCycle.getId())
                        .build());
        assertFalse(dataObjectsRepository.findById(dataObject.getId()).isEmpty());
        assertEquals("Test", dataObjectsRepository.findById(dataObject.getId()).get().getDataContent());
    }

    @Test
    void getTopDataObjectsByDataTypeName() {
        Optional<DataObject> optionalDataObject = dataService.getTopDataObjectsByDataTypeName(
                webDataType.getName(),
                Date.valueOf(LocalDate.now()));

        assertTrue(optionalDataObject.isPresent());

        DataObject dataObject = optionalDataObject.get();

        assertEquals(webDataType.getName(), dataObject.getDataType().getName());
        assertEquals(closestDate, dataObject.getValidTill());
    }

    @Test
    void getTopDataForEachDataType() {
        List<DataObject> dataObjects = new ArrayList<>();
        for (DataType dataType : dataTypesRepository.findAll()) {
            Optional<DataObject> optionalDataObject = dataObjectsRepository.findTop1ByDataType_NameAndValidTillGreaterThanOrderByValidTillAsc(
                    dataType.getName(),
                    Date.valueOf(LocalDate.now()));

            assertTrue(optionalDataObject.isPresent());

            dataObjects.add(optionalDataObject.get());
        }
        for (DataObject dataObject: dataObjects){
            assertTrue(dataObject.getDataType().getName().equals(webDataType.getName()) || dataObject.getDataType().getName().equals(mobileDataType.getName()));
            assertEquals(closestDate, dataObject.getValidTill());
        }
    }

    @AfterEach
    public void clear() {
        dataObjectsRepository.deleteAll();
        lifeCyclesRepository.deleteAll();
        dataTypesRepository.deleteAll();
    }


}