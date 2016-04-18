<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ page import="pcpnru.utilities.DBConnect" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONValue"%>
<%
	String costcode = request.getParameter("costcode");
	String standardprice = request.getParameter("standardprice");
	
	float float_standardprice = Float.parseFloat(standardprice);
	System.out.println(standardprice);
	DBConnect dbcon = new DBConnect();
	ResultSet rs = null; 
	JSONObject obj=new JSONObject();
	String sql = "SELECT "+
			"a.costcode, "+
			"a.costname, "+
			"a.percentprice, "+
			"a.datetime, "+
			"a.gcostcode "+
			"FROM "+
			"costcode_master AS a "+
			"where costcode = '"+costcode+"'";
	Connection conn = dbcon.getConnectMYSql();
	Statement pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sql); 
	double price_services=0;
	if(rs.next()){
		
			 
			
			double standardpercent = float_standardprice * Float.parseFloat(rs.getString("percentprice")) / 100;
			price_services = float_standardprice + standardpercent;
			obj.put("price_services",price_services); 
			
			
	}else{
		obj.put("price_services",0); 
	}
		out.println(obj);
	
	rs.close();
	pStmt.close(); 
	conn.close();
	  		
%>