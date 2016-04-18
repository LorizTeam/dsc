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

public class CreateCentralBudgetDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetUnitMasterList(String year) throws Exception { // 09-03-2016
		String amt = "";
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		List CentralBudgetList = new ArrayList();
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT year, budget_central " + "FROM central_budget " + "WHERE ";
			if (!year.equals(""))
				sqlStmt = sqlStmt + "year like '" + year + "%' AND ";

			sqlStmt = sqlStmt + "year <> '' order by year";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				year = rs.getString("year");
				amt = rs.getString("budget_central");

				amt = df2.format(Float.parseFloat(amt));

				CentralBudgetList.add(new CentralBudgetForm(year, amt));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return CentralBudgetList;
	}

	public void AddCentralBudget(String year, String amt) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO central_budget(year,budget_central) " + "VALUES ('" + year + "','" + amt
				+ "')";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateCentralBudget(String year, String amt) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE central_budget set budget_central = '" + amt + "' " + "WHERE year = '" + year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();

	}

	public void DeleteCentralBudget(String year) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From central_budget " + "WHERE year = '" + year + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean checkYear(String year) throws IOException, Exception {
		boolean checkhave = true;

		conn = agent.getConnectMYSql();

		String sqlQuery = "SELECT year " + "FROM central_budget " + "where year = '" + year + "'";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			checkhave = false;
		}
		rs.close();
		pStmt.close();
		conn.close();
		return checkhave;
	}
}
