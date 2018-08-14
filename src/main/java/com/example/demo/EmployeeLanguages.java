 package com.example.demo;

public class EmployeeLanguages {

	String Language;
	String Reading;
	String Writing;
	String Listening;
	String Speaking;
	String Jusification;
	
	public EmployeeLanguages(String Language, String Reading, String Writing, String Listening, String Speaking ,String Jusification)
	{
		this.Language=Language;
		this.Reading=Reading;
		this.Writing=Writing;
		this.Listening=Listening;
		this.Speaking=Speaking;
		this.Jusification=Jusification;
	}

	public EmployeeLanguages()
	{
		
	}
	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getReading() {
		return Reading;
	}

	public void setReading(String reading) {
		Reading = reading;
	}

	public String getWriting() {
		return Writing;
	}

	public void setWriting(String writing) {
		Writing = writing;
	}

	public String getListening() {
		return Listening;
	}

	public void setListening(String listening) {
		Listening = listening;
	}

	public String getSpeaking() {
		return Speaking;
	}

	public void setSpeaking(String speaking) {
		Speaking = speaking;
	}

	public String getJusification() {
		return Jusification;
	}

	public void setJusification(String jusification) {
		Jusification = jusification;
	}
}