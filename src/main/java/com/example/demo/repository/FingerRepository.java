package com.example.demo.repository;


import com.example.demo.model.Finger;

public interface FingerRepository {
	Finger findFingerID(int id);
	Iterable<Finger>findByName(String name,int studentid);
	Iterable<Finger>findByidStudent(int studentid);
	Finger saveFinger(Finger finger);
	void delete(int id);
}
