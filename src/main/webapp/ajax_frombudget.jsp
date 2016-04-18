<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ page import="pcpnru.utilities.DBConnect" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONValue"%>
<%
	String projectCode = request.getParameter("projectCode");
	String year = request.getParameter("year");
	String gcostcode = request.getParameter("gcostcode");
	
	String[] pjS = projectCode.split(" - ");
	projectCode = pjS[0];
	
	DBConnect dbcon = new DBConnect();
	ResultSet rs = null; 
	
	String sql = "SELECT (IFNULL(SUM(a.budget),0)+IFNULL(rock.budget,0)-(IFNULL(gave_rock.gave_budget,0)+IFNULL(SUM(b.amount),0))) as frombalance "
			+ ",SUM(a.budget),rock.budget,gave_rock.gave_budget,SUM(b.amount) "
			+ "FROM projectplan_detail a "
			+ "LEFT JOIN requisition b on (a.gcostcode = b.gcostcode and a.project_code = b.project_code and a.year = b.project_year) " 
			+ "LEFT JOIN (SELECT IFNULL(SUM(c.amount_rock),0) as budget ,project_code,year,gcostcode FROM rocking_budget c where c.approve_status in ('AP','WA') GROUP BY c.gcostcode) " 
			+ "as rock on(a.gcostcode = rock.gcostcode and a.project_code = rock.project_code and a.year = rock.year) " 
			+ "LEFT JOIN (SELECT IFNULL(SUM(e.amount_rock),0) as gave_budget ,project_code,year,gcostcode_rock FROM rocking_budget e where e.approve_status in ('AP','WA') and e.gcostcode_rock = '"+gcostcode+"' GROUP BY e.gcostcode_rock) "
			+ "as gave_rock on(a.gcostcode = gave_rock.gcostcode_rock and a.project_code = gave_rock.project_code and a.year = gave_rock.year) "
			+ " where a.project_code = '"+projectCode+"' and a.`year` = '"+year+"' and a.gcostcode = '"+gcostcode+"' GROUP BY a.gcostcode";
	Connection conn = dbcon.getConnectMYSql();
	Statement pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sql); 
	while(rs.next()){
			 
		out.println(rs.getString("frombalance"));
    		
	}
	
	rs.close();
	pStmt.close(); 
	conn.close();
	  		
%>
  
 