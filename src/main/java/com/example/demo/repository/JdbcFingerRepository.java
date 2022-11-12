package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.example.demo.model.Finger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcFingerRepository implements FingerRepository{
	private JdbcTemplate jdbc;
	@Autowired
	public JdbcFingerRepository(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}
	@Override
	public List<Finger> findByidStudent(int studentid) {
		List<Finger>listfinger=new ArrayList<>();
		listfinger=jdbc.query("select * from fingerprint where Studentid=?",this::mapRowToFinger,studentid);
		return listfinger;
	}
	public Finger mapRowToFinger(ResultSet rs,int rowNum) throws SQLException
	{
		return new Finger(rs.getInt("id"),rs.getString("Studentid"),rs.getString("name"),rs.getString("finger"),rs.getString("filePath"));
	}
	@Override
	public void delete(int id) {
		jdbc.update("delete from fingerprint where id=?",id);
	}

	@Override
	public Iterable<Finger> findAll() {
		return jdbc.query("select * from fingerprint",this::mapRowToFinger);
	}

	@Override
	public Finger saveFinger (Finger finger,int studentid) {
		jdbc.update("insert into fingerprint(Studentid,name,finger,filePath)"
				+ "values(?,?,?,?)",studentid,finger.getName(),finger.getFinger(),finger.getFilePath());
		return finger;
	}

}