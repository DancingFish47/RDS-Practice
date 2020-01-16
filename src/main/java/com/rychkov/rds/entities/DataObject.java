package com.rychkov.rds.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "dataobject")
public class DataObject extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datatype")
    private DataType dataType;

    @Column(name = "datacontent")
    private String dataContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lifecycle")
    private LifeCycle lifeCycle;

    @Column(name = "maxlifecyclelevel")
    private Integer maxLifeCycleLevel;

    @Column(name = "validtill")
    private Date validTill;

    @Column(name = "editedBy")
    private String editedBy;

}
