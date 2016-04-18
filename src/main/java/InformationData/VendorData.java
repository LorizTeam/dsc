package InformationData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import InformationModel.VendorModel;
import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;

public class VendorData {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_VendorID() throws IOException, Exception {

		String sqlQuery = "select MAX(vendor_id) as vendor_id from vendor_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("vendor_id");
		}

		if (!rs.isClosed())
			rs.close();
		if (!pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ResultString;
	}

	public String PlusOneID_FormatID(String vendor_id) {
		if (vendor_id == null) {

			vendor_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(vendor_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				vendor_id = "000" + ResultString_plusone;
				break;
			case 2:
				vendor_id = "00" + ResultString_plusone;
				break;
			case 3:
				vendor_id = "0" + ResultString_plusone;
				break;
			case 4:
				vendor_id = ResultString_plusone;
				break;
			}

		}

		return vendor_id;
	}

	public boolean Add_Vendor(String vendor_id, String vendor_name, String create_by) throws IOException, Exception {

		String sqlQuery = "insert ignore into vendor_master(vendor_id,vendor_name,create_by,create_datetime) values (?,?,?,now())";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, vendor_id);
		ppStmt.setString(2, vendor_name);
		ppStmt.setString(3, create_by);
		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();

		boolean resultboolean = false;
		if (rowsupdate > 0) {
			resultboolean = true;
		}
		return resultboolean;
	}

	public void Add_Vendor(VendorModel vendormodel, String username) throws IOException, Exception {

		String sqlQuery = "insert ignore into vendor_master(vendor_id,vendor_name,create_by,create_datetime) values (?,?,?,now())";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, vendormodel.getVendor_id());
		ppStmt.setString(2, vendormodel.getVendor_name());
		ppStmt.setString(3, username);
		ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();
	}

	public List<VendorModel> Get_vendorList(String vendor_id) throws IOException, Exception {
		String sqlQuery = "select * from vendor_master where ";
		if (!vendor_id.equals(""))
			sqlQuery += "vendor_id = '" + vendor_id + "' and ";
		sqlQuery += "vendor_id != '' ";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();

		rs = pStmt.executeQuery(sqlQuery);

		List<VendorModel> ResultList = new ArrayList<VendorModel>();
		while (rs.next()) {
			// vendor_id,vendor_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(
					new VendorModel(rs.getString("vendor_id"), rs.getString("vendor_name"), rs.getString("create_by"),
							new DateUtil().CnvYYYYMMDDToYYYYMMDDThaiYear(rs.getString("create_datetime"), "-"),
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

	public boolean Delete_vendor(String vendor_id) {

		String sqlQuery = "delete from vendor_master where vendor_id = ?";
		boolean resultboolean = false;
		try {
			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, vendor_id);
			ppStmt.executeUpdate();
			conn.commit();

			List ResultList = Get_vendorList(vendor_id);

			if (ResultList.isEmpty()) {
				resultboolean = true;
			}

			if (!ppStmt.isClosed())
				ppStmt.close();
			if (!conn.isClosed())
				conn.close();
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

		return resultboolean;
	}

	public void Update_Vendor(String vendor_id, String update_vendor_name, String update_by)
			throws IOException, Exception {

		String sqlQuery = "update vendor_master set vendor_name = ?,update_by = ?,update_datetime = now() where vendor_id = ?";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(3, vendor_id);
		ppStmt.setString(1, update_vendor_name);
		ppStmt.setString(2, update_by);
		ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();
	}
}
