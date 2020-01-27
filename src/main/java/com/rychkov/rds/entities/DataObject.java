package com.rychkov.rds.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "validtill")
    private Date validTill;

    @Column(name = "editedBy")
    private String editedBy;

}
