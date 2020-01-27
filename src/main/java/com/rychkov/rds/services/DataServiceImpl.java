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
import java.util.Date;
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
    public Page<DataObject> getPageDataObjects(@Nullable String dataTypeName, @Nullable Date date, Integer page) {
        if (dataTypeName != null) {
            if (date != null) {
                return dataObjectsRepository.findAllByDataType_NameAndValidTill(
                        dataTypeName,
                        date,
                        PageRequest.of(page - 1, Integer.parseInt(Objects.requireNonNull(environment.getProperty("dataObjects.by.page")))));
            } else {
                return dataObjectsRepository.findAllByDataType_Name(
                        dataTypeName,
                        PageRequest.of(page - 1, Integer.parseInt(Objects.requireNonNull(environment.getProperty("dataObjects.by.page")))));
            }
        } else {
            if (date != null) {
                return dataObjectsRepository.findAllByValidTill(
                        date,
                        PageRequest.of(page - 1, Integer.parseInt(Objects.requireNonNull(environment.getProperty("dataObjects.by.page")))));
            } else {
                return dataObjectsRepository.findAll(
                        PageRequest.of(page - 1, Integer.parseInt(Objects.requireNonNull(environment.getProperty("dataObjects.by.page")))));
            }
        }

    }
}
