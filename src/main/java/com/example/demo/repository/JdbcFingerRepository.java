package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.example.demo.model.Finger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcFingerRepository implements FingerRepository{
	private final JdbcTemplate jdbc;
	@Autowired
	public JdbcFingerRepository(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}
	@Override
	public Iterable<Finger> findByName(String name, int studentid) {
		
		return jdbc.query("select * from fingerprint where finger like ? and Studentid=?", this::mapRowToFinger,"%"+name+"%",studentid);
	}
	@Override
	public Iterable<Finger> findByidStudent(int studentid) {
		return jdbc.query("select * from fingerprint where Studentid=?",this::mapRowToFinger,studentid);
	}
	public Finger mapRowToFinger(ResultSet rs,int rowNum) throws SQLException
	{
		return new Finger(rs.getInt("id"),rs.getInt("Studentid"),rs.getString("name"),rs.getString("finger"),rs.getString("filePath"));
	}
	@Override
	public void delete(int id) {
		jdbc.update("delete from fingerprint where id=?",id);
	}
	@Override
	public Finger findFingerID(int id) {
		return jdbc.queryForObject("select * from fingerprint where id=?", this::mapRowToFinger,id);
	}
	@Override
	public Finger saveFinger(Finger finger) {
		jdbc.update("insert into fingerprint(Studentid,name,finger,filePath)"
				+ "values(?,?,?,?)",finger.getStudentid(),finger.getName(),finger.getFinger(),finger.getFilePath());
		return finger;
	}
	
}