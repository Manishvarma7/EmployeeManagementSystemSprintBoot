package com.week2.springboot_WebTutorial.springboot_WebTutorial.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation,String> {
    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        if(inputRole==null) return false;
        List<String> roles = List.of("USER","ADMIN");
        return roles.contains(inputRole);
    }
}
