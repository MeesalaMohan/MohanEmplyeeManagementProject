package com.test.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Entity.Employee;

public interface Repositry extends JpaRepository<Employee,Integer>{

}
