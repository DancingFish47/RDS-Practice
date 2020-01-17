package com.rychkov.rds.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private boolean error;
    private String message;
}
