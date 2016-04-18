package pcpnru.masterModel;

public class GroupCostCodeMasterModel {

	private String costCodeHD;
	private String costCode;
	private String costName;
	private String dateTime;
	private String standardprice, fundprice, amount;
	private String type_gcostcode;
	private String project_name;
	private String project_code;
	private String add;
	private String update;
	private String delete;

	private String grp_gcostcode;

	public GroupCostCodeMasterModel() {

		// TODO Auto-generated constructor stub
	}

	public GroupCostCodeMasterModel(String project_code, String project_name, String costCode, String costName,
			String standardprice, String fundprice, String dateTime, String amount, String grp_gcostcode) {

		this.project_code = project_code;
		this.project_name = project_name;
		this.costCode = costCode;
		this.costName = costName;
		this.standardprice = standardprice;
		this.fundprice = fundprice;
		this.dateTime = dateTime;
		this.amount = amount;
		this.grp_gcostcode = grp_gcostcode;
	}

	public GroupCostCodeMasterModel(String project_code, String project_name, String costCode, String costName,
			String dateTime, String amount) {

		this.project_code = project_code;
		this.project_name = project_name;
		this.costCode = costCode;
		this.costName = costName;
		this.dateTime = dateTime;
		this.amount = amount;
	}

	public GroupCostCodeMasterModel(String costCode, String costName) {

		this.costCode = costCode;
		this.costName = costName;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getType_gcostcode() {
		return type_gcostcode;
	}

	public void setType_gcostcode(String type_gcostcode) {
		this.type_gcostcode = type_gcostcode;
	}

	public String getCostCodeHD() {
		return costCodeHD;
	}

	public void setCostCodeHD(String costCodeHD) {
		this.costCodeHD = costCodeHD;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
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

	public void reset() {
		this.costCodeHD = "";
		this.costCode = "";
		this.costName = "";
		this.standardprice = "";
		this.fundprice = "";
		this.amount = "";
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getStandardprice() {
		return standardprice;
	}

	public void setStandardprice(String standardprice) {
		this.standardprice = standardprice;
	}

	public String getFundprice() {
		return fundprice;
	}

	public void setFundprice(String fundprice) {
		this.fundprice = fundprice;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGrp_gcostcode() {
		return grp_gcostcode;
	}

	public void setGrp_gcostcode(String grp_gcostcode) {
		this.grp_gcostcode = grp_gcostcode;
	}
}
