package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.repositories.DataObjectsRepository;
import com.rychkov.rds.repositories.DataTypesRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    void getAllDataTypes() {
        assertFalse(dataService.getAllDataTypes().isEmpty());
    }

    @Test
    void getAllLifeCycles() {
        assertFalse(dataService.getAllLifeCycles().isEmpty());
    }

    @Test
    void countLifeCycles() {
        assertNotEquals(0, dataService.countLifeCycles());
    }

    @Test
    void saveNewDataObject() {
        DataObject dataObject = dataService.saveNewDataObject(DataObjectDto.builder().dataContent("Test").dataType(1).lifeCycle(1).build());
        assertFalse(dataObjectsRepository.findById(dataObject.getId()).isEmpty());
        assertEquals("Test", dataObjectsRepository.findById(dataObject.getId()).get().getDataContent());
    }

    @Test
    void getAllDataObjectsByDataType() {
        Optional<DataType> optionalDataType = dataTypesRepository.findByName("Web");
        assertTrue(optionalDataType.isPresent());
        dataService.saveNewDataObject(DataObjectDto.builder().dataContent("Test").dataType(optionalDataType.get().getId()).lifeCycle(1).build());
        assertNotNull(dataService.getAllDataObjectsByDataTypeName(optionalDataType.get().getName()));
    }

    @Test
    void getPageDataObjectsByDataTypeName() {
        Optional<DataType> optionalDataType = dataTypesRepository.findByName("Web");
        assertTrue(optionalDataType.isPresent());
        dataService.saveNewDataObject(DataObjectDto.builder().dataContent("Test").dataType(optionalDataType.get().getId()).lifeCycle(1).build());
        Page<DataObject> dataObjectPage = dataService.getPageDataObjectsByDataTypeName(optionalDataType.get().getName(), 1);
        assertTrue(dataObjectPage.getTotalPages() > 0);
    }
}