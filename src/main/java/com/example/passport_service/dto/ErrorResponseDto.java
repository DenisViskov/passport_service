package com.example.passport_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    @JsonProperty(value = "field_name")
    private String field;

    @JsonProperty(value = "description_error")
    private String description;

    @JsonProperty(value = "actual_value")
    private String givenValue;
}
