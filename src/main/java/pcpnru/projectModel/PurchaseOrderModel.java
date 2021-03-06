package pcpnru.projectModel;

import java.io.File;

import pcpnru.masterModel.RecordApproveModel;

public class PurchaseOrderModel extends RecordApproveModel {

	private String po_docno, project_code;
	private String po_docdate;
	private String pre_loadpr, ref_pr, ref_prdate, ref_quotation, ref_quotationdate;
	private String type;
	private String po_offer, approve_by;
	private double penaltype_perday;
	private int credit_day;

	private double mulct_day;
	private String vender;

	private String quotation_number;
	private String quotation_date;

	private String approve_status;
	private String create_by;
	
	private String docno;
	private String itemno;
	private String year;
	private String description;
	private String qty;
	private String amount;
	private String amounttotal;
	private String remark;
	
	private String po_day;
	private String po_month;
	private String po_year;
	
	private File toBeUploaded;
	private String vendor_name,vendor_id,toBeUploadedFileName,toBeUploadedContentType,img_path;
	
	private String alertmsg;

	public PurchaseOrderModel() {
	}

	public PurchaseOrderModel(String docno, String itemno, String year, String description, String qty, String amount,
			String amounttotal, String remark) {
		super();
		this.docno = docno;
		this.itemno = itemno;
		this.year = year;
		this.description = description;
		this.qty = qty;
		this.amount = amount;
		this.amounttotal = amounttotal;
		this.remark = remark;
	}
	public PurchaseOrderModel(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, int s10, double s11, String s12, String s13) {
			this.po_docno = s1;
			this.vender = s2;
			this.year = s3;
			this.po_docdate = s4; 
			this.approve_status = s5;
			this.type = s6;
			this.create_by = s7;
			this.ref_pr = s8;
			this.ref_prdate = s9;
			this.credit_day = s10;
			this.mulct_day = s11;
			this.quotation_number = s12;
			this.quotation_date = s13;
	}
	
	public PurchaseOrderModel(String img_path, String docno, String year) {
		this.img_path = img_path;
		this.docno = docno;
		this.year = year;
	}
	
	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getPre_loadpr() {
		return pre_loadpr;
	}

	public void setPre_loadpr(String pre_loadpr) {
		this.pre_loadpr = pre_loadpr;
	}

	public String getPo_docno() {
		return po_docno;
	}

	public void setPo_docno(String po_docno) {
		this.po_docno = po_docno;
	}

	public String getPo_docdate() {
		return po_docdate;
	}

	public void setPo_docdate(String po_docdate) {
		this.po_docdate = po_docdate;
	}

	public String getRef_pr() {
		return ref_pr;
	}

	public void setRef_pr(String ref_pr) {
		this.ref_pr = ref_pr;
	}

	public String getRef_prdate() {
		return ref_prdate;
	}

	public void setRef_prdate(String ref_prdate) {
		this.ref_prdate = ref_prdate;
	}

	public String getRef_quotation() {
		return ref_quotation;
	}

	public void setRef_quotation(String ref_quotation) {
		this.ref_quotation = ref_quotation;
	}

	public String getRef_quotationdate() {
		return ref_quotationdate;
	}

	public void setRef_quotationdate(String ref_quotationdate) {
		this.ref_quotationdate = ref_quotationdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPo_offer() {
		return po_offer;
	}

	public void setPo_offer(String po_offer) {
		this.po_offer = po_offer;
	}

	public String getApprove_by() {
		return approve_by;
	}

	public void setApprove_by(String approve_by) {
		this.approve_by = approve_by;
	}

	public double getPenaltype_perday() {
		return penaltype_perday;
	}

	public void setPenaltype_perday(double penaltype_perday) {
		this.penaltype_perday = penaltype_perday;
	}

	public int getCredit_day() {
		return credit_day;
	}

	public void setCredit_day(int credit_day) {
		this.credit_day = credit_day;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public String getQuotation_number() {
		return quotation_number;
	}

	public void setQuotation_number(String quotation_number) {
		this.quotation_number = quotation_number;
	}

	public String getQuotation_date() {
		return quotation_date;
	}

	public void setQuotation_date(String quotation_date) {
		this.quotation_date = quotation_date;
	}

	public double getMulct_day() {
		return mulct_day;
	}

	public void setMulct_day(double mulct_day) {
		this.mulct_day = mulct_day;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmounttotal() {
		return amounttotal;
	}

	public void setAmounttotal(String amounttotal) {
		this.amounttotal = amounttotal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAlertmsg() {
		return alertmsg;
	}

	public void setAlertmsg(String alertmsg) {
		this.alertmsg = alertmsg;
	}

	public File getToBeUploaded() {
		return toBeUploaded;
	}

	public void setToBeUploaded(File toBeUploaded) {
		this.toBeUploaded = toBeUploaded;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
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

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getPo_day() {
		return po_day;
	}

	public void setPo_day(String po_day) {
		this.po_day = po_day;
	}

	public String getPo_month() {
		return po_month;
	}

	public void setPo_month(String po_month) {
		this.po_month = po_month;
	}

	public String getPo_year() {
		return po_year;
	}

	public void setPo_year(String po_year) {
		this.po_year = po_year;
	}
}
