package pcpnru.projectData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectModel.CentralBudgetForm;
import pcpnru.projectModel.PurchaseOrderModel;
import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;

public class PurchaseOrderDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetPRList(String docno, String year) throws Exception { // 30-05-2014
		List PRList = new ArrayList();
		String itemno = "", description = "", qty = "0", amount = "0", amounttotal = "0", amt_budget = "",
				frombalance = "", rocking_budget = "", balance = "", docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.po_docno,a.`year`,a.itemno,a.numthai_itemno,a.description,a.qty,a.price,a.numthai_qty,a.unit,a.remark,a.create_by,a.create_datetime "
					+ "FROM po_dt a WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "po_docno = '" + docno + "' and ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "year = '" + year + "' ";

			sqlStmt = sqlStmt + "order by a.itemno";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("po_docno");
				itemno = String.valueOf(Integer.parseInt(rs.getString("itemno")));
				year = rs.getString("year");
				description = rs.getString("description");
				qty = rs.getString("qty");
				amount = rs.getString("price");
				remark = rs.getString("remark");
				if (remark == null)
					remark = "";
				if (amount == null || amount.equals(""))
					amount = "0";

				amounttotal = String.valueOf(Double.parseDouble(qty) * Double.parseDouble(amount));
				if (amounttotal.equals("0.0")) {
					amounttotal = "";
				} else {
					amounttotal = df2.format(Float.parseFloat(amounttotal));
				}

				if (amount.equals("0") || amount.equals("0.00")) {
					amount = "";
				} else {
					amount = df2.format(Float.parseFloat(amount));
				}

				PRList.add(new PurchaseOrderModel(docno, itemno, year, description, qty, amount, amounttotal, remark));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return PRList;
	}

	public void AddPOHD(String ref_pr, String ref_prdate, String po_docno, String year, String po_docdate, String type, String vender, int credit_day,
			double mulct_day, String quotation_number, String quotation_date, String username) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO po_hd(ref_pr, ref_prdate, po_docno, year, docdate, type, vender, creditday, penaltype_perday, ref_quotation, ref_quotationdate, approve_status, create_by) "
				+ "VALUES ('"+ref_pr+ "','"+ ref_prdate+"','"+po_docno+ "','"+ year+"','" + po_docdate + "','" + type + "','" + vender + "',"
				+ credit_day + "," + mulct_day + ",'" + quotation_number + "','" + quotation_date + "', 'WA', '"+username+"')";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}
	public void UpdatePOHD(String ref_pr, String ref_prdate, String po_docno, String year, String po_docdate, String type, String vender, int credit_day,
			double mulct_day, String quotation_number, String quotation_date) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE po_hd set ref_pr = '"+ref_pr+"', ref_prdate = '"+ref_prdate+"', docdate = '"+po_docdate+"', type = '" + type
				+ "' , vender = '" + vender + "'," + "creditday = " + credit_day + " , penaltype_perday = " + mulct_day + ", ref_quotation = '"+quotation_number+"', "
				+ "ref_quotationdate = '"+quotation_date+"' "
				+ "WHERE po_docno = '" + po_docno + "' and year = '" + year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String AddPODT(String pr_docno, String po_docno, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "";

		if (po_docno.equals("")) {
			sqlStmt = "SELECT max(po_docno) as lno FROM po_hd " + "WHERE year = '" + year + "' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				po_docno = rs.getString("lno");
				if (null == po_docno || "".equals(po_docno)) {
					po_docno = "0";
				}
				po_docno = String.valueOf(Integer.parseInt(po_docno) + 1);
			}

			if (po_docno.length() == 1) {
				po_docno = "0000" + po_docno;
			} else if (po_docno.length() == 2) {
				po_docno = "000" + po_docno;
			} else if (po_docno.length() == 3) {
				po_docno = "00" + po_docno;
			} else if (po_docno.length() == 4) {
				po_docno = "0" + po_docno;
			}
			rs.close();
			pStmt.close();
		} else {
			sqlStmt = "DELETE FROM po_dt " + "WHERE po_docno = '" + po_docno + "' and year = '" + year + "' ";
			pStmt = conn.createStatement();
			pStmt.executeUpdate(sqlStmt);
			pStmt.close();
		}

		sqlStmt = "INSERT INTO po_dt(po_docno,year,itemno,description,qty,unit) " + "SELECT '" + po_docno
				+ "',year,itemno,product_code,qty,unit_id FROM record_approve_dt WHERE docno = '" + pr_docno + "' and year = '"
				+ year + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
		return po_docno;
	}

	public void UpdatePOPrice(String po_docno, String year, String itemno, String qty, String amount, String remark)
			throws Exception {
		conn = agent.getConnectMYSql();

		if (itemno.length() == 1) {
			itemno = "00" + itemno;
		} else if (itemno.length() == 2) {
			itemno = "0" + itemno;
		}

		if (!amount.equals("")) {
			amount = amount.replace(",", "");
		} else {
			amount = "0";
		}

		String sqlStmt = "UPDATE po_dt set qty = '" + qty + "', price = '" + amount + "' , remark = '" + remark
				+ "', create_date = now() " + "WHERE po_docno = '" + po_docno + "' and year = '" + year
				+ "' and itemno = '" + itemno + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateProjectHD(String project_code, String target, String percen, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE projectplan_header set project_code = '" + project_code + "', target = '" + target
				+ "' , percen = '" + percen + "'," + "year = '" + year + "' " + ", datetime_response = now()"
				+ "WHERE project_code = '" + project_code + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeletePRDetail(String po_docno, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE FROM po_dt " + "WHERE po_docno = '" + po_docno + "' and year = '" + year + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String SelectUpdateDocNo(String year) throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(po_docno) as lno FROM po_hd " + "WHERE year = '" + year + "' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
				if (null == requestno || "".equals(requestno)) {
					requestno = "0";
				}
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);
			}

			if (requestno.length() == 1) {
				requestno = "0000" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "000" + requestno;
			} else if (requestno.length() == 3) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 4) {
				requestno = "0" + requestno;
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return requestno;
	}

	public boolean chk_pr(String docno, String year) throws Exception {
		boolean ck = false;
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno FROM record_approve_dt " + "WHERE docno = '" + docno + "' and year = '"
					+ year + "' group by docno ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				ck = true;
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return ck;
	}
	
