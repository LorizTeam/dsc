package test.pcpnru.inventoryData;

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
import test.pcpnru.inventoryModel.ProductGroupModel;

public class ProductGroupDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_AddProductGroupID() throws IOException, Exception {

		String sqlQuery = "select MAX(progroup_id) as progroup_id from productgroup_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("progroup_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String progroup_id) {
		if (progroup_id == null) {

			progroup_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(progroup_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				progroup_id = "000" + ResultString_plusone;
				break;
			case 2:
				progroup_id = "00" + ResultString_plusone;
				break;
			case 3:
				progroup_id = "0" + ResultString_plusone;
				break;
			case 4:
				progroup_id = ResultString_plusone;
				break;
			}

		}

		return progroup_id;
	}

	public int AddProductGroup(String progroup_id, String progroup_name, String create_by) {
		String sqlQuery = "insert into productgroup_master (progroup_id,progroup_name,create_by,create_datetime) "
				+ "values (?,?,?,now())";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, progroup_id);
			ppStmt.setString(2, progroup_name);
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

	public Boolean DeleteProductGroup(String progroup_id) {
		String sqlQuery = "delete from productgroup_master where progroup_id = ?";
		Boolean delete_success = false;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, progroup_id);
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

	public int UpdateProductGroup(String progroup_id, String progroup_name, String update_by) {
		String sqlQuery = "update productgroup_master set progroup_id = ? , progroup_name = ? , update_by = ? , update_datetime = now() "
				+ "where progroup_id = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, progroup_id);
			ppStmt.setString(2, progroup_name);
			ppStmt.setString(3, update_by);
			ppStmt.setString(4, progroup_id);
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

	public List<ProductGroupModel> Get_ProductGroupList(String progroup_id, String progroup_name)
			throws IOException, Exception {
		String sqlQuery = "select * from productgroup_master where ";

		if (new Validate().Check_String_notnull_notempty(progroup_id))
			sqlQuery += "progroup_id = '" + progroup_id + "' and ";

		if (new Validate().Check_String_notnull_notempty(progroup_name))
			sqlQuery += "progroup_name like '%" + progroup_name + "%' and ";

		sqlQuery += "progroup_id != '' ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<ProductGroupModel> ResultList = new ArrayList<ProductGroupModel>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new ProductGroupModel(rs.getString("progroup_id"), rs.getString("progroup_name"),
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
