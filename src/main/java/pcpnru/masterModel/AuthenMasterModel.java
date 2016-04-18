package pcpnru.masterModel;

public class AuthenMasterModel {

	private String authen_type;
	private String authen_type_name;

	private String add;
	private String update;
	private String delete;

	public AuthenMasterModel() {

	}

	public AuthenMasterModel(String authen_type, String authen_type_name) {
		this.authen_type = authen_type;
		this.authen_type_name = authen_type_name;
	}

	public void reset() {
		this.authen_type_name = "";
		this.authen_type = "";
	}

	public String getAuthen_type() {
		return authen_type;
	}

	public void setAuthen_type(String authen_type) {
		this.authen_type = authen_type;
	}

	public String getAuthen_type_name() {
		return authen_type_name;
	}

	public void setAuthen_type_name(String authen_type_name) {
		this.authen_type_name = authen_type_name;
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
