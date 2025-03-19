package com.week2.springboot_WebTutorial.springboot_WebTutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long Id;
    @NotBlank(message = "Name Shouldnt be empty or blank")
    @Size(min = 3,max = 10,message = "The number of character should be in range from 3 to 10")
    private String name;
    @Email
    private String email;
    @Positive(message = "Age cannot be negative")
    @Min(message = "the min age should be 20", value = 20)
    @Max(message = "The max should cannot exceed 70",value = 70)

    private Integer age;

   /* @Pattern(regexp = "^(ADMIN|USER)$" , message = "The role of the employee can be USER or ADMIN")*/
    @EmployeeRoleValidation // This is a custom annotation that we created in annotations package
    @NotBlank(message = "The Role is a mandatory field")
    private String role;
    @PastOrPresent(message = "Date can only be in Past or Present")
    private LocalDate dateOfJoining;

    @JsonProperty("isActive")
    private Boolean isActive;

}
