package pcpnru.projectData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.projectModel.*;
import pcpnru.utilities.*;

public class ChildSubjobMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetChildSubjobMasterList(String subjobcode, String childsubjobcode, String childsubjobname)
			throws Exception { // 30-05-2014
		List childSubjobMasterList = new ArrayList();
		String subjobname = "", dateTime = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT childsubjob_master.subjob_code, subjob_name, childsubjobcode, childsubjobname, DATE_FORMAT(childsubjob_master.datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "FROM childsubjob_master left join subjob_master on(childsubjob_master.subjob_code = subjob_master.subjob_code) "
					+ "WHERE ";
			if (!subjobcode.equals(""))
				sqlStmt = sqlStmt + "subjob_code like '" + subjobcode + "%' AND ";
			if (!childsubjobcode.equals(""))
				sqlStmt = sqlStmt + "childsubjobcode like '" + childsubjobcode + "%' AND ";
			if (!childsubjobname.equals(""))
				sqlStmt = sqlStmt + "childsubjobname like '" + childsubjobname + "%' AND ";

			sqlStmt = sqlStmt + "childsubjobcode <> '' order by childsubjob_master.datetime desc";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				subjobcode = rs.getString("subjob_code");
				subjobname = rs.getString("subjob_name");
				childsubjobcode = rs.getString("childsubjobcode");
				if (rs.getString("childsubjobname") != null)
					childsubjobname = rs.getString("childsubjobname");
				else
					childsubjobname = "";

				dateTime = rs.getString("datetime");
				String day = dateTime.substring(0, 2);
				String month = dateTime.substring(3, 5);
				String year = Integer.toString((Integer.parseInt(dateTime.substring(6, 10)) + 543));

				String time = dateTime.substring(11);
				dateTime = day + "-" + month + "-" + year + " " + time;
				// amount = df2.format(Float.parseFloat(amount));

				childSubjobMasterList.add(
						new ChildSubjobMasterForm(subjobcode, subjobname, childsubjobcode, childsubjobname, dateTime));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return childSubjobMasterList;
	}

	public void AddChildSubjobMaster(String subjobcode, String childsubjobcode, String childsubjobname)
			throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO childsubjob_master(subjob_code, childsubjobcode, childsubjobname, datetime) "
				+ "VALUES ('" + subjobcode + "', '" + childsubjobcode + "', '" + childsubjobname + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateChildSubjobMaster(String subjobcode, String childsubjobcode, String childsubjobname,
			String subjobcodehd, String childsubjobcodehd) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE childsubjob_master set subjob_code = '" + subjobcode + "', childsubjobcode = '"
				+ childsubjobcode + "', childsubjobname = '" + childsubjobname + "', datetime = now() "
				+ "WHERE subjob_code = '" + subjobcodehd + "' and childsubjobcode = '" + childsubjobcodehd + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteChildSubjobMaster(String subjobcode, String childsubjobcode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From childsubjob_master " + "WHERE subjob_code = '" + subjobcode
				+ "' and childsubjobcode = '" + childsubjobcode + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String SelectUpdateDocNo(String subjobcode) throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(childsubjobcode) as lno FROM childsubjob_master " + "WHERE subjob_code = '"
					+ subjobcode + "' ";
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
