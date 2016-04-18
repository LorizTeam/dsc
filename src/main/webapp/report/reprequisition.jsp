<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.sf.jasperreports.engine.*" %> 
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="pcpnru.utilities.DBConnect"%>
<%@page import="pcpnru.projectData.ThaiBaht"%>
<%@page import="pcpnru.projectData.RequisitionDB"%> 
<%@ page contentType="application/pdf" %>
<%
	DBConnect dbcon = new DBConnect();
	Connection conn = dbcon.getConnectMYSql();
	String filejrxml = application.getRealPath("report/requisition.jrxml");
	
	String requisition_docno = request.getParameter("requisition_docno");
	String project_code = request.getParameter("project_code");
	String project_year = request.getParameter("project_year");
	
	RequisitionDB requisitiondb = new RequisitionDB();
	String sumamount = requisitiondb.Sumamount(requisition_docno, project_code, project_year);
	
	ThaiBaht classthaibaht = new ThaiBaht();
	String thaibaht = classthaibaht.getText(sumamount);
	System.out.println(thaibaht);
	File reportFile = new File(filejrxml);
	
	if (!reportFile.exists()){
		System.out.println("File Not found");
	}else{
		System.out.println("File is found");
	}
	    	
	
	JasperReport jr= JasperCompileManager.compileReport(filejrxml);
	
	Map prm = new HashMap();
	prm.put("requisition_docno", requisition_docno.trim());
	prm.put("project_code", project_code);
	prm.put("project_year", project_year);
	prm.put("prmtext", thaibaht);
	
	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, prm, conn);
	
	OutputStream outStream = response.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	
 %>
