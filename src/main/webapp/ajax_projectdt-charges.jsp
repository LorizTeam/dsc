<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ page import="pcpnru.utilities.DBConnect" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONValue"%>
<%
	String subjobcode = request.getParameter("subjobcode");
	//String[] pjS = projectCode.split(" - ");
	//projectCode = pjS[0];

	DBConnect dbcon = new DBConnect();
	ResultSet rs = null; 
	List listJson = new LinkedList();
	
	String sql = "SELECT childsubjob_master.subjob_code, subjob_name, childsubjobcode, childsubjobname "+
				 "from childsubjob_master left join subjob_master on(subjob_master.subjob_code = childsubjob_master.subjob_code) "+
	 			 "WHERE childsubjob_master.subjob_code = '"+subjobcode+"' order by childsubjob_master.subjob_code";
	Connection conn = dbcon.getConnectMYSql();
	Statement pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sql); 
	while(rs.next()){
		JSONObject obj=new JSONObject();
			 
    		obj.put("childsubjobcode",rs.getString("childsubjobcode"));
    		obj.put("childsubjobname",rs.getString("childsubjobname")); 
    		
    		listJson.add(obj); 
	}
	out.println(listJson);
	
	rs.close();
	pStmt.close(); 
	conn.close();
	  		
%>
  
 