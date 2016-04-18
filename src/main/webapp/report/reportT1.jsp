<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import ="pcpnru.utilities.*" %>
<%@ page import="net.sf.jasperreports.engine.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page contentType="application/pdf" %>
<%

	Connection conn = null;
	Class.forName ("com.mysql.jdbc.Driver");
	//Class.forName ("org.gjt.mm.mysql.Driver");
	String dbName = "panasonic";
	String hostname = "localhost";
	String port = "3306";
	String dbUserName = "root";
	String dbPassword = "1234";
	
	String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
	port + "/" + dbName + "?useUnicode=yes&characterEncoding=UTF-8&user=" + dbUserName + "&password=" + dbPassword;
	
	conn = DriverManager.getConnection (jdbcUrl);
	
	String filejrxml = application.getRealPath("report/trainreport_1.jrxml");
	
	File reportFile = new File(filejrxml);
	if (!reportFile.exists()){
		System.out.println("File Not found");
	}else{
		System.out.println("File is found");
	}
	    	
	
	JasperReport jr= JasperCompileManager.compileReport(filejrxml);
	
	Map prm = new HashMap();
	prm.put("prmmatcode","kx");
	
	
	JasperPrint jasperPrint = JasperFillManager.fillReport(jr,prm, conn);
	
	OutputStream outStream = response.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	
 %>

