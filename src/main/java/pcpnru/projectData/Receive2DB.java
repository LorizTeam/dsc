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

public class Receive2DB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetReceiveList(String docNo, String projectcode) throws Exception { // 30-05-2014
		List ReceiveList = new ArrayList();

		String itemNo = "", receivedetail = "", description = "", qty = "", amount = "", amountTotal = "";

		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT docno, projectcode, itemno, receivedetail, description, qty, amount, amounttotal "
					+ "FROM receivedt WHERE docno = '" + docNo + "' and projectcode = '" + projectcode + "' and ";

			sqlStmt = sqlStmt + "docno <> '' order by itemno";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				docNo = rs.getString("docno");
				itemNo = rs.getString("itemno");
				receivedetail = rs.getString("receivedetail");
				if (rs.getString("description") != null)
					description = rs.getString("description");
				else
					description = "";
				qty = rs.getString("qty");
				amount = rs.getString("amount");
				amountTotal = rs.getString("amounttotal");

				itemNo = Integer.toString(Integer.parseInt(itemNo));

				ReceiveList
						.add(new ReceiveForm(docNo, itemNo, receivedetail, description, qty, amount, amountTotal, ""));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return ReceiveList;
	}

	public void AddReceiveDT(String docNo, String vol, String projectCode, String receivedetail, String description,
			String qty, String amount, String amountTotal, String costcode) throws Exception {

		conn = agent.getConnectMYSql();

		String itemNo = "0";
		String sqlStmt = "SELECT itemno FROM receivedt WHERE " + "docno = '" + docNo + "' "
				+ "ORDER BY itemno DESC LIMIT 1";

		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);
		while (rs.next()) {
			itemNo = rs.getString("itemno");
		}
		rs.close();
		pStmt.close();

		itemNo = String.valueOf(Integer.parseInt(itemNo) + 1);
		if (itemNo.length() == 1)
			itemNo = "00" + itemNo;
		if (itemNo.length() == 2)
			itemNo = "0" + itemNo;

		sqlStmt = "INSERT IGNORE INTO receivedt(docno, vol, projectcode, itemno, receivedetail, description, qty, amount, amountTotal, costcode) "
				+ "VALUES ('" + docNo + "', '" + vol + "', '" + projectCode + "', '" + itemNo + "', '" + receivedetail
				+ "', '" + description + "', '" + qty + "', '" + amount + "', '" + amountTotal + "', '" + costcode
				+ "')";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateReceive(String docNo, String vol, String projectCode, String itemNo, String receivedetail,
			String description, String qty, String amount, String amountTotal) throws Exception {
		conn = agent.getConnectMYSql();

		if (itemNo.length() == 1)
			itemNo = "00" + itemNo;
		if (itemNo.length() == 2)
			itemNo = "0" + itemNo;

		String sqlStmt = "UPDATE receivedt set receivedetail = '" + receivedetail + "', description = '" + description
				+ "', qty = '" + qty + "', amount = '" + amount + "', amounttotal = '" + amountTotal + "' "
				+ "WHERE docno = '" + docNo + "' and vol = '" + vol + "' and projectcode = '" + projectCode
				+ "' and itemno = '" + itemNo + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteReceiveDT(String docNo, String vol, String projectCode, String itemNo) throws Exception {
		conn = agent.getConnectMYSql();

		if (itemNo.length() == 1)
			itemNo = "00" + itemNo;
		if (itemNo.length() == 2)
			itemNo = "0" + itemNo;

		String sqlStmt = "DELETE From receivedt " + "WHERE docno = '" + docNo + "' and vol = '" + vol
				+ "' and projectcode = '" + projectCode + "' and itemno = '" + itemNo + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String getSumAmount(String docNo, String projectCode) throws Exception {

		String amountTotal = "";

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT sum(amounttotal) as att " + "FROM receivedt WHERE docno = '" + docNo
				+ "' and projectcode = '" + projectCode + "' Group by docno ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			amountTotal = rs.getString("att");
		}

		rs.close();
		pStmt.close();
		conn.close();

		return amountTotal;
	}

	public String SumReceive(String docNo, String projectcode) throws Exception { // 30-05-2014

		String itemNo = "", description = "", qty = "", amount = "", amountTotal = "";

		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT sum(amounttotal) as sumamt " + "FROM receivedt WHERE docno = '" + docNo
					+ "' and projectcode = '" + projectcode + "'";

			sqlStmt = sqlStmt + "group by docno order by itemno";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {

				amountTotal = rs.getString("sumamt");

			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return amountTotal;
	}

	public void UpdateReceiveMoney(String docNo, String projectCode, String torn, String receiveAmt) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE receivehd set receiveamount = '" + receiveAmt + "', changeamount = '" + torn + "' "
				+ "WHERE docno = '" + docNo + "' and projectcode = '" + projectCode + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public String getReceiveAmount(String docNo, String projectCode) throws Exception {
		String receiveAmt = "";

		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT receiveamount " + "FROM receivehd WHERE  docno = '" + docNo
					+ "' and projectcode = '" + projectCode + "' ";

			sqlStmt = sqlStmt + "group by docno, projectcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				receiveAmt = rs.getString("receiveamount");
				if (receiveAmt != null)
					receiveAmt = rs.getString("receiveamount");
				else
					receiveAmt = "0";
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return receiveAmt;
	}
}
