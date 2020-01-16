package com.rychkov.rds.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "lifecycle")
public class LifeCycle extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;
}
