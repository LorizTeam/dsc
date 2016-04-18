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

public class CentralBudgetDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetCentralBudgetList(String docno, String project_code, String year) throws Exception { // 30-05-2014
		List CentralBudgetList = new ArrayList();
		String project_name = "", gcostcode = "", gcostname = "", amt_budget = "", frombalance = "",
				rocking_budget = "", balance = "", docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,a.project_code,b.project_name,a.year,gcostcode_rock,amt_budget,amount_rock,balance,"
					+ "docdate,remark,approve_status,t2.g2,e.budget_central as amt " + "FROM rocking_budget_central a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "left join (SELECT gcostcode,gcostcode_name as g2 FROM groupcostcode_master c GROUP BY gcostcode) AS t2 on(t2.gcostcode = a.gcostcode_rock) "
					+ "INNER JOIN central_budget e on(e.year = a.year) " + "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";

			sqlStmt = sqlStmt + "a.project_code <> '' order by a.gcostcode_rock";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode_rock");
				gcostname = rs.getString("g2");
				amt_budget = rs.getString("amt");
				frombalance = rs.getString("amt_budget");
				rocking_budget = rs.getString("amount_rock");
				balance = rs.getString("balance");
				remark = rs.getString("remark");
				docdate = rs.getString("docdate");
				approve_status = rs.getString("approve_status");

				/*
				 * amt_budget = df2.format(Float.parseFloat(amt_budget));
				 * frombalance = df2.format(Float.parseFloat(frombalance));
				 * rocking_budget =
				 * df2.format(Float.parseFloat(rocking_budget)); balance =
				 * df2.format(Float.parseFloat(balance));
				 */

