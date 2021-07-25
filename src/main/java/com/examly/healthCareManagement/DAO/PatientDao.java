package com.examly.healthCareManagement.DAO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examly.healthCareManagement.Exceptions.InvAuthException;
import com.examly.healthCareManagement.model.Apointment;
import com.examly.healthCareManagement.model.Doctor;
import com.examly.healthCareManagement.model.User;
import com.examly.healthCareManagement.rowMapper.ApointmentRowMapper;
import com.examly.healthCareManagement.rowMapper.DoctorRowMapper;
import com.examly.healthCareManagement.rowMapper.UserRowMapper;

@Repository
public class PatientDao  {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public User login(String email, String password) throws InvAuthException{
		// TODO Auto-generated method stub
		String sql = "select * from HealthUsers where email = ? ";
		User u = null;
		try {
			u = jdbcTemplate.queryForObject(sql, new UserRowMapper(),email);
			if(u.getPassword().equals(password))
				return u;
			throw new InvAuthException("invalid Password");
		}
		catch(EmptyResultDataAccessException e) {
			throw new InvAuthException("invalid email");
		}
		
	}
	
	public User register(User user) {
		// TODO Auto-generated method stub
		String sql ="insert into HealthUsers (firstName,lastName,email,gender,password) values(?,?,?,?,?)";
		Object args[] = {user.getFirstName(), user.getLastName(),user.getEmail(),user.getGender(),user.getPassword()};
		jdbcTemplate.update(sql, args);
		return login( user.getEmail(),  user.getPassword());
	}

	public List<String> getSpecifications() {
		// TODO Auto-generated method stub
		String sql ="select specification from specifications";
		List<String> res = jdbcTemplate.queryForList(sql, String.class);
		return res;
	}

	public List<Doctor> getDoctorsBySpecification(String specification) {
		// TODO Auto-generated method stub
		String sql=null;
		List<Doctor> docs = null;
		if(specification.equals("*")) {
			sql = "select "
					+ "u.id, u.firstName, u.lastName, u.email, u.gender,specification, description, hospital "
					+ "from HealthUsers u join doc_specification "
					+ "on doc_id=u.id "
					+ "join specifications s "
					+ " on s.id=specification_id";
			docs = jdbcTemplate.query(sql, new DoctorRowMapper());
		}
		else {
		sql = "select "
				+ "u.id, u.firstName, u.lastName, u.email, u.gender,specification, description, hospital "
				+ "from HealthUsers u join doc_specification "
				+ "on doc_id=u.id "
				+ "join specifications s "
				+ " on s.id=specification_id "
				+ "where specification =?";
		docs = jdbcTemplate.query(sql, new DoctorRowMapper(),specification );
		}
		return docs;
	}

	public User getUserDetailsById(Long userId) {
		// TODO Auto-generated method stub
		String sql = "select * from HealthUsers where id = ?";
		return  jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
	}

	public List<String> getTimeSlots(Long doc_id, String date) {
		// TODO Auto-generated method stub
		String sql = "select time from apointments where doc_id = ? and date = ?";
		Object args[] = {doc_id,  date};
		List<String> timeSlots = new ArrayList<>();
		timeSlots = jdbcTemplate.queryForList(sql, String.class, args);
		return timeSlots;
	}

	public void scheduleApointment(Long userId, Long docId, String description, String date, String time) {
		// TODO Auto-generated method stub
		String sql = "insert into bookings (user_id, doc_id, description, date, time) "
				+ "values (?,?,?,?,?)";
		Object args[] = {userId, docId, description, date, time};
		jdbcTemplate.update(sql, args);
	}

	public List<Apointment> getMyApointments(Long userId) {
		// TODO Auto-generated method stub
		String sql ="select "
				+ "b.booking_id as doc_id, u.firstName, u.lastName, u.email, s.specification, b.description, b.status, b.date, b.time "
				+ "from bookings b join HealthUsers u "
				+ "on b.doc_id=u.id "
				+ "join doc_specification ds "
				+ "on ds.doc_id = b.doc_id "
				+ "join specifications s "
				+ "on s.id = ds.specification_id "
				+ "where b.user_id = ?";
		
		return jdbcTemplate.query(sql, new ApointmentRowMapper(), userId);
	}

