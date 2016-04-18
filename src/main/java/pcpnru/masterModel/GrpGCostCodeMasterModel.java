package pcpnru.masterModel;

public class GrpGCostCodeMasterModel {

	private String gcostcode_r;
	private String gcostcode_c;
	private String grp_gcostcode;
	private String grp_gcostname;
	private String project_code;
	private String project_name;
	private String grp_costyear;
	private String amount;
	private String amount_c;
	private String amounttotal;
	private String qty;
	private String gcostcode;
	private String gcostcode_name;

	private String add;
	private String update;
	private String delete;

	public GrpGCostCodeMasterModel() {

		// TODO Auto-generated constructor stub
	}

	public GrpGCostCodeMasterModel(String gcostcode_c, String grp_gcostcode, String grp_gcostname, String project_code,
			String grp_costyear, String amount) {
		super();

		this.gcostcode_c = gcostcode_c;
		this.grp_gcostcode = grp_gcostcode;
		this.grp_gcostname = grp_gcostname;
		this.project_code = project_code;
		this.grp_costyear = grp_costyear;
		this.amount = amount;
	}

	public GrpGCostCodeMasterModel(String project_code, String project_name, String grp_gcostcode, String grp_gcostname,
			String grp_costyear, String amounttotal, String qty) {
		super();
		this.project_code = project_code;
		this.project_name = project_name;
		this.grp_gcostcode = grp_gcostcode;
		this.grp_gcostname = grp_gcostname;
		this.amounttotal = amounttotal;
		this.grp_costyear = grp_costyear;
		this.qty = qty;
	}

	public GrpGCostCodeMasterModel(String project_code, String project_name, String gcostcode, String gcostcode_name,
			String amount_c) {
		super();
		this.project_code = project_code;
		this.project_name = project_name;
		this.gcostcode = gcostcode;
		this.gcostcode_name = gcostcode_name;
		this.amount_c = amount_c;
	}

	public void reset() {
		this.gcostcode_r = "";
		this.gcostcode_c = "";
		this.grp_gcostcode = "";
		this.grp_gcostname = "";
		this.project_code = "";
		this.grp_costyear = "";
	}

	public String getGcostcode_r() {
		return gcostcode_r;
	}

	public void setGcostcode_r(String gcostcode_r) {
		this.gcostcode_r = gcostcode_r;
	}

	public String getGcostcode_c() {
		return gcostcode_c;
	}

	public void setGcostcode_c(String gcostcode_c) {
		this.gcostcode_c = gcostcode_c;
	}

	public String getGrp_gcostcode() {
		return grp_gcostcode;
	}

	public void setGrp_gcostcode(String grp_gcostcode) {
		this.grp_gcostcode = grp_gcostcode;
	}

	public String getGrp_gcostname() {
		return grp_gcostname;
	}

	public void setGrp_gcostname(String grp_gcostname) {
		this.grp_gcostname = grp_gcostname;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getGrp_costyear() {
		return grp_costyear;
	}

	public void setGrp_costyear(String grp_costyear) {
		this.grp_costyear = grp_costyear;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount_c() {
		return amount_c;
	}

	public void setAmount_r(String amount_c) {
		this.amount_c = amount_c;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getGcostcode_name() {
		return gcostcode_name;
	}

	public void setGcostcode_name(String gcostcode_name) {
		this.gcostcode_name = gcostcode_name;
	}

	public String getGcostcode() {
		return gcostcode;
	}

	public void setGcostcode(String gcostcode) {
		this.gcostcode = gcostcode;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public void setAmount_c(String amount_c) {
		this.amount_c = amount_c;
	}

	public String getAmounttotal() {
		return amounttotal;
	}

	public void setAmounttotal(String amounttotal) {
		this.amounttotal = amounttotal;
	}

}