				CentralBudgetList.add(new CentralBudgetForm(docno, project_code, project_name, year, gcostcode,
						gcostname, amt_budget, frombalance, rocking_budget, balance, remark, docdate, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return CentralBudgetList;
	}

	public void AddCentralBudget(String docno, String project_code, String year, String gcostcode_rock,
			String amt_budget, String amount_rock, String balance, String docdate, String remark, String approve_status)
					throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO rocking_budget_central(docno,project_code,year,gcostcode_rock,amt_budget,amount_rock,balance,docdate,remark,approve_status) "
				+ "VALUES ('" + docno + "', '" + project_code + "', '" + year + "', '" + gcostcode_rock + "', '"
				+ amt_budget + "', '" + amount_rock + "', '" + balance + "', " + "'" + docdate + "', '" + remark
				+ "', '" + approve_status + "')";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteCentralBudget(String docno, String project_code, String year, String gcostcode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From rocking_budget_central " + "WHERE docno = '" + docno + "' and project_code = '"
				+ project_code + "' and year = '" + year + "' and gcostcode_rock = '" + gcostcode + "' ";
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
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + projectcode + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";
			if (!groupcostCode.equals(""))
				sqlStmt = sqlStmt + "b.gcostcode = '" + groupcostCode + "' AND ";
			sqlStmt = sqlStmt + "b.gcostcode <> '' order by b.gcostcode";

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

			String sqlStmt = "SELECT max(docno) as lno FROM rocking_budget_central " + "WHERE docno <> '' and year = '"
					+ year + "' ";
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
					+ "LEFT JOIN (SELECT IFNULL(SUM(c.amount_rock),0) as budget ,project_code,year,gcostcode FROM rocking_budget c where c.approve_status in ('AP','WA') GROUP BY c.gcostcode) "
					+ "as rock on(a.gcostcode = rock.gcostcode and a.project_code = rock.project_code and a.year = rock.year) "
					+ "LEFT JOIN (SELECT IFNULL(SUM(e.amount_rock),0) as gave_budget ,project_code,year,gcostcode_rock FROM rocking_budget e where e.approve_status in ('AP','WA') and e.gcostcode_rock = '"
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

	public List getListProject(String project_code, String project_name, String year, String target)
			throws IOException, Exception {

		String sqlWhere = "";
		if (!project_code.equals("")) {
			// sqlWhere += "project_master.project_code not in
			// ('"+project_code+"','PCC') AND ";
			sqlWhere += "project_master.project_code not in ('PCC') AND ";
		}
		if (!project_name.equals("")) {
			sqlWhere += "project_master.project_name = '" + project_name + "' AND ";
		}
		if (!year.equals("")) {
			sqlWhere += "projectplan_header.year = '" + year + "' AND ";
		}
		if (!target.equals("")) {
			sqlWhere += "projectplan_header.target = '" + target + "' AND ";
		}

		String sqlQuery = "SELECT " + "project_master.project_code, " + "project_master.project_name, "
				+ "projectplan_header.`year`, " + "projectplan_header.target, "
				+ "projectplan_header.datetime_response " + "FROM " + "projectplan_header "
				+ "INNER JOIN project_master ON project_master.project_code = projectplan_header.project_code where ";
		sqlQuery += sqlWhere;
		sqlQuery += "project_master.project_code <> '' order by project_master.project_code ";

		System.out.println(sqlQuery);

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListProject_Join_Projecthead = new ArrayList();
		String forwhat = "getListProject_Join_Projecthead";
		while (rs.next()) {
			project_code = rs.getString("project_code");
			project_name = rs.getString("project_name");
			year = rs.getString("year");
			target = rs.getString("target");
			getListProject_Join_Projecthead
					.add(new ProjectModel(forwhat, project_code, project_name, year, target, forwhat));

		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListProject_Join_Projecthead;
	}

	public String AmountBudget(String year) throws Exception {
		String amt = "";
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT IFNULL(SUM(budget_central),0)-IFNULL(t1.amt,0) as amt " + "FROM central_budget a "
				+ "LEFT JOIN (select year, sum(amount_rock) as amt from rocking_budget_central where year = '" + year
				+ "' " + "and approve_status not in ('CC') group by year) as t1 on(t1.year = a.year) "
				+ "where a.year = '" + year + "'";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amt = rs.getString("amt");
			amt = df2.format(Float.parseFloat(amt));
		}
		return amt;

	}

	// approve central budget

	public List GetCentralBudgetApproveList(String docno, String project_code, String year, String gcostcode,
			String approve_status) throws Exception { // 30-05-2014
		List CentralBudgetApproveList = new ArrayList();
		String project_name = "", gcostname = "", amt_budget = "", frombalance = "", rocking_budget = "", balance = "",
				docdate = "", remark = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,a.project_code,b.project_name,a.year,gcostcode_rock,amt_budget,amount_rock,balance,"
					+ "docdate,remark,approve_status,t2.g2,e.budget_central as amt " + "FROM rocking_budget_central a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "left join (SELECT project_code,gcostcode,gcostcode_name as g2 FROM groupcostcode_master c) AS t2 on(t2.gcostcode = a.gcostcode_rock "
					+ "and t2.project_code = a.project_code) " + "INNER JOIN central_budget e on(e.year = a.year) "
					+ "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";
			if (!approve_status.equals(""))
				sqlStmt = sqlStmt + "a.approve_status = '" + approve_status + "' AND ";

			sqlStmt = sqlStmt + "a.project_code <> '' group by docno order by a.approve_status, a.docdate";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode_rock");
				gcostname = rs.getString("g2");
				amt_budget = rs.getString("amt");
				frombalance = rs.getString("amt_budget");
				rocking_budget = rs.getString("amount_rock");
				balance = rs.getString("balance");
				remark = rs.getString("remark");
				docdate = rs.getString("docdate");
				approve_status = rs.getString("approve_status");

				CentralBudgetApproveList.add(new CentralBudgetForm(docno, project_code, project_name, year, gcostcode,
						gcostname, amt_budget, frombalance, rocking_budget, balance, remark, docdate, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return CentralBudgetApproveList;
	}

	public List WindowCentralBudgetList(String docno, String project_code, String year, String gcostcode)
			throws Exception { // 30-05-2014
		List WCentralBudgetApproveList = new ArrayList();
		String project_name = "", gcostname = "", amt_budget = "", frombalance = "", rocking_budget = "", balance = "",
				docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,a.project_code,b.project_name,a.year,gcostcode_rock,amt_budget,amount_rock,balance,"
					+ "docdate,remark,approve_status,t2.g2,e.budget_central as amt " + "FROM rocking_budget_central a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "left join (SELECT project_code,gcostcode,gcostcode_name as g2 FROM groupcostcode_master c) AS t2 on(t2.gcostcode = a.gcostcode_rock "
					+ "and t2.project_code = a.project_code) " + "INNER JOIN central_budget e on(e.year = a.year) "
					+ "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";

			sqlStmt = sqlStmt + "a.project_code <> '' order by a.gcostcode_rock";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode_rock");
				gcostname = rs.getString("g2");
				amt_budget = rs.getString("amt");
				frombalance = rs.getString("amt_budget");
				rocking_budget = rs.getString("amount_rock");
				balance = rs.getString("balance");
				remark = rs.getString("remark");
				docdate = rs.getString("docdate");
				approve_status = rs.getString("approve_status");

				WCentralBudgetApproveList.add(new CentralBudgetForm(docno, project_code, project_name, year, gcostcode,
						gcostname, amt_budget, frombalance, rocking_budget, balance, remark, docdate, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return WCentralBudgetApproveList;
	}

	public List WindowCentralBudget_DT(String docno, String project_code, String year, String gcostcode)
			throws Exception { // 30-05-2014
		List WCentralBudgetApproveList = new ArrayList();
		String project_name = "", gcostname = "", amt_budget = "", frombalance = "", rocking_budget = "", balance = "",
				docdate = "", remark = "", approve_status = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno,a.project_code,b.project_name,a.year,gcostcode_rock,amt_budget,amount_rock,balance,"
					+ "docdate,remark,approve_status,t2.g2,e.budget_central as amt " + "FROM rocking_budget_central a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "left join (SELECT project_code,gcostcode,gcostcode_name as g2 FROM groupcostcode_master c) AS t2 on(t2.gcostcode = a.gcostcode_rock "
					+ "and t2.project_code = a.project_code) " + "INNER JOIN central_budget e on(e.year = a.year) "
					+ "WHERE ";
			if (!docno.equals(""))
				sqlStmt = sqlStmt + "docno = '" + docno + "' AND ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "a.year = '" + year + "' AND ";
			if (!gcostcode.equals(""))
				sqlStmt = sqlStmt + "a.gcostcode_rock = '" + gcostcode + "' AND ";

			sqlStmt = sqlStmt + "a.project_code <> '' and a.approve_status = 'AP' order by a.docno,a.docdate";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docno = rs.getString("docno");
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				year = rs.getString("year");
				gcostcode = rs.getString("gcostcode_rock");
				gcostname = rs.getString("g2");
				amt_budget = rs.getString("amt");
				frombalance = rs.getString("amt_budget");
				rocking_budget = rs.getString("amount_rock");
				balance = rs.getString("balance");
				remark = rs.getString("remark");
				docdate = rs.getString("docdate");
				approve_status = rs.getString("approve_status");

				WCentralBudgetApproveList.add(new CentralBudgetForm(docno, project_code, project_name, year, gcostcode,
						gcostname, amt_budget, frombalance, rocking_budget, balance, remark, docdate, approve_status));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return WCentralBudgetApproveList;
	}

	public void UpdateStatusCentralBudget(String docno, String year, String approve_status) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE rocking_budget_central set approve_status = '" + approve_status + "' "
				+ "WHERE docno = '" + docno + "' and year = '" + year + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}
}
