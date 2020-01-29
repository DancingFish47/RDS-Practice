package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DataService {
    List<DataType> getAllDataTypes();

    List<LifeCycle> getAllLifeCycles();

    Long countLifeCycles();

    DataObject saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException;

    Optional<DataObject> getTopDataObjectByDataTypeName(@Nullable String dataTypeName, Date date);

    List<DataObject> getTopDataObjectForEachDataType(Date date);

}
