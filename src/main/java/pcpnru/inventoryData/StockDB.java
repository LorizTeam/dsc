package pcpnru.inventoryData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;
import pcpnru.inventoryModel.StockModel;

public class StockDB {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_StockID() throws IOException, Exception {

		String sqlQuery = "select MAX(stock_id) as stock_id from stock_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("stock_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String stock_id) {
		if (stock_id == null) {

			stock_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(stock_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				stock_id = "000" + ResultString_plusone;
				break;
			case 2:
				stock_id = "00" + ResultString_plusone;
				break;
			case 3:
				stock_id = "0" + ResultString_plusone;
				break;
			case 4:
				stock_id = ResultString_plusone;
				break;
			}

		}

		return stock_id;
	}

	public int AddStock(String stock_id, String stock_name, String create_by) {
		String sqlQuery = "insert into stock_master (stock_id,stock_name,create_by,create_datetime) "
				+ "values (?,?,?,now())";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, stock_id);
			ppStmt.setString(2, stock_name);
			ppStmt.setString(3, create_by);
			rowsupdate = ppStmt.executeUpdate();
			conn.commit();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!ppStmt.isClosed())
					ppStmt.close();
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowsupdate;
	}

	public Boolean DeleteBrand(String stock_id) {
		String sqlQuery = "delete from stock_master where stock_id = ?";
		Boolean delete_success = false;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, stock_id);
			int rowsupdate = ppStmt.executeUpdate();
			conn.commit();

			if (rowsupdate > 0)
				delete_success = true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!ppStmt.isClosed())
					ppStmt.close();
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return delete_success;
	}

	public int UpdateBrand(String stock_id, String stock_name, String update_by) {
		String sqlQuery = "update stock_master set stock_id = ? , stock_name = ? , update_by = ? , update_datetime = now() "
				+ "where stock_id = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, stock_id);
			ppStmt.setString(2, stock_name);
			ppStmt.setString(3, update_by);
			ppStmt.setString(4, stock_id);
			rowsupdate = ppStmt.executeUpdate();
			conn.commit();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!ppStmt.isClosed())
					ppStmt.close();
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowsupdate;
	}

	public List<StockModel> Get_StockList(String stock_id, String stock_name) throws IOException, Exception {
		String sqlQuery = "select * from stock_master where ";

		if (new Validate().Check_String_notnull_notempty(stock_id))
			sqlQuery += "stock_id = '" + stock_id + "' and ";

		if (new Validate().Check_String_notnull_notempty(stock_name))
			sqlQuery += "stock_name like '%" + stock_name + "%' and ";

		sqlQuery += "stock_id != '' ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<StockModel> ResultList = new ArrayList<StockModel>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new StockModel(rs.getString("stock_id"), rs.getString("stock_name"),
					rs.getString("create_by"), rs.getString("create_datetime"), rs.getString("update_by"),
					rs.getString("update_datetime")));
		}

		if (!rs.isClosed())
			rs.close();
		if (!pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ResultList;
	}
}
