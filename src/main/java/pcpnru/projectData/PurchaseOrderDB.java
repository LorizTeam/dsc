package pcpnru.projectData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

	public void AddPOHD(String po_docno, String year, String po_docdate, String type, String vender, int credit_day,
			double mulct_day, String quotation_number, String quotation_date) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO po_hd(po_docno, year, docdate, type, vender, creditday, penaltype_perday, ref_quotation, ref_quotationdate) "
				+ "VALUES ('" + po_docno + "','" + year + "','" + po_docdate + "','" + type + "','" + vender + "',"
				+ credit_day + "," + mulct_day + ",'" + quotation_number + "','" + quotation_date + "')";
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

		sqlStmt = "INSERT INTO po_dt(po_docno,year,itemno,description,qty) " + "SELECT '" + po_docno
				+ "',year,itemno,product_code,qty FROM record_approve_dt WHERE docno = '" + pr_docno + "' and year = '"
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
}
