package com.week2.springboot_WebTutorial.springboot_WebTutorial.controller;

import com.week2.springboot_WebTutorial.springboot_WebTutorial.dto.EmployeeDto;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.entities.EmployeeEntity;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.exceptions.ResourceNotFoundException;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path="/employees")
public class EmployeeController {

   /* @GetMapping(path = "/getSecretMessage")
    String getSecretMessage(){
        return "SecretMessage:Magnus!2025";
    }*/


    /*NOTE: All the requests in the browser are Getmapping Requests by default*/

  //  private final EmployeeRepository employeeRepository;

   /* public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }*/ //constructor dependency injection


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")//this is used when user should provide and ID defintely
    public ResponseEntity<EmployeeDto> getEmployeeByID(@PathVariable(name="employeeId") Long ID){
       Optional<EmployeeDto>  employeeDto= employeeService.getEmployeebyId(ID);
        return employeeDto
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1))
                .orElseThrow(()->new ResourceNotFoundException("Employee Not Found with ID:"+ID));
    }



    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam(required = false, name="newAge") Integer age){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody @Valid EmployeeDto inputNewEmployee){
        EmployeeDto savedEmployee = employeeService.createNewEmployee(inputNewEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path="/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeID(@RequestBody EmployeeDto employeeDto,@PathVariable Long employeeId){

        return ResponseEntity.ok(employeeService.updateEmployeeID(employeeId,employeeDto));
    }


    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeID(@PathVariable Long employeeId){

        boolean gotDeleted = employeeService.deleteEmployeeID(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
       return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updatepartialEmployeeId(@RequestBody Map<String,Object> updates ,
                                               @PathVariable Long employeeId){
        EmployeeDto employeeDto = employeeService.updatepartialEmployeeId(updates,employeeId);
        if(employeeDto==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDto);
    }


}
