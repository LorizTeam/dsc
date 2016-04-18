package test.pcpnru.inventoryData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.utilities.*;
import test.pcpnru.inventoryModel.UnitMasterForm;

public class UnitMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public String GetHighest_AddUnitID() throws IOException, Exception {

		String sqlQuery = "select MAX(unit_id) as unit_id from unit_master";
		String ResultString = "";
		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		if (rs.next()) {
			ResultString = rs.getString("unit_id");
		}

		return ResultString;
	}

	public String PlusOneID_FormatID(String unit_id) {
		if (unit_id == null) {

			unit_id = "0001";

		} else {

			String ResultString_plusone = String.valueOf((Integer.parseInt(unit_id) + 1));
			switch (ResultString_plusone.length()) {
			case 1:
				unit_id = "000" + ResultString_plusone;
				break;
			case 2:
				unit_id = "00" + ResultString_plusone;
				break;
			case 3:
				unit_id = "0" + ResultString_plusone;
				break;
			case 4:
				unit_id = ResultString_plusone;
				break;
			}

		}

		return unit_id;
	}

	public List GetUnitMasterList(String unit_name) throws Exception { // 09-03-2016
		List unitMasterList = new ArrayList();
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT * " + "FROM unit_master " + "WHERE ";
			if (new Validate().Check_String_notnull_notempty(unit_name))
				sqlStmt = sqlStmt + "unit_name like '" + unit_name + "%' AND ";

			sqlStmt = sqlStmt + "unit <> '' order by unit";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				unit_name = rs.getString("unit");

				unitMasterList.add(new UnitMasterForm(unit_name));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return unitMasterList;
	}

	public void AddUnitMaster(String unit_id, String unit_name, String create_by) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "INSERT IGNORE INTO unit_master(unit_id,unit_name,create_by,create_datetime) "
				+ "values (?,?,?,now())";
		// System.out.println(sqlStmt);
		ppStmt = conn.prepareStatement(sqlStmt);
		ppStmt.setString(1, unit_id);
		ppStmt.setString(2, unit_name);
		ppStmt.setString(3, create_by);
		ppStmt.executeUpdate();
		ppStmt.close();
		conn.close();
	}

	public int UpdateUnitMaster(String unit_id, String unit_name, String update_by) {
		String sqlQuery = "update unit_master set unit_id = ?, unit_name = ? , update_by = ? , update_datetime = now() "
				+ "where unit_id = ?";
		int rowsupdate = 0;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, unit_id);
			ppStmt.setString(2, unit_name);
			ppStmt.setString(3, update_by);
			ppStmt.setString(4, unit_id);
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

	public Boolean DeleteUnitMaster(String unit_id) {
		String sqlQuery = "delete from unit_master where unit_id = ?";
		Boolean resultboolean = false;
		try {

			conn = agent.getConnectMYSql();
			conn.setAutoCommit(false);
			ppStmt = conn.prepareStatement(sqlQuery);
			ppStmt.setString(1, unit_id);
			ppStmt.executeUpdate();
			conn.commit();

			if (Get_UnitList(unit_id, "").isEmpty()) {
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

	public boolean checkunit(String unie_id, String unit_name) throws IOException, Exception {
		boolean checkhave = true;

		conn = agent.getConnectMYSql();

		String sqlQuery = "SELECT * " + "FROM unit_master " + "where ";

		if (new Validate().Check_String_notnull_notempty(unit_name))
			sqlQuery += "unit_name = '" + unit_name + "' and ";
		if (new Validate().Check_String_notnull_notempty(unie_id))
			sqlQuery += "unit_name = '" + unie_id + "' and ";

		sqlQuery += "unit_id != '' ";

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

	public List<UnitMasterForm> Get_UnitList(String unit_id, String unit_name) throws IOException, Exception {

		String sqlQuery = "select * from unit_master where ";

		if (new Validate().Check_String_notnull_notempty(unit_id))
			sqlQuery += "unit_id = " + unit_id + " and ";

		if (new Validate().Check_String_notnull_notempty(unit_name))
			sqlQuery += "unit_name like '%" + unit_name + "%' and ";

		sqlQuery += "unit_id != '' ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List<UnitMasterForm> ResultList = new ArrayList<UnitMasterForm>();
		while (rs.next()) {
			// vender_id,vender_name,create_by,create_datetime,update_by,update_datetime
			ResultList.add(new UnitMasterForm(rs.getString("unit_id"), rs.getString("unit_name"),
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
