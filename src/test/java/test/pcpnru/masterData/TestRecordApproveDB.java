package test.pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;
import pcpnru.utilities.ThaiMonth;
import pcpnru.utilities.ThaiNumber;
import test.pcpnru.masterModel.TestRecordApproveModel;

public class TestRecordApproveDB {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	PreparedStatement ppStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getListAuthen(String authen_type) throws IOException, Exception {
		String authen_type_name = "";
		String sqlWhere = "";
		if (!authen_type.equals("")) {
			sqlWhere += "authen_master.authen_type = '" + authen_type + "' AND ";
		}

		String sqlQuery = "SELECT " + "authen_master.authen_type, " + "authen_master.authen_type_name " + "FROM "
				+ "authen_master where ";
		sqlQuery += sqlWhere;
		sqlQuery += "authen_master.authen_type <> '' order by authen_master.authen_type ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListAuthen = new ArrayList();
		while (rs.next()) {
			authen_type = rs.getString("authen_type");
			authen_type_name = rs.getString("authen_type_name");

			getListAuthen.add(new AuthenMasterModel(authen_type, authen_type_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListAuthen;
	}

	public List GetListPR_Header(String pr_number, String pr_title, String pr_date, String pr_month, String pr_year)
			throws IOException, Exception {

		List ListPR = new ArrayList();

		String sqlQuery = "SELECT * FROM `record_approve_hd` " + "where ";

		if (!pr_number.equals(""))
			sqlQuery += "docno = '" + pr_number + "' and ";

		if (!pr_title.equals(""))
			sqlQuery += "record_approve_title like '%" + pr_title + "%' and ";

		if (!pr_date.equals(""))
			sqlQuery += "record_approve_date = '" + pr_date + "' and ";

		if (!pr_month.equals(""))
			sqlQuery += "MONTH(record_approve_date) = '" + pr_month + "' and ";

		if (!pr_year.equals(""))
			sqlQuery += "YEAR(record_approve_date) = '" + pr_year + "' and ";

		sqlQuery += "docno != ''";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		String forwhat = "prhd";

		while (rs.next()) {
			ListPR.add(new TestRecordApproveModel(forwhat, rs.getString("docno"), rs.getString("record_approve_title"),
					rs.getString("record_approve_cen"), rs.getString("create_by"), rs.getString("year"),
					rs.getString("record_approve_date")));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ListPR;
	}

	public Map GetAllValueHeader_byDocno(String docno, String year) throws IOException, Exception {
		Map mapresultGet = new HashMap();

		String sqlQuery = "SELECT "
				+ "a.docno,a.`year`,a.record_approve_hd,a.record_approve_t,a.record_approve_date,a.record_approve_title,"
				+ "a.record_approve_rian,a.record_approve_des1,a.record_approve_des2,a.record_approve_des3,a.record_approve_cen,"
				+ "a.record_approve_dep,a.thaidate_report,a.create_by,a.vendor_id,a.total_amount,a.approve_status "
				+ "FROM " + "record_approve_hd AS a " + "WHERE " + "a.docno = '" + docno + "' and year = '" + year
				+ "'";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		while (rs.next()) {
			mapresultGet.put("docno", rs.getString("docno"));
			mapresultGet.put("year", rs.getString("year"));
			mapresultGet.put("record_approve_hd", rs.getString("record_approve_hd"));
			mapresultGet.put("record_approve_t", rs.getString("record_approve_t"));
			mapresultGet.put("record_approve_date", rs.getString("record_approve_date"));
			mapresultGet.put("record_approve_title", rs.getString("record_approve_title"));
			mapresultGet.put("record_approve_rian", rs.getString("record_approve_rian"));
			mapresultGet.put("record_approve_des1", rs.getString("record_approve_des1"));
			mapresultGet.put("record_approve_des2", rs.getString("record_approve_des2"));
			mapresultGet.put("record_approve_des3", rs.getString("record_approve_des3"));
			mapresultGet.put("record_approve_cen", rs.getString("record_approve_cen"));
			mapresultGet.put("record_approve_dep", rs.getString("record_approve_dep"));
			mapresultGet.put("thaidate_report", rs.getString("thaidate_report"));
			mapresultGet.put("vendor_id", rs.getString("vendor_id"));
			mapresultGet.put("total_amount", rs.getString("total_amount"));
			mapresultGet.put("create_by", rs.getString("create_by"));
			mapresultGet.put("approve_status", rs.getString("approve_status"));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return mapresultGet;
	}

	public List ListRecordApproveDT(String docno, String year) throws IOException, Exception {
		String itemno = "", description = "", qty = "0", unit_id = "";

		String sqlWhere = "";
		if (!docno.equals("")) {
			sqlWhere += "docno = '" + docno + "' AND ";
		}
		if (!year.equals("")) {
			sqlWhere += "year = '" + year + "' AND ";
		}

		String sqlQuery = "SELECT docno, year, itemno, description, qty, unit_id " + "FROM record_approve_dt "
				+ " where ";
		sqlQuery += sqlWhere;
		sqlQuery += "docno <> '' order by itemno ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List ListRecordApproveDT = new ArrayList();
		while (rs.next()) {
			docno = rs.getString("docno");
			year = rs.getString("year");
			itemno = rs.getString("itemno");
			description = rs.getString("description");
			qty = rs.getString("qty");
			unit_id = rs.getString("unit_id");

			ListRecordApproveDT.add(new TestRecordApproveModel(docno, year, itemno, description, qty, unit_id));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return ListRecordApproveDT;
	}

	public List ListRecordApproveDT(String docno, String month, String year) throws IOException, Exception {
		String itemno = "", product_code = "", qty = "0", unit_id = "", unit_name = "", product_name = "";

		String sqlWhere = "";
		if (!docno.equals("")) {
			sqlWhere += "b.docno = '" + docno + "' AND ";
		}
		if (!year.equals("")) {
			sqlWhere += "b.year = '" + year + "' AND ";
		}

		if (!month.equals("")) {
			sqlWhere += "substr(a.record_approve_date from 6 for 2) = '" + month + "' AND ";
		}

		String sqlQuery = "SELECT " + "b.docno,b.`year`,a.record_approve_date,b.itemno,b.numthai_itemno,b.product_code,"
				+ "b.qty,b.numthai_qty,b.unit_id,b.create_by,c.unit_name,d.product_name " + "FROM "
				+ "record_approve_hd AS a "
				+ "INNER JOIN record_approve_dt AS b ON (a.docno = b.docno AND a.`year` = b.`year`) "
				+ "INNER JOIN unit_master c on (b.unit_id = c.unit_id) "
				+ "INNER JOIN product d on (b.product_code = d.product_code) " + "where ";
		sqlQuery += sqlWhere;
		sqlQuery += "b.docno <> '' order by itemno ";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List ListRecordApproveDT = new ArrayList();
		while (rs.next()) {
			docno = rs.getString("docno");
			year = rs.getString("year");
			itemno = rs.getString("itemno");
			product_code = rs.getString("product_code");
			qty = rs.getString("qty");
			unit_id = rs.getString("unit_id");
			unit_name = rs.getString("unit_name");
			product_name = rs.getString("product_name");
			ListRecordApproveDT.add(new TestRecordApproveModel("PR", docno, year, itemno, product_code, qty, unit_id,
					unit_name, product_name));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return ListRecordApproveDT;
	}

	public int UpdateApprovehd(String docno, String year, String record_approve_hd, String record_approve_t,
			String record_approve_date, String record_approve_title, String record_approve_rian,
			String record_approve_des1, String record_approve_des2, String record_approve_des3,
			String record_approve_cen, String record_approve_dep, String create_by, String vendor_id,
			double total_amount, String approve_status) throws Exception {

		String sqlStmt = "update record_approve_hd "
				+ "set record_approve_hd =?, record_approve_t =?, record_approve_date =?"
				+ ", record_approve_title =?, record_approve_rian =? , record_approve_des1 =?"
				+ ", record_approve_des2 =?, record_approve_des3 =?, record_approve_cen =?"
				+ ", record_approve_dep =?, thaidate_report =?, vendor_id =?" + ", total_amount =?, approve_status=? "
				+ "where docno =? and year  =?";

		String[] splitdate = record_approve_date.split("-");

		ThaiNumber thnumber = new ThaiNumber();
		ThaiMonth thmonth = new ThaiMonth();

		String thaidate_report = (thnumber.CenverT_To_ThaiNumberic(splitdate[2]) + "-"
				+ thmonth.Convert_To_ThaiMonth(record_approve_date) + "-" + thnumber
						.CenverT_To_ThaiNumberic(String.valueOf(Integer.parseInt(splitdate[0]) + 543)).replace(",", ""))
								.replace("-", " ");

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);

		ppStmt = conn.prepareStatement(sqlStmt);
		ppStmt.setString(1, record_approve_hd);
		ppStmt.setString(2, record_approve_t);
		ppStmt.setDate(3, Date.valueOf(record_approve_date));
		ppStmt.setString(4, record_approve_title);
		ppStmt.setString(5, record_approve_rian);
		ppStmt.setString(6, record_approve_des1);
		ppStmt.setString(7, record_approve_des2);
		ppStmt.setString(8, record_approve_des3);
		ppStmt.setString(9, record_approve_cen);
		ppStmt.setString(10, record_approve_dep);
		ppStmt.setString(11, thaidate_report);
		ppStmt.setString(12, vendor_id);
		ppStmt.setDouble(13, total_amount);
		ppStmt.setString(14, approve_status);
		ppStmt.setString(15, docno);
		ppStmt.setString(16, year);

		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
		return rowsupdate;
	}

	public int AddRecordApprovehd(String docno, String year, String record_approve_hd, String record_approve_t,
			String record_approve_date, String record_approve_title, String record_approve_rian,
			String record_approve_des1, String record_approve_des2, String record_approve_des3,
			String record_approve_cen, String record_approve_dep, String create_by, String vendor_id,
			double total_amount, String approve_status) throws Exception {

		String dateTime = "";
		String sqlStmt = "INSERT IGNORE INTO record_approve_hd(docno, year, record_approve_hd, record_approve_t, record_approve_date, record_approve_title, record_approve_rian, "
				+ "record_approve_des1, record_approve_des2,record_approve_des3, record_approve_cen, record_approve_dep,thaidate_report,create_by,create_datetime,vendor_id,total_amount,approve_status) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?)";

		String[] splitdate = record_approve_date.split("-");

		ThaiNumber thnumber = new ThaiNumber();
		ThaiMonth thmonth = new ThaiMonth();

		String thaidate_report = (thnumber.CenverT_To_ThaiNumberic(splitdate[2]) + "-"
				+ thmonth.Convert_To_ThaiMonth(record_approve_date) + "-" + thnumber
						.CenverT_To_ThaiNumberic(String.valueOf(Integer.parseInt(splitdate[0]) + 543)).replace(",", ""))
								.replace("-", " ");
		// System.out.println(sqlStmt);
		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		ppStmt = conn.prepareStatement(sqlStmt);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, record_approve_hd);
		ppStmt.setString(4, record_approve_t);
		ppStmt.setDate(5, Date.valueOf(record_approve_date));
		ppStmt.setString(6, record_approve_title);
		ppStmt.setString(7, record_approve_rian);
		ppStmt.setString(8, record_approve_des1);
		ppStmt.setString(9, record_approve_des2);
		ppStmt.setString(10, record_approve_des3);
		ppStmt.setString(11, record_approve_cen);
		ppStmt.setString(12, record_approve_dep);
		ppStmt.setString(13, thaidate_report);
		ppStmt.setString(14, create_by);
		ppStmt.setString(15, vendor_id);
		ppStmt.setDouble(16, total_amount);
		ppStmt.setString(17, approve_status);
		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
		return rowsupdate;
	}

	public int AddRecordApprovedt(String docno, String year, String product_code, String qty, String unit_id,
			String create_by) throws Exception {

		conn = agent.getConnectMYSql();
		ThaiNumber thnumber = new ThaiNumber();
		String itemno = "", itemno_thai = "";
		String sqlStmt = "SELECT max(itemno) as lno FROM record_approve_dt " + "WHERE itemno <> '' and docno = '"
				+ docno + "' and year = '" + year + "' ";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);
		while (rs.next()) {
			itemno = rs.getString("lno");
		}
		if (null == itemno || "".equals(itemno))
			itemno = "0";
		itemno = String.valueOf(Integer.parseInt(itemno) + 1);
		itemno_thai = thnumber.CenverT_To_ThaiNumberic(itemno);
		String numthai_qty = thnumber.CenverT_To_ThaiNumberic(qty);
		if (itemno.length() == 1) {
			itemno = "00" + itemno;
		} else if (itemno.length() == 2) {
			itemno = "0" + itemno;
		}

		rs.close();
		pStmt.close();
		conn.close();

		conn = agent.getConnectMYSql();
		conn.setAutoCommit(false);
		sqlStmt = "INSERT IGNORE INTO record_approve_dt(docno, year, itemno, numthai_itemno, product_code, qty,numthai_qty, unit_id,create_by,create_datetime) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,now())";
		// System.out.println(sqlStmt);
		ppStmt = conn.prepareStatement(sqlStmt);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, itemno);
		ppStmt.setString(4, itemno_thai);
		ppStmt.setString(5, product_code);
		ppStmt.setInt(6, Integer.parseInt(qty));
		ppStmt.setString(7, numthai_qty);
		ppStmt.setString(8, unit_id);
		ppStmt.setString(9, create_by);
		int rowsupdate = ppStmt.executeUpdate();
		ppStmt.close();
		conn.commit();
		conn.close();
		return rowsupdate;
	}

	public void UpdateAuthenMaster(String authen_type, String authen_type_name) throws Exception {
		conn = agent.getConnectMYSql();

		String sqlStmt = "UPDATE authen_master set authen_type_name = '" + authen_type_name + "', datetime = now()"
				+ "WHERE authen_type = '" + authen_type + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();
		conn.close();
	}

	public void DeleteRecordApprovedt(String docno, String year, String itemno) throws Exception {
		conn = agent.getConnectMYSql();

		conn.setAutoCommit(false);

		String sqlStmt = "DELETE From record_approve_dt " + "WHERE docno = '" + docno + "' and year = '" + year
				+ "' and itemno = '" + itemno + "'";
		// System.out.println(sqlStmt);
		pStmt = conn.createStatement();
		pStmt.executeUpdate(sqlStmt);
		pStmt.close();

		conn.commit();
		pStmt.close();
		conn.close();
	}

	public void update_itemno(String docno, String year) throws IOException, Exception {
		conn = agent.getConnectMYSql();
		ThaiNumber thnumber = new ThaiNumber();
		String sqlstmtS = "SELECT itemno FROM record_approve_dt " + "where docno = '" + docno + "' and year = '" + year
				+ "'";
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlstmtS);
		int rows = 1;
		while (rs.next()) {

			String itemno = rs.getString("itemno");
			String itemno_Update = String.valueOf(rows);

			String itemno_thai = (thnumber.CenverT_To_ThaiNumberic(itemno_Update));

			if (itemno_Update.length() == 1) {
				itemno_Update = "00" + itemno_Update;
			} else if (itemno_Update.length() == 2) {
				itemno_Update = "0" + itemno_Update;
			}

			sqlstmtS = "UPDATE record_approve_dt set itemno = '" + itemno_Update + "',numthai_itemno = '" + itemno_thai
					+ "' " + "WHERE docno = '" + docno + "' and year = '" + year + "' and itemno = '" + itemno + "'";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			pStmt.executeUpdate(sqlstmtS);
			pStmt.close();
			rows++;
		}
		rs.close();
		pStmt.close();
		conn.close();
	}

	public List<TestRecordApproveModel> GetListDetail(String docno, String year) throws IOException, Exception {
		conn = agent.getConnectMYSql();
		List<TestRecordApproveModel> resultList = new ArrayList<TestRecordApproveModel>();
		String sqlstmtS = "SELECT * FROM record_approve_dt " + "where docno = '" + docno + "' and year = '" + year
				+ "'";
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlstmtS);
		while (rs.next()) {
			// docno,`year`,itemno,numthai_itemno,description,qty,numthai_qty,unit,create_by,create_datetime
			/*
			 * this.docno = docno; this.year = year; this.itemno = itemno;
			 * this.description = description; this.qty = qty; this.unit = unit;
			 */
			resultList
					.add(new TestRecordApproveModel(rs.getString("docno"), rs.getString("year"), rs.getString("itemno"),
							rs.getString("product_code"), rs.getString("qty"), rs.getString("unit_id")));
		}
		rs.close();
		pStmt.close();
		conn.close();

		return resultList;

	}

