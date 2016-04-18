package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.PageGroupMasterModel;
import pcpnru.utilities.*;

public class PageGroupMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getListGroupPage(String pagegroup_code) throws IOException, Exception {
		String pagegroup_name = "";
		String sqlWhere = "";
		if (!pagegroup_code.equals("")) {
			sqlWhere += "page_master.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "pagegroup_master.pagegroup_code, " + "pagegroup_master.pagegroup_name " + "FROM "
				+ "pagegroup_master where ";
		sqlQuery += sqlWhere;
		sqlQuery += "pagegroup_master.pagegroup_code <> '' order by pagegroup_master.pagegroup_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListPage = new ArrayList();
		while (rs.next()) {
			pagegroup_code = rs.getString("pagegroup_code");
			pagegroup_name = rs.getString("pagegroup_name");

			getListPage.add(new PageGroupMasterModel(pagegroup_code, pagegroup_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListPage;
	}

	public void AddPageGroupMaster(String pagegroup_code, String pagegroup_name) throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "INSERT INTO pagegroup_master (pagegroup_code, pagegroup_name, datetime) " + "VALUES ('"
				+ pagegroup_code + "', '" + pagegroup_name + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdatePageGroupMaster(String pagegroup_code, String pagegroup_name) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE pagegroup_master set pagegroup_name = '" + pagegroup_name + "', datetime = now()"
				+ "WHERE pagegroup_code = '" + pagegroup_code + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeletePageGroupMaster(String pagegroup_code) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From pagegroup_master " + "WHERE pagegroup_code = '" + pagegroup_code + "'";
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

			String sqlStmt = "SELECT max(pagegroup_code) as lno FROM pagegroup_master " + "WHERE pagegroup_code <> '' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
				if (null == requestno || "".equals(requestno)) {

					requestno = "0";
				}
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);
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
