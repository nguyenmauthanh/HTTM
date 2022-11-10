package com.example.demo.repository;

import com.example.demo.model.Student;

public interface StudentRepository{
	Iterable <Student>findAll();
	Student findById(int id);
	Student save(Student student);
	Student update(Student student);
	void delete(int id);
}