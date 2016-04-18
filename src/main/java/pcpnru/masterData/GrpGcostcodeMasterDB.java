package pcpnru.masterData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.GroupCostCodeMasterModel;
import pcpnru.masterModel.GrpGCostCodeMasterModel;
import pcpnru.projectModel.*;
import pcpnru.utilities.*;

public class GrpGcostcodeMasterDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List GetGrpGCostCodeMasterList() throws Exception { // 30-05-2014
		List grpGcostCodeMasterList = new ArrayList();
		String gcostcode_r = "", gcostcode_c = "", grp_gcostcode = "", grp_gcostname = "", project_code = "",
				grp_costyear = "", amount = "0";

		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.gcostcode_c,a.grp_gcostcode,a.grp_gcostname,a.project_code,a.grp_costyear, sum(a.amount) as sumamt "
					+ "FROM grp_gcostcode_master a " + "WHERE ";

			sqlStmt = sqlStmt + "gcostcode_c <> '' group by gcostcode_c order by a.gcostcode_c";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {

				gcostcode_c = rs.getString("gcostcode_c");
				grp_gcostcode = rs.getString("grp_gcostcode");
				if (rs.getString("grp_gcostname") != null)
					grp_gcostname = rs.getString("grp_gcostname");
				else
					grp_gcostname = "";
				project_code = rs.getString("project_code");
				grp_costyear = rs.getString("grp_costyear");

				amount = rs.getString("sumamt");
				amount = df2.format(Float.parseFloat(amount));

				grpGcostCodeMasterList.add(new GrpGCostCodeMasterModel(gcostcode_c, grp_gcostcode, grp_gcostname,
						project_code, grp_costyear, amount));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return grpGcostCodeMasterList;
	}

	public List GetCostCodeList_C(String project_code, String grp_costyear) throws Exception { // 30-05-2014
		List CostCodeList_C = new ArrayList();
		String project_name = "", gcostcode = "", gcostcode_name = "", amount_c = "0";

		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.project_code, b.project_name, a.gcostcode, a.gcostcode_name, a.amount "
					+ "FROM groupcostcode_master a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) " + "WHERE ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			sqlStmt = sqlStmt
					+ "a.gcostcode not in (select DISTINCT(b.gcostcode_c) from grp_gcostcode_master b where b.gcostcode_c = a.gcostcode "
					+ "and b.project_code = '" + project_code + "' and b.grp_costyear = '" + grp_costyear + "') "
					+ "and type_gcostcode = '2' order by a.gcostcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				project_code = rs.getString("project_code");
				if (rs.getString("project_name") != null)
					project_name = rs.getString("project_name");
				else
					project_name = "";
				gcostcode = rs.getString("gcostcode");
				if (rs.getString("gcostcode_name") != null)
					gcostcode_name = rs.getString("gcostcode_name");
				else
					gcostcode_name = "";

				amount_c = rs.getString("amount");
				amount_c = df2.format(Float.parseFloat(amount_c));

				CostCodeList_C.add(
						new GrpGCostCodeMasterModel(project_code, project_name, gcostcode, gcostcode_name, amount_c));
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return CostCodeList_C;
	}

	public List GetGrpCostCodeList(String project_code, String grp_costyear) throws Exception { // 30-05-2014
		List GrpCostCodeList = new ArrayList();
		String project_name = "", grp_gcostcode = "", grp_gcostname = "", amounttotal = "0", qty = "0";

		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.project_code, b.project_name, a.grp_gcostcode, a.grp_gcostname, a.grp_costyear, sum(a.qty) as countqty, sum(a.amounttotal) as sumamt "
					+ "FROM grp_gcostcode_master a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) " + "WHERE ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!grp_costyear.equals(""))
				sqlStmt = sqlStmt + "a.grp_costyear = '" + grp_costyear + "' AND ";
			sqlStmt = sqlStmt + "grp_gcostcode <> '' "
			// + "and a.grp_gcostcode not in (select DISTINCT(c.grp_gcostcode)
			// from "
			// + "groupcostcode_master c where c.grp_gcostcode <> '') "
					+ "GROUP BY a.grp_gcostcode ";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				project_code = rs.getString("project_code");
				if (rs.getString("project_name") != null)
					project_name = rs.getString("project_name");
				else
					project_name = "";
				grp_gcostcode = rs.getString("grp_gcostcode");
				if (rs.getString("grp_gcostname") != null)
					grp_gcostname = rs.getString("grp_gcostname");
				else
					grp_gcostname = "";
				grp_costyear = rs.getString("grp_costyear");

				qty = rs.getString("countqty");
				qty = df1.format(Float.parseFloat(qty));

				amounttotal = rs.getString("sumamt");
				amounttotal = df2.format(Float.parseFloat(amounttotal));

				GrpCostCodeList.add(new GrpGCostCodeMasterModel(project_code, project_name, grp_gcostcode,
						grp_gcostname, grp_costyear, amounttotal, qty));
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return GrpCostCodeList;
	}

