package com.product.productservice.controller;

import com.product.productservice.model.Employee;
import com.product.productservice.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //ResponseEntity class is used because we can provide status code or headers to this class
    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp){
        return new ResponseEntity<Employee>(employeeService.saveEmployee(emp), HttpStatus.CREATED);
    }

    //Get all employee from the table
    @GetMapping("/getEmployees")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployees();
    }


    //Get employee details by Id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long empId){
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(empId),HttpStatus.OK);
    }


    //Update employee in the employee table
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp, @PathVariable("id") long id){
        return new ResponseEntity<Employee>(employeeService.updateEmployee(emp,id), HttpStatus.OK);
    }
    
    //Delete employee in the employee table
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long empId){
        employeeService.deleteEmployee(empId);

        return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
    }
}
