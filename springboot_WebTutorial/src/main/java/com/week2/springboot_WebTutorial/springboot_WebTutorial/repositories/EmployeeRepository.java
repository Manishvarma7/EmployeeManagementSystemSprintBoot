package com.week2.springboot_WebTutorial.springboot_WebTutorial.repositories;

import com.week2.springboot_WebTutorial.springboot_WebTutorial.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // this is extending from component annotation so this is now a BEAN
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{

}
