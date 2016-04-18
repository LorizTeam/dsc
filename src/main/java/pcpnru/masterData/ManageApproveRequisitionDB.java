package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.utilities.*;

public class ManageApproveRequisitionDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getListAuthen(String authen_type) throws IOException, Exception {
		String authen_type_name = "";
		String sqlWhere = "";
		if (!authen_type.equals("")) {
			sqlWhere += "authen_master.authen_type = '" + authen_type + "' AND ";
		}

		String sqlQuery = "SELECT " + "authen_master.authen_type, " + "authen_master.authen_type_name " + "FROM "
				+ "authen_master where ";
		sqlQuery += sqlWhere;
		sqlQuery += "authen_master.authen_type <> '' order by authen_master.authen_type ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListAuthen = new ArrayList();
		while (rs.next()) {
			authen_type = rs.getString("authen_type");
			authen_type_name = rs.getString("authen_type_name");

			getListAuthen.add(new AuthenMasterModel(authen_type, authen_type_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListAuthen;
	}

	public void AddManageApproveRequisition(String manageapprove, String manageapprovename, String budget)
			throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "INSERT INTO manage_approve (manageapprove, manageapprovename, budget, datetime) "
				+ "VALUES ('" + manageapprove + "', '" + manageapprovename + "', '" + budget + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateAuthenMaster(String authen_type, String authen_type_name) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE authen_master set authen_type_name = '" + authen_type_name + "', datetime = now()"
				+ "WHERE authen_type = '" + authen_type + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteAuthenMaster(String authen_type) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From authen_master " + "WHERE authen_type = '" + authen_type + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String SelectUpdateDocNo() throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(authen_type) as lno FROM authen_master " + "WHERE authen_type <> '' ";
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
				requestno = "00" + requestno;
			} else if (requestno.length() == 2) {
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
