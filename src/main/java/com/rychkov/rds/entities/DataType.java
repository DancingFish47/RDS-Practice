package com.rychkov.rds.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "datatype")
public class DataType extends AbstractEntity {
    @Column(name = "name")
    private String name;
}
