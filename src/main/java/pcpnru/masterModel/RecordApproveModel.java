package pcpnru.masterModel;

import java.io.File;

public class RecordApproveModel {

	private String record_approve_hd;
	private String record_approve_t;
	private String record_approve_date,record_approve_send;
	private String record_approve_title;
	private String record_approve_rian;
	private String record_approve_des1;
	private String record_approve_des2;
	private String record_approve_des3;
	private String record_approve_cen;
	private String record_approve_dep, record_approve_month;
	private String fromwindow, createwindow, alertmsg, create_name;

	// Vendor --------------------------------------------------------
	private File toBeUploaded;
	private String vendor_name, vendor_id, toBeUploadedFileName, toBeUploadedContentType, img_path;
	private double total_amount;
	private int credit_day;

	// Vender --------------------------------------------------------

	private String docno, docnohidden;
	private String year;
	private String itemno;
	private String qty;
	private String unit_id, create_by, product_code, product_name, unit_name, approve_status;
	private String saved;

	public RecordApproveModel() {
	}

	public RecordApproveModel(String forwhat, String s1, String s2, String s3, String s4, String s5, String s6) {
		if (forwhat.equals("prhd")) {
			this.docno = s1;
			this.create_by = s2;
			this.year = s3;
			this.record_approve_date = s4;
			this.create_name = s5;
			this.approve_status = s6;
		}
	}

	public RecordApproveModel(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8,
			String s9, String s10) {
		if (s1.equals("ListRecordApproveDT")) {
			this.docno = s2;
			this.year = s3;
			this.itemno = s4;
			this.product_code = s5;
			this.qty = s6;
			this.unit_id = s7;
			this.unit_name = s8;
			this.product_name = s9;
			this.approve_status = s10;
		}
	}

	public RecordApproveModel(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8,
			String s9) {
		if (s1.equals("ListRecordApproveDT")) {
			this.docno = s2;
			this.year = s3;
			this.itemno = s4;
			this.product_code = s5;
			this.qty = s6;
			this.unit_id = s7;
			this.unit_name = s8;
			this.product_name = s9;
		} else if (s1.equals("prhd")) {
			this.docno = s2;
			this.record_approve_title = s3;
			this.record_approve_cen = s4;
			this.create_by = s5;
			this.year = s6;
			this.record_approve_date = s7;
			this.create_name = s8;
			this.approve_status = s9;
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

	public RecordApproveModel(String docno, String year, String itemno, String product_code, String qty,
			String unit_id) {
		super();
		this.docno = docno;
		this.year = year;
		this.itemno = itemno;
		this.product_code = product_code;
		this.qty = qty;
		this.unit_id = unit_id;
	}

	public RecordApproveModel(String img_path, String docno, String year) {
		this.img_path = img_path;
		this.docno = docno;
		this.year = year;
	}

	public RecordApproveModel(String s1, String s2, String s3, String s4) {
		this.product_code = s1;
		this.product_name = s2;
		this.unit_id = s3;
		this.unit_name = s4;
	}

	public RecordApproveModel(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8) {
		if (s1.equals("ListRecordApproveDT")) {
			this.docno = s2;
			this.year = s3;
			this.itemno = s4;
			this.product_code = s5;
			this.qty = s6;
			this.unit_id = s7;
			this.unit_name = s8;
		}
		if (s1.equals("prhd")) {
			this.docno = s2;
			this.record_approve_title = s3;
			this.record_approve_cen = s4;
			this.create_by = s5;
			this.year = s6;
			this.record_approve_date = s7;
			this.create_name = s8;
		}
	}

	// Reset --------------------------------------------------------
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
		this.product_code = "";
		this.qty = "";
		this.unit_id = "";
	}

	public void Approve_pr() {
		this.docno = "";
		this.year = "";
		this.record_approve_title = "";
		this.record_approve_month = "";
		this.record_approve_date = "";
	}

	public void reset_ListItem() {
		this.product_code = "";
		this.product_name = "";
		this.qty = "";
		this.unit_id = "";
	}

	public void reset_alert() {
		this.alertmsg = "";
	}
	// Reset --------------------------------------------------------
	
	public String getApprove_status() {
		return approve_status;
	}
	
	public int getCredit_day() {
		return credit_day;
	}

	public void setCredit_day(int credit_day) {
		this.credit_day = credit_day;
	}

	public String getRecord_approve_send() {
		return record_approve_send;
	}

	public void setRecord_approve_send(String record_approve_send) {
		this.record_approve_send = record_approve_send;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
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

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getSaved() {
		return saved;
	}

	public void setSaved(String saved) {
		this.saved = saved;
	}

	public File getToBeUploaded() {
		return toBeUploaded;
	}

	public void setToBeUploaded(File toBeUploaded) {
		this.toBeUploaded = toBeUploaded;
	}

	public String getToBeUploadedFileName() {
		return toBeUploadedFileName;
	}

	public void setToBeUploadedFileName(String toBeUploadedFileName) {
		this.toBeUploadedFileName = toBeUploadedFileName;
	}

	public String getToBeUploadedContentType() {
		return toBeUploadedContentType;
	}

	public void setToBeUploadedContentType(String toBeUploadedContentType) {
		this.toBeUploadedContentType = toBeUploadedContentType;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getAlertmsg() {
		return alertmsg;
	}

	public void setAlertmsg(String alertmsg) {
		this.alertmsg = alertmsg;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getCreatewindow() {
		return createwindow;
	}

	public void setCreatewindow(String createwindow) {
		this.createwindow = createwindow;
	}

	public String getFromwindow() {
		return fromwindow;
	}

	public void setFromwindow(String fromwindow) {
		this.fromwindow = fromwindow;
	}

	public String getDocnohidden() {
		return docnohidden;
	}

	public void setDocnohidden(String docnohidden) {
		this.docnohidden = docnohidden;
	}

	public String getRecord_approve_month() {
		return record_approve_month;
	}

	public void setRecord_approve_month(String record_approve_month) {
		this.record_approve_month = record_approve_month;
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
