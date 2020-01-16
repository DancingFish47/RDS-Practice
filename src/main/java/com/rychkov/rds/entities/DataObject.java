package com.rychkov.rds.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "data")
public class DataObject extends AbstractEntity {

}
