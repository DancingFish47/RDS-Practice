package com.rychkov.rds.services;

import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import com.rychkov.rds.entities.LifeCycle;
import com.rychkov.rds.exceptions.DataObjectException;
import com.rychkov.rds.repositories.DataObjectsRepository;
import com.rychkov.rds.repositories.DataTypesRepository;
import com.rychkov.rds.repositories.LifeCyclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataTypesRepository dataTypesRepository;
    private final DataObjectsRepository dataObjectsRepository;
    private final LifeCyclesRepository lifeCyclesRepository;

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
    }
}
