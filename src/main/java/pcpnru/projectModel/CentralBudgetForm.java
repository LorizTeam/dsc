package pcpnru.projectModel;

public class CentralBudgetForm {

	private String year;
	private String amt;

	private String docno;
	private String project_code;
	private String project_name;
	private String gcostcode;
	private String gcostname;
	private String frombalance;
	private String rocking_budget;
	private String balance;
	private String remark;
	private String docdate;
	private String approve_status;

	public CentralBudgetForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CentralBudgetForm(String docno, String project_code, String project_name, String year, String gcostcode,
			String gcostname, String amt, String frombalance, String rocking_budget, String balance, String remark,
			String docdate, String approve_status) {
		super();
		this.docno = docno;
		this.project_code = project_code;
		this.project_name = project_name;
		this.year = year;
		this.gcostcode = gcostcode;
		this.gcostname = gcostname;
		this.amt = amt;
		this.frombalance = frombalance;
		this.rocking_budget = rocking_budget;
		this.balance = balance;
		this.remark = remark;
		this.docdate = docdate;
		this.approve_status = approve_status;
	}

	public CentralBudgetForm(String year, String amt) {
		super();
		this.year = year;
		this.amt = amt;
	}

	public void reset() {
		this.year = "";
		this.amt = "";
		this.docno = "";
		this.project_code = "";
		this.project_name = "";
		this.gcostcode = "";
		this.gcostname = "";
		this.frombalance = "";
		this.rocking_budget = "";
		this.balance = "";
		this.remark = "";
		this.docdate = "";
		this.approve_status = "";
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getFrombalance() {
		return frombalance;
	}

	public void setFrombalance(String frombalance) {
		this.frombalance = frombalance;
	}

	public String getRocking_budget() {
		return rocking_budget;
	}

	public void setRocking_budget(String rocking_budget) {
		this.rocking_budget = rocking_budget;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGcostcode() {
		return gcostcode;
	}

	public void setGcostcode(String gcostcode) {
		this.gcostcode = gcostcode;
	}

	public String getGcostname() {
		return gcostname;
	}

	public void setGcostname(String gcostname) {
		this.gcostname = gcostname;
	}

	public String getDocdate() {
		return docdate;
	}

	public void setDocdate(String docdate) {
		this.docdate = docdate;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

}
