package com.examly.healthCareManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.healthCareManagement.Exceptions.InvAuthException;
import com.examly.healthCareManagement.model.Apointment;
import com.examly.healthCareManagement.model.Doctor;
import com.examly.healthCareManagement.model.User;
import com.examly.healthCareManagement.servises.Servise;

@RestController
@CrossOrigin
public class Controller {
	
	@Autowired
	Servise servise;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> map) {
		String email = map.get("email");
		String password = map.get("password");
		User u = null;
		Map<String, Object> data = new HashMap<>();
		try{
			u= servise.login(email, password);
			data.put("user",u);
			return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}catch(InvAuthException e) {
			
			data.put("err",e.msg);
			return new ResponseEntity<Map<String,Object>>(data, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody User user){
		System.out.println(user.getFirstName()+ user.getEmail());
		user = servise.register(user);
		Map<String, Object> data = new HashMap<>();
		data.put("user",user);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
	}
	
	@GetMapping("/getSpecifications")
	public ResponseEntity<Map<String, List<String>>> getSpecifications(){
		List<String> specifications = servise.getSpecifications();
		Map<String, List<String>> data = new HashMap<>();
		data.put("specifications",specifications);
		return new ResponseEntity<Map<String,List<String>>>(data, HttpStatus.OK);
		
	}
	
	@PostMapping("/getDoctors")
	public ResponseEntity<Map<String, List<Doctor>>> getDoctorsBySpecification(@RequestBody Map<String,String> map){
		String specification = map.get("specification");
		
		List<Doctor> docs = servise.getDoctorsBySpecification(specification);
		Map<String, List<Doctor>> data = new HashMap<>();
		data.put("doctors",docs);
		return new ResponseEntity<Map<String,List<Doctor>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/getuserById")
	public ResponseEntity< Map< String, User > > getUserDetailsById(@RequestBody Map<String,String> map){
		Long userId = Long.parseLong(map.get("userId"));
		User u = servise.getUserDetailsById(userId);
		Map<String, User> data = new HashMap<>();
		data.put("user",u);
		return new ResponseEntity<Map<String,User>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/getTimeSlots")
	public ResponseEntity<Map<String, List<String>>> getTimeSlots(@RequestBody Map<String,String> map){
		Long doc_id = Long.parseLong(map.get("doc_id"));
		String date = map.get("Booking_date");
		List<String> timeSlots = servise.getTimeSlots(doc_id, date);
		Map<String, List<String>> data = new HashMap<>();
		data.put("timeSlots",timeSlots);
		return new ResponseEntity<Map<String,List<String>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/scheduleApointment")
	@ResponseBody
	public String scheduleApointment(@RequestBody Map<String,String> map) {
		Long userId = Long.parseLong(map.get("userId"));
		Long docId = Long.parseLong(map.get("doc_id"));
		String description = map.get("description");
		String date = map.get("date");
		String time = map.get("time");
		servise.scheduleApointment(userId, docId, description, date, time);
		return "Apointment scheduled";
	}
	
	@PostMapping("/getMyApointments")
	public ResponseEntity<Map<String,List<Apointment>>> getMyApointments(@RequestBody Map<String,String> map){
		Long userId = Long.parseLong(map.get("userId"));
		List<Apointment> res = servise.getMyApointments(userId); 
		Map<String, List<Apointment>> data = new HashMap<>();
		data.put("apointments",res);
		return new ResponseEntity<Map<String,List<Apointment>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/updateUser")
	@ResponseBody
	public String updateUser(@RequestBody Map<String,String> map) {
		Long userId = Long.parseLong(map.get("userId"));
		String firstName = map.get("firstName");
		String lastName = map.get("lastName");
		String gender = map.get("gender");
		String password = map.get("password");
		servise.updateUser(firstName, lastName, gender, password,userId);
		return "user updated";
	}
	
	@PostMapping("/getDoctorsTodaysApointments")
	public ResponseEntity<Map<String,List<Apointment>>> getDoctorsTodaysApointments(@RequestBody Map<String,String> map){
		String docId = map.get("docId");
		List<Apointment> res = servise.getDoctorsTodaysApointments(docId);
		Map<String, List<Apointment>> data = new HashMap<>();
		data.put("apointments",res);
		return new ResponseEntity<Map<String,List<Apointment>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/getDoctorsAcceptedApointments")
	public  ResponseEntity<Map<String,List<Apointment>>> getDoctorsAcceptedApointments(@RequestBody Map<String,String> map){
		String docId = map.get("docId");
		List<Apointment> res = servise.getDoctorsAcceptedApointments(docId);
		Map<String, List<Apointment>> data = new HashMap<>();
		data.put("apointments",res);
		return new ResponseEntity<Map<String,List<Apointment>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/getDoctorsPendingApointments")
	public  ResponseEntity<Map<String,List<Apointment>>> getDoctorsPendingApointments(@RequestBody Map<String,String> map){
		String docId = map.get("docId");
		List<Apointment> res = servise.getDoctorsPendingApointments(docId);
		Map<String, List<Apointment>> data = new HashMap<>();
		data.put("apointments",res);
		return new ResponseEntity<Map<String,List<Apointment>>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/updateBooking")
	@ResponseBody
	public String updateBooking(@RequestBody Map<String,String> map) {
		String booking_id = map.get("bookingId");
		String status = map.get("status");
		servise.updateBooking(booking_id, status);
		return status;
	}
	
	@PostMapping("/addReport")
	@ResponseBody
	public String addReport(@RequestBody Map<String,String> map) {
		String booking_id = map.get("bookingId");
		String report = map.get("report");
		servise.addReport(booking_id, report);
		return "report added";
	}
	
	@PostMapping("/getReport")
	public ResponseEntity<Map<String,String >> getReport(@RequestBody Map<String,String> map){
		String booking_id = map.get("bookingId");
		String report = servise.getReport(booking_id);
		Map<String, String> data = new HashMap<>();
		data.put("report",report);
		return new ResponseEntity<Map<String,String>>(data, HttpStatus.OK);
	}
	
	@PostMapping("/addDoctor")
	public void addDoctor(@RequestBody Map<String,String> map) {
		String firstName = map.get("firstName"),
		        lastName=map.get("lastName"),
		        gender=map.get("gender"),
		        email=map.get("email"),
		        password=map.get("password"),
		        specification=map.get("specification"),
		        hospital=map.get("hospital"),
		        description=map.get("description");
		servise.addDoctor(firstName,lastName,gender, email, password, specification, hospital,description);
	}

}
