package pcpnru.projectData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.projectModel.*;
import pcpnru.utilities.*;

public class CostCodeMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetCostCodeMasterList(String costCode, String costName, String gcostcode) throws Exception { // 30-05-2014
		List costCodeMasterList = new ArrayList();
		String dateTime = "", gcostcode_name = "";
		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.costcode,a.costname,a.percentprice, DATE_FORMAT(a.datetime,'%d-%m-%Y %H:%i') as datetime,a.gcostcode,b.gcostcode_name "
					+ "FROM costcode_master AS a "
					+ "INNER JOIN groupcostcode_master AS b ON b.gcostcode = a.gcostcode " +

			"WHERE ";
			if (!costCode.equals(""))
				sqlStmt = sqlStmt + "a.costcode like '" + costCode + "%' AND ";
			if (!costName.equals(""))
				sqlStmt = sqlStmt + "a.costname like '" + costName + "%' AND ";
			if (!gcostcode.equals(""))
				sqlStmt = sqlStmt + "a.gcostcode like '" + gcostcode + "%' AND ";

			sqlStmt = sqlStmt + "a.costcode <> '' order by datetime desc";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			String percentprice = "";
			while (rs.next()) {
				costCode = rs.getString("costcode");
				if (rs.getString("costname") != null)
					costName = rs.getString("costname");
				else
					costName = "";
				gcostcode = rs.getString("gcostcode");
				gcostcode_name = rs.getString("gcostcode_name");
				dateTime = rs.getString("datetime");
				String day = dateTime.substring(0, 2);
				String month = dateTime.substring(3, 5);
				String year = Integer.toString((Integer.parseInt(dateTime.substring(6, 10)) + 543));
				percentprice = rs.getString("percentprice");
				String time = dateTime.substring(11);
				dateTime = day + "-" + month + "-" + year + " " + time;
				// amount = df2.format(Float.parseFloat(amount));

				costCodeMasterList.add(
						new CostCodeMasterForm(costCode, costName, dateTime, gcostcode, gcostcode_name, percentprice));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return costCodeMasterList;
	}

	public void AddCostCodeMaster(String gcostcode, String costCode, String costName, String percentprice)
			throws Exception {

		if (!getCheckMaster(costCode)) {
			conn = agent.getConnectMYSql();

			String dateTime = "";
			String sqlStmt = "INSERT INTO `costcode_master` (`costcode`, `costname`,gcostcode, datetime ,percentprice) VALUES "
					+ "('" + costCode + "', '" + costName + "','" + gcostcode + "', now(),'" + percentprice + "')";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			pStmt.executeUpdate(sqlStmt);
			pStmt.close();
			conn.close();
		}

	}

	public void UpdateCostCodeMaster(String costCode, String costName, String costCodeHD, String gcostcode,
			String percentprice) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE costcode_master set costcode = '" + costCode + "', costname = '" + costName
				+ "', datetime = now(),percentprice = '" + percentprice + "' ,gcostcode = '" + gcostcode + "'"
				+ "WHERE costcode = '" + costCodeHD + "'";
		System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteCostCodeMaster(String costCode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From costcode_master " + "WHERE costCode = '" + costCode + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean getCheckMaster(String costCode) throws Exception {

		boolean chkcostcode = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT costcode " + "FROM costcode_master WHERE costcode = '" + costCode + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkcostcode = true;
		}

		conn.close();
		pStmt.close();
		rs.close();

		return chkcostcode;
	}
}
