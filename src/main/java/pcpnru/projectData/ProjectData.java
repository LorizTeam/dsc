package pcpnru.projectData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.projectModel.ProjectMasterForm;
import pcpnru.projectModel.ProjectModel;
import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;

public class ProjectData {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	DateUtil dateUtil = new DateUtil();

	public List GetProjectHDList() throws Exception { // 30-05-2014
		List projectplanHDList = new ArrayList();
		String datetime_response = "", project_name, target = "", percen = "", year_projectplan = "", project_code = "",
				freeze = "";
		try {

			String sqlStmt = "SELECT " + "a.project_code," + "b.project_name," + "a.target," + "a.`year`,"
					+ "a.`percen`," + "DATE_FORMAT(a.datetime_response,'%d-%m-%Y %H:%i') as datetime_response "
					+ ",a.`status_freeze` " + "FROM " + "projectplan_header AS a "
					+ "INNER JOIN project_master AS b ON b.project_code = a.project_code where a.project_code <> '' "
					+ "order by a.project_code";

			conn = agent.getConnectMYSql();
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				target = rs.getString("target");
				percen = rs.getString("percen");
				freeze = rs.getString("status_freeze");
				year_projectplan = rs.getString("year");
				datetime_response = rs.getString("datetime_response");
				// dateTime = dateTime.replace(".0", "");

				String day = datetime_response.substring(0, 2);
				String month = datetime_response.substring(3, 5);
				String year = Integer.toString((Integer.parseInt(datetime_response.substring(6, 10)) + 543));

				String time = datetime_response.substring(11);
				datetime_response = day + "-" + month + "-" + year + " " + time;
				// amount = df2.format(Float.parseFloat(amount));

				projectplanHDList.add(new ProjectModel(project_code, project_name, target, percen, year_projectplan,
						datetime_response, freeze, ""));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return projectplanHDList;
	}

	public void AddProjectHD(String project_code, String target, String percen, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO projectplan_header(project_code, target, percen, year, datetime_response) "
				+ "VALUES ('" + project_code + "', '" + target + "', '" + percen + "','" + year + "', now())";
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

	public void DeleteProjectHD(String project_code, String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From projectplan_header " + "WHERE project_code = '" + project_code + "' and year = '"
				+ year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean getCheckProjectHD(String project_code) throws Exception {

		boolean chkProject = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT project_code " + "FROM projectplan_header WHERE project_code = '" + project_code
				+ "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkProject = true;
		}

		rs.close();
		pStmt.close();
		conn.close();
		return chkProject;
	}

	public double getTarget(String projectcode, String year) throws Exception {

		String targetTXT = "";
		double target = 0;

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT target " + "FROM projectplan_header WHERE project_code = '" + projectcode
				+ "' and year = '" + year + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			targetTXT = rs.getString("target");
			if (targetTXT != null)
				target = Double.parseDouble(rs.getString("target"));
			else
				target = 0;
		}

		rs.close();
		pStmt.close();
		conn.close();
		return target;
	}

	public String selectProjectname(String project_code) throws Exception {

		String project_name = "";
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT b.project_name "
				+ "FROM projectplan_header a inner join project_master b on(b.project_code = a.project_code) WHERE a.project_code = '"
				+ project_code + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			project_name = rs.getString("project_name");
		}

		rs.close();
		pStmt.close();
		conn.close();
		return project_name;
	}

	public void UpdateFreeze(String project_code, String year, String freeze) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE projectplan_header set status_freeze = '" + freeze + "' " + "WHERE project_code = '"
				+ project_code + "' and year = '" + year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
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

