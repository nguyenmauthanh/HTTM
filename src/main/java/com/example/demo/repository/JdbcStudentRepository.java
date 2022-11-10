package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStudentRepository implements StudentRepository{
	private final JdbcTemplate jdbc;
	@Autowired
	public JdbcStudentRepository(JdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}
	@Override
	public Iterable<Student> findAll() {
		return jdbc.query("select * from student",this::mapRowToStudent);
	}

	@Override
	public Student findById(int id) {
		return jdbc.queryForObject("select * from student where id=?",this::mapRowToStudent,id);
	}
	
	@Override
	public Student save(Student student) {
		jdbc.update("insert into Student(studentCode,name,dayOfBirth,phoneNumber,address) values(?,?,?,?,?)",
				student.getStudentCode(),student.getName(),student.getDayOfBirth(),student.getPhoneNumber(),student.getAddress());
		return student;
	}
	@Override
	public Student update(Student student) {
		jdbc.update("update student set studentCode=?,name=?,dayOfBirth=?,phoneNumber=?,address=? where id=?",
				student.getStudentCode(),student.getName(),student.getDayOfBirth(),student.getPhoneNumber(),
				student.getAddress(),student.getId());
		return student;
	}
	@Override
	public void delete(int id) {
		jdbc.update("delete from student where id=?",id);
	}

	private Student mapRowToStudent(ResultSet rs, int rowNum)
		    throws SQLException {
		  return new Student(rs.getInt("id"),rs.getString("studentCode"),rs.getString("name"),rs.getDate("dayOfBirth"),rs.getString("phoneNumber"),rs.getString("address"));
	}
	
}