package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.masterModel.PageMasterModel;
import pcpnru.utilities.*;

public class PageMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getListPage(String pagegroup_code, String page_code) throws IOException, Exception {
		String pagegroup_name = "", page_name = "";
		String sqlWhere = "";
		if (!page_code.equals("")) {
			sqlWhere += "page_master.page_code = '" + page_code + "' AND ";
		} else if (!pagegroup_code.equals("")) {
			sqlWhere += "page_master.pagegroup_code = '" + pagegroup_code + "' AND ";
		}

		String sqlQuery = "SELECT page_master.pagegroup_code, " + "page_master.page_code, " + "page_master.page_name, "
				+ "pagegroup_master.pagegroup_name " + "FROM "
				+ "page_master left join pagegroup_master on(pagegroup_master.pagegroup_code = page_master.pagegroup_code) "
				+ "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "page_master.page_code <> '' order by page_master.page_code ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListPage = new ArrayList();
		while (rs.next()) {
			pagegroup_code = rs.getString("pagegroup_code");
			pagegroup_name = rs.getString("pagegroup_name");
			page_code = rs.getString("page_code");
			page_name = rs.getString("page_name");

			getListPage.add(new PageMasterModel(pagegroup_code, pagegroup_name, page_code, page_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListPage;
	}

	public void AddPageMaster(String pagegroup_code, String page_code, String page_name) throws Exception {

		conn = agent.getConnectMYSql();

		String dateTime = "";
		String sqlStmt = "INSERT INTO page_master (pagegroup_code, page_code, page_name, datetime) " + "VALUES ('"
				+ pagegroup_code + "', '" + page_code + "', '" + page_name + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdatePageMaster(String pagegroup_code, String page_code, String page_name) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE page_master set page_name = '" + page_name + "', datetime = now()"
				+ "WHERE pagegroup_code = '" + pagegroup_code + "' and page_code = '" + page_code + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeletePageMaster(String pagegroup_code, String page_code) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From page_master " + "WHERE pagegroup_code = '" + pagegroup_code
				+ "' and page_code = '" + page_code + "'";
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

	public String SelectUpdateDocNo() throws Exception {
		String requestno = "", typeR = "";
		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(page_code) as lno FROM page_master " + "WHERE page_code <> '' ";
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
