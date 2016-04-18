package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.AuthenPageMasterModel;
import pcpnru.utilities.*;

public class AuthenPageMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getListAuthenPage(String authen_type, String page_code) throws IOException, Exception {
		String authen_name = "", page_name = "";
		String sqlWhere = "";
		if (!authen_type.equals("")) {
			sqlWhere += "a.authen_type = '" + authen_type + "' AND ";
		} else if (!page_code.equals("")) {
			sqlWhere += "a.page_code = '" + page_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "a.authen_type, " + "b.authen_type_name, " + "a.page_code, " + "c.page_name "
				+ "FROM " + "authen_page a " + "INNER JOIN authen_master b on(b.authen_type = a.authen_type) "
				+ "INNER JOIN page_master c on(c.page_code = a.page_code) " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "a.authen_type <> '' order by a.authen_type, a.page_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListAuthenPage = new ArrayList();
		while (rs.next()) {
			authen_type = rs.getString("authen_type");
			authen_name = rs.getString("authen_type_name");
			page_code = rs.getString("page_code");
			page_name = rs.getString("page_name");

			getListAuthenPage.add(new AuthenPageMasterModel(authen_type, authen_name, page_code, page_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListAuthenPage;
	}

	public void AddAuthenPageMaster(String authen_type, String page_code) throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "INSERT INTO authen_page (authen_type, page_code, datetime) " + "VALUES ('" + authen_type
				+ "', '" + page_code + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateAuthenPageMaster(String authen_type, String page_code) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE authen_page set page_code = '" + page_code + "', datetime = now()"
				+ "WHERE authen_type = '" + authen_type + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteAuthenPageMaster(String authen_type, String page_code) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From authen_page " + "WHERE authen_type = '" + authen_type + "' and page_code = '"
				+ page_code + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void SaveAuthenPage(String authen_type, String page_code) throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "INSERT INTO authen_page (authen_type, page_code, datetime) " + "VALUES ('" + authen_type
				+ "', '" + page_code + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void unSaveAuthenPage(String authen_type) throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "DELETE From authen_page " + "WHERE authen_type = '" + authen_type + "'";
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

	public int countGroupPage(String pagegroup_code) throws IOException, Exception {
		int countN = 0;
		String sqlWhere = "";
		if (!pagegroup_code.equals("")) {
			sqlWhere += "a.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "count(a.pagegroup_code) as countn " + "FROM " + "pagegroup_master a " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "a.pagegroup_code <> '' order by a.pagegroup_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		while (rs.next()) {
			countN = rs.getInt("countn");
		}

		rs.close();
		pStmt.close();
		conn.close();

		return countN;
	}

	public List getGroupPageCode(String pagegroup_code) throws IOException, Exception {

		String sqlWhere = "";
		if (!pagegroup_code.equals("")) {
			sqlWhere += "a.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "a.pagegroup_code " + "FROM " + "pagegroup_master a " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "a.pagegroup_code <> '' order by a.pagegroup_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<String> listpagegroup = new ArrayList<String>();

		while (rs.next()) {
			listpagegroup.add(rs.getString("pagegroup_code"));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return listpagegroup;
	}

	public List getGroupPageName(String pagegroup_code) throws IOException, Exception {

		String sqlWhere = "";
		if (!pagegroup_code.equals("")) {
			sqlWhere += "a.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "a.pagegroup_name " + "FROM " + "pagegroup_master a " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "a.pagegroup_code <> '' order by a.pagegroup_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<String> listpagegroup = new ArrayList<String>();

		while (rs.next()) {
			listpagegroup.add(rs.getString("pagegroup_name"));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return listpagegroup;
	}

	public List getPage(String pagegroup_code) throws IOException, Exception {
		String page_code = "", page_name = "";
		String sqlWhere = "";
		if (!pagegroup_code.equals("")) {
			sqlWhere += "a.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT " + "a.page_code, a.page_name " + "FROM " + "page_master a " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "a.pagegroup_code <> '' order by a.pagegroup_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListAuthenPage = new ArrayList();
		while (rs.next()) {

			page_code = rs.getString("page_code");
			page_name = rs.getString("page_name");

			getListAuthenPage.add(new AuthenPageMasterModel(page_code, page_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListAuthenPage;
	}

	public boolean chkAuthenPage(String authen_code, String page_code) throws IOException, Exception {
		boolean chkauthenpage = false;
		String sqlWhere = "";
		if (!authen_code.equals("")) {
			sqlWhere += "a.authen_type = '" + authen_code + "' AND ";
		}
		if (!authen_code.equals("")) {
			sqlWhere += "a.page_code = '" + page_code + "' ";
		}

		String sqlQuery = "SELECT " + "* " + "FROM " + "authen_page a " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "group by a.authen_type, a.page_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListAuthenPage = new ArrayList();
		while (rs.next()) {
			chkauthenpage = true;
		}

		rs.close();
		pStmt.close();
		conn.close();

		return chkauthenpage;
	}
}
