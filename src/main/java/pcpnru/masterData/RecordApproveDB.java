package pcpnru.masterData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.utilities.*;

public class RecordApproveDB {

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

	public List ListRecordApproveDT(String docno, String month, String year) throws IOException, Exception {
		String itemno = "", product_code = "", qty = "0", unit_id = "", unit_name = "", product_name = "",
				approve_status = "";

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
				+ "b.qty,b.numthai_qty,b.unit_id,b.create_by,c.unit_name,d.product_name,a.approve_status " + "FROM "
				+ "record_approve_hd AS a "
				+ "INNER JOIN record_approve_dt AS b ON (a.docno = b.docno AND a.`year` = b.`year`)"
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
			approve_status = rs.getString("approve_status");
			ListRecordApproveDT.add(new RecordApproveModel("ListRecordApproveDT", docno, year, itemno, product_code,
					qty, unit_id, unit_name, product_name, approve_status));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return ListRecordApproveDT;
	}

	public int UpdateApprovehd(String docno, String year, String record_approve_date, String record_approve_send, 
			String create_by, String vendor_id, String approve_status) throws Exception {

		String sqlStmt = "update record_approve_hd "
				+ "set record_approve_date =?"
				+ ", record_approve_send =?, thaidate_report =?, vendor_id =? , approve_status=? "
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
		ppStmt.setDate(1, Date.valueOf(record_approve_date));
		ppStmt.setDate(2, Date.valueOf(record_approve_send));
		ppStmt.setString(3, thaidate_report);
		ppStmt.setString(4, vendor_id);
		ppStmt.setString(5, approve_status);
		ppStmt.setString(6, docno);
		ppStmt.setString(7, year);

		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
		return rowsupdate;
	}
	
