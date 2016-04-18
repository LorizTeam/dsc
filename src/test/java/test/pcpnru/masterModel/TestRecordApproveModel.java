package test.pcpnru.masterModel;

import java.io.File;

public class TestRecordApproveModel {
	private String record_approve_hd;
	private String record_approve_t;
	private String record_approve_date;
	private String record_approve_title;
	private String record_approve_rian;
	private String record_approve_des1;
	private String record_approve_des2;
	private String record_approve_des3;
	private String record_approve_cen;
	private String record_approve_dep, record_approve_month;
	private String fromwindow, createwindow, alertmsg;

	// Vender --------------------------------------------------------
	private File quotation_img;
	private String vender_name, vender_id, quotation_imgFileName, quotation_imgContentType, img_path;
	private double total_amount;

	// Vender --------------------------------------------------------

	private String docno, docnohidden;
	private String year;
	private String itemno;
	private String description;
	private String qty;
	private String unit_id, create_by, product_code, product_name, unit_name, approve_status;

	public TestRecordApproveModel() {
	}

	public TestRecordApproveModel(String s1, String s2, String s3, String s4, String s5, String s6, String s7,
			String s8, String s9) {
		if (s1.equals("ListRecordApproveDT")) {

			this.docno = s2;
			this.year = s3;
			this.itemno = s4;
			this.product_code = s5;
			this.qty = s6;
			this.unit_id = s7;
			this.unit_name = s8;
			this.product_name = s9;
		} else {
			this.record_approve_hd = s1;
			this.record_approve_t = s2;
			this.record_approve_date = s3;
			this.record_approve_title = s4;
			this.record_approve_rian = s5;
			this.record_approve_des1 = s6;
			this.record_approve_des2 = s7;
			this.record_approve_cen = s8;
			this.record_approve_dep = s9;
		}
	}

	public TestRecordApproveModel(String docno, String year, String itemno, String description, String qty,
			String unit_id) {
		this.docno = docno;
		this.year = year;
		this.itemno = itemno;
		this.description = description;
		this.qty = qty;
		this.unit_id = unit_id;
	}

	public TestRecordApproveModel(String forwhat, String s1, String s2, String s3, String s4, String s5, String s6) {
		if (forwhat.equals("prhd")) {
			this.docno = s1;
			this.record_approve_title = s2;
			this.record_approve_cen = s3;
			this.create_by = s4;
			this.year = s5;
			this.record_approve_date = s6;
		}
	}

	public TestRecordApproveModel(String img_path, String docno, String year) {
		this.img_path = img_path;
		this.docno = docno;
		this.year = year;
	}

	public void reset_hd() {
		this.record_approve_hd = "";
		this.record_approve_t = "";
		this.record_approve_date = "";
		this.record_approve_title = "";
		this.record_approve_rian = "";
		this.record_approve_des1 = "";
		this.record_approve_des2 = "";
		this.record_approve_cen = "";
		this.record_approve_dep = "";
	}

	public void reset_dt() {
		this.docno = "";
		this.year = "";
		this.description = "";
		this.qty = "";
		this.unit_id = "";
	}

	public void reset_ListItem() {
		this.description = "";
		this.qty = "";
		this.unit_id = "";
	}

	public TestRecordApproveModel(String product_code, String product_name, String unit, String unit_name) {
		this.unit_id = unit;
		this.product_code = product_code;
		this.product_name = product_name;
		this.unit_name = unit_name;
	}

	public TestRecordApproveModel(String s7, String docno, String year, String itemno, String description, String qty,
			String unit_id, String unit_name, String product_name, String AA) {
		if (s7.equals("s7")) {
			this.docno = docno;
			this.year = year;
			this.itemno = itemno;
			this.description = description;
			this.qty = qty;
			this.unit_id = unit_id;
			this.unit_name = unit_name;
			this.product_name = product_name;
		}

	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getRecord_approve_month() {
		return record_approve_month;
	}

	public void setRecord_approve_month(String record_approve_month) {
		this.record_approve_month = record_approve_month;
	}

	public String getFromwindow() {
		return fromwindow;
	}

	public void setFromwindow(String fromwindow) {
		this.fromwindow = fromwindow;
	}

	public String getCreatewindow() {
		return createwindow;
	}

	public void setCreatewindow(String createwindow) {
		this.createwindow = createwindow;
	}

	public String getAlertmsg() {
		return alertmsg;
	}

	public void setAlertmsg(String alertmsg) {
		this.alertmsg = alertmsg;
	}

	public File getQuotation_img() {
		return quotation_img;
	}

	public void setQuotation_img(File quotation_img) {
		this.quotation_img = quotation_img;
	}

	public String getVender_name() {
		return vender_name;
	}

	public void setVender_name(String vender_name) {
		this.vender_name = vender_name;
	}

	public String getVender_id() {
		return vender_id;
	}

	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}

	public String getQuotation_imgFileName() {
		return quotation_imgFileName;
	}

	public void setQuotation_imgFileName(String quotation_imgFileName) {
		this.quotation_imgFileName = quotation_imgFileName;
	}

	public String getQuotation_imgContentType() {
		return quotation_imgContentType;
	}

	public void setQuotation_imgContentType(String quotation_imgContentType) {
		this.quotation_imgContentType = quotation_imgContentType;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getDocnohidden() {
		return docnohidden;
	}

	public void setDocnohidden(String docnohidden) {
		this.docnohidden = docnohidden;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getRecord_approve_des3() {
		return record_approve_des3;
	}

	public void setRecord_approve_des3(String record_approve_des3) {
		this.record_approve_des3 = record_approve_des3;
	}

	public String getRecord_approve_hd() {
		return record_approve_hd;
	}

	public void setRecord_approve_hd(String record_approve_hd) {
		this.record_approve_hd = record_approve_hd;
	}

	public String getRecord_approve_t() {
		return record_approve_t;
	}

	public void setRecord_approve_t(String record_approve_t) {
		this.record_approve_t = record_approve_t;
	}

	public String getRecord_approve_date() {
		return record_approve_date;
	}

	public void setRecord_approve_date(String record_approve_date) {
		this.record_approve_date = record_approve_date;
	}

	public String getRecord_approve_title() {
		return record_approve_title;
	}

	public void setRecord_approve_title(String record_approve_title) {
		this.record_approve_title = record_approve_title;
	}

	public String getRecord_approve_rian() {
		return record_approve_rian;
	}

	public void setRecord_approve_rian(String record_approve_rian) {
		this.record_approve_rian = record_approve_rian;
	}

	public String getRecord_approve_des1() {
		return record_approve_des1;
	}

	public void setRecord_approve_des1(String record_approve_des1) {
		this.record_approve_des1 = record_approve_des1;
	}

	public String getRecord_approve_des2() {
		return record_approve_des2;
	}

	public void setRecord_approve_des2(String record_approve_des2) {
		this.record_approve_des2 = record_approve_des2;
	}

	public String getRecord_approve_cen() {
		return record_approve_cen;
	}

	public void setRecord_approve_cen(String record_approve_cen) {
		this.record_approve_cen = record_approve_cen;
	}

	public String getRecord_approve_dep() {
		return record_approve_dep;
	}

	public void setRecord_approve_dep(String record_approve_dep) {
		this.record_approve_dep = record_approve_dep;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
}
