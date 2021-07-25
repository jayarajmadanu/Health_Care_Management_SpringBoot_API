package com.examly.healthCareManagement.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.examly.healthCareManagement.model.Doctor;

public class DoctorRowMapper implements RowMapper<Doctor> {

	@Override
	public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Doctor doc = new Doctor();
		doc.setId(rs.getLong("id"));
		doc.setFirstName(rs.getString("firstName"));
		doc.setLastName(rs.getString("lastName"));
		doc.setSpecification(rs.getString("specification"));
		doc.setDescription(rs.getString("description"));
		doc.setEmail(rs.getString("email"));
		doc.setHospital(rs.getString("hospital"));
		doc.setGender(rs.getString("gender"));
		return doc;
	}

}
