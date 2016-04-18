package pcpnru.utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.utilities.*;

public class CheckAuthenPageDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public boolean getCheckAuthen(String username, String page_code) throws Exception {

		boolean chkAuthen = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT a.project_code, a.username, a.`password`, a.`name`, a.lastname, a.dow, a.dob, "
				+ "a.telephone, a.address, a.position, a.authen_type, a.datetime, b.page_code, b.datetime " + "FROM "
				+ "employee a " + "INNER JOIN authen_page b ON a.authen_type = b.authen_type " + "WHERE a.username = '"
				+ username + "' and b.page_code = '" + page_code + "' "
				+ "GROUP BY a.username, b.authen_type, b.page_code ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkAuthen = true;
		}

		conn.close();
		rs.close();
		pStmt.close();

		return chkAuthen;
	}

	public boolean getCheckAuthenLockProject(String username) throws Exception {

		boolean chkAuthen = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT b.authen_type " + "FROM " + "employee a "
				+ "INNER JOIN authen_master b ON a.authen_type = b.authen_type " + "WHERE a.username = '" + username
				+ "' and b.authen_type = '006' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkAuthen = true;
		}

		conn.close();
		rs.close();
		pStmt.close();

		return chkAuthen;
	}

	public String getProjectCode(String username) throws Exception {

		String project_code = "";
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT a.project_code, a.username, a.`password`, a.`name`, a.lastname, a.dow, a.dob, "
				+ "a.telephone, a.address, a.position, a.authen_type, a.datetime " + "FROM " + "employee a "
				+ "WHERE a.username = '" + username + "' " + "GROUP BY a.username ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			project_code = rs.getString("project_code");
		}

		conn.close();
		rs.close();
		pStmt.close();

		return project_code;
	}
}
