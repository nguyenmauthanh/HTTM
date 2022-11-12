package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Finger{
    private int id;
    private String name;// ten file

    private String studentCode;
    private String finger;// ten ngon
    private String filePath;//duong dan den file

    public Finger(int id,String name,String finger,String filePath) {
        this.id=id;
        this.name=name;
        this.finger=finger;
        this.filePath=filePath;
    }

    public Finger(int id,String studentCode, String name,String finger,String filePath) {
        this.id=id;
        this.studentCode = studentCode;
        this.name=name;
        this.finger=finger;
        this.filePath=filePath;
    }
}

