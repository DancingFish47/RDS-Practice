package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface DataService {
    List<DataType> getAllDataTypes();

    List<LifeCycle> getAllLifeCycles();

    Long countLifeCycles();

    DataObject saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException;

    Page<DataObject> getPageDataObjects(@Nullable String dataTypeName, @Nullable Date date, Integer page);

}
