package com.rychkov.rds.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.repositories.DataObjectsRepository;
import com.rychkov.rds.repositories.DataTypesRepository;
import com.rychkov.rds.repositories.LifeCyclesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MainPageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainPageController mainPageController;
    @Autowired
    private ObjectMapper objectMapper;

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
    public void contextLoads() throws Exception {
        assertThat(mainPageController).isNotNull();
    }

    @Test
    void homePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/getDataObjects"));
    }

    @Test
    void getDataObjects() throws Exception {
        this.mockMvc.perform(get("/getDataObjects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dataObjects", "dataTypes", "lifeCycles", "lifeCyclesCount"))
                .andExpect(view().name("index"));

    }

    @Test
    void saveNewDataObject() throws Exception {
        DataObjectDto dataObjectDto = DataObjectDto.builder()
                .lifeCycle(lifeCycle.getId())
                .dataContent("Test")
                .dataType(webDataType.getId())
                .build();
        MvcResult result = mockMvc.perform(post("/saveNewDataObject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataObjectDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("false"));

    }

    @AfterEach
    public void clear() {
        dataObjectsRepository.deleteAll();
        lifeCyclesRepository.deleteAll();
        dataTypesRepository.deleteAll();
    }

}