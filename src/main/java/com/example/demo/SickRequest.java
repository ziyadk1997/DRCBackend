package com.example.demo;

import java.io.Serializable;

public class SickRequest extends Request implements Serializable {
	public String MedicalUrl;
	public String Type;
	public SickRequest (String t , String d , String e ,String r , String s,String i,String m ,String f , String to) {
		super(   d ,  e , r,  s, i,"No Comment Available","Sick",f,to);
		MedicalUrl = m;
		Type = t;

	}
	
}