	public String SelectUpdateDocNo(String typedocno) throws Exception {
		String requestno = "";
		try {
			conn = agent.getConnectMYSql();

			String year = dateUtil.curYear();

			String sqlStmt = "SELECT max(docno) as lno FROM runningdocno_prpo " + "WHERE docno <> '' and year = '"
					+ year + "' and type='" + typedocno + "' ";
			// System.out.println(sqlStmt);
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlStmt);
			while (rs.next()) {
				requestno = rs.getString("lno");
			}
			rs.close();
			pStmt.close();

			if (null == requestno || "".equals(requestno)) {
				requestno = "0";
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);

				sqlStmt = "INSERT IGNORE INTO runningdocno_prpo(docno, year,type) " + "VALUES ('" + requestno + "', '"
						+ year + "','" + typedocno + "')";
				// System.out.println(sqlStmt);
				pStmt = conn.createStatement();
				pStmt.executeUpdate(sqlStmt);
				pStmt.close();
				conn.close();

			} else {
				requestno = String.valueOf(Integer.parseInt(requestno) + 1);

				sqlStmt = "UPDATE runningdocno_prpo set docno = '" + requestno + "'" + "WHERE year = '" + year
						+ "' and type='" + typedocno + "' ";
				// System.out.println(sqlStmt);
				pStmt = conn.createStatement();
				pStmt.executeUpdate(sqlStmt);
				pStmt.close();
				conn.close();
			}

