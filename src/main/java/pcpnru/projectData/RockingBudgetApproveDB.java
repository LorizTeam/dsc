package pcpnru.projectData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.GroupCostCodeMasterModel;
import pcpnru.projectModel.*;
import pcpnru.utilities.*;

public class RockingBudgetApproveDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetRockingBudgetDList(String docno, String project_code, String year, String gcostcode)
			throws Exception { // 30-05-2014
		List RockingBudgetList = new ArrayList();
		String gcostname = "", amount1 = "", gcostcode_rock = "", gcostname_rock = "", amount2 = "", amount_rock = "",
				balance = "", docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,project_code,year,a.gcostcode,amount1,gcostcode_rock,amount2,amount_rock,balance,docdate,remark,approve_status,t1.g1,t2.g2 "
					+ "FROM rocking_budget a "
					+ "left join (SELECT gcostcode,gcostcode_name as g1 FROM groupcostcode_master b where b.project_code = '"
					+ project_code + "') AS t1 on(t1.gcostcode = a.gcostcode) "
					+ "left join (SELECT gcostcode,gcostcode_name as g2 FROM groupcostcode_master c where c.project_code = '"
					+ project_code + "') AS t2 on(t2.gcostcode = a.gcostcode_rock) " + "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			// if(!project_code.equals("")) sqlStmt = sqlStmt+ "project_code =
			// '"+project_code+"' AND ";
			if (!gcostcode.equals(""))
				sqlStmt = sqlStmt + "a.gcostcode = '" + gcostcode + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "year = '" + year + "' AND ";

			sqlStmt = sqlStmt + "project_code = '" + project_code
					+ "' group by docno order by approve_status desc, docno, project_code, gcostcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode");
				gcostname = rs.getString("g1");
				amount1 = rs.getString("amount1");
				gcostcode_rock = rs.getString("gcostcode_rock");
				gcostname_rock = rs.getString("g2");
				amount2 = rs.getString("amount2");
				amount_rock = rs.getString("amount_rock");
				balance = rs.getString("balance");
				docdate = rs.getString("docdate");
				remark = rs.getString("remark");
				approve_status = rs.getString("approve_status");

				amount1 = df2.format(Float.parseFloat(amount1));
				amount2 = df2.format(Float.parseFloat(amount2));
				amount_rock = df2.format(Float.parseFloat(amount_rock));
				balance = df2.format(Float.parseFloat(balance));

				RockingBudgetList.add(
						new RockingBudgetForm(docno, project_code, year, gcostcode, gcostname, amount1, gcostcode_rock,
								gcostname_rock, amount2, amount_rock, balance, docdate, remark, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return RockingBudgetList;
	}

	public List WindowRockingBudgetList(String docno, String project_code, String year, String gcostcode, String pj)
			throws Exception { // 30-05-2014
		List RockingBudgetList = new ArrayList();
		String gcostname = "", amount1 = "", gcostcode_rock = "", gcostname_rock = "", amount2 = "", amount_rock = "",
				balance = "", docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,project_code,year,a.gcostcode,amount1,gcostcode_rock,amount2,amount_rock,balance,docdate,remark,approve_status,t1.g1,t2.g2 "
					+ "FROM rocking_budget a "
					+ "left join (SELECT gcostcode,gcostcode_name as g1 FROM groupcostcode_master b where b.project_code = '"
					+ project_code + "') AS t1 on(t1.gcostcode = a.gcostcode) "
					+ "left join (SELECT gcostcode,gcostcode_name as g2 FROM groupcostcode_master c where c.project_code = '"
					+ project_code + "') AS t2 on(t2.gcostcode = a.gcostcode_rock) " + "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + project_code + "' AND ";
			if (!gcostcode.equals(""))
				sqlStmt = sqlStmt + "a.gcostcode = '" + gcostcode + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "year = '" + year + "' AND ";
			if (!pj.equals(""))
				sqlStmt = sqlStmt + "approve_status  not in ('WA','CC') AND ";

			sqlStmt = sqlStmt + "project_code <> '' order by approve_status desc";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode");
				gcostname = rs.getString("g1");
				amount1 = rs.getString("amount1");
				gcostcode_rock = rs.getString("gcostcode_rock");
				gcostname_rock = rs.getString("g2");
				amount2 = rs.getString("amount2");
				amount_rock = rs.getString("amount_rock");
				balance = rs.getString("balance");
				docdate = rs.getString("docdate");
				remark = rs.getString("remark");
				approve_status = rs.getString("approve_status");

				amount1 = df2.format(Float.parseFloat(amount1));
				amount2 = df2.format(Float.parseFloat(amount2));
				amount_rock = df2.format(Float.parseFloat(amount_rock));
				balance = df2.format(Float.parseFloat(balance));

				RockingBudgetList.add(
						new RockingBudgetForm(docno, project_code, year, gcostcode, gcostname, amount1, gcostcode_rock,
								gcostname_rock, amount2, amount_rock, balance, docdate, remark, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return RockingBudgetList;
	}

	public void AddRockingBudget(String docno, String project_code, String year, String gcostcode, String amount1,
			String gcostcode_rock, String amount2, String amount_rock, String balance, String docdate, String remark,
			String approve_status) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO rocking_budget(docno,project_code,year,gcostcode,amount1,gcostcode_rock,amount2,amount_rock,balance,docdate,remark,approve_status) "
				+ "VALUES ('" + docno + "', '" + project_code + "', '" + year + "', '" + gcostcode + "', '" + amount1
				+ "', '" + gcostcode_rock + "', '" + amount2 + "', '" + amount_rock + "', " + "'" + balance + "', '"
				+ docdate + "', '" + remark + "', '" + approve_status + "')";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateStatusRockingBudget(String docno, String project_code, String year, String gcostcode,
			String approve_status) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE rocking_budget set approve_status = '" + approve_status + "' " + "WHERE docno = '"
				+ docno + "' and project_code = '" + project_code + "' and year = '" + year + "' and " + "gcostcode = '"
				+ gcostcode + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
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

	public String SelectUpdateDocNo(String project_code, String year) throws Exception {
		String requestno = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(docno) as lno FROM rocking_budget " + "WHERE docno <> '' and project_code = '"
					+ project_code + "' and year = '" + year + "' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
			}
			rs.close();
			pStmt.close();

			if (null == requestno || "".equals(requestno)) {
				requestno = "0";
			}

			requestno = String.valueOf(Integer.parseInt(requestno) + 1);

			if (requestno.length() == 1) {
				requestno = "0000" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "000" + requestno;
			} else if (requestno.length() == 3) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 4) {
				requestno = "0" + requestno;
			}

			conn.close();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return requestno;
	}

	public List getAP() throws Exception {

		List<String> listAP = new ArrayList<String>();

		conn = agent.getConnectMYSql();

		String sqlStmt = "select a.project_code, count(approve_status) as cap FROM projectplan_header a "
				+ "LEFT JOIN rocking_budget b on(b.project_code = a.project_code and approve_status = 'WA') "
				+ "WHERE a.project_code <> ''  group by a.project_code order by a.project_code ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			listAP.add(rs.getString("cap"));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return listAP;
	}
}