	public List GrpReqisToReceive(String project_code, String grp_costyear, String grp_gcostcode) throws Exception { // 30-05-2014
		List GrpReqisToReceiveList = new ArrayList();
		String project_name = "", grp_gcostname = "", amounttotal = "0", qty = "0";

		DecimalFormat df1 = new DecimalFormat("#,###,##0.##");
		DecimalFormat df2 = new DecimalFormat("#,###,##0.00");
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT a.project_code, b.project_name, a.gcostcode_c, c.gcostcode_name, a.grp_gcostcode, a.grp_gcostname, a.grp_costyear, a.qty, a.amounttotal "
					+ "FROM grp_gcostcode_master a "
					+ "INNER JOIN project_master b on(b.project_code = a.project_code) "
					+ "LEFT JOIN groupcostcode_master c on(c.project_code = a.project_code and c.gcostcode = a.gcostcode_c) "
					+ "WHERE ";
			if (!project_code.equals(""))
				sqlStmt = sqlStmt + "a.project_code = '" + project_code + "' AND ";
			if (!grp_costyear.equals(""))
				sqlStmt = sqlStmt + "a.grp_costyear = '" + grp_costyear + "' AND ";
			if (!grp_gcostcode.equals(""))
				sqlStmt = sqlStmt + "a.grp_gcostcode = '" + grp_gcostcode + "' ";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				project_code = rs.getString("project_code");
				if (rs.getString("project_name") != null)
					project_name = rs.getString("project_name");
				else
					project_name = "";
				grp_gcostcode = rs.getString("gcostcode_c");
				if (rs.getString("gcostcode_name") != null)
					grp_gcostname = rs.getString("gcostcode_name");
				else
					grp_gcostname = "";
				grp_costyear = rs.getString("grp_costyear");

				qty = rs.getString("qty");
				qty = df1.format(Float.parseFloat(qty));

				amounttotal = rs.getString("amounttotal");
				amounttotal = df2.format(Float.parseFloat(amounttotal));

				GrpReqisToReceiveList.add(new GrpGCostCodeMasterModel(project_code, project_name, grp_gcostcode,
						grp_gcostname, grp_costyear, amounttotal, qty));
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return GrpReqisToReceiveList;
	}

	public List GetGroupCostCodeList(String projectcode, String groupcostCode) throws Exception { // 30-05-2014
		List groupcostCodeList = new ArrayList();
		String groupcostName = "";
		try {

			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT gcostcode, gcostcode_name  " + "from groupcostcode_master "
					+ "WHERE type_gcostcode = '1' and ";
			if (!projectcode.equals(""))
				sqlStmt = sqlStmt + "project_code = '" + projectcode + "' AND ";
			if (!groupcostCode.equals(""))
				sqlStmt = sqlStmt + "gcostcode = '" + groupcostCode + "' AND ";
			sqlStmt = sqlStmt + "gcostcode <> '' order by gcostcode";

			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				groupcostCode = rs.getString("gcostcode");
				if (rs.getString("gcostcode_name") != null)
					groupcostName = rs.getString("gcostcode_name");
				else
					groupcostName = "";

				groupcostCodeList.add(new GroupCostCodeMasterModel(groupcostCode, groupcostName));
			}
			rs.close();
			pStmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		return groupcostCodeList;
	}

