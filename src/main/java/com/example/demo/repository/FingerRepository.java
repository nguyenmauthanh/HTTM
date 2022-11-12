package com.example.demo.repository;


import com.example.demo.model.Finger;
import com.example.demo.model.Student;

import java.util.List;

public interface FingerRepository {
	List<Finger> findByidStudent(int studentid);
	Finger saveFinger(Finger finger,int studentid);
	void delete(int id);

	Iterable <Finger>findAll();
}

