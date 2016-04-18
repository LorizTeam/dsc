package pcpnru.masterModel;

public class ManageApproveRequisitionModel {

	private String manageapprove;
	private String manageapprovename;
	private String budget;

	private String add;
	private String update;
	private String delete;

	public ManageApproveRequisitionModel() {

	}

	public ManageApproveRequisitionModel(String manageapprove, String manageapprovename, String budget) {
		super();
		this.manageapprove = manageapprove;
		this.manageapprovename = manageapprovename;
		this.budget = budget;
	}

	public void reset() {
		this.manageapprove = "";
		this.manageapprove = "";
		this.budget = "";
	}

	public String getManageapprove() {
		return manageapprove;
	}

	public void setManageapprove(String manageapprove) {
		this.manageapprove = manageapprove;
	}

	public String getManageapprovename() {
		return manageapprovename;
	}

	public void setManageapprovename(String manageapprovename) {
		this.manageapprovename = manageapprovename;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
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

}
