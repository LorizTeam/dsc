package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.PersonnelMasterModel;
import pcpnru.utilities.*;

public class PersonnelMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String encrypt(String x) throws Exception {
		String storepass = "";
		try {
			java.security.Security.addProvider(new sun.security.provider.Sun());
			java.security.MessageDigest lMessageDigest = java.security.MessageDigest.getInstance("SHA", "SUN");
			byte[] _result = lMessageDigest.digest(x.getBytes());
			storepass = new sun.misc.BASE64Encoder().encode(_result);

		} catch (java.security.NoSuchProviderException nspe) {

		}
		return storepass;
	}

	public List GetPersonnelList(String project_code, String personnel_id, String personnel_name,
			String personnel_lastname, String authen_type) throws Exception { // 30-05-2014
		List grouPersonnelMasterList = new ArrayList();
		String project_name = "", password = "", dow = "", dob = "", telephone = "", address = "", position = "",
				dateTime = "", authen_type_name = "", manday="";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.project_code, a.username, a.`password`, a.`name`, a.lastname, a.dow, "
					+ "a.dob, a.telephone, a.address, a.position, a.authen_type, a.datetime, a.manday, b.project_name, c.authen_type_name "
					+ "FROM employee a " + "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "INNER JOIN authen_master c on(c.authen_type = a.authen_type) " + "WHERE ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!personnel_id.equals(""))
				sqlStmt = sqlStmt + "a.username like '" + personnel_id + "' AND ";
			if (!personnel_name.equals(""))
				sqlStmt = sqlStmt + "a.name like '" + personnel_name + "%' AND ";
			if (!personnel_lastname.equals(""))
				sqlStmt = sqlStmt + "a.lastname like '" + personnel_lastname + "%' AND ";

			sqlStmt = sqlStmt + "a.username <> '' order by a.username desc";

			String forwhat = "personnel";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				personnel_id = rs.getString("username");
				personnel_name = rs.getString("name");
				personnel_lastname = rs.getString("lastname");
				authen_type = rs.getString("authen_type");
				authen_type_name = rs.getString("authen_type_name");
				project_name = rs.getString("project_name");
				project_code = rs.getString("project_code");
				dow = rs.getString("dow");
				dob = rs.getString("dob");
				telephone = rs.getString("telephone");
				address = rs.getString("address");
				position = rs.getString("position");
				dateTime = rs.getString("datetime");
				manday	= rs.getString("manday");

				dow = dateUtil.CnvToDDMMYYYY_Date(dateUtil.CnvYYYYMMDDToYYYYMMDDThaiYear(dow, "-"), "-");
				dob = dateUtil.CnvToDDMMYYYY_Date(dateUtil.CnvYYYYMMDDToYYYYMMDDThaiYear(dob, "-"), "-");
				dateTime = dateUtil.CnvToDDMMYYYY_Date(dateTime, "-");
				// dateTime = dateTime.replace(".0", "");
				/*
				 * DateUtil dateUtil = new DateUtil(); String date =
				 * dateUtil.curDateTH(); String time = dateUtil.curTime();
				 * String dateTime = date+" "+time; // amount =
				 * df2.format(Float.parseFloat(amount));
				 */

				grouPersonnelMasterList.add(new PersonnelMasterModel(forwhat, personnel_id, personnel_name,
						personnel_lastname, authen_type, authen_type_name, project_code, project_name, dow, dob,
						telephone, address, position, dateTime, manday));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return grouPersonnelMasterList;
	}

	public void AddPersonnelMaster(String project_code, String personnel_id, String personnel_name,
			String personnel_lastname, String authen_type, String dow, String dob, String telephone, String address,
			String position, String manday) throws Exception {
		// DateUtil dateUtil = new DateUtil();
		conn = agent.getConnectMYSql();

		String encrypPass = encrypt("password");
		// String dateTime = dateUtil.curDateTime();
		String sqlStmt = "INSERT INTO `employee` (project_code, username, `password`, `name`, lastname, authen_type, dow, "
				+ "dob, telephone, address, position, manday, datetime) " + "VALUES ('" + project_code + "', '" + personnel_id
				+ "', '" + encrypPass + "', '" + personnel_name + "','" + personnel_lastname + "','" + authen_type
				+ "', " + "'" + dow + "','" + dob + "','" + telephone + "','" + address + "','" + position
				+ "', '" + manday + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean checkPassword(String personnel_id, String password_old) throws Exception {
		boolean chkpass = false;
		String password = "";
		String encrypPass = encrypt(password_old);

		conn = agent.getConnectMYSql();

		String sqlStmt = "Select password from employee " + "WHERE username = '" + personnel_id + "'";
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			password = rs.getString("password");
		}

		if (encrypPass.equals(password)) {
			chkpass = true;
		}

		return chkpass;
	}

	public void UpdatePassword(String personnel_id, String password_new) throws Exception {
		conn = agent.getConnectMYSql();
		String encrypPass = encrypt(password_new);

		String sqlStmt = "UPDATE employee set password = '" + encrypPass + "'" + "WHERE username = '" + personnel_id
				+ "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdatePersonnelMaster(String project_code, String personnel_id, String personnel_name,
			String personnel_lastname, String authen_type, String dow, String dob, String telephone, String address,
			String position, String manday) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE employee set project_code = '" + project_code + "', name = '" + personnel_name
				+ "', lastname = '" + personnel_lastname + "',authen_type = '" + authen_type + "', " + "dow = '" + dow
				+ "', dob = '" + dob + "', telephone = '" + telephone + "', address = '" + address + "', position = '"
				+ position + "', manday = '" + manday + "', datetime = now()" + "WHERE username = '" + personnel_id + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdatePersonnel_Profile(String personnel_id, String personnel_name, String personnel_lastname,
			String dob, String telephone, String address, String position) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE employee set name = '" + personnel_name + "', lastname = '" + personnel_lastname
				+ "', " + "dob = '" + dob + "', telephone = '" + telephone + "', address = '" + address
				+ "', position = '" + position + "', datetime = now()" + "WHERE username = '" + personnel_id + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateProfile_Password(String personnel_id, String password) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE employee set password = '" + password + "'" + "WHERE username = '" + personnel_id
				+ "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeletePersonnelMaster(String personnel_id) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From employee " + "WHERE username = '" + personnel_id + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean getCheckMaster(String groupcostCode) throws Exception {

		boolean chkCustomer = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT * " + "FROM groupcostcode_master WHERE gcostcode = '" + groupcostCode + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkCustomer = true;
		}

		conn.close();
		rs.close();
		pStmt.close();

		return chkCustomer;
	}

	public List getListProject_User_Personnel(String project_code) throws IOException, Exception {
		String project_name = "";
		String sqlWhere = "";
		if (!project_code.equals("")) {
			sqlWhere += "project_master.project_code = '" + project_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "project_master.project_code, " + "project_master.project_name " + "FROM "
				+ "project_master where ";
		sqlQuery += sqlWhere;
		sqlQuery += "project_master.project_code <> '' order by project_master.project_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListProject_User_Personnel = new ArrayList();
		while (rs.next()) {
			project_code = rs.getString("project_code");
			project_name = rs.getString("project_name");

			getListProject_User_Personnel.add(new PersonnelMasterModel(project_code, project_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListProject_User_Personnel;
	}
}
