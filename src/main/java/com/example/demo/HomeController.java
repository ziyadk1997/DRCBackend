package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.jdbc.CallableStatement;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
public class HomeController {
	public String DatabaseConnection = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7251677?useSSL=false";
	public String DatabaseName = "sql7251677";
	public String DatabasePassword = "JYRyRhpVDj";

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/login")
	public ResponseEntity login(@RequestBody String userStr) {
		String username;
		String password;
		try {

			JSONObject user = new JSONObject(userStr);
			username = user.getString("username");
			password = user.get("password").toString();

			try {
				if (username != null && password != null) {
					Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
					Statement myStmt = myConn.createStatement();

					ResultSet owner = myStmt.executeQuery("select * from Owner ");

					while (owner.next()) {
//System.out.print(owner.getString("Email"));
						if (owner.getString("Email").equalsIgnoreCase(username)
								|| owner.getString("username").equalsIgnoreCase(username)
										&& owner.getString("Userpassword").equals(password))
							return new ResponseEntity(true, HttpStatus.OK);
					}

					ResultSet employee = myStmt.executeQuery("select * from Employee");
					while (employee.next()) {

						if (employee.getString("Email").equalsIgnoreCase(username)
								|| employee.getString("username").equalsIgnoreCase(username)
										&& employee.getString("Userpassword").equals(password))
							return new ResponseEntity(true, HttpStatus.OK);
					}
					myConn.close();
				}

			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApplyAnnualandCasualLeaveRequest")
	public ResponseEntity ApplyAnnualandCasualLeaveRequest(@RequestBody String requestStr) {
		String username;
		String from;
		String to;
		String comment;
		String timeinday;
		String type;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("username");
			from = request.getString("from");
			to = request.getString("to");
			comment = request.getString("comment");
			timeinday = request.getString("timeinday");
			type = request.getString("type");

			try {
				String query = "CALL ApplyCasualLeaveRequest(" + "\"" + username + "\"" + "," + "\"" + comment + "\""
						+ "," + "\"" + from + "\"" + "," + "\"" + to + "\"" + "," + "\"" + timeinday + "\"" + "," + "\""
						+ type + "\"" + ")  ";
				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyCasualRequests/{username}")
	public ResponseEntity ViewMyCasualRequests(@PathVariable String username) {

		try {
			String query = "{result = CALL ViewmyPreviousAnnualRequests(" + "\"" + username + "\"" + ")}";
			String Type;
			String Date;
			String Reviewerusername;
			String Status;
			String Employeeusername;
			String Id;
			String FromDate;
			String ToDate;
			String annualorcasual;
			String comment;

			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery(
					"select * from Request R inner join LeaveRequest L where L.Id = R.Id and R.Employeeusername = "
							+ "\"" + username + "\"" + "and L.CasualorAnnual =" + "\"" + "Casual" + "\"");
			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				Type = r.getString("Type");
				Date = r.getString("Date");
				FromDate = r.getString("FromDate");
				ToDate = r.getString("ToDate");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				comment = r.getString("Comment");
				annualorcasual = r.getString("CasualorAnnual");
				Status = r.getString("Status");

				Id = r.getString("Id");
				Requests.add(new AnnualorCasualRequest(Type, Date, Reviewerusername, Employeeusername, Status, Id,
						annualorcasual, comment, FromDate, ToDate));
			}
			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyAnnualRequests/{username}")
	public ResponseEntity ViewMyAnnualRequests(@PathVariable String username) {

		try {
			String query = "{result = CALL ViewmyPreviousAnnualRequests(" + "\"" + username + "\"" + ")}";
			String Type;
			String Date;
			String Reviewerusername;
			String Status;
			String Employeeusername;
			String Id;
			String FromDate;
			String ToDate;
			String comment;
			String annualorcasual;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery(
					"select * from Request R inner join LeaveRequest L where L.Id = R.Id and R.Employeeusername = "
							+ "\"" + username + "\"" + "and L.CasualorAnnual =" + "\"" + "Annual" + "\"");

			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				Type = r.getString("Type");
				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				comment = r.getString("Comment");
				annualorcasual = r.getString("CasualorAnnual");
				Status = r.getString("Status");
				FromDate = r.getString("FromDate");
				ToDate = r.getString("ToDate");
				Id = r.getString("Id");
				Requests.add(new AnnualorCasualRequest(Type, Date, Reviewerusername, Employeeusername, Status, Id,
						annualorcasual, comment, FromDate, ToDate));
			}
			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMySickRequests/{username}")
	public ResponseEntity ViewMySickRequests(@PathVariable String username) {

		try {
			String query = "{result = CALL ViewmyPreviousAnnualRequests(" + "\"" + username + "\"" + ")}";
			String timeinday;
			String Date;
			String Reviewerusername;
			String Status;
			String Employeeusername;
			String Id;
			String FromDate;
			String ToDate;
			String MedicalUrl;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery(
					"select * from Request R inner join SickRequest L where L.Id = R.Id and R.Employeeusername = "
							+ "\"" + username + "\"");

			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				timeinday = r.getString("timeinday");
				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				Status = r.getString("Status");
				Id = r.getString("Id");
				FromDate = r.getString("FromDate");
				ToDate = r.getString("ToDate");
				MedicalUrl = r.getString("MedicalReportURL");
				Requests.add(new SickRequest(timeinday, Date, Employeeusername, Reviewerusername, Status, Id,
						MedicalUrl, FromDate, ToDate));
			}

			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyWorkFromHomeRequests/{username}")
	public ResponseEntity ViewMyWorkFromHomeRequests(@PathVariable String username) {

		try {
			String query = "{result = CALL ViewmyPreviousAnnualRequests(" + "\"" + username + "\"" + ")}";
			String Type;
			String Date;
			String Employeeusername;
			String Id;
			String Reviewerusername;
			String Status;
			String comment;
			String FromDate;
			String ToDate;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery(
					"select * from Request R inner join WorkFromHomeRequest W where W.Id = R.Id and R.Employeeusername = "
							+ "\"" + username + "\"");
			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {

				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				Status = r.getString("Status");
				Id = r.getString("Id");
				FromDate = r.getString("startdate");
				ToDate = r.getString("enddate");
				comment = r.getString("Comment");
				Requests.add(new WorkFromHomeRequest(Date, Employeeusername, Reviewerusername, Status, Id, comment,
						FromDate, ToDate));
			}
			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApplySickRequest")
	public ResponseEntity ApplySickRequest(@RequestBody String requestStr) {
		String username;
		String startdate;
		String enddate;
		String comment;
		String timeinday;
		String link;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("username");
			startdate = request.getString("Startdate");
			enddate = request.getString("Enddate");
			comment = request.getString("Comment");
			timeinday = request.getString("timeinday");
			link = request.getString("Link");
			try {
				String query = "{ CALL ApplyForSickRequest(" + "\"" + username + "\"" + "," + "\"" + startdate + "\""
						+ "," + "\"" + enddate + "\"" + "," + "\"" + link + "\"" + "," + "\"" + comment + "\"" + ","
						+ "\"" + timeinday + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApplyWorkFromHomeRequest")
	public ResponseEntity ApplyWorkFromHomeRequest(@RequestBody String requestStr) {
		String username;
		String startdate;
		String enddate;
		String comment;
		String timeinday;
		String link;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("username");
			startdate = request.getString("Startdate");
			enddate = request.getString("Enddate");
			comment = request.getString("Comment");

			try {
				String query = "{ CALL ApplyWorkFromHomeRequest(" + "\"" + username + "\"" + "," + "\"" + startdate
						+ "\"" + "," + "\"" + enddate + "\"" + "," + "\"" + comment + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApplyReciptClaimRequest")
	public ResponseEntity ApplyReciptClaimRequest(@RequestBody String requestStr) {
		String username;
		String startdate;
		String Amount;
		String comment;
		String link;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("username");
			comment = request.getString("Comment");
			Amount = request.getString("Amount");
			startdate = request.getString("DueDate");
			link = request.getString("ReciptLink");
			try {

				String query = "{ CALL ReciptClaimRequest(" + "\"" + username + "\"" + "," + "\"" + Amount + "\"" + ","
						+ "\"" + startdate + "\"" + "," + "\"" + comment + "\"" + "," + "\"" + link + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyReviewedRequests/{username}")
	public ResponseEntity ViewMyReviewedRequests(@PathVariable String username) {
		try {

			String Type;
			String Date;
			String Employeeusername;
			String Status;
			String Id;
			String Reviewerusername;
			String comment;
			String annualorcasual;
			String medicalurl;

			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt
					.executeQuery("select * from  Request  R   where   R.Reviewerusername = " + "\"" + username + "\"");
			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				Status = r.getString("Status");
				Id = r.getString("Id");
				comment = r.getString("Comment");

				Requests.add(new Request(Date, Employeeusername, Reviewerusername, Status, Id, comment, "", "", ""));
			}

			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewRequestbyId/{requestid}")
	public ResponseEntity ViewRequestbyId(@PathVariable String requestid) {
		try {
			String query = "{result = CALL ViewmyPreviousAnnualRequests(" + "\"" + requestid + "\"" + ")}";
			String Type;
			String Date;
			String Employeeusername;
			String Status;
			String Id;
			String Reviewerusername;
			String comment;
			String annualorcasual;
			String medicalurl;
			String FromDate;
			String ToDate;
			String amount;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt
					.executeQuery("select * from LeaveRequest L inner join Request  R   where  R.Id = L.Id and R.Id = "
							+ "\"" + requestid + "\"");
			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				Status = r.getString("Status");
				Id = r.getString("Id");
				comment = r.getString("Comment");
				Type = r.getString("Type");
				FromDate = r.getString("FromDate");
				ToDate = r.getString("ToDate");
				annualorcasual = r.getString("CasualorAnnual");
				Requests.add(new AnnualorCasualRequest(Type, Date, Employeeusername, Reviewerusername, Status, Id,
						annualorcasual, comment, FromDate, ToDate));
			}
			ResultSet sick = myStmt
					.executeQuery("select * from SickRequest L inner join Request R   where  R.Id = L.Id and R.Id = "
							+ "\"" + requestid + "\"");
			while (sick.next()) {
				Date = sick.getString("Date");
				Employeeusername = sick.getString("Employeeusername");
				Reviewerusername = sick.getString("Reviewerusername");
				Status = sick.getString("Status");
				Id = sick.getString("Id");
				comment = sick.getString("Comment");
				Type = sick.getString("timeinday");
				medicalurl = sick.getString("MedicalReportURL");
				FromDate = sick.getString("FromDate");
				ToDate = sick.getString("ToDate");
				Requests.add(new SickRequest(Type, Date, Employeeusername, Reviewerusername, Status, Id, medicalurl,
						FromDate, ToDate));
			}

			ResultSet workfromhome = myStmt.executeQuery(
					"select * from WorkFromHomeRequest  L inner join Request R   where  R.Id = L.Id and  R.Id = " + "\""
							+ requestid + "\"");
			while (workfromhome.next()) {
				Date = workfromhome.getString("Date");
				Employeeusername = workfromhome.getString("Employeeusername");
				Reviewerusername = workfromhome.getString("Reviewerusername");
				Status = workfromhome.getString("Status");
				Id = workfromhome.getString("Id");
				comment = workfromhome.getString("Comment");
				FromDate = workfromhome.getString("startdate");
				ToDate = workfromhome.getString("enddate");
				WorkFromHomeRequest x = new WorkFromHomeRequest(Date, Employeeusername, Reviewerusername, Status, Id,
						comment, FromDate, ToDate);
				Requests.add(x);

			}
			ResultSet recipt = myStmt.executeQuery(
					"select * from ReciptRequest  L inner join Request R   where  R.Id = L.Id and  R.Id = " + "\""
							+ requestid + "\"");
			while (recipt.next()) {
				Date = recipt.getString("Date");
				Employeeusername = recipt.getString("Employeeusername");
				Reviewerusername = recipt.getString("Reviewerusername");
				Status = recipt.getString("Status");
				Id = recipt.getString("Id");
				comment = recipt.getString("Comment");
				amount = recipt.getString("amount");
				ReceiptRequest x = new ReceiptRequest(Date, Employeeusername, Reviewerusername, Status, Id, comment,
						amount);
				Requests.add(x);

			}
			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApproveRequest")
	public ResponseEntity ApproveRequest(@RequestBody String requestStr) {
		String username;
		String requestid;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("reviewerusername");
			requestid = request.getString("requestid");

			try {
				String query = "{ CALL ApproveRequest(" + "\"" + username + "\"" + "," + "\"" + requestid + "\""
						+ ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/RejectRequest")
	public ResponseEntity RejectRequest(@RequestBody String requestStr) {
		String username;
		String requestid;
		String comment;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("reviewerusername");
			requestid = request.getString("requestid");
			comment = request.getString("comment");

			try {
				String query = "{ CALL RejectRequest(" + "\"" + username + "\"" + "," + "\"" + requestid + "\"" + ","
						+ "\"" + comment + "\"" + ")} ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*") // 3ashan accept el request
	@PostMapping(value = "/IsOwner")
	public ResponseEntity IsOwner(@RequestBody String userstr) {
		String username;
		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");
			try {

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
				Statement myStmt = myConn.createStatement();

				ResultSet employee = myStmt.executeQuery("select * from Owner");
				while (employee.next()) {

					if (employee.getString("username").equalsIgnoreCase(username)) {
						myConn.close();
						return new ResponseEntity(true, HttpStatus.OK);
					}
				}
				myConn.close();
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*") // 3ashan accept el request
	@PostMapping(value = "/IsManager")
	public ResponseEntity IsManager(@RequestBody String userstr) {
		String username;

		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");
			try {

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
				Statement myStmt = myConn.createStatement();

				ResultSet Manager = myStmt.executeQuery("select * from Manager");
				while (Manager.next()) {
					if (Manager.getString("username").equalsIgnoreCase(username)) {
						myConn.close();
						return new ResponseEntity(true, HttpStatus.OK);
					}
				}
				myConn.close();
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*") // 3ashan accept el request
	@PostMapping(value = "/IsAdmin")
	public ResponseEntity IsAdmin(@RequestBody String userstr) {
		String username;
		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");
			try {

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
				Statement myStmt = myConn.createStatement();

				ResultSet employee = myStmt.executeQuery("select * from Employee");
				while (employee.next()) {

					if (employee.getString("username").equalsIgnoreCase(username)
							&& employee.getString("Adminbit").equals("1")) {
						myConn.close();
						return new ResponseEntity(true, HttpStatus.OK);
					}
				}
				myConn.close();

			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/IsHr") // 3ashan ye add users
	public ResponseEntity IsHr(@RequestBody String userstr) {
		String username;

		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");

			try {
				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
				Statement myStmt = myConn.createStatement();
				ResultSet HREmployee = myStmt.executeQuery("select * from HR");
				while (HREmployee.next()) {

					if (HREmployee.getString("username").equalsIgnoreCase(username)) {
						myConn.close();
						return new ResponseEntity(true, HttpStatus.OK);
					}
				}
				ResultSet ManagerEmployee = myStmt.executeQuery("select * from Manager H where H.isHr =1");
				while (ManagerEmployee.next()) {

					if (ManagerEmployee.getString("username").equalsIgnoreCase(username)) {
						myConn.close();
						return new ResponseEntity(true, HttpStatus.OK);
					}
				}
				myConn.close();
				return new ResponseEntity(false, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ViewMyInfo")
	public ResponseEntity ViewMyInfo(@RequestBody String userStr) throws JSONException {
		String username;
		String password;

		JSONObject user = new JSONObject(userStr);
		username = user.getString("username");

		username.replace("@three2one.net", "");
		// password= user.get("password").toString();

		try {
			username = user.getString("username");

			String newusername = username.replaceAll("@three2one.net", "").toString();
			if (username != null) {
				String query = "Select * from Employee  E where E.username ='" + newusername + "'";

				String query2 = "Select * from EmployeeSkills  ES where ES.Employeeusername ='" + newusername + "'";

				String query3 = "Select * from EmployeeEducation  ES where ES.Employeeusername ='" + newusername + "'";

				String query4 = "Select * from EmployeeLanguage  ES where ES.Employeeusername ='" + newusername + "'";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs;
				CallableStatement cs2;

				Statement myStmt = myConn.createStatement();
				Statement myStmt2 = myConn.createStatement();
				Statement myStmt3 = myConn.createStatement();
				Statement myStmt4 = myConn.createStatement();

				ResultSet info = myStmt.executeQuery(query);
				ResultSet skills = myStmt2.executeQuery(query2);
				ResultSet Education = myStmt3.executeQuery(query3);
				ResultSet Languages = myStmt4.executeQuery(query4);

				Employee e = new Employee();

				while (info.next()) {

					e.setUsername(info.getString("username"));
					e.setName(info.getString("Name"));
					e.setUserpassword(info.getInt("Userpassword"));
					e.setNumberOfAnnualVacations(info.getInt("NumberOfAnnualVacations"));
					e.setNumberOfCasualVacations(info.getInt("NumberOfCasualVacations"));
					e.setGender(info.getString("Gender"));
					e.setEmail(info.getString("Email"));
					e.setAddress(info.getString("Address"));
					e.setNationality(info.getString("Nationality"));
					e.setSocialStatus(info.getString("SocialStatus"));
					e.setNumberOfDependencies(info.getInt("NumberOfDependencies"));
					e.setEmployeeID(info.getString("EmployeeID"));
					e.setUniversity(info.getString("University"));
					e.setDegree(info.getString("Degree"));
					e.setFieldStudy(info.getString("FieldStudy"));
					e.setCarrierLevel(info.getString("CarrierLevel"));
					e.setInterest(info.getString("Interest"));
					e.setYearsOfExperience(info.getInt("YearsOfExperience"));
					e.setJobTitle(info.getString("JobTitle"));
					e.setAboveManagerusername(info.getString("AboveManagerusername"));
					e.setAdminbit(info.getInt("Adminbit"));
					e.setBasicSalary(info.getInt("BasicSalary"));
					e.setAllowance(info.getInt("Allowance"));
					e.setActive(info.getInt("Active"));
					e.setMobile(info.getString("Mobile"));
					e.setMilitaryService(info.getString("MilitaryService"));

					break;

				}

				while (skills.next()) {

					e.getSkills().add(new EmployeeSkills(skills.getString("Skill"), skills.getString("Profeciency"),
							skills.getString("Interest"), skills.getString("yearsOfExperince")));
				}

				while (Education.next()) {
					e.getEducation()
							.add(new EmployeeEducation(Education.getString("school"),
									Education.getString("fieldOfStudy"), Education.getString("degree"),
									Education.getString("grade"), Education.getString("activities")));
				}

				while (Languages.next()) {
					e.getLanguages()
							.add(new EmployeeLanguages(Languages.getString("Language"), Languages.getString("Reading"),
									Languages.getString("Writing"), Languages.getString("Listening"),
									Languages.getString("Speaking"), Languages.getString("Jusification")));
				}
				myConn.close();
				return new ResponseEntity(e, HttpStatus.OK);

			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyTransactions/{username}")
	public ResponseEntity ViewMyTransactions(@PathVariable String username) {

		try {
			String user;
			String date;
			String amount;
			String comment;
			String signbit;
			String currency;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt
					.executeQuery("select * from financialbalance F where F.username = " + "\"" + username + "\"");
			ArrayList<Financial> Financials = new ArrayList<Financial>();
			while (r.next()) {

				user = r.getString("username");
				date = r.getString("date");
				amount = r.getString("Amount");
				comment = r.getString("comment");
				signbit = r.getString("signbit");
				currency = r.getString("Currency");

				Financials.add(new Financial(user, date, amount, comment, signbit, currency));
			}
			myConn.close();
			return new ResponseEntity(Financials, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/MakeAdmin")
	public ResponseEntity MakeAdmin(@RequestBody String userstr) {
		String username;

		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");

			try {

				String query = "{ CALL Addadminbit(" + "\"" + username + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/RemoveAdmin")
	public ResponseEntity RemoveAdmin(@RequestBody String userstr) {
		String username;

		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");

			try {

				String query = "{ CALL RemoveAdminbit(" + "\"" + username + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/RemoveUser")
	public ResponseEntity RemoveUser(@RequestBody String userstr) {
		String username;

		try {

			JSONObject request = new JSONObject(userstr);
			username = request.getString("username");

			try {

				String query = "{ CALL DeleteEmployees(" + "\"" + username + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/ApplyFundRequest")
	public ResponseEntity ApplyFundRequest(@RequestBody String requestStr) {
		String username;
		String Amount;
		String Duedate;
		String Comment;
		try {

			JSONObject request = new JSONObject(requestStr);
			username = request.getString("username");
			Amount = request.getString("Amount");
			Duedate = request.getString("Duedate");
			Comment = request.getString("comment");

			try {
				String query = "{ CALL ApplyFundRequest(" + "\"" + username + "\"" + "," + "\"" + Amount + "\"" + ","
						+ "\"" + Duedate + "\"" + "," + "\"" + Comment + "\"" + ") } ";

				Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);

				CallableStatement cs = (CallableStatement) myConn.prepareCall(query);
				// cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.execute();
				myConn.close();
				return new ResponseEntity(true, HttpStatus.OK);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(false, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyExpenses/{username}")
	public ResponseEntity ViewMyExpenses(@PathVariable String username) {

		try {

			String Date;
			String Reviewerusername;
			String Status;
			String Employeeusername;
			String Id;
			String comment;
			String amount;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery(
					"select * from Request R inner join reciptrequest L where L.Id = R.Id and R.Employeeusername = "
							+ "\"" + username + "\"");

			ArrayList<Request> Requests = new ArrayList<Request>();
			while (r.next()) {
				Date = r.getString("Date");
				Employeeusername = r.getString("Employeeusername");
				Reviewerusername = r.getString("Reviewerusername");
				comment = r.getString("Comment");
				Status = r.getString("Status");
				Id = r.getString("Id");
				amount = r.getString("Amount");

				Requests.add(new ReceiptRequest(Date, Reviewerusername, Employeeusername, Status, Id, comment, amount));
			}
			myConn.close();
			return new ResponseEntity(Requests, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/GetAllEmployees")
	public ResponseEntity GetAllEmployees() {
		try {
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery("select * from Employee");
			ArrayList<Employee> Employees = new ArrayList<Employee>();
			while (r.next()) {
				Employee e = new Employee();
				e.setUsername(r.getString("username"));
				e.setName(r.getString("Name"));
				e.setUserpassword(r.getInt("Userpassword"));
				e.setNumberOfAnnualVacations(r.getInt("NumberOfAnnualVacations"));
				e.setNumberOfCasualVacations(r.getInt("NumberOfCasualVacations"));
				e.setGender(r.getString("Gender"));
				e.setEmail(r.getString("Email"));
				e.setAddress(r.getString("Address"));
				e.setNationality(r.getString("Nationality"));
				e.setSocialStatus(r.getString("SocialStatus"));
				e.setNumberOfDependencies(r.getInt("NumberOfDependencies"));
				e.setEmployeeID(r.getString("EmployeeID"));
				e.setUniversity(r.getString("University"));
				e.setDegree(r.getString("Degree"));
				e.setFieldStudy(r.getString("FieldStudy"));
				e.setCarrierLevel(r.getString("CarrierLevel"));
				e.setInterest(r.getString("Interest"));
				e.setYearsOfExperience(r.getInt("YearsOfExperience"));
				e.setJobTitle(r.getString("JobTitle"));
				e.setAboveManagerusername(r.getString("AboveManagerusername"));
				e.setAdminbit(r.getInt("Adminbit"));
				e.setBasicSalary(r.getInt("BasicSalary"));
				e.setAllowance(r.getInt("Allowance"));
				e.setActive(r.getInt("Active"));
				e.setMobile(r.getString("Mobile"));
				e.setMilitaryService(r.getString("MilitaryService"));
				Employees.add(e);
			}
			myConn.close();
			return new ResponseEntity(Employees, HttpStatus.OK);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new ResponseEntity(null, HttpStatus.OK);

	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMySalary/{username}")
	public ResponseEntity ViewMySalary(@PathVariable String username) {

		try {
			String BasicSalary = null;

			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet Employee = myStmt.executeQuery(
					"select  E.BasicSalary from Employee E  where E.username = " + "\"" + username + "\"");

			while (Employee.next()) {
				BasicSalary = Employee.getString("BasicSalary");
			}
//            if(BasicSalary==null) {
//            	
//    			ResultSet owner = myStmt.executeQuery(
//    							"select  E.BasicSalary from Owner E  where E.username = "
//    									+ "\"" + username + "\"" );
//
//                while(Employee.next()){
//                	BasicSalary = Employee.getString("BasicSalary");
//                }
//            }
//            	
			myConn.close();
			return new ResponseEntity(BasicSalary, HttpStatus.OK);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping("/ViewMyVacations/{username}")
	public ResponseEntity ViewMyVacations(@PathVariable String username) {

		try {

			int Annual;
			int Casual;
			int Sick;
			int total;
			Connection myConn = DriverManager.getConnection(DatabaseConnection, DatabaseName, DatabasePassword);
			Statement myStmt = myConn.createStatement();
			ResultSet r = myStmt.executeQuery("select * from employee E where  E.username= " + "\"" + username + "\"");
			int[] Days = new int[4];
			while (r.next()) {
				Annual = r.getInt("NumberOfAnnualVacations");
				Days[0] = Annual;
				Casual = r.getInt("NumberOfCasualVacations");
				Days[1] = Casual;
				Sick = r.getInt("NumberOfSickVacations");
				Days[2] = Sick;
				total = r.getInt("TotalNumberOfVacations");
				Days[3] = total;
				myConn.close();
				return new ResponseEntity(Days, HttpStatus.OK);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return new ResponseEntity(null, HttpStatus.OK);
	}
}