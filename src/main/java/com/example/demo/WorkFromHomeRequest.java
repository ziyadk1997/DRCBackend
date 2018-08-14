package com.example.demo;

import java.io.Serializable;

public class WorkFromHomeRequest extends Request implements Serializable {
	public WorkFromHomeRequest ( String d , String e ,String r , String s,String i,String c,String f , String to) {

		super(d,e,r,s,i,c,"WorkFromHome",f,to);
		
	}
}
