package com.example.demo;

import java.io.Serializable;

public class AnnualorCasualRequest extends Request implements Serializable {
String Type;

public AnnualorCasualRequest (String t , String d , String e ,String r , String s,String i,String m,String c,String f , String to) {
	super(  d ,  e , r ,  s, i,c,t,f,to);
	name = m;
	Type = t;
}

}
