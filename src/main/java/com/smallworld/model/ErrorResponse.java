package com.smallworld.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private Integer errorCode;
    private String errorMessage;
}
