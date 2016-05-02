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

public class ProjectDTChargesDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetProjDTChargesList(String projectcode) throws Exception { // 18-01-2016
		List ProjDTReceive = new ArrayList();
		String gcostcost = "", gcostname = "", budget = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT project_code, gcostcode, gcostcode_name, budget "
					+ "FROM projectplan_detail WHERE ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + projectcode + "' AND ";
			sqlStmt = sqlStmt + "project_code <> '' and subjob_code not in ('0003') order by datetime_response desc";

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

	public void AddProjDTCharges(String projectcode, String year, String subjob, String csubjob, String costcode,
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

	public void DeleteProjDTCharges(String projectcode, String subjobcode, String csubjobcode, String gcostcode)
			throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From projectplan_detail " + "WHERE project_code = '" + projectcode
				+ "' and subjob_code = '" + subjobcode + "' and childsubjobcode = '" + csubjobcode
				+ "' and gcostcode = '" + gcostcode + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public double getAmtValue(String projectcode, String year, int percentage) throws Exception {

		String amountTotalTXT = "";
		double amountTotal = 0;

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT sum(budget) as att " + "FROM projectplan_detail WHERE project_code = '" + projectcode
				+ "' and year = '" + year + "' and subjob_code = '0003' Group by project_code, subjob_code ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amountTotalTXT = rs.getString("att");
			if (amountTotalTXT != null)
				amountTotal = Double.parseDouble(rs.getString("att"));
			else
				amountTotal = 0;
		}
		amountTotal = (amountTotal * percentage) / 100;

		rs.close();
		pStmt.close();
		conn.close();

		return amountTotal;
	}

	public int getPercen(String projectcode, String year) throws Exception {

		String amountTotalTXT = "";
		int amountTotal = 0;

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT percen " + "FROM projectplan_header WHERE project_code = '" + projectcode
				+ "' and year = '" + year + "' Group by project_code, year ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amountTotalTXT = rs.getString("percen");
			if (amountTotalTXT != null)
				amountTotal = Integer.parseInt(rs.getString("percen"));
			else
				amountTotal = 0;
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

	public List GetSubjobList() throws Exception { // 19-01-2016
		List childSubjobList = new ArrayList();
		String subjobcode = "", subjobname = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT subjob_code, subjob_name " + "FROM subjob_master " + "WHERE ";
			// if(!subjobcode.equals("")) sqlStmt = sqlStmt+ "subjobcode like
			// '"+subjobcode+"%' AND ";
			// if(!childsubjobcode.equals("")) sqlStmt = sqlStmt+
			// "childsubjobcode like '"+childsubjobcode+"%' AND ";
			// if(!childsubjobname.equals("")) sqlStmt = sqlStmt+
			// "childsubjobname like '"+childsubjobname+"%' AND ";

			sqlStmt = sqlStmt + "subjob_code not in ('0003') order by subjob_code";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				subjobcode = rs.getString("subjob_code");
				subjobname = rs.getString("subjob_name");

				childSubjobList.add(new SubjobMasterForm(subjobcode, subjobname));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return childSubjobList;
	}

	public List GetChildSubjobList() throws Exception { // 19-01-2016
		List childSubjobList = new ArrayList();
		String childsubjobcode = "", childsubjobname = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT subjobcode, subjob_name, childsubjobcode, childsubjobname "
					+ "FROM childsubjob_master left join subjob_master on(subjobcode = subjob_code) " + "WHERE ";
			// if(!subjobcode.equals("")) sqlStmt = sqlStmt+ "subjobcode like
			// '"+subjobcode+"%' AND ";
			// if(!childsubjobcode.equals("")) sqlStmt = sqlStmt+
			// "childsubjobcode like '"+childsubjobcode+"%' AND ";
			// if(!childsubjobname.equals("")) sqlStmt = sqlStmt+
			// "childsubjobname like '"+childsubjobname+"%' AND ";

			sqlStmt = sqlStmt + "subjobcode = '0003'  group by subjobcode order by childsubjob_master.datetime desc";

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
					+ "FROM groupcostcode_master a   " + "WHERE type_gcostcode = '2' and ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + projectcode + "' AND ";
			if (!groupcostCode.equals(""))
				sqlStmt = sqlStmt + "gcostcode like '" + groupcostCode + "%' AND ";
			if (!groupcostName.equals(""))
				sqlStmt = sqlStmt + "gcostcode_name like '" + groupcostName + "%' AND ";

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
	public double getAmtValueEmp(String emp, String date_start, String date_end) throws Exception {

		String amountTotalTXT = "", dateD = "";
		double amountTotal = 0;

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT sum(manday) as att , DATEDIFF('"+date_end+"','"+date_start+"') AS DiffDate FROM employee WHERE username in "+emp+"";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amountTotalTXT = rs.getString("att");
			if (amountTotalTXT != null){
				amountTotal = Double.parseDouble(rs.getString("att"));
				dateD	= rs.getString("DiffDate");
				amountTotal = amountTotal*Double.parseDouble(dateD);
			}
			else{
				amountTotal = 0;
			}
		} 

		
		rs.close();
		pStmt.close();
		conn.close();

		return amountTotal;
	}
}
