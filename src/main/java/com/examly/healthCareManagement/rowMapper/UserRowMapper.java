package com.examly.healthCareManagement.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.examly.healthCareManagement.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		User u = new User();
		u.setId(rs.getLong("id"));
		u.setFirstName(rs.getString("firstName"));
		u.setLastName(rs.getString("lastName"));
		u.setEmail(rs.getString("email"));
		u.setGender(rs.getString("gender"));
		u.setRole(rs.getString("role"));
		u.setPassword(rs.getString("password"));
		return u;
	}

}
