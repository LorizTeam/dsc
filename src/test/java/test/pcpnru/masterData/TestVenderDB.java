package test.pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;
import test.pcpnru.masterModel.TestVenderMaster;

public class TestVenderDB {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_VenderID() throws IOException, Exception {

		String sqlQuery = "select MAX(vendor_id) as vendor_id from vendor_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("vendor_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String vender_id) {
		if (vender_id == null) {

			vender_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(vender_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				vender_id = "000" + ResultString_plusone;
				break;
			case 2:
				vender_id = "00" + ResultString_plusone;
				break;
			case 3:
				vender_id = "0" + ResultString_plusone;
				break;
			case 4:
				vender_id = ResultString_plusone;
				break;
			}

		}

		return vender_id;
	}

	public boolean Add_Vender(String vender_id, String vender_name, String create_by) throws IOException, Exception {

		String sqlQuery = "insert ignore into vendor_master(vendor_id,vendor_name,create_by,create_datetime) values (?,?,?,now())";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, vender_id);
		ppStmt.setString(2, vender_name);
		ppStmt.setString(3, create_by);
		ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();

		List ResultList = Get_venderList(vender_id);
		boolean resultboolean = false;
		if (!ResultList.isEmpty()) {
			resultboolean = true;
		}
		return resultboolean;
	}

	public List<TestVenderMaster> Get_venderList(String vender_id) throws IOException, Exception {
		String sqlQuery = "select * from vendor_master where vendor_id = ?";
		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, vender_id);
		rs = ppStmt.executeQuery();

		List<TestVenderMaster> ResultList = new ArrayList<TestVenderMaster>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new TestVenderMaster(rs.getString("vendor_id"), rs.getString("vendor_name"),
					rs.getString("create_by"), rs.getString("create_datetime"), rs.getString("update_by"),
					rs.getString("update_datetime")));
		}

		if (!rs.isClosed())
			rs.close();
		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();

		return ResultList;
	}

	public boolean Delete_vender(String vender_id) throws IOException, Exception {

		String sqlQuery = "delete from vendor_master where vendor_id = ?";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, vender_id);
		ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();

		List ResultList = Get_venderList(vender_id);
		boolean resultboolean = false;
		if (ResultList.isEmpty()) {
			resultboolean = true;
		}
		return resultboolean;
	}

	public void Update_Vender(String vender_id, String update_vender_name, String update_by)
			throws IOException, Exception {

		String sqlQuery = "update vendor_master set vendor_name = ?,update_by = ?,update_datetime = now() where vendor_id = ?";

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(3, vender_id);
		ppStmt.setString(1, update_vender_name);
		ppStmt.setString(2, update_by);
		ppStmt.executeUpdate();
		conn.commit();

		if (!conn.isClosed())
			conn.close();
		if (!ppStmt.isClosed())
			ppStmt.close();
	}
}
