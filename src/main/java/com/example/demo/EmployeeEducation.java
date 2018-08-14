 package com.example.demo;

public class EmployeeEducation {
	
	String school ;
	String fieldOfStudy;
    String degree   ;
    String grade ;
    String activities;
    
    public EmployeeEducation() {
    	
    }

    public EmployeeEducation(String school ,String fieldOfStudy,String degree ,String grade ,String activities) {
    	this.school=school;
    	this.fieldOfStudy=fieldOfStudy;
    	this.degree=degree;
    	this.grade=grade;
    	this.activities=activities;
    	
    }

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}
}
