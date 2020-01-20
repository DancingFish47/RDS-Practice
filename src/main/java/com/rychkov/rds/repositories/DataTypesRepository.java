package com.rychkov.rds.repositories;

import com.rychkov.rds.entities.DataType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataTypesRepository extends CrudRepository<DataType, Integer> {
    Optional<DataType> findByName(String name);
}
