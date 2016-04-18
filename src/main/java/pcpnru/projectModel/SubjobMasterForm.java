package pcpnru.projectModel;

public class SubjobMasterForm {

	private String subjobCodeHD;
	private String subjobCode;
	private String subjobName;
	private String dateTime;

	private String add;
	private String update;
	private String delete;

	public SubjobMasterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjobMasterForm(String subjobCode, String subjobName, String dateTime) {
		super();
		this.subjobCode = subjobCode;
		this.subjobName = subjobName;
		this.dateTime = dateTime;
	}

	public SubjobMasterForm(String subjobCode, String subjobName) {
		super();
		this.subjobCode = subjobCode;
		this.subjobName = subjobName;
	}

	public void reset() {
		this.subjobCode = "";
		this.subjobCodeHD = "";
		this.subjobName = "";
	}

	public String getSubjobCodeHD() {
		return subjobCodeHD;
	}

	public void setSubjobCodeHD(String subjobCodeHD) {
		this.subjobCodeHD = subjobCodeHD;
	}

	public String getSubjobCode() {
		return subjobCode;
	}

	public void setSubjobCode(String subjobCode) {
		this.subjobCode = subjobCode;
	}

	public String getSubjobName() {
		return subjobName;
	}

	public void setSubjobName(String subjobName) {
		this.subjobName = subjobName;
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
