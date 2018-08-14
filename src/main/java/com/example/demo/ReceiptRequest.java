package com.example.demo;

import java.io.Serializable;

public class ReceiptRequest extends Request implements Serializable{
public String amount;
	public ReceiptRequest(String d, String e, String r, String s, String i, String c, String a) {
		super(d,e,r,s,i,c,"Receipt","","");
	    amount = a;
	}

}