			if (requestno.length() == 1) {
				requestno = "0000" + requestno;
			} else if (requestno.length() == 2) {
				requestno = "000" + requestno;
			} else if (requestno.length() == 3) {
				requestno = "00" + requestno;
			} else if (requestno.length() == 4) {
				requestno = "0" + requestno;
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return requestno;
	}

	public void Add_PurchaseRequest_Image(String docno, String year, String img_path) throws IOException, Exception {

		String sqlQuery = "insert ignore into record_approve_imgref values (?,?,?)";

		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, img_path);
		ppStmt.executeUpdate();

		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();
	}

	public List GET_PurchaseRequest_Image(String docno, String year, String img_path) throws IOException, Exception {

		String sqlQuery = "select * from record_approve_imgref where docno = ? and year = ? and img_path = ?";

		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, img_path);
		rs = ppStmt.executeQuery();
		List<TestRecordApproveModel> ResultList = new ArrayList<TestRecordApproveModel>();
		while (rs.next()) {
			ResultList.add(
					new TestRecordApproveModel(rs.getString("img_path"), rs.getString("docno"), rs.getString("year")));
		}

		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ResultList;
	}

	public void Delete_PR_Image(String docno, String year, String img_path) throws IOException, Exception {

		String sqlQuery = "delete from record_approve_imgref where docno = ? and year = ? and img_path = ?";

		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, docno);
		ppStmt.setString(2, year);
		ppStmt.setString(3, img_path);
		ppStmt.executeUpdate();

		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();
	}

	public List<TestRecordApproveModel> Get_Product() throws IOException, Exception {
		String sqlQuery = "select a.*,b.unit_name from product a "
				+ "INNER JOIN unit_master b on (a.unit_id = b.unit_id) limit 1";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		List<TestRecordApproveModel> ResultList = new ArrayList<TestRecordApproveModel>();
		rs = pStmt.executeQuery(sqlQuery);
		while (rs.next()) {
			ResultList.add(new TestRecordApproveModel(rs.getString("product_code"), rs.getString("product_name"),
					rs.getString("unit_id"), rs.getString("unit_name")));
		}

		if (!rs.isClosed())
			rs.close();
		if (!pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();
		return ResultList;
	}

	public void approve_pr(String docno, String year, String approve_status) throws IOException, Exception {
		String sqlQuery = "update record_approve_hd set approve_status = ? where docno = ? and year = ?";

		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, approve_status);
		ppStmt.setString(2, docno);
		ppStmt.setString(3, year);
		ppStmt.executeUpdate();

		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();
	}

}
