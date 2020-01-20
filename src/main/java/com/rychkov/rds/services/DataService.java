package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataService {
    List<DataType> getAllDataTypes();

    List<LifeCycle> getAllLifeCycles();

    Long countLifeCycles();

    DataObject saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException;

    List<DataObject> getAllDataObjectsByDataTypeName(String dataTypeName);

    Page<DataObject> getPageDataObjectsByDataTypeName(String dataTypeName, Integer page);
}
