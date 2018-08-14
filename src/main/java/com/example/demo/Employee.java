package com.example.demo;

import java.util.ArrayList;

public class Employee {
	String username ;
	String Name;
	int Userpassword;
	int NumberOfAnnualVacations;
	int NumberOfCasualVacations;
	String Gender ;
	String Email ;
	String Address;
	String Nationality;
	String SocialStatus; 
	int NumberOfDependencies;
	String EmployeeID;
	String University;
	String Degree;
	String FieldStudy;
	String CarrierLevel;
	String Interest;
	int YearsOfExperience;
	String JobTitle; 
	String AboveManagerusername;
	int Adminbit;
	int BasicSalary;
	int Allowance; 
	int Active;
	String Mobile;
	String MilitaryService;
	String DeviceId;
	String DeviceType;
	ArrayList <EmployeeSkills> Skills;
	ArrayList<EmployeeEducation> Education ;
	ArrayList<EmployeeLanguages> Languages ;
	
	
	public Employee(){
		
		this.Skills= new ArrayList <EmployeeSkills>();
		this.Education= new ArrayList<EmployeeEducation>();
		this.Languages= new ArrayList<EmployeeLanguages>();
	}
	
	
	
	
	public ArrayList<EmployeeSkills> getSkills() {
		return Skills;
	}




	public void setSkills(ArrayList<EmployeeSkills> skills) {
		Skills = skills;
	}




	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getUserpassword() {
		return Userpassword;
	}

	public void setUserpassword(int userpassword) {
		Userpassword = userpassword;
	}

	public int getNumberOfAnnualVacations() {
		return NumberOfAnnualVacations;
	}

	public void setNumberOfAnnualVacations(int numberOfAnnualVacations) {
		NumberOfAnnualVacations = numberOfAnnualVacations;
	}

	public int getNumberOfCasualVacations() {
		return NumberOfCasualVacations;
	}

	public void setNumberOfCasualVacations(int numberOfCasualVacations) {
		NumberOfCasualVacations = numberOfCasualVacations;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getNationality() {
		return Nationality;
	}

	public void setNationality(String nationality) {
		Nationality = nationality;
	}

	public String getSocialStatus() {
		return SocialStatus;
	}

	public void setSocialStatus(String socialStatus) {
		SocialStatus = socialStatus;
	}

	public int getNumberOfDependencies() {
		return NumberOfDependencies;
	}

	public void setNumberOfDependencies(int numberOfDependencies) {
		NumberOfDependencies = numberOfDependencies;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

	public String getUniversity() {
		return University;
	}

	public void setUniversity(String university) {
		University = university;
	}

	public String getDegree() {
		return Degree;
	}

	public void setDegree(String degree) {
		Degree = degree;
	}

	public String getFieldStudy() {
		return FieldStudy;
	}

	public void setFieldStudy(String fieldStudy) {
		FieldStudy = fieldStudy;
	}

	public String getCarrierLevel() {
		return CarrierLevel;
	}

	public void setCarrierLevel(String carrierLevel) {
		CarrierLevel = carrierLevel;
	}

	public String getInterest() {
		return Interest;
	}

	public void setInterest(String interest) {
		Interest = interest;
	}

	public int getYearsOfExperience() {
		return YearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		YearsOfExperience = yearsOfExperience;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getAboveManagerusername() {
		return AboveManagerusername;
	}

	public void setAboveManagerusername(String aboveManagerusername) {
		AboveManagerusername = aboveManagerusername;
	}

	public int getAdminbit() {
		return Adminbit;
	}

	public void setAdminbit(int adminbit) {
		Adminbit = adminbit;
	}

	public int getBasicSalary() {
		return BasicSalary;
	}

	public void setBasicSalary(int basicSalary) {
		BasicSalary = basicSalary;
	}

	public int getAllowance() {
		return Allowance;
	}

	public void setAllowance(int allowance) {
		Allowance = allowance;
	}

	public int getActive() {
		return Active;
	}

	public void setActive(int active) {
		Active = active;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getMilitaryService() {
		return MilitaryService;
	}

	public void setMilitaryService(String militaryService) {
		MilitaryService = militaryService;
	}

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public String getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(String deviceType) {
		DeviceType = deviceType;
	}




	public ArrayList<EmployeeEducation> getEducation() {
		return Education;
	}




	public void setEducation(ArrayList<EmployeeEducation> education) {
		Education = education;
	}




	public ArrayList<EmployeeLanguages> getLanguages() {
		return Languages;
	}




	public void setLanguages(ArrayList<EmployeeLanguages> languages) {
		Languages = languages;
	}


	
}