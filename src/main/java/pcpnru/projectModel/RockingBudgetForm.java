package pcpnru.projectModel;

public class RockingBudgetForm {

	private String docno;
	private String year;
	private String project_code;
	private String gcostcode;
	private String gcostname;
	private String frombalance;
	private String gcostcode_rock;
	private String gcostname_rock;
	private String frombalance_rock;
	private String rocking_budget;
	private String balance;
	private String docdate;
	private String remark;
	private String approve_status;

	private String add;
	private String update;
	private String delete;

	public RockingBudgetForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RockingBudgetForm(String docno, String project_code, String year, String gcostcode, String gcostname,
			String frombalance, String gcostcode_rock, String gcostname_rock, String frombalance_rock,
			String rocking_gudget, String balance, String docdate, String remark, String approve_status) {
		super();
		this.docno = docno;
		this.project_code = project_code;
		this.year = year;
		this.gcostcode = gcostcode;
		this.gcostname = gcostname;
		this.frombalance = frombalance;
		this.gcostcode_rock = gcostcode_rock;
		this.gcostname_rock = gcostname_rock;
		this.frombalance_rock = frombalance_rock;
		this.rocking_budget = rocking_gudget;
		this.balance = balance;
		this.docdate = docdate;
		this.remark = remark;
		this.approve_status = approve_status;
	}

	public void reset() {
		this.project_code = "";
		this.year = "";
		this.gcostcode = "";
		this.frombalance = "";
		this.gcostcode_rock = "";
		this.frombalance_rock = "";
		this.rocking_budget = "";
		this.balance = "";
		this.docdate = "";
		this.remark = "";
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

	public String getGcostcode() {
		return gcostcode;
	}

	public void setGcostcode(String gcostcode) {
		this.gcostcode = gcostcode;
	}

	public String getFrombalance() {
		return frombalance;
	}

	public void setFrombalance(String frombalance) {
		this.frombalance = frombalance;
	}

	public String getGcostcode_rock() {
		return gcostcode_rock;
	}

	public void setGcostcode_rock(String gcostcode_rock) {
		this.gcostcode_rock = gcostcode_rock;
	}

	public String getFrombalance_rock() {
		return frombalance_rock;
	}

	public void setFrombalance_rock(String frombalance_rock) {
		this.frombalance_rock = frombalance_rock;
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

	public String getDocdate() {
		return docdate;
	}

	public void setDocdate(String docdate) {
		this.docdate = docdate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getGcostname() {
		return gcostname;
	}

	public void setGcostname(String gcostname) {
		this.gcostname = gcostname;
	}

	public String getGcostname_rock() {
		return gcostname_rock;
	}

	public void setGcostname_rock(String gcostname_rock) {
		this.gcostname_rock = gcostname_rock;
	}

}