	public List GetProjectDTDetailList(String project_code, String year, String project_name, String subjob_code,
			String subjob_name, String childsubjobcode, String childsubjobname, String gcostcode, String gcostcode_name,
			String budget, String datetime_response, String orderby, String receive, String groupby) {
		List ProjectDTList = new ArrayList();

		String sqlWhere = "", sqlQuery = "";

		if (!project_code.equals(""))
			sqlWhere += "e.project_code = '" + project_code + "' and ";
		if (!year.equals(""))
			sqlWhere += "f.year = '" + year + "' and ";
		if (!project_name.equals(""))
			sqlWhere += "e.project_name = '" + project_name + "' and ";
		if (!subjob_code.equals(""))
			sqlWhere += "a.subjob_code = '" + subjob_code + "' and ";
		if (!subjob_name.equals(""))
			sqlWhere += "b.subjob_name = '" + subjob_name + "' and ";
		if (!childsubjobcode.equals(""))
			sqlWhere += "a.childsubjobcode = '" + childsubjobcode + "' and ";
		if (!childsubjobname.equals(""))
			sqlWhere += "c.childsubjobname = '" + childsubjobname + "' and ";
		if (!gcostcode.equals(""))
			sqlWhere += "a.gcostcode = '" + gcostcode + "' and ";
		if (!gcostcode_name.equals(""))
			sqlWhere += "a.gcostcode_name = '" + gcostcode_name + "' and ";
		if (!budget.equals(""))
			sqlWhere += "a.budget = '" + budget + "' and ";
		if (!datetime_response.equals(""))
			sqlWhere += "a.datetime_response = '" + datetime_response + "' and ";
		if (!receive.equals("")) {
			sqlWhere += "a.subjob_code = '0003' and ";
		} else {
			sqlWhere += "a.subjob_code not in ('0003') and ";
		}

		if (!project_code.equals("PCC")) {
			sqlQuery = "SELECT " + "e.project_name, " + "e.project_code, " + "a.subjob_code, " + "b.subjob_name, "
					+ "a.childsubjobcode, " + "c.childsubjobname, " + "a.gcostcode, " + "a.gcostcode_name, "
					+ "a.budget, " + "a.datetime_response " + "FROM " + "projectplan_detail AS a "
					+ "INNER JOIN subjob_master AS b ON b.subjob_code = a.subjob_code "
					+ "INNER JOIN childsubjob_master AS c ON a.childsubjobcode = c.childsubjobcode AND b.subjob_code = c.subjob_code "
					+ "INNER JOIN groupcostcode_master AS d ON d.gcostcode = a.gcostcode AND d.project_code = a.project_code "
					+ "INNER JOIN project_master as e ON e.project_code = a.project_code "
					+ "INNER JOIN projectplan_header AS f ON f.project_code = a.project_code and f.year = a.year "
					+ "where " + sqlWhere + "a.project_code <> '' ";
		} else {
			sqlQuery = "SELECT " + "e.project_name, " + "e.project_code, " + "a.subjob_code, " + "b.subjob_name, "
					+ "a.childsubjobcode, " + "c.childsubjobname, " + "a.gcostcode, " + "a.gcostcode_name, "
					+ "a.budget, " + "a.datetime_response " + "FROM " + "projectplan_detail AS a "
					+ "INNER JOIN subjob_master AS b ON b.subjob_code = a.subjob_code "
					+ "INNER JOIN childsubjob_master AS c ON a.childsubjobcode = c.childsubjobcode AND b.subjob_code = c.subjob_code "
					+ "INNER JOIN project_master as e ON e.project_code = a.project_code "
					+ "INNER JOIN projectplan_header AS f ON f.project_code = a.project_code and f.year = a.year "
					+ "where " + sqlWhere + "a.project_code <> '' ";
		}

		if (!groupby.equals(""))
			sqlQuery += "group by " + groupby;
		// ORDER BY a.project_code,a.subjob_code,a.childsubjobcode,a.gcostcode
		// sqlQuery += " ORDER BY
		// a.project_code,a.subjob_code,a.childsubjobcode,a.gcostcode";
		if (!orderby.equals(""))
			sqlQuery += " order by datetime_response " + orderby;

		try {

			conn = agent.getConnectMYSql();
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlQuery);
			while (rs.next()) {
				project_code = rs.getString("project_code");
				project_name = rs.getString("project_name");
				subjob_code = rs.getString("subjob_code");
				subjob_name = rs.getString("subjob_name");
				childsubjobcode = rs.getString("childsubjobcode");
				childsubjobname = rs.getString("childsubjobname");
				gcostcode = rs.getString("gcostcode");
				gcostcode_name = rs.getString("gcostcode_name");
				budget = rs.getString("budget");
				datetime_response = rs.getString("datetime_response");

				String budget_now = "0";
				String sql = "SELECT (IFNULL(SUM(a.budget),0)+IFNULL(rock.budget,0)-(IFNULL(gave_rock.gave_budget,0)+IFNULL(SUM(b.amount),0))) as frombalance "
						+ ",SUM(a.budget),rock.budget,gave_rock.gave_budget,SUM(b.amount) "
						+ "FROM projectplan_detail a "
						+ "LEFT JOIN requisition b on (a.gcostcode = b.gcostcode and a.project_code = b.project_code and a.year = b.project_year) "
						+ "LEFT JOIN (SELECT IFNULL(SUM(c.amount_rock),0) as budget ,project_code,year,gcostcode FROM rocking_budget c where c.approve_status in ('AP') GROUP BY c.gcostcode) "
						+ "as rock on(a.gcostcode = rock.gcostcode and a.project_code = rock.project_code and a.year = rock.year) "
						+ "LEFT JOIN (SELECT IFNULL(SUM(e.amount_rock),0) as gave_budget ,project_code,year,gcostcode_rock FROM rocking_budget e where e.approve_status in ('AP') and e.gcostcode_rock = '"
						+ gcostcode + "' GROUP BY e.gcostcode_rock) "
						+ "as gave_rock on(a.gcostcode = gave_rock.gcostcode_rock and a.project_code = gave_rock.project_code and a.year = gave_rock.year) "
						+ " where a.project_code = '" + project_code + "' and a.`year` = '" + year
						+ "' and a.gcostcode = '" + gcostcode + "' GROUP BY a.gcostcode";

				pStmt = conn.createStatement();
				rs1 = pStmt.executeQuery(sql);
				while (rs1.next()) {
					budget_now = rs1.getString("frombalance");
				}

				ProjectDTList
						.add(new ProjectModel(project_code, project_name, subjob_code, subjob_name, childsubjobcode,
								childsubjobname, gcostcode, gcostcode_name, budget, budget_now, datetime_response));
			}

			rs.close();
			pStmt.close();
			conn.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				rs.close();
				pStmt.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				rs.close();
				pStmt.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return ProjectDTList;
	}

}