	public void AddGrpGCostCodeMaster(String project_code, String groupcostcode_c, String grp_gcostcode,
			String grp_gcostname, String grp_costyear, String qty, String amount) throws Exception {

		conn = agent.getConnectMYSql();

		if (qty.equals(""))
			qty = "0";
		qty = qty.replace(",", "");
		amount = amount.replace(",", "");
		String amounttotal = Float.toString(Float.parseFloat(qty) * Float.parseFloat(amount));
		String sqlStmt = "INSERT IGNORE INTO `grp_gcostcode_master` (project_code, gcostcode_c, grp_gcostcode, grp_gcostname, grp_costyear, qty, amount, amounttotal, datetime) "
				+ "VALUES ('" + project_code + "','" + groupcostcode_c + "','" + grp_gcostcode + "','" + grp_gcostname
				+ "','" + grp_costyear + "','" + qty + "','" + amount + "','" + amounttotal + "', now())";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void UpdateCostCodeMaster(String project_code, String groupcostCode, String groupcostName,
			String groupcostCodeHD, String standardprice, String fundprice, String amount) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE groupcostcode_master set gcostcode = '" + groupcostCode + "', gcostcode_name = '"
				+ groupcostName + "',gcostcode_standardprice = '" + standardprice + "' " + ",gcostcode_fundprice = '"
				+ fundprice + "', amount = '" + amount + "', datetime = now() " + "WHERE project_code = '"
				+ project_code + "' and gcostcode = '" + groupcostCodeHD + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteGCostCodeMaster(String project_code, String grp_gcostcode, String grp_costyear) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From grp_gcostcode_master " + "WHERE project_code = '" + project_code
				+ "' and grp_gcostcode = '" + grp_gcostcode + "' and grp_costyear = '" + grp_costyear + "'";
		System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteCostCode(String project_code, String groupcostCode) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "DELETE From groupcostcode_master " + "WHERE project_code = '" + project_code
				+ "' and gcostcode = '" + groupcostCode + "'";
		System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public boolean getCheckMaster(String groupcostCode) throws Exception {

		boolean chkCustomer = false;
		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT * " + "FROM groupcostcode_master WHERE gcostcode = '" + groupcostCode + "' ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			chkCustomer = true;
		}

		conn.close();
		rs.close();
		pStmt.close();

		return chkCustomer;
	}

	public String SelectGrpDocNo(String project_code, String grp_costyear) throws Exception {
		String requestno = "";

		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(grp_gcostcode) as lno FROM grp_gcostcode_master " + "WHERE project_code = '"
					+ project_code + "' and grp_costyear ='" + grp_costyear + "' GROUP BY grp_gcostcode ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
			}

			if (null == requestno || requestno.equals("")) {
				requestno = "0";
			}

			requestno = String.valueOf(Integer.parseInt(requestno) + 1);

			if (requestno.length() == 1) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "0" + requestno;
			}

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (pStmt != null)
					pStmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return requestno;
	}

	public String SelectUpdateDocNo(String project_code, String type_gcostcode) throws Exception {
		String requestno = "", typeR = "";
		if (type_gcostcode.equals("1")) {
			typeR = "R";
		} else {
			typeR = "C";
		}

		try {
			conn = agent.getConnectMYSql();

			String sqlStmt = "SELECT max(gcostcode) as lno FROM groupcostcode_master " + "WHERE project_code = '"
					+ project_code + "' and type_gcostcode ='" + type_gcostcode + "' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");

			}
			if (null == requestno || "".equals(requestno)) {
				// System.out.println("requestno = null");
				requestno = "0";
			} else {
				typeR = requestno.substring(0, 1);
				requestno = requestno.substring(2);
			}

			requestno = String.valueOf(Integer.parseInt(requestno) + 1);
			// System.out.println("requestno = "+requestno);

			if (requestno.length() == 1) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "0" + requestno;
			}

			requestno = typeR + requestno;

			rs.close();
			pStmt.close();
			conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (pStmt != null)
					pStmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return requestno;
	}
}
