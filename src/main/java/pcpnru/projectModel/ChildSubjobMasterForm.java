package pcpnru.projectModel;

public class ChildSubjobMasterForm {

	private String childsubjobcodehd;
	private String subjobcodehd;
	private String subjobcode;
	private String subjobname;
	private String childsubjobcode;
	private String childsubjobname;
	private String dateTime;

	private String add;
	private String update;
	private String delete;

	public ChildSubjobMasterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChildSubjobMasterForm(String subjobcode, String subjobname, String childsubjobcode, String childsubjobname,
			String dateTime) {
		super();
		this.subjobcode = subjobcode;
		this.subjobname = subjobname;
		this.childsubjobcode = childsubjobcode;
		this.childsubjobname = childsubjobname;
		this.dateTime = dateTime;
	}

	public ChildSubjobMasterForm(String childsubjobcode, String childsubjobname) {
		super();
		this.childsubjobcode = childsubjobcode;
		this.childsubjobname = childsubjobname;
	}

	public void reset() {
		this.subjobcodehd = "";
		this.childsubjobcodehd = "";
		this.childsubjobcode = "";
		this.childsubjobname = "";
	}

	public String getChildsubjobcodehd() {
		return childsubjobcodehd;
	}

	public void setChildsubjobcodehd(String childsubjobcodehd) {
		this.childsubjobcodehd = childsubjobcodehd;
	}

	public String getSubjobcode() {
		return subjobcode;
	}

	public void setSubjobcode(String subjobcode) {
		this.subjobcode = subjobcode;
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

	public String getSubjobname() {
		return subjobname;
	}

	public void setSubjobname(String subjobname) {
		this.subjobname = subjobname;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getSubjobcodehd() {
		return subjobcodehd;
	}

	public void setSubjobcodehd(String subjobcodehd) {
		this.subjobcodehd = subjobcodehd;
	}

}
