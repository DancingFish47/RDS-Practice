package com.rychkov.rds.repositories;

import com.rychkov.rds.entities.DataObject;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DataObjectsRepository extends CrudRepository<DataObject, Integer> {
    List<DataObject> findAllByDataType_Id(Integer dataTypeId);
}
