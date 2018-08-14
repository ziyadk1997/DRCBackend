package com.example.demo;

import java.io.Serializable;

public class Financial implements Serializable {
	public String username;
	public String date;
	public String amount;
	public String comment;
	public String signbit;
	public String Currency;
	public Financial(String u ,String d, String a , String c , String s, String Curr ) {
		username = u;
		date = d;
		amount =a;
		comment =c;
		signbit =s;
		Currency = Curr;
	}

}