package test.pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;

public class TestPurchaseDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public Map<String, String> GetPO_HD(String po_docno) throws IOException, Exception {

		String sqlQuery = "SELECT a.po_docno,a.`year`+543 as year"
				+ ",CONCAT(SUBSTR(a.docdate FROM 9 FOR 2),'-',SUBSTR(a.docdate FROM 6 FOR 2),'-',SUBSTR(a.docdate FROM 1 FOR 4)+543) as docdate"
				+ ",a.ref_pr"
				+ ",CONCAT(SUBSTR(a.ref_prdate FROM 9 FOR 2),'-',SUBSTR(a.ref_prdate FROM 6 FOR 2),'-',SUBSTR(a.ref_prdate FROM 1 FOR 4)+543) as ref_prdate"
				+ ",a.type,a.po_offer,a.creditday,a.penaltype_perday,a.ref_quotation"
				+ ",CONCAT(SUBSTR(a.ref_quotationdate FROM 9 FOR 2),'-',SUBSTR(a.ref_quotationdate FROM 6 FOR 2),'-',SUBSTR(a.ref_quotationdate FROM 1 FOR 4)+543) as ref_quotationdate"
				+ ",a.`status`,a.create_by,a.project_code,a.approve_by,a.create_datetime FROM po_hd AS a "
				+ "where po_docno = '" + po_docno + "'";
		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		Map<String, String> MapResultGetPOHD = new HashMap<String, String>();
		while (rs.next()) {
			MapResultGetPOHD.put("po_docno", rs.getString("po_docno"));
			MapResultGetPOHD.put("year", rs.getString("year"));
			MapResultGetPOHD.put("docdate", rs.getString("docdate"));
			MapResultGetPOHD.put("ref_pr", rs.getString("ref_pr"));
			MapResultGetPOHD.put("ref_prdate", rs.getString("ref_prdate"));
			MapResultGetPOHD.put("type", rs.getString("type"));
			MapResultGetPOHD.put("po_offer", rs.getString("po_offer"));
			MapResultGetPOHD.put("creditday", rs.getString("creditday"));
			MapResultGetPOHD.put("penaltype_perday", rs.getString("penaltype_perday"));
			MapResultGetPOHD.put("ref_quotation", rs.getString("ref_quotation"));
			MapResultGetPOHD.put("ref_quotationdate", rs.getString("ref_quotationdate"));
			MapResultGetPOHD.put("status", rs.getString("status"));
			MapResultGetPOHD.put("create_by", rs.getString("create_by"));
			MapResultGetPOHD.put("project_code", rs.getString("project_code"));
			MapResultGetPOHD.put("approve_by", rs.getString("approve_by"));
			MapResultGetPOHD.put("create_datetime", rs.getString("create_datetime"));
		}

		if (!rs.isClosed())
			rs.close();
		if (!pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return MapResultGetPOHD;
	}

	public int InsertPO_HD(String po_docno, String po_year, String docdate, String ref_pr, String ref_prdate,
			String type, String po_offer, int creditday, double penaltype_perday, String ref_quotation,
			String ref_quotationdate, String status, String create_by, String project_code, String approve_by)
					throws IOException, Exception {

		String sqlQuery = "INSERT IGNORE INTO `po_hd` (`po_docno`, `year`, `docdate`, `ref_pr`, `ref_prdate`,"
				+ " `type`, `po_offer`, `creditday`, `penaltype_perday`, `ref_quotation`, `ref_quotationdate`,"
				+ " `status`, `create_by`, `project_code`,approve_by, `create_datetime`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, po_docno);
		ppStmt.setString(2, po_year);
		ppStmt.setString(3, docdate);
		ppStmt.setString(4, ref_pr);
		ppStmt.setString(5, ref_prdate);
		ppStmt.setString(6, type);
		ppStmt.setString(7, po_offer);
		ppStmt.setInt(8, creditday);
		ppStmt.setDouble(9, penaltype_perday);
		ppStmt.setString(10, ref_quotation);
		ppStmt.setString(11, ref_quotationdate);
		ppStmt.setString(12, status);
		ppStmt.setString(13, create_by);
		ppStmt.setString(14, project_code);
		ppStmt.setString(15, approve_by);
		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();
		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();

		return rowsupdate;
	}
	/*
	 * public int InsertPO_HD(String po_docno,String po_year,String
	 * docdate,String ref_pr,String ref_prdate,String type ,String po_offer,int
	 * creditday,double penaltype_perday,String ref_quotation,String
	 * ref_quotationdate ,String status,String create_by,String
	 * project_code,String approve_by) throws IOException, Exception{
	 * 
	 * String sqlQuery =
	 * "INSERT IGNORE INTO `po_hd` (`po_docno`, `year`, `docdate`, `ref_pr`, `ref_prdate`,"
	 * +
	 * " `type`, `po_offer`, `creditday`, `penaltype_perday`, `ref_quotation`, `ref_quotationdate`,"
	 * +
	 * " `status`, `create_by`, `project_code`,approve_by, `create_datetime`) values "
	 * +
	 * "('"+po_docno+"','"+po_year+"','"+docdate+"','"+ref_pr+"','"+ref_prdate+
	 * "','"+type+"','"+po_offer+"','"+creditday+"','"+penaltype_perday+"'" +
	 * ",'"+ref_quotation+"','"+ref_quotationdate+"','"+status+"','"+create_by+
	 * "','"+project_code+"','"+approve_by+"')"; conn = agent.getConnectMYSql();
	 * conn.setAutoCommit(false); ppStmt = conn.prepareStatement(sqlQuery);
	 * ppStmt.setString(1, po_docno); ppStmt.setString(2, po_year);
	 * ppStmt.setString(3, docdate); ppStmt.setString(4, ref_pr);
	 * ppStmt.setString(5, ref_prdate); ppStmt.setString(6, type);
	 * ppStmt.setString(7, po_offer); ppStmt.setInt(8, creditday);
	 * ppStmt.setDouble(9, penaltype_perday); ppStmt.setString(10,
	 * ref_quotation); ppStmt.setString(11, ref_quotationdate);
	 * ppStmt.setString(12, status); ppStmt.setString(13, create_by);
	 * ppStmt.setString(14, project_code); ppStmt.setString(15, approve_by); int
	 * rowsupdate = ppStmt.executeUpdate(); conn.commit();
	 * if(!ppStmt.isClosed()) ppStmt.close(); if(!conn.isClosed()) conn.close();
	 * 
	 * return rowsupdate; }
	 */

	public boolean InsertPO_DT() {

		return true;
	}

	public List GetPO_ListDT() {
		return null;
	}
}
