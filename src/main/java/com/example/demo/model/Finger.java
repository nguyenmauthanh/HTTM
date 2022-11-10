package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Finger{
    private int id;
    private int Studentid;
    private String name;// ten file
    private String finger;// ten ngon
    private String filePath;//duong dan den file

    public Finger(int id,int Studentid,String name,String finger,String filePath) {
        this.id=id;
        this.Studentid=Studentid;
        this.name=name;
        this.finger=finger;
        this.filePath=filePath;
    }
}
