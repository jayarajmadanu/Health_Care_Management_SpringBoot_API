package com.examly.healthCareManagement.servises;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.healthCareManagement.DAO.PatientDao;
import com.examly.healthCareManagement.Exceptions.InvAuthException;
import com.examly.healthCareManagement.model.Apointment;
import com.examly.healthCareManagement.model.Doctor;
import com.examly.healthCareManagement.model.User;

@Service
public class Servise {
	
	@Autowired
	PatientDao patientdao;

	public User login(String email, String password) throws InvAuthException{
		// TODO Auto-generated method stub
		return patientdao.login(email,password);
	}

	public User register(User user) {
		// TODO Auto-generated method stub
		return patientdao. register( user);
	}

	public List<String> getSpecifications() {
		// TODO Auto-generated method stub
		return patientdao.getSpecifications();
	}

	public List<Doctor> getDoctorsBySpecification(String specification) {
		// TODO Auto-generated method stub
		return patientdao.getDoctorsBySpecification( specification);
	}

	public User getUserDetailsById(Long userId) {
		// TODO Auto-generated method stub
		return patientdao.getUserDetailsById( userId);
	}

	public List<String> getTimeSlots(Long doc_id, String date) {
		// TODO Auto-generated method stub
		return patientdao.getTimeSlots( doc_id,  date);
	}

	public void scheduleApointment(Long userId, Long docId, String description, String date, String time) {
		// TODO Auto-generated method stub
		patientdao.scheduleApointment(userId, docId, description, date, time);
	}

	public List<Apointment> getMyApointments(Long userId) {
		// TODO Auto-generated method stub
		return patientdao.getMyApointments( userId);
	}

	public void updateUser(String firstName, String lastName, String gender, String password, Long userId) {
		// TODO Auto-generated method stub
		patientdao.updateUser(firstName, lastName, gender, password, userId);
	}

	public List<Apointment> getDoctorsTodaysApointments(String docId) {
		// TODO Auto-generated method stub
		 LocalDate dt = LocalDate.now();
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		 String fdt = dt.format(df);
		return patientdao.getDoctorsTodaysApointments( docId, fdt);
	}

	public List<Apointment> getDoctorsAcceptedApointments(String docId) {
		// TODO Auto-generated method stub
		LocalDate dt = LocalDate.now();
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		 String fdt = dt.format(df);
		return patientdao.getDoctorsAcceptedApointments( docId, fdt);
	}

	public List<Apointment> getDoctorsPendingApointments(String docId) {
		LocalDate dt = LocalDate.now();
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		 String fdt = dt.format(df);
		return patientdao.getDoctorsPendingApointments( docId, fdt);
	}

	public void updateBooking(String booking_id, String status) {
		// TODO Auto-generated method stub
		patientdao.updateBooking( booking_id,  status);
	}

	public void addReport(String booking_id, String report) {
		// TODO Auto-generated method stub
		patientdao.addReport(booking_id, report);
	}

	public String getReport(String booking_id) {
		// TODO Auto-generated method stub
		return patientdao.getReport( booking_id);
	}

	public void addDoctor(String firstName, String lastName, String gender, String email, String password,
			String specification, String hospital, String description) {
		// TODO Auto-generated method stub
		patientdao.addDoctor(firstName,lastName,gender, email, password, specification, hospital, description);
	}

}