public void Add_PurchaseRequest_Image(String docno,String year,String img_path) throws IOException, Exception{
		PreparedStatement ppStmt = null;
		String sqlQuery = "insert ignore into po_imgref values (?,?,?)";
		
		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, img_path);
		ppStmt.executeUpdate();
		
		if(!ppStmt.isClosed())
			ppStmt.close();
		if(!conn.isClosed())
			conn.close();
	}
public List<PurchaseOrderModel> GET_PurchaseRequest_Image(String docno,String year,String img_path) throws IOException, Exception{
	
	String sqlQuery = "select * from po_imgref where ";
	
	if(!docno.equals("")) sqlQuery += "docno = '"+docno+"' and ";
	if(!year.equals("")) sqlQuery += "year = '"+year+"' and ";
	if(!img_path.equals("")) sqlQuery += "img_path = '"+img_path+"' and ";
	
	sqlQuery += "docno != ''";
	
	conn = agent.getConnectMYSql();
	pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sqlQuery);
	List<PurchaseOrderModel> ResultList = new ArrayList<PurchaseOrderModel>();
	while (rs.next()) {
		ResultList.add(new PurchaseOrderModel(rs.getString("img_path"),rs.getString("docno"),rs.getString("year")));
	}
	
	if(!pStmt.isClosed())
		pStmt.close();
	if(!conn.isClosed())
		conn.close();
	
	return ResultList;
}
public List<PurchaseOrderModel> GetListPO_Header(String po_number, String vender, String po_date, String po_month,
		String po_year, String approve_status, String type) throws IOException, Exception {

	List<PurchaseOrderModel> ListPO = new ArrayList<PurchaseOrderModel>();

	String sqlQuery = "SELECT po_docno,`year`+543 as year,"
			+ "CONCAT(substr(docdate from 9 for 2),\"-\",substr(docdate from 6 for 2),\"-\",year(docdate)+543) as docdate," 
			+ "a.create_by," 
			+ "CONCAT(b.name,' ',b.lastname) as create_name,approve_status, type, vendor_name, "
			+ "ref_pr, creditday, penaltype_perday, ref_quotation, "
			+ "CONCAT(substr(ref_prdate from 9 for 2),\"-\",substr(ref_prdate from 6 for 2),\"-\",year(ref_prdate)+543) as ref_prdate, "
			+ "CONCAT(substr(ref_quotationdate from 9 for 2),\"-\",substr(ref_quotationdate from 6 for 2),\"-\",year(ref_quotationdate)+543) as ref_quotationdate " 
			+ "FROM `po_hd` a "
			+ "INNER JOIN employee b on (a.create_by = b.username) "
			+ "INNER JOIN vendor_master c on (a.vender = c.vendor_id) "

			+ "where ";

	if (!po_number.equals(""))
		sqlQuery += "docno = '" + po_number + "' and ";
	
	if (!vender.equals(""))
		sqlQuery += "vender = '" + vender + "' and ";
	
	if (!po_date.equals(""))
		sqlQuery += "docdate = '" + po_date + "' and ";

	if (!po_month.equals(""))
		sqlQuery += "MONTH(docdate) = '" + po_month + "' and ";

	if (!po_year.equals(""))
		sqlQuery += "YEAR(docdate) = '" + po_year + "' and ";
	
	if (!approve_status.equals(""))
		sqlQuery += "approve_status = '" + approve_status + "' and ";
	
	if (!type.equals(""))
		sqlQuery += "type = '" + type + "' and ";

	sqlQuery += "po_docno != '' order by approve_status desc";

	conn = agent.getConnectMYSql();
	pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sqlQuery);

	 
	while (rs.next()) {
		ListPO.add(new PurchaseOrderModel(rs.getString("po_docno"), rs.getString("vendor_name"), rs.getString("year"),
				rs.getString("docdate"), rs.getString("approve_status"), rs.getString("type"), rs.getString("create_name")
				, rs.getString("ref_pr"), rs.getString("ref_prdate"), rs.getInt("creditday"), rs.getDouble("penaltype_perday")
				, rs.getString("ref_quotation"), rs.getString("ref_quotationdate")));
	}

	if (!rs.isClosed())
		rs.close();
	if (pStmt.isClosed())
		pStmt.close();
	if (!conn.isClosed())
		conn.close();

	return ListPO;
}
public void approve_po(String docno, String year, String approve_status) throws IOException, Exception {
	String sqlQuery = "update po_hd set approve_status = ? where po_docno = ? and year = ?";
	PreparedStatement ppStmt = null;
	conn = agent.getConnectMYSql();
	ppStmt = conn.prepareStatement(sqlQuery);
	ppStmt.setString(1, approve_status);
	ppStmt.setString(2, docno);
	ppStmt.setString(3, year);
	ppStmt.executeUpdate();

	if (!ppStmt.isClosed())
		ppStmt.close();
	if (!conn.isClosed())
		conn.close();
}
public Map GetAllValueHeader_byDocno(String po_docno, String year) throws IOException, Exception {
	Map mapresultGet = new HashMap();

	String sqlQuery = "SELECT po_docno,`year`+543 as year,"
				+ "CONCAT(substr(docdate from 9 for 2),\"-\",substr(docdate from 6 for 2),\"-\",year(docdate)+543) as docdate," 
				+ "a.create_by," 
				+ "CONCAT(b.name,' ',b.lastname) as create_name, approve_status, type, vendor_name, "
				+ "ref_pr, creditday, penaltype_perday, ref_quotation, "
				+ "CONCAT(substr(ref_prdate from 9 for 2),\"-\",substr(ref_prdate from 6 for 2),\"-\",year(ref_prdate)+543) as ref_prdate, "
				+ "CONCAT(substr(ref_quotationdate from 9 for 2),\"-\",substr(ref_quotationdate from 6 for 2),\"-\",year(ref_quotationdate)+543) as ref_quotationdate " 
				+ "FROM `po_hd` a "
				+ "INNER JOIN employee b on (a.create_by = b.username) "
				+ "INNER JOIN vendor_master c on (a.vender = c.vendor_id) WHERE "
				+ "po_docno = '"+po_docno+"' and year = '"+year+"'";
	
	conn = agent.getConnectMYSql();
	pStmt = conn.createStatement();
	rs = pStmt.executeQuery(sqlQuery);

	while (rs.next()) {
		mapresultGet.put("po_docno", rs.getString("po_docno"));
		mapresultGet.put("year", rs.getString("year"));
		mapresultGet.put("docdate", rs.getString("docdate"));
		mapresultGet.put("ref_pr", rs.getString("ref_pr"));
		mapresultGet.put("ref_prdate", rs.getString("ref_prdate"));
		mapresultGet.put("creditday", rs.getInt("creditday"));
		mapresultGet.put("penaltype_perday", rs.getDouble("penaltype_perday"));
		mapresultGet.put("ref_quotation", rs.getString("ref_quotation"));
		mapresultGet.put("ref_quotationdate", rs.getString("ref_quotationdate"));
		mapresultGet.put("vender", rs.getString("vendor_name"));
		mapresultGet.put("type", rs.getString("type"));
	}

	if (!rs.isClosed())
		rs.close();
	if (pStmt.isClosed())
		pStmt.close();
	if (!conn.isClosed())
		conn.close();

	return mapresultGet;
}
public String chk_poapprove(String po_docno, String year) throws Exception {
	String ck = "";
	try {
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT approve_status FROM po_hd " + "WHERE po_docno = '"+po_docno+"' and year = '"+year+"' group by po_docno ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);
		while (rs.next()) { 
				ck = rs.getString("approve_status"); 
		}

		rs.close();
		pStmt.close();
		conn.close();
	} catch (Exception e) {
		throw new Exception(e.getMessage());
	}
	return ck;
}
}
