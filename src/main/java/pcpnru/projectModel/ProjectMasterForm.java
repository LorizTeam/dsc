package pcpnru.projectModel;

public class ProjectMasterForm {

	private String projectCodeHD;
	private String projectCode;
	private String projectName;
	private String dateTime;

	private String add;
	private String update;
	private String delete;

	public ProjectMasterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectMasterForm(String projectCode, String projectName, String dateTime) {
		super();
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.dateTime = dateTime;
	}

	public void reset() {
		this.projectCode = "";
		this.projectCodeHD = "";
		this.projectName = "";
	}

	public String getProjectCodeHD() {
		return projectCodeHD;
	}

	public void setProjectCodeHD(String projectCodeHD) {
		this.projectCodeHD = projectCodeHD;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
