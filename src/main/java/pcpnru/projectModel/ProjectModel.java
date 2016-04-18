package pcpnru.projectModel;

public class ProjectModel {

	// HD
	private String project_code, target, percen, year, datetime_response, freeze;
	private String project_name;

	// DT receive
	private String gcostcode, gcostname, budget, budget_now;

	// Project plan DT
	private String subjob_code, subjob_name, childsubjobcode, childsubjobname, gcostcode_name;

	public ProjectModel() {

	}

	public ProjectModel(String project_code, String project_name, String subjob_code, String subjob_name,
			String childsubjobcode, String childsubjobname, String gcostcode, String gcostcode_name, String budget,
			String budget_now, String datetime_response) {

		this.project_code = project_code;
		this.project_name = project_name;
		this.subjob_code = subjob_code;
		this.subjob_name = subjob_name;
		this.childsubjobcode = childsubjobcode;
		this.childsubjobname = childsubjobname;
		this.gcostcode = gcostcode;
		this.gcostcode_name = gcostcode_name;
		this.budget = budget;
		this.budget_now = budget_now;
		this.datetime_response = datetime_response;

	}

	public ProjectModel(String project_code, String project_name, String target, String percen, String year,
			String datetime_response, String freeze, String forwat) {

		this.project_code = project_code;
		this.project_name = project_name;
		this.target = target;
		this.percen = percen;
		this.year = year;
		this.datetime_response = datetime_response;
		this.freeze = freeze;

	}

	public ProjectModel(String project_code, String gcostcode, String gcostname, String budget) {
		this.project_code = project_code;
		this.gcostcode = gcostcode;
		this.gcostname = gcostname;
		this.budget = budget;

	}

	public ProjectModel(String forwhat, String project_code2, String project_name2, String year2, String target2,
			String forwhat2) {
		if (forwhat.equals("getListProject_Join_Projecthead")) {
			this.project_code = project_code2;
			this.project_name = project_name2;
			this.year = year2;
			this.target = target2;
		}
	}

	public void reset() {

		this.project_code = "";
		this.target = "";
		this.percen = "";
		this.year = "";
		this.datetime_response = "";

	}

	public String getSubjob_code() {
		return subjob_code;
	}

	public void setSubjob_code(String subjob_code) {
		this.subjob_code = subjob_code;
	}

	public String getSubjob_name() {
		return subjob_name;
	}

	public void setSubjob_name(String subjob_name) {
		this.subjob_name = subjob_name;
	}

	public String getChildsubjobcode() {
		return childsubjobcode;
	}

	public void setChildsubjobcode(String childsubjobcode) {
		this.childsubjobcode = childsubjobcode;
	}

	public String getChildsubjobname() {
		return childsubjobname;
	}

	public void setChildsubjobname(String childsubjobname) {
		this.childsubjobname = childsubjobname;
	}

	public String getGcostcode() {
		return gcostcode;
	}

	public void setGcostcode(String gcostcode) {
		this.gcostcode = gcostcode;
	}

	public String getGcostcode_name() {
		return gcostcode_name;
	}

	public void setGcostcode_name(String gcostcode_name) {
		this.gcostcode_name = gcostcode_name;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDatetime_response() {
		return datetime_response;
	}

	public void setDatetime_response(String datetime_response) {
		this.datetime_response = datetime_response;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getGcostname() {
		return gcostname;
	}

	public void setGcostname(String gcostname) {
		this.gcostname = gcostname;
	}

	public String getPercen() {
		return percen;
	}

	public void setPercen(String percen) {
		this.percen = percen;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	public String getBudget_now() {
		return budget_now;
	}

	public void setBudget_now(String budget_now) {
		this.budget_now = budget_now;
	}
}
