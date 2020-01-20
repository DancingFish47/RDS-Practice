package com.rychkov.rds.repositories;

import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.entities.DataType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataObjectsRepository extends PagingAndSortingRepository<DataObject, Integer> {
    List<DataObject> findAllByDataType_Id(Integer dataTypeId);
    Page<DataObject> findAllByDataType_Name(String dataTypeName, Pageable pageable);
}
