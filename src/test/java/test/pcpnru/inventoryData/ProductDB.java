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
import test.pcpnru.inventoryModel.ProductModel;

public class ProductDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public int AddProduct(ProductModel productmodel) {
		String sqlQuery = "insert ignore into product (product_code, product_name, status_id, progroup_id, protype_id"
				+ ", unit_id, brand_id, create_by, create_datetime) " + "values (?,?,?,?,?,?,?,?,now())";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, productmodel.getProduct_code());
			ppStmt.setString(2, productmodel.getProduct_name());
			ppStmt.setString(3, productmodel.getStatus_id());
			ppStmt.setString(4, productmodel.getProgroup_id());
			ppStmt.setString(5, productmodel.getProtype_id());
			ppStmt.setString(6, productmodel.getUnit_id());
			ppStmt.setString(7, productmodel.getBrand_id());
			ppStmt.setString(8, productmodel.getStatus_id());
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

	public int DeleteProduct(String product_code) {
		String sqlQuery = "delete from product where product_code = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, product_code);
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

	public int UpdateProduct(ProductModel productmodel) {
		String sqlQuery = "update product set product_code = ? , product_name = ? , status_id = ? , progroup_id = ? "
				+ ", protype_id = ? , unit_id = ? , brand_id = ?, update_by = ? , update_datetime = now() "
				+ "where product_code = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, productmodel.getProduct_code());
			ppStmt.setString(2, productmodel.getProduct_name());
			ppStmt.setString(3, productmodel.getStatus_id());
			ppStmt.setString(4, productmodel.getProgroup_id());
			ppStmt.setString(5, productmodel.getProtype_id());
			ppStmt.setString(6, productmodel.getUnit_id());
			ppStmt.setString(7, productmodel.getBrand_id());
			ppStmt.setString(8, productmodel.getUpdate_by());
			ppStmt.setString(9, productmodel.getProduct_code());
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

	public List<ProductModel> Get_ProductList(ProductModel productmodel) throws IOException, Exception {
		String sqlQuery = "SELECT "
				+ "product.product_code, product.product_name, product.status_id, `status`.status_name, "
				+ "product.progroup_id, productgroup_master.progroup_name, product.protype_id, producttype_master.protype_name, "
				+ "product.unit_id, unit_master.unit_name, product.brand_id, brand_master.brand_name, product.create_by, "
				+ "product.create_datetime, product.update_by, product.update_datetime " + "FROM " + "product "
				+ "INNER JOIN brand_master ON brand_master.brand_id = product.brand_id "
				+ "INNER JOIN productgroup_master ON productgroup_master.progroup_id = product.progroup_id "
				+ "INNER JOIN producttype_master ON producttype_master.protype_id = product.protype_id "
				+ "INNER JOIN `status` ON `status`.status_id = product.status_id "
				+ "INNER JOIN unit_master ON unit_master.unit_id = product.unit_id where ";
		if (productmodel != null) {
			if (new Validate().Check_String_notnull_notempty(productmodel.getProduct_code()))
				sqlQuery += "product_code = '" + productmodel.getProduct_code() + "' and ";

			if (new Validate().Check_String_notnull_notempty(productmodel.getProduct_name()))
				sqlQuery += "product_name like '%" + productmodel.getProduct_name() + "%' and ";
		}
		sqlQuery += "product_code != '' order by product.create_datetime desc";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<ProductModel> ResultList = new ArrayList<ProductModel>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new ProductModel(rs.getString("product_code"), rs.getString("product_name"),
					rs.getString("status_id"), rs.getString("status_name"), rs.getString("progroup_id"),
					rs.getString("progroup_name"), rs.getString("protype_id"), rs.getString("protype_name"),
					rs.getString("unit_id"), rs.getString("unit_name"), rs.getString("brand_id"),
					rs.getString("brand_name"), rs.getString("create_by"), rs.getString("create_datetime"),
					rs.getString("update_by"), rs.getString("update_datetime")));
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