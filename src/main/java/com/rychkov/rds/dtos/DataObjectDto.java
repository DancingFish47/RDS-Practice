package com.rychkov.rds.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DataObjectDto {
    private String editedBy;
    private Integer dataType;
    private String dataContent;
    private Integer lifeCycle;
    private Integer maxLifeCycleLevel;
    private Date validTill;
}
