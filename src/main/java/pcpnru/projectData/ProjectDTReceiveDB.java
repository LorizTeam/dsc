package pcpnru.projectData;

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

public class ProjectDTReceiveDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetProjDTReceiveList(String projectcode) throws Exception { // 18-01-2016
		List ProjDTReceive = new ArrayList();
		String gcostcost = "", gcostname = "", budget = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT project_code, gcostcode, gcostcode_name, budget "
					+ "FROM projectplan_detail WHERE ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + projectcode + "' AND ";
			sqlStmt = sqlStmt + "project_code <> '' and subjob_code = '0003' order by datetime_response desc";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				projectcode = rs.getString("project_code");
				gcostcost = rs.getString("gcostcode");
				gcostname = rs.getString("gcostcode_name");
				budget = rs.getString("budget");

				ProjDTReceive.add(new ProjectModel(projectcode, gcostcost, gcostname, budget));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return ProjDTReceive;
	}

	public void AddProjDTReceive(String projectcode, String year, String subjob, String csubjob, String costcode,
			String costname, String budget) throws Exception {

		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO projectplan_detail(project_code, year, subjob_code, childsubjobcode, gcostcode, gcostcode_name, budget, datetime_response) "
				+ "VALUES ('" + projectcode + "', '" + year + "', '" + subjob + "', '" + csubjob + "', '" + costcode
				+ "', '" + costname + "', '" + budget + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteProjDTReceive(String projectcode, String costcode, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From projectplan_detail " + "WHERE project_code = '" + projectcode
				+ "' and gcostcode = '" + costcode + "' and year = '" + year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String getSumAmount(String docNo) throws Exception {

		String amountTotal = "";

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT sum(amounttotal) as att " + "FROM receivedt WHERE docno = '" + docNo
				+ "' Group by docno ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amountTotal = rs.getString("att");
		}

		rs.close();
		pStmt.close();
		conn.close();

		return amountTotal;
	}

	public String SumReceive(String docNo) throws Exception { // 30-05-2014

		String itemNo = "", description = "", qty = "", amount = "", amountTotal = "";

		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT sum(amounttotal) as sumamt " + "FROM receivedt WHERE docno = '" + docNo + "' ";

			sqlStmt = sqlStmt + "group by docno order by itemno";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {

				amountTotal = rs.getString("sumamt");

			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return amountTotal;
	}

	public List GetChildSubjobList() throws Exception { // 19-01-2016
		List childSubjobList = new ArrayList();
		String childsubjobcode = "", childsubjobname = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT childsubjob_master.subjob_code, subjob_name, childsubjobcode, childsubjobname "
					+ "FROM childsubjob_master left join subjob_master on(subjob_master.subjob_code = childsubjob_master.subjob_code) "
					+ "WHERE ";
			// if(!subjobcode.equals("")) sqlStmt = sqlStmt+ "subjobcode like
			// '"+subjobcode+"%' AND ";
			// if(!childsubjobcode.equals("")) sqlStmt = sqlStmt+
			// "childsubjobcode like '"+childsubjobcode+"%' AND ";
			// if(!childsubjobname.equals("")) sqlStmt = sqlStmt+
			// "childsubjobname like '"+childsubjobname+"%' AND ";

			sqlStmt = sqlStmt
					+ "childsubjob_master.subjob_code = '0003'  group by childsubjob_master.subjob_code order by childsubjob_master.datetime desc";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				// subjobcode = rs.getString("subjobcode");
				// subjobname = rs.getString("subjob_name");
				childsubjobcode = rs.getString("childsubjobcode");
				if (rs.getString("childsubjobname") != null)
					childsubjobname = rs.getString("childsubjobname");
				else
					childsubjobname = "";

				childSubjobList.add(new ChildSubjobMasterForm(childsubjobcode, childsubjobname));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return childSubjobList;
	}

	public List GetGroupCostCodeList(String projectcode, String year) throws Exception { // 30-05-2014
		List groupcostCodeList = new ArrayList();
		String groupcostCode = "", groupcostName = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT gcostcode, gcostcode_name,gcostcode_standardprice,gcostcode_fundprice, DATE_FORMAT(datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "FROM groupcostcode_master a " + "WHERE type_gcostcode = '1' and ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + projectcode + "' AND ";
			sqlStmt = sqlStmt
					+ "a.gcostcode not in (select DISTINCT(b.gcostcode) from projectplan_detail b where b.gcostcode = a.gcostcode and "
					+ "project_code = '" + projectcode + "' and year = '" + year + "') order by a.gcostcode";

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

	public String getSumAmountPCC(String project_code, String year) throws Exception {

		String target = "";

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT target " + "FROM projectplan_header WHERE project_code = '" + project_code
				+ "' and year = '" + year + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			target = rs.getString("target");
		}

		rs.close();
		pStmt.close();
		conn.close();

		return target;
	}

	public String SelectProjFreeze(String projectcode, String year) throws Exception {
		conn = agent.getConnectMYSql();
		String freeze = "";
		String sqlStmt = "SELECT status_freeze From projectplan_header " + "WHERE project_code = '" + projectcode
				+ "' and year = '" + year + "'";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			freeze = rs.getString("status_freeze");
		}

		rs.close();
		pStmt.close();
		conn.close();
		return freeze;
	}
}
