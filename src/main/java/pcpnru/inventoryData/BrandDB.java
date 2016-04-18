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
import pcpnru.inventoryModel.BrandModel;

public class BrandDB {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_BrandID() throws IOException, Exception {

		String sqlQuery = "select MAX(brand_id) as brand_id from brand_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("brand_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String brand_id) {
		if (brand_id == null) {

			brand_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(brand_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				brand_id = "000" + ResultString_plusone;
				break;
			case 2:
				brand_id = "00" + ResultString_plusone;
				break;
			case 3:
				brand_id = "0" + ResultString_plusone;
				break;
			case 4:
				brand_id = ResultString_plusone;
				break;
			}

		}

		return brand_id;
	}

	public int AddBrand(String brand_id, String brand_name, String create_by) {
		String sqlQuery = "insert into brand_master (brand_id,brand_name,create_by,create_datetime) "
				+ "values (?,?,?,now())";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, brand_id);
			ppStmt.setString(2, brand_name);
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

	public Boolean DeleteBrand(String brand_id) {
		String sqlQuery = "delete from brand_master where brand_id = ?";
		Boolean delete_success = false;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, brand_id);
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

	public int UpdateBrand(String brand_id, String brand_name, String update_by) {
		String sqlQuery = "update brand_master set brand_id = ? , brand_name = ? , update_by = ? , update_datetime = now() "
				+ "where brand_id = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, brand_id);
			ppStmt.setString(2, brand_name);
			ppStmt.setString(3, update_by);
			ppStmt.setString(4, brand_id);
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

	public List<BrandModel> Get_BrandList(String brand_id, String brand_name) throws IOException, Exception {
		String sqlQuery = "select * from brand_master where ";

		if (new Validate().Check_String_notnull_notempty(brand_id))
			sqlQuery += "brand_id = '" + brand_id + "' and ";

		if (new Validate().Check_String_notnull_notempty(brand_name))
			sqlQuery += "brand_name like '%" + brand_name + "%' and ";

		sqlQuery += "brand_id != '' ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<BrandModel> ResultList = new ArrayList<BrandModel>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new BrandModel(rs.getString("brand_id"), rs.getString("brand_name"),
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
