package com.rd.loancalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

        private String errorMessage;
        private String statusCode;

}
