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
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataTypesRepository dataTypesRepository;
    private final DataObjectsRepository dataObjectsRepository;
    private final LifeCyclesRepository lifeCyclesRepository;
    private final Environment env;

    @Override
    public Iterable<DataType> getAllDataTypes() {
        return dataTypesRepository.findAll();
    }

    @Override
    public List<DataObject> getDataObjects(Integer dataTypeId, Integer page) {
        return dataObjectsRepository.findAllByDataType_Id(dataTypeId);
    }

    @Override
    public Iterable<LifeCycle> getAllLifeCycles() {
        return lifeCyclesRepository.findAll();
    }

    @Override
    public Long countLifeCycles() {
        return lifeCyclesRepository.count();
    }

    @Override
    public void saveNewDataObject(DataObject dataObject) throws DataObjectException {
        if(dataObjectsRepository.save(dataObject) == null) throw new DataObjectException("Saving has failed!");
    public void saveNewDataObject(DataObjectDto dataObjectDto) throws DataObjectException {
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

        if (dataObjectsRepository.save(dataObject) == null)
            throw new DataObjectException("Failed to save new data object");
    }
}
