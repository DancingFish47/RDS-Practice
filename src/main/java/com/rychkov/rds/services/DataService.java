package com.rychkov.rds.services;

import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;

import java.util.List;

public interface DataService {
    Iterable<DataType> getAllDataTypes();

    List<DataObject> getDataObjects(Integer dataTypeId, Integer page);

    Iterable<LifeCycle> getAllLifeCycles();

    Long countLifeCycles();

    void saveNewDataObject(DataObject dataObject) throws DataObjectException;
}
