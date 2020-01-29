package com.rychkov.rds.services;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import com.rychkov.rds.repositories.DataObjectsRepository;
import com.rychkov.rds.repositories.DataTypesRepository;
import com.rychkov.rds.repositories.LifeCyclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataTypesRepository dataTypesRepository;
    private final DataObjectsRepository dataObjectsRepository;
    private final LifeCyclesRepository lifeCyclesRepository;
    @Autowired
    private Environment environment;

    @Override
    public List<DataType> getAllDataTypes() {
        return (List<DataType>) dataTypesRepository.findAll();
    }

    @Override
    public List<LifeCycle> getAllLifeCycles() {
        return (List<LifeCycle>) lifeCyclesRepository.findAll();
    }

    @Override
    public Long countLifeCycles() {
        return lifeCyclesRepository.count();
    }

    public DataObject saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException {
        Optional<DataType> optionalDataType = dataTypesRepository.findById(dataObjectDto.getDataType());
        if (optionalDataType.isEmpty()) throw new DataObjectException("Wrong data type!");
        Optional<LifeCycle> optionalLifeCycle = lifeCyclesRepository.findById(dataObjectDto.getLifeCycle());
        if (optionalLifeCycle.isEmpty()) throw new DataObjectException("Wrong lifecycle!");

        DataObject dataObject = DataObject.builder().dataType(optionalDataType.get())
                .lifeCycle(optionalLifeCycle.get())
                .dataContent(dataObjectDto.getDataContent())
                .editedBy(dataObjectDto.getEditedBy())
                .maxLifeCycleLevel(dataObjectDto.getMaxLifeCycleLevel())
                .validTill(dataObjectDto.getValidTill())
                .build();

        dataObject = dataObjectsRepository.save(dataObject);
        if (dataObject == null)
            throw new DataObjectException("Failed to save new data object");

        return dataObject;
    }

    @Override
    public Optional<DataObject> getTopDataObjectsByDataTypeName(@Nullable String dataTypeName, Date date) {
        return dataObjectsRepository.findTop1ByDataType_NameAndValidTillGreaterThanOrderByValidTillAsc(
                dataTypeName,
                date
        );
    }

    @Override
    public List<DataObject> getTopDataForEachDataType(Date date) {
        List<DataObject> dataObjects = new ArrayList<>();
        for (DataType dataType : dataTypesRepository.findAll()) {
            Optional<DataObject> optionalDataObject = dataObjectsRepository.findTop1ByDataType_NameAndValidTillGreaterThanOrderByValidTillAsc(dataType.getName(), date);
            optionalDataObject.ifPresent(dataObjects::add);
        }
        return dataObjects;
    }


}
