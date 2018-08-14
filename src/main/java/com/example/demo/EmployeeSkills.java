 package com.example.demo;

public class EmployeeSkills {
	// String Employeeusername;
	String Skill;
	String Profeciency;
	String Interest;
	String yearsOfExprince;

	public EmployeeSkills(String Skill, String Profeciency, String Interest,String yearsOfExprince) {
		// this.Employeeusername=Employeeusername;
		this.Skill = Skill;
		this.Profeciency = Profeciency;
		this.Interest = Interest;
		this.yearsOfExprince = yearsOfExprince;

	}

	public EmployeeSkills() {

	}

	public String getSkill() {
		return Skill;
	}

	public void setSkill(String skill) {
		Skill = skill;
	}

	public String getProfeciency() {
		return Profeciency;
	}

	public void setProfeciency(String profeciency) {
		Profeciency = profeciency;
	}

	public String getInterest() {
		return Interest;
	}

	public void setInterest(String interest) {
		Interest = interest;
	}

	public String getYearsOfExprince() {
		return yearsOfExprince;
	}

	public void setYearsOfExprince(String yearsOfExprince) {
		this.yearsOfExprince = yearsOfExprince;
	}

}
