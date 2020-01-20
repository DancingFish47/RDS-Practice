package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataService {
    Iterable<DataType> getAllDataTypes();

    Iterable<LifeCycle> getAllLifeCycles();

    Long countLifeCycles();

    DataObject saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException;

    Iterable<DataObject> getAllDataObjectsByDataType(String dataTypeName);
}
