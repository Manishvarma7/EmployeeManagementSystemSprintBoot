package com.week2.springboot_WebTutorial.springboot_WebTutorial.advices;

import com.week2.springboot_WebTutorial.springboot_WebTutorial.annotations.EmployeeRoleValidation;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Data
@Builder

public class ApiError {

    
    private HttpStatus status;
    private String message;
    private List<String> subErrors;


}
