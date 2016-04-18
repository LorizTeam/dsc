package pcpnru.projectData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.projectModel.*;
import pcpnru.utilities.*;

public class SubjobMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetSubjobMasterList(String subjobCode, String subjobName) throws Exception { // 30-05-2014
		List subjobMasterList = new ArrayList();
		String dateTime = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT subjob_code, subjob_name, DATE_FORMAT(datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "FROM subjob_master " + "WHERE ";
			if (!subjobCode.equals(""))
				sqlStmt = sqlStmt + "subjob_code like '" + subjobCode + "%' AND ";
			if (!subjobName.equals(""))
				sqlStmt = sqlStmt + "subjob_name like '" + subjobName + "%' AND ";

			sqlStmt = sqlStmt + "subjob_code <> '' order by datetime desc, subjob_code";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				subjobCode = rs.getString("subjob_code");
				if (rs.getString("subjob_name") != null)
					subjobName = rs.getString("subjob_name");
				else
					subjobName = "";

				dateTime = rs.getString("datetime");
				String day = dateTime.substring(0, 2);
				String month = dateTime.substring(3, 5);
				String year = Integer.toString((Integer.parseInt(dateTime.substring(6, 10)) + 543));

				String time = dateTime.substring(11);
				dateTime = day + "-" + month + "-" + year + " " + time;
				// amount = df2.format(Float.parseFloat(amount));

				subjobMasterList.add(new SubjobMasterForm(subjobCode, subjobName, dateTime));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return subjobMasterList;
	}

	public List SubjobDropDown(String subjobCode, String subjobName) throws Exception { // 30-05-2014
		List subjobDropDown = new ArrayList();
		String dateTime = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT subjob_code, subjob_name " + "FROM subjob_master " + "WHERE ";

			sqlStmt = sqlStmt + "subjob_code <> '' order by subjob_code";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				subjobCode = rs.getString("subjob_code");
				if (rs.getString("subjob_name") != null)
					subjobName = rs.getString("subjob_name");
				else
					subjobName = "";

				subjobDropDown.add(new SubjobMasterForm(subjobCode, subjobName));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return subjobDropDown;
	}

	public void AddSubjobMaster(String subjobCode, String subjobName) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO subjob_master(subjob_code, subjob_name, datetime) " + "VALUES ('"
				+ subjobCode + "', '" + subjobName + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateSubjobMaster(String subjobCode, String subjobName, String subjobCodeHD) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE subjob_master set subjob_code = '" + subjobCode + "', subjob_name = '" + subjobName
				+ "', datetime = now() " + "WHERE subjob_code = '" + subjobCodeHD + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();

		if (checksubjob_inchildsubjob(subjobCode)) {
			conn = agent.getConnectMYSql();
			sqlStmt = "UPDATE childsubjob_master set subjob_code = '" + subjobCode + "' " + "WHERE subjob_code = '"
					+ subjobCodeHD + "'";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			pStmt.executeUpdate(sqlStmt);
			pStmt.close();

			conn.close();
		}

	}

	public void DeleteSubjobMaster(String subjobCode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From subjob_master " + "WHERE subjob_code = '" + subjobCode + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean getCheckMaster(String materialCode) throws Exception {

		boolean chkCustomer = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT material_code " + "FROM material_master WHERE material_code = '" + materialCode + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkCustomer = true;
		}

		rs.close();
		pStmt.close();
		conn.close();
		return chkCustomer;
	}

	public boolean checksubjob_inchildsubjob(String subjob_code) throws IOException, Exception {
		boolean checkhave = false;
		String sqlQuery = "SELECT " + "a.subjob_code, " + "a.subjob_name, " + "a.datetime " + "FROM "
				+ "subjob_master AS a " + "INNER JOIN childsubjob_master AS b ON b.subjob_code = a.subjob_code "
				+ "where a.subjob_code = '" + subjob_code + "'";

		// conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			checkhave = true;
		}
		rs.close();
		pStmt.close();
		conn.close();
		return checkhave;
	}

	public String SelectUpdateDocNo(String subjobCode) throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(subjob_code) as lno FROM subjob_master " + "WHERE subjob_code <> '' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
				if (null == requestno || "".equals(requestno)) {
					// System.out.println("requestno = null");
					requestno = "0";
				}
				// typeR = requestno.substring(0, 1);
				// requestno = requestno.substring(2);
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);
				// System.out.println("requestno = "+requestno);
			}

			if (requestno.length() == 1) {
				requestno = "000" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 3) {
				requestno = "0" + requestno;
			}

			requestno = typeR + requestno;

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return requestno;
	}
}