	public int UpdateApprovehd(String docno, String year, String record_approve_date, String record_approve_send, 
			String create_by, String vendor_id, String approve_status,int credit_day) throws Exception {

		String sqlStmt = "update record_approve_hd "
				+ "set record_approve_date =?"
				+ ", record_approve_send =?, thaidate_report =?, vendor_id =? , approve_status=?, credit_day =? "
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
		ppStmt.setDate(1, Date.valueOf(record_approve_date));
		ppStmt.setDate(2, Date.valueOf(record_approve_send));
		ppStmt.setString(3, thaidate_report);
		ppStmt.setString(4, vendor_id);
		ppStmt.setString(5, approve_status);
		ppStmt.setInt(6, credit_day);
		ppStmt.setString(7, docno);
		ppStmt.setString(8, year);

		int rowsupdate = ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
		return rowsupdate;
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
				+ ", record_approve_dep =?, thaidate_report =?, vendor_id =?" + ", total_amount =? , approve_status=? "
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

	public void AddRecordApprovehd(String docno, String year, String record_approve_hd, String record_approve_t,
			String record_approve_date, String record_approve_title, String record_approve_rian,
			String record_approve_des1, String record_approve_des2, String record_approve_des3,
			String record_approve_cen, String record_approve_dep, String create_by, String vendor_id,
			double total_amount) throws Exception {

		String dateTime = "";
		String sqlStmt = "INSERT IGNORE INTO record_approve_hd(docno, year, record_approve_hd, record_approve_t, record_approve_date, record_approve_title, record_approve_rian, "
				+ "record_approve_des1, record_approve_des2,record_approve_des3, record_approve_cen, record_approve_dep,thaidate_report,create_by,create_datetime,vendor_id,total_amount) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)";

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
		ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
	}
	
	public void AddRecordApprovehd(String docno, String year, String record_approve_date, String record_approve_send, 
			String create_by, String vendor_id) throws Exception {

		String dateTime = "";
		String sqlStmt = "INSERT IGNORE INTO record_approve_hd(docno, year, record_approve_date, record_approve_send, "
				+ "thaidate_report,create_by,create_datetime,vendor_id) "
				+ "VALUES (?,?,?,?,?,?,now(),?)";

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
		ppStmt.setDate(3, Date.valueOf(record_approve_date));
		ppStmt.setDate(4, Date.valueOf(record_approve_send));
		ppStmt.setString(5, thaidate_report);
		ppStmt.setString(6, create_by);
		ppStmt.setString(7, vendor_id);
		ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
	}
	
	public void AddRecordApprovehd(String docno, String year, String record_approve_date, String record_approve_send, 
			String create_by, String vendor_id,int credit_day) throws Exception {

		String dateTime = "";
		String sqlStmt = "INSERT IGNORE INTO record_approve_hd(docno, year, record_approve_date, record_approve_send, "
				+ "thaidate_report,create_by,create_datetime,vendor_id,credit_day) "
				+ "VALUES (?,?,?,?,?,?,now(),?,?)";

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
		ppStmt.setDate(3, Date.valueOf(record_approve_date));
		ppStmt.setDate(4, Date.valueOf(record_approve_send));
		ppStmt.setString(5, thaidate_report);
		ppStmt.setString(6, create_by);
		ppStmt.setString(7, vendor_id);
		ppStmt.setInt(8, credit_day);
		ppStmt.executeUpdate();
		conn.commit();
		ppStmt.close();
		conn.close();
	}

	public boolean CheckHaveAddHD(String docno) {
		String sqlQuery = "SELECT * FROM `record_approve_hd` where docno = '" + docno + "'";
		boolean GetResult = false;
		try {
			conn = agent.getConnectMYSql();
			pStmt = conn.createStatement();
			rs = pStmt.executeQuery(sqlQuery);
			while (rs.next()) {
				GetResult = true;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GetResult;

	}

	public void AddRecordApprovedt(String docno, String year, String product_code, String qty, String unit_id,
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
		ppStmt.executeUpdate();
		ppStmt.close();
		conn.commit();
		conn.close();
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
	
	public List<RecordApproveModel> GetListPR_Header(String pr_number, String pr_date, String pr_month,
			String pr_year) throws IOException, Exception {

		List<RecordApproveModel> ListPR = new ArrayList<RecordApproveModel>();

		String sqlQuery = "SELECT docno,`year`+543 as year,"
				+ "CONCAT(substr(record_approve_date from 9 for 2),\"-\",substr(record_approve_date from 6 for 2),\"-\",year(record_approve_date)+543) as record_approve_date,"
				+ "CONCAT(substr(record_approve_send from 9 for 2),\"-\",substr(record_approve_send from 6 for 2),\"-\",year(record_approve_send)+543) as record_approve_send,"
				+ "thaidate_report,create_by,"
				+ "CONCAT(b.name,' ',b.lastname) as create_name,approve_status FROM `record_approve_hd` a "
				+ "INNER JOIN employee b on (a.create_by = b.username)"

				+ "where ";

		if (!pr_number.equals(""))
			sqlQuery += "docno = '" + pr_number + "' and ";
		
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
			ListPR.add(new RecordApproveModel(forwhat, rs.getString("docno"), rs.getString("create_by"), rs.getString("year"),
					rs.getString("record_approve_date"), rs.getString("create_name"), rs.getString("approve_status")));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ListPR;
	}
	
	public List<RecordApproveModel> GetListPR_Header(String pr_number, String pr_date, String pr_month,
			String pr_year, String approve_status) throws IOException, Exception {

		Validate validate = new Validate();
		List<RecordApproveModel> ListPR = new ArrayList<RecordApproveModel>();

		String sqlQuery = "SELECT docno,`year`+543 as year,"
				+ "CONCAT(substr(record_approve_date from 9 for 2),\"-\",substr(record_approve_date from 6 for 2),\"-\",year(record_approve_date)+543) as record_approve_date,"
				+ "thaidate_report,create_by,"
				+ "CONCAT(b.name,' ',b.lastname) as create_name,approve_status " + "FROM `record_approve_hd` a "
				+ "INNER JOIN employee b on (a.create_by = b.username)"

				+ "where ";

		if (validate.Check_String_notnull_notempty(pr_number))
			sqlQuery += "docno like '%" + pr_number + "%' and ";

		if (validate.Check_String_notnull_notempty(pr_date))
			sqlQuery += "record_approve_date = '" + pr_date + "' and ";

		if (validate.Check_String_notnull_notempty(pr_month))
			sqlQuery += "MONTH(record_approve_date) = '" + pr_month + "' and ";

		if (validate.Check_String_notnull_notempty(pr_year))
			sqlQuery += "YEAR(record_approve_date) = '" + pr_year + "' and ";

		if (validate.Check_String_notnull_notempty(approve_status))
			sqlQuery += "approve_status = '" + approve_status + "' and ";

		sqlQuery += "docno != ''";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		String forwhat = "prhd";

		while (rs.next()) {
			ListPR.add(new RecordApproveModel(forwhat, rs.getString("docno"), "",
					"", rs.getString("create_by"), rs.getString("year"),
					rs.getString("record_approve_date"), rs.getString("create_name"), rs.getString("approve_status")));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return ListPR;
	}

	/*public Map GetAllValueHeader_byDocno(String docno, String year) throws IOException, Exception {
		Map mapresultGet = new HashMap();

		String sqlQuery = "SELECT docno,`year`+543 as year,record_approve_hd,record_approve_t,"
				+ "CONCAT(substr(record_approve_date from 9 for 2),\"-\",substr(record_approve_date from 6 for 2),\"-\",year(record_approve_date)+543) as record_approve_date,record_approve_title,record_approve_rian,"
				+ "record_approve_des1,record_approve_des2,record_approve_des3,"
				+ "record_approve_cen,record_approve_dep,thaidate_report,record_approve_hd.create_by,total_amount,b.vendor_id,b.vendor_name,approve_status "
				+ "FROM `record_approve_hd` "
				+ "inner join vendor_master as b on (record_approve_hd.vendor_id = b.vendor_id)" + "WHERE "
				+ "docno = '" + docno + "' and year = '" + year + "'";

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
			mapresultGet.put("create_by", rs.getString("create_by"));
			mapresultGet.put("total_amount", rs.getDouble("total_amount"));
			mapresultGet.put("vendor_id", rs.getString("vendor_id"));
			mapresultGet.put("vendor_name", rs.getString("vendor_name"));
			mapresultGet.put("approve_status", rs.getString("approve_status"));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return mapresultGet;
	}*/
	
	/*public Map GetAllValueHeader_byDocno(String docno, String year) throws IOException, Exception {
		Map mapresultGet = new HashMap();

		String sqlQuery = "SELECT docno,`year`+543 as year,"
				+ "CONCAT(substr(record_approve_date from 9 for 2),\"-\",substr(record_approve_date from 6 for 2),\"-\",year(record_approve_date)+543) as record_approve_date,"
				+ "CONCAT(substr(record_approve_send from 9 for 2),\"-\",substr(record_approve_send from 6 for 2),\"-\",year(record_approve_send)+543) as record_approve_send,"
				+ "record_approve_hd.create_by,b.vendor_id,b.vendor_name,approve_status,thaidate_report "
				+ "FROM `record_approve_hd` "
				+ "inner join vendor_master as b on (record_approve_hd.vendor_id = b.vendor_id) WHERE "
				+ "docno = '" + docno + "' and year = '" + year + "'";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		while (rs.next()) {
			mapresultGet.put("docno", rs.getString("docno"));
			mapresultGet.put("year", rs.getString("year"));
			mapresultGet.put("record_approve_date", rs.getString("record_approve_date"));
			mapresultGet.put("record_approve_send", rs.getString("record_approve_send"));
			mapresultGet.put("thaidate_report", rs.getString("thaidate_report"));
			mapresultGet.put("create_by", rs.getString("create_by"));
			mapresultGet.put("vendor_id", rs.getString("vendor_id"));
			mapresultGet.put("vendor_name", rs.getString("vendor_name"));
			mapresultGet.put("approve_status", rs.getString("approve_status"));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return mapresultGet;
	}*/
	
	public Map GetAllValueHeader_byDocno(String docno, String year) throws IOException, Exception {
		Map mapresultGet = new HashMap();

		String sqlQuery = "SELECT docno,`year`+543 as year,"
				+ "CONCAT(substr(record_approve_date from 9 for 2),\"-\",substr(record_approve_date from 6 for 2),\"-\",year(record_approve_date)+543) as record_approve_date,"
				+ "CONCAT(substr(record_approve_send from 9 for 2),\"-\",substr(record_approve_send from 6 for 2),\"-\",year(record_approve_send)+543) as record_approve_send,"
				+ "record_approve_hd.create_by,b.vendor_id,b.vendor_name,approve_status,thaidate_report,credit_day "
				+ "FROM `record_approve_hd` "
				+ "inner join vendor_master as b on (record_approve_hd.vendor_id = b.vendor_id) WHERE "
				+ "docno = '" + docno + "' and year = '" + year + "'";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		while (rs.next()) {
			mapresultGet.put("docno", rs.getString("docno"));
			mapresultGet.put("year", rs.getString("year"));
			mapresultGet.put("record_approve_date", rs.getString("record_approve_date"));
			mapresultGet.put("record_approve_send", rs.getString("record_approve_send"));
			mapresultGet.put("thaidate_report", rs.getString("thaidate_report"));
			mapresultGet.put("create_by", rs.getString("create_by"));
			mapresultGet.put("vendor_id", rs.getString("vendor_id"));
			mapresultGet.put("vendor_name", rs.getString("vendor_name"));
			mapresultGet.put("approve_status", rs.getString("approve_status"));
			mapresultGet.put("credit_day", rs.getInt("credit_day"));
		}

		if (!rs.isClosed())
			rs.close();
		if (pStmt.isClosed())
			pStmt.close();
		if (!conn.isClosed())
			conn.close();

		return mapresultGet;
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

	public List<RecordApproveModel> GET_PurchaseRequest_Image(String docno, String year, String img_path)
			throws IOException, Exception {

		String sqlQuery = "select * from record_approve_imgref where ";

		if (!docno.equals(""))
			sqlQuery += "docno = '" + docno + "' and ";
		if (!year.equals(""))
			sqlQuery += "year = '" + year + "' and ";
		if (!img_path.equals(""))
			sqlQuery += "img_path = '" + img_path + "' and ";

		sqlQuery += "docno != ''";

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);
		List<RecordApproveModel> ResultList = new ArrayList<RecordApproveModel>();
		while (rs.next()) {
			ResultList
					.add(new RecordApproveModel(rs.getString("img_path"), rs.getString("docno"), rs.getString("year")));
		}

		if (!pStmt.isClosed())
			pStmt.close();
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

	public String Get_Product(String product_code) throws IOException, Exception {
		String sqlQuery = "select a.*,b.unit_name from product a "
				+ "INNER JOIN unit_master b on (a.unit_id = b.unit_id) " + "where a.product_code = ?";

		conn = agent.getConnectMYSql();
		ppStmt = conn.prepareStatement(sqlQuery);
		ppStmt.setString(1, product_code);

		String unit_id = "";
		rs = ppStmt.executeQuery();
		while (rs.next()) {
			unit_id = rs.getString("unit_id");
		}

		if (!rs.isClosed())
			rs.close();
		if (!ppStmt.isClosed())
			ppStmt.close();
		if (!conn.isClosed())
			conn.close();

		return unit_id;
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
