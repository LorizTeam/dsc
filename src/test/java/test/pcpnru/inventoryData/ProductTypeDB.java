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
import test.pcpnru.inventoryModel.ProductTypeModel;

public class ProductTypeDB {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_AddProductTypeID() throws IOException, Exception {

		String sqlQuery = "select MAX(protype_id) as protype_id from producttype_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("protype_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String protype_id) {
		if (protype_id == null) {

			protype_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(protype_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				protype_id = "000" + ResultString_plusone;
				break;
			case 2:
				protype_id = "00" + ResultString_plusone;
				break;
			case 3:
				protype_id = "0" + ResultString_plusone;
				break;
			case 4:
				protype_id = ResultString_plusone;
				break;
			}

		}

		return protype_id;
	}

	public int AddProductType(String protype_id, String protype_name, String create_by) {
		String sqlQuery = "insert into producttype_master (protype_id,protype_name,create_by,create_datetime) "
				+ "values (?,?,?,now())";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, protype_id);
			ppStmt.setString(2, protype_name);
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

	public Boolean DeleteProductType(String protype_id) {
		String sqlQuery = "delete from producttype_master where protype_id = ?";
		Boolean delete_success = false;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, protype_id);
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

	public int UpdateProductType(String protype_id, String protype_name, String update_by) {
		String sqlQuery = "update producttype_master set protype_id = ? , protype_name = ? , update_by = ? , update_datetime = now() "
				+ "where protype_id = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, protype_id);
			ppStmt.setString(2, protype_name);
			ppStmt.setString(3, update_by);
			ppStmt.setString(4, protype_id);
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

	public List<ProductTypeModel> Get_ProductTypeList(String protype_id, String protype_name)
			throws IOException, Exception {

		String sqlQuery = "select * from producttype_master where ";

		if (new Validate().Check_String_notnull_notempty(protype_id))
			sqlQuery += "protype_id = '" + protype_id + "' and ";

		if (new Validate().Check_String_notnull_notempty(protype_name))
			sqlQuery += "protype_name like '%" + protype_name + "%' and ";

		sqlQuery += "protype_id != '' ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<ProductTypeModel> ResultList = new ArrayList<ProductTypeModel>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new ProductTypeModel(rs.getString("protype_id"), rs.getString("protype_name"),
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
