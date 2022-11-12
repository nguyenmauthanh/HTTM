package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Finger> listFingerprint=new ArrayList<>();
    public Student(int id, String studentCode,String name,Date dayOfBirth, String phoneNumber, String address) {
        this.id=id;
        this.studentCode=studentCode;
        this.name=name;
        this.dayOfBirth=dayOfBirth;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }
}