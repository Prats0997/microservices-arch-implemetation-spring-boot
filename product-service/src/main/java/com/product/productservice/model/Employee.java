package com.product.productservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name="emp_firstName", nullable = false)
    private String firstName;

    @Column(name="emp_lastName", nullable = false)
    private String lastName;

    @Column(name="emp_email", nullable = false)
    private String email;

}
