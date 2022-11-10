package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student{
    private int id;
    private String studentCode;
    private String name;
    private Date dayOfBirth;
    private String phoneNumber;
    private String address;
}