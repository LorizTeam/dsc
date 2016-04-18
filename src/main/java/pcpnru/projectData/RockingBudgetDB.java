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

public class RockingBudgetDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetRockingBudgetList(String docno, String project_code, String year) throws Exception { // 30-05-2014
		List RockingBudgetList = new ArrayList();
		String gcostcode = "", gcostname = "", amount1 = "", gcostcode_rock = "", gcostname_rock = "", amount2 = "",
				amount_rock = "", balance = "", docdate = "", remark = "", approve_status = "";
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
			if (!year.equals(""))
				sqlStmt = sqlStmt + "year = '" + year + "' AND ";

			sqlStmt = sqlStmt + "project_code <> '' order by gcostcode";

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

	public void DeleteRockingBudget(String docno, String project_code, String year, String gcostcode,
			String gcostcode_rock) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From rocking_budget " + "WHERE docno = '" + docno + "' and project_code = '"
				+ project_code + "' and year = '" + year + "' and gcostcode = '" + gcostcode
				+ "' and gcostcode_rock = '" + gcostcode_rock + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public List GetGroupCostCodeList(String projectcode, String year, String groupcostCode) throws Exception { // 30-05-2014
		List groupcostCodeList = new ArrayList();
		String groupcostName = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT b.gcostcode, b.gcostcode_name,gcostcode_standardprice,gcostcode_fundprice, DATE_FORMAT(datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "from projectplan_detail a INNER JOIN groupcostcode_master b ON (b.gcostcode = a.gcostcode and a.project_code = b.project_code)  "
					+ "WHERE type_gcostcode = '2' and ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";
			if (!projectcode.equals("")) {
				sqlStmt = sqlStmt + "a.project_code = '" + projectcode + "' AND ";
				if (!groupcostCode.equals("")) {
					sqlStmt = sqlStmt + "b.gcostcode = '" + groupcostCode + "' ";
				} else {
					sqlStmt = sqlStmt + "b.gcostcode <> '' ";
				}
			} else {
				sqlStmt = sqlStmt + "a.project_code = '' AND b.gcostcode = '' ";
			}

			sqlStmt = sqlStmt + "order by b.gcostcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				groupcostCode = rs.getString("gcostcode");
				if (rs.getString("gcostcode_name") != null)
					groupcostName = rs.getString("gcostcode_name");
				else
					groupcostName = "";

				groupcostCodeList.add(new GroupCostCodeMasterModel(groupcostCode, groupcostName));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return groupcostCodeList;
	}

	public List GetGroupCostCode2List(String projectcode, String year, String groupcostCode) throws Exception { // 30-05-2014
		List groupcostCodeList = new ArrayList();
		String groupcostName = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT b.gcostcode, b.gcostcode_name,gcostcode_standardprice,gcostcode_fundprice, DATE_FORMAT(datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "from projectplan_detail a INNER JOIN groupcostcode_master b ON (b.gcostcode = a.gcostcode and a.project_code = b.project_code)  "
					+ "WHERE type_gcostcode = '2' and ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + projectcode + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";
			sqlStmt = sqlStmt + "b.gcostcode not in ('" + groupcostCode + "') order by b.gcostcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				groupcostCode = rs.getString("gcostcode");
				if (rs.getString("gcostcode_name") != null)
					groupcostName = rs.getString("gcostcode_name");
				else
					groupcostName = "";

				groupcostCodeList.add(new GroupCostCodeMasterModel(groupcostCode, groupcostName));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return groupcostCodeList;
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

	public String AmountRockingBudget(String project_code, String year, String gcostcode) throws Exception {
		String amount = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT (IFNULL(SUM(a.budget),0)+IFNULL(rock.budget,0)-(IFNULL(gave_rock.gave_budget,0)+IFNULL(SUM(b.amount),0))) as frombalance "
					+ ",SUM(a.budget),rock.budget,gave_rock.gave_budget,SUM(b.amount) " + "FROM projectplan_detail a "
					+ "LEFT JOIN requisition b on (a.gcostcode = b.gcostcode and a.project_code = b.project_code and a.year = b.project_year) "
					+ "LEFT JOIN (SELECT IFNULL(SUM(c.amount_rock),0) as budget ,project_code,year,gcostcode FROM rocking_budget c where c.approve_status in ('AP','NA') GROUP BY c.gcostcode) "
					+ "as rock on(a.gcostcode = rock.gcostcode and a.project_code = rock.project_code and a.year = rock.year) "
					+ "LEFT JOIN (SELECT IFNULL(SUM(e.amount_rock),0) as gave_budget ,project_code,year,gcostcode_rock FROM rocking_budget e where e.approve_status in ('AP','NA') and e.gcostcode_rock = '"
					+ gcostcode + "' GROUP BY e.gcostcode_rock) "
					+ "as gave_rock on(a.gcostcode = gave_rock.gcostcode_rock and a.project_code = gave_rock.project_code and a.year = gave_rock.year) "
					+ " where a.project_code = '" + project_code + "' and a.`year` = '" + year + "' and a.gcostcode = '"
					+ gcostcode + "'";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				amount = rs.getString("frombalance");
			}
			rs.close();
			pStmt.close();

			if (null == amount || "".equals(amount)) {
				amount = "0";
			}

			conn.close();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return amount;
	}
}
