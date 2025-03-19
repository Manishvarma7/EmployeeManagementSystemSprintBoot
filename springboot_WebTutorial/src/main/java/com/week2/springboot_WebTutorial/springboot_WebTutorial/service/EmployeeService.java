package com.week2.springboot_WebTutorial.springboot_WebTutorial.service;


import com.week2.springboot_WebTutorial.springboot_WebTutorial.dto.EmployeeDto;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.entities.EmployeeEntity;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.exceptions.ResourceNotFoundException;
import com.week2.springboot_WebTutorial.springboot_WebTutorial.repositories.EmployeeRepository;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDto> getEmployeebyId(Long id) {

//       Optional<EmployeeEntity> employeeEntity= employeeRepository.findById(id);
//       return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity,EmployeeDto.class));

        return employeeRepository.findById(id).map(employeeEntity ->  modelMapper.map(employeeEntity,EmployeeDto.class));
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList= employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto createNewEmployee(EmployeeDto inputNewEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputNewEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity= employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDto.class);
    }

    public void isExistById(Long employeeId){ //
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) throw new ResourceNotFoundException("Employee with ID:"+ employeeId + " is not found");

    }

    public EmployeeDto updateEmployeeID(Long employeeId, EmployeeDto employeeDto) {
        isExistById(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDto.class);
    }



    public boolean deleteEmployeeID(Long employeeId) {
        isExistById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDto updatepartialEmployeeId(Map<String, Object> updates, Long employeeId) {
        isExistById(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);//bcoz the fields in the employee entity are private so we are making them public here
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);

        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDto.class);
    }

}