	public void updateUser(String firstName, String lastName, String gender, String password, Long userId) {
		// TODO Auto-generated method stub
		String sql = "update HealthUsers set firstName = ?, lastName=?, gender=?, password=? where id=?";
		Object args[] = {firstName, lastName, gender, password, userId};
		jdbcTemplate.update(sql, args);
		
	}

	public List<Apointment> getDoctorsTodaysApointments(String docId, String date) {
		// TODO Auto-generated method stub
		String sql ="select "
				+ "b.booking_id as doc_id, u.firstName, u.lastName, u.email, s.specification, b.description, b.status, b.date, b.time "
				+ "from bookings b join HealthUsers u "
				+ "on b.user_id=u.id "
				+ "join doc_specification ds "
				+ "on ds.doc_id = b.doc_id "
				+ "join specifications s "
				+ "on s.id = ds.specification_id "
				+ "where b.doc_id = ? and b.status='Accepted' and b.date=?";
		Object args[]= {docId,date};
		return jdbcTemplate.query(sql, new ApointmentRowMapper(),args );
	}

	public List<Apointment> getDoctorsAcceptedApointments(String docId, String date) {
		String sql ="select "
				+ "b.doc_id, u.firstName, u.lastName, u.email, s.specification, b.description, b.status, b.date, b.time "
				+ "from bookings b join HealthUsers u "
				+ "on b.user_id=u.id "
				+ "join doc_specification ds "
				+ "on ds.doc_id = b.doc_id "
				+ "join specifications s "
				+ "on s.id = ds.specification_id "
				+ "where b.doc_id = ? and b.status='Accepted' and b.date>=?";
		Object args[]= {docId,date};
		return jdbcTemplate.query(sql, new ApointmentRowMapper(),args );
	}

	public List<Apointment> getDoctorsPendingApointments(String docId, String date) {
		String sql ="select "
				+ "b.booking_id as doc_id, u.firstName, u.lastName, u.email, s.specification, b.description, b.status, b.date, b.time "
				+ "from bookings b join HealthUsers u "
				+ "on b.user_id=u.id "
				+ "join doc_specification ds "
				+ "on ds.doc_id = b.doc_id "
				+ "join specifications s "
				+ "on s.id = ds.specification_id "
				+ "where b.doc_id = ? and b.status='Pending' and b.date>=?";
		Object args[]= {docId,date};
		return jdbcTemplate.query(sql, new ApointmentRowMapper(),args );
	}

	public void updateBooking(String booking_id, String status) {
		// TODO Auto-generated method stub
		String sql = "update bookings set status = ? where booking_id =?";
		Object args[]= {status, booking_id};
		jdbcTemplate.update(sql, args);
		
	}

	public void addReport(String booking_id, String report) {
		// TODO Auto-generated method stub
		String sql = "update bookings set report =?, status='Completed' where booking_id =?";
		Object args[]= {report, booking_id};
		jdbcTemplate.update(sql, args);
	}

	public String getReport(String booking_id) {
		// TODO Auto-generated method stub
		String sql = "select report from bookings where booking_id =?";
		return jdbcTemplate.queryForObject(sql, String.class, booking_id); 
	}
	
	public String getDocIdByEmail(String email) {
		String sql = "select id from HealthUsers where email = ?";
		return jdbcTemplate.queryForObject(sql, String.class, email); 
	}

	public void addDoctor(String firstName, String lastName, String gender, String email, String password,
			String specification, String hospital, String description) {
		// TODO Auto-generated method stub
		String sql = "insert into HealthUsers(firstName,lastName,gender, email, password, role) values(?,?,?,?,?,'doctor')";
		Object args[]= {firstName,lastName,gender, email, password};
		jdbcTemplate.update(sql, args);
		
		String docId = getDocIdByEmail( email);
		sql = "insert into doc_specification values(?,?,?,?)";
		Object param[] = {docId, specification, description, hospital};
		jdbcTemplate.update(sql, param);
		
	}
	
	
}
