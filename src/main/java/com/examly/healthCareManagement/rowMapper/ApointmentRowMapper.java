package com.examly.healthCareManagement.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.examly.healthCareManagement.model.Apointment;

public class ApointmentRowMapper implements RowMapper<Apointment> {

	@Override
	public Apointment mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Apointment ap = new Apointment();
		ap.setDoc_id(rs.getLong("doc_id"));
		ap.setFirstName(rs.getString("firstName"));
		ap.setLastName(rs.getString("lastName"));
		ap.setEmail(rs.getString("email"));
		ap.setDescription(rs.getString("description"));
		ap.setStatus(rs.getString("status"));
		ap.setDate(rs.getString("date"));
		ap.setTime(rs.getString("time"));
		ap.setSpecification(rs.getString("specification"));
		return ap;
	}

}
