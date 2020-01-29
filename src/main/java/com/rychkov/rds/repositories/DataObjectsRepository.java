package com.rychkov.rds.repositories;

import com.rychkov.rds.entities.DataObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataObjectsRepository extends PagingAndSortingRepository<DataObject, Integer> {
    Optional<DataObject> findTop1ByDataType_NameAndValidTillGreaterThanOrderByValidTillAsc(String dataTypeName, Date date);
}
