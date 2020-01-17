package com.rychkov.rds.repositories;

import com.rychkov.rds.entities.LifeCycle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeCyclesRepository extends CrudRepository<LifeCycle, Integer> {
}
