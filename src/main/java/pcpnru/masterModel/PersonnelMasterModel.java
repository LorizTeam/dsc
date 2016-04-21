package pcpnru.masterModel;

public class PersonnelMasterModel {

	private String personnel_id;
	private String personnel_name;
	private String personnel_lastname;
	private String authen_type;
	private String authen_type_name;
	private String project_code;
	private String project_name;
	
	private String manday;

	private String dow, dob, telephone, address, position, dateTime;

	private String add;
	private String update;
	private String delete;

	public PersonnelMasterModel() {

	}

	public PersonnelMasterModel(String forwhat, String personnel_id, String personnel_name, String personnel_lastname,
			String authen_type, String authen_type_name, String project_code, String project_name, String dow,
			String dob, String telephone, String address, String position, String dateTime, String manday) {

		if (forwhat.equals("personnel")) {
			this.personnel_id = personnel_id;
			this.personnel_name = personnel_name;
			this.personnel_lastname = personnel_lastname;
			this.authen_type = authen_type;
			this.authen_type_name = authen_type_name;
			this.project_code = project_code;
			this.project_name = project_name;
			this.dow = dow;
			this.dob = dob;
			this.telephone = telephone;
			this.address = address;
			this.position = position;
			this.dateTime = dateTime;
			this.manday	= manday;
		}
	}

	public PersonnelMasterModel(String personnel_id, String personnel_name, String authen_type, String project_code,
			String project_name) {

		this.personnel_id = personnel_id;
		this.personnel_name = personnel_name;
		this.authen_type = authen_type;
		this.project_code = project_code;
		this.project_name = project_name;
	}

	public PersonnelMasterModel(String project_code, String project_name) {
		this.project_code = project_code;
		this.project_name = project_name;
	}

	public void reset() {
		this.personnel_id = "";
		this.personnel_name = "";
		this.personnel_lastname = "";
		this.authen_type = "";
		this.project_code = "";
		this.project_name = "";
		this.dow = "";
		this.dob = "";
		this.telephone = "";
		this.address = "";
		this.position = "";
		this.dateTime = "";
		this.manday = "";
	}

	public String getPersonnel_id() {
		return personnel_id;
	}

	public void setPersonnel_id(String personnel_id) {
		this.personnel_id = personnel_id;
	}

	public String getPersonnel_name() {
		return personnel_name;
	}

	public void setPersonnel_name(String personnel_name) {
		this.personnel_name = personnel_name;
	}

	public String getPersonnel_lastname() {
		return personnel_lastname;
	}

	public void setPersonnel_lastname(String personnel_lastname) {
		this.personnel_lastname = personnel_lastname;
	}

	public String getAuthen_type() {
		return authen_type;
	}

	public void setAuthen_type(String authen_type) {
		this.authen_type = authen_type;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAuthen_type_name() {
		return authen_type_name;
	}

	public void setAuthen_type_name(String authen_type_name) {
		this.authen_type_name = authen_type_name;
	}

	public String getManday() {
		return manday;
	}

	public void setManday(String manday) {
		this.manday = manday;
	}

}
