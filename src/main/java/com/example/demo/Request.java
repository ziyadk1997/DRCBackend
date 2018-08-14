package com.example.demo;

import java.io.Serializable;

public class Request implements Serializable{
	public String Date;
	public String Employeeusername;
	public String Reviewerusername;
	public String Status;
	public String Id;
	public String Comment;
	public String RequestType ;
	public String name ;
	public String FromDate;
	public String ToDate;
	public Request ( String d , String e ,String r , String s,String i,String c,String n,String f, String to) {

		Date = d;
		Reviewerusername = r;
		System.out.println(r);
		Employeeusername = e;
		Status = s;
		Id = i;
		Comment = c;
		name = n;
		FromDate = f;
		ToDate = to;
	}
	
	
}
