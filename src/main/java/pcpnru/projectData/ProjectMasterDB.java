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

public class ProjectMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetProjectMasterList(String projectCode, String projectName) throws Exception { // 30-05-2014
		List projectMasterList = new ArrayList();
		String dateTime = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT project_code, project_name, DATE_FORMAT(datetime,'%d-%m-%Y %H:%i') as datetime "
					+ "FROM project_master " + "WHERE ";
			if (!projectCode.equals(""))
				sqlStmt = sqlStmt + "project_code like '" + projectCode + "%' AND ";
			if (!projectName.equals(""))
				sqlStmt = sqlStmt + "project_name like '" + projectName + "%' AND ";

			sqlStmt = sqlStmt + "project_code <> '' order by project_code";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				projectCode = rs.getString("project_code");
				if (rs.getString("project_name") != null)
					projectName = rs.getString("project_name");
				else
					projectName = "";
				dateTime = rs.getString("datetime");
				// dateTime = dateTime.replace(".0", "");

				String day = dateTime.substring(0, 2);
				String month = dateTime.substring(3, 5);
				String year = Integer.toString((Integer.parseInt(dateTime.substring(6, 10)) + 543));

				String time = dateTime.substring(11);
				dateTime = day + "-" + month + "-" + year + " " + time;
				// amount = df2.format(Float.parseFloat(amount));

				projectMasterList.add(new ProjectMasterForm(projectCode, projectName, dateTime));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return projectMasterList;
	}

	public void AddProjectMaster(String projectCode, String projectName) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO project_master(project_code, project_name, datetime) " + "VALUES ('"
				+ projectCode + "', '" + projectName + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateProjectMaster(String projectCode, String projectName, String projectCodeHD) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE project_master set project_code = '" + projectCode + "', project_name = '"
				+ projectName + "' " + ", datetime = now()" + "WHERE project_code = '" + projectCodeHD + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteProjectMaster(String projectCode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From project_master " + "WHERE project_code = '" + projectCode + "'";
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

	public List getListProject_Join_Projecthead(String project_code, String project_name, String year, String target)
			throws IOException, Exception {

		String sqlWhere = "";
		if (!project_code.equals("")) {
			sqlWhere += "project_master.project_code = '" + project_code + "' AND ";
		}
		if (!project_name.equals("")) {
			sqlWhere += "project_master.project_name = '" + project_name + "' AND ";
		}

		String sqlQuery = "SELECT " + "project_master.project_code, " + "project_master.project_name " + "FROM "
				+ "project_master where ";
		sqlQuery += sqlWhere;

		sqlQuery += "project_master.project_code <> '' order by project_master.project_code ";

		// System.out.println(sqlQuery);

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListProject_Join_Projecthead = new ArrayList();
		String forwhat = "getListProject_Join_Projecthead";
		while (rs.next()) {
			project_code = rs.getString("project_code");
			project_name = rs.getString("project_name");
			getListProject_Join_Projecthead.add(new ProjectModel(forwhat, project_code, project_name, "", "", forwhat));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListProject_Join_Projecthead;
	}

	public List getListProject_Join_Projecthead_PCC(String project_code, String project_name, String year,
			String target) throws IOException, Exception {

		String sqlWhere = "";
		// if(!project_code.equals("")){
		// sqlWhere += "project_master.project_code = '"+project_code+"' AND ";
		// }
		if (!project_name.equals("")) {
			sqlWhere += "project_master.project_name = '" + project_name + "' AND ";
		}

		String sqlQuery = "SELECT " + "project_master.project_code, " + "project_master.project_name " + "FROM "
				+ "project_master where ";
		sqlQuery += sqlWhere;

		if (project_code.equals("PCC")) {
			sqlQuery += "project_master.project_code not in (select b.gcostcode from projectplan_detail b where b.gcostcode <> '' and b.gcostcode not in ('PCC') and "
					+ "b.gcostcode = project_master.project_code " + "GROUP BY b.gcostcode) and ";
		}
		sqlQuery += "project_master.project_code <> '' and project_master.project_code not in ('PCC') order by project_master.project_code ";

		// System.out.println(sqlQuery);

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListProject_Join_Projecthead = new ArrayList();
		String forwhat = "getListProject_Join_Projecthead";
		while (rs.next()) {
			project_code = rs.getString("project_code");
			project_name = rs.getString("project_name");
			getListProject_Join_Projecthead.add(new ProjectModel(forwhat, project_code, project_name, "", "", forwhat));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListProject_Join_Projecthead;
	}

	public String SelectUpdateDocNo() throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(project_code) as lno FROM project_master " + "WHERE project_code <> '' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
				if (null == requestno || "".equals(requestno)) {
					// System.out.println("requestno = null");
					requestno = "0";
				}
				requestno = requestno.replace("PCC", "");
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);
				// System.out.println("requestno = "+requestno);
			}

			if (requestno.length() == 1) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "0" + requestno;
			}

			requestno = "PCC" + requestno;

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (pStmt != null)
					pStmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return requestno;
	}

}
