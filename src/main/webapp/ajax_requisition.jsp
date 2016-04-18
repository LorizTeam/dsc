<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ page import="pcpnru.utilities.DBConnect" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONValue"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="pcpnru.projectData.Receive1DB"%>
<%
	String projectCode = request.getParameter("projectCode");
	String year = request.getParameter("year");
	String gcostcode = request.getParameter("gcostcode");
	String unit = request.getParameter("unit");
	String priceperunit = request.getParameter("priceperunit");
	String frombalance = request.getParameter("frombalance");
	String tobalance = request.getParameter("tobalance");
	String day = request.getParameter("day");
	String ajax_type = request.getParameter("ajax_type");
	String requisiton_type = request.getParameter("requisiton_type");
	String description = request.getParameter("description");
	String amount = request.getParameter("amount");
	
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	Calendar cal = Calendar.getInstance();
    String timeofday = dateFormat.format(cal.getTime());
    
    DateFormat yearFormat = new SimpleDateFormat("yyyy");
    String thisyear = yearFormat.format(cal.getTime());
    thisyear = String.valueOf(Integer.parseInt(thisyear)+543);
    
    day +=" "+timeofday; 
    
	DBConnect dbcon = new DBConnect();
	ResultSet rs = null; 
	List listJson = new LinkedList();
	
	if(ajax_type.equals("select")){
		String sql = "";
		if(request.getParameter("gcostcode")!=null){
			sql = "SELECT b.gcostcode, b.gcostcode_name "+
					 "from projectplan_detail a INNER JOIN groupcostcode_master b ON (b.gcostcode = a.gcostcode and a.project_code = b.project_code)  "+
		 			 "WHERE a.project_code = '"+projectCode+"' and year = '"+year+"' and b.gcostcode not in ('"+gcostcode+"') and type_gcostcode = '2' order by b.gcostcode";
		}else{
			sql = "SELECT b.gcostcode, b.gcostcode_name "+
					 "from projectplan_detail a INNER JOIN groupcostcode_master b ON (b.gcostcode = a.gcostcode and a.project_code = b.project_code)  "+
		 			 "WHERE a.project_code = '"+projectCode+"' and year = '"+year+"' and type_gcostcode = '2' order by b.gcostcode";
		}
		
		 
		Connection conn = dbcon.getConnectMYSql();
		Statement pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sql); 
		while(rs.next()){
			JSONObject obj=new JSONObject();
				 
	   		obj.put("gcostcode",rs.getString("gcostcode"));
	   		obj.put("gcostcode_name",rs.getString("gcostcode_name")); 
	   		
	   		listJson.add(obj); 
		}
		out.println(listJson);
		
		rs.close();
		pStmt.close(); 
		conn.close();
	}else if(ajax_type.equals("add")){
		
		Receive1DB receive1DB = new Receive1DB();
		String docno = request.getParameter("docno");
		
		
		if(docno.equals("")){
			
			docno = receive1DB.SelectUpdateDocNo(thisyear, "requisition",projectCode,year);
		}
		
		day = day.replace("/", "-");
		String[] splitdatetime = day.split(" ");
		
		String[] splitdate = splitdatetime[0].split("-");
		
		day = String.valueOf((Integer.parseInt(splitdate[2])-543))+"-"+splitdate[1]+"-"+splitdate[0]+" "+splitdatetime[1];
		String sql = "INSERT INTO `requisition` (`requisition_docno`,`project_code`, `project_year`, `docdate` "
				+ ", `takeby`, `requisition_type`, `gcostcode`, `description`, `priceperunit`, `unit`, `amount`, `frombalance`, `tobalance`) " 
				+ " VALUES ('"+docno+"','"+projectCode+"', '"+year+"', '"+day+"', 'aof', '"+requisiton_type+"' "
				+ "	, '"+gcostcode+"', '"+description+"', '"+priceperunit+"', '"+unit+"', '"+amount+"' " 
				+ " , '"+frombalance.trim()+"', '"+tobalance.trim()+"')";
		
		
		Connection conn = dbcon.getConnectMYSql();
		Statement pStmt = conn.createStatement();
		int rowsofupdate = pStmt.executeUpdate(sql);
		JSONObject obj=new JSONObject();
		 
   		obj.put("rowsofupdate",rowsofupdate);
   		obj.put("docno",docno); 
   		
   		out.println(obj);
		
		pStmt.close(); 
		conn.close();
		
		
	}else if(ajax_type.equals("delete")){
		
		String docno = request.getParameter("docno");
		
		String sql = "DELETE from requisition where requisition_docno = '"+docno+"' and gcostcode = '"+gcostcode+"' and project_code = '"+projectCode+"' and project_year = '"+year+"'";
		
		
		Connection conn = dbcon.getConnectMYSql();
		Statement pStmt = conn.createStatement();
		int rowsofupdate = pStmt.executeUpdate(sql);
		JSONObject obj=new JSONObject();
		 
   		obj.put("rowsofupdate",rowsofupdate);
   		
   		
   		out.println(obj);
		
		pStmt.close(); 
		conn.close();
		
	}else if(ajax_type.equals("selectlist")){
		
		String docno = request.getParameter("docno");
		
		String sql = "SELECT "
			+ "a.requisition_docno,g.project_name,a.project_year,a.docdate,"
			+ "a.takeby,c.requisition_typename,d.subjob_name,e.childsubjobname,"
			+ "f.gcostcode,f.gcostcode_name,a.description,a.priceperunit,a.unit,"
			+ "a.amount,a.frombalance,a.tobalance "
			+ "FROM "
			+ "requisition AS a "
			+ "INNER JOIN projectplan_detail AS b ON b.project_code = a.project_code AND b.`year` = a.project_year AND b.gcostcode = a.gcostcode "
			+ "INNER JOIN requisition_type AS c ON c.requisition_type = a.requisition_type "
			+ "INNER JOIN subjob_master AS d ON d.subjob_code = b.subjob_code "
			+ "INNER JOIN childsubjob_master AS e ON e.childsubjobcode = b.childsubjobcode "
			+ "INNER JOIN groupcostcode_master AS f ON f.gcostcode = b.gcostcode and a.project_code = f.project_code "
			+ "INNER JOIN project_master AS g ON g.project_code = b.project_code "
			+ "where  a.requisition_docno = '"+docno+"' and a.project_code = '"+projectCode+"' and a.project_year = '"+year+"' "
			+ "ORDER BY "
			+ "a.docdate DESC ";
		
		
		Connection conn = dbcon.getConnectMYSql();
		Statement pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sql); 
		while(rs.next()){
			JSONObject obj=new JSONObject();
				 
	   		obj.put("requisition_docno",rs.getString("requisition_docno"));
	   		obj.put("project_name",rs.getString("project_name"));
	   		obj.put("project_year",rs.getString("project_year"));
	   		obj.put("docdate",rs.getString("docdate"));
	   		obj.put("takeby",rs.getString("takeby"));
	   		obj.put("subjob_name",rs.getString("subjob_name"));
	   		obj.put("childsubjobname",rs.getString("childsubjobname"));
	   		obj.put("requisition_typename",rs.getString("requisition_typename"));
	   		obj.put("gcostcode",rs.getString("gcostcode"));
	   		obj.put("gcostcode_name",rs.getString("gcostcode_name"));
	   		obj.put("description",rs.getString("description"));
	   		obj.put("priceperunit",rs.getString("priceperunit"));
	   		obj.put("unit",rs.getString("unit"));
	   		obj.put("amount",rs.getString("amount"));
	   		obj.put("frombalance",rs.getString("frombalance"));
	   		obj.put("tobalance",rs.getString("tobalance"));
	   		
	   		listJson.add(obj); 
		}
		out.println(listJson);
		
		rs.close();
		pStmt.close(); 
		conn.close();
	}
	
	
	  		
%>
  
 