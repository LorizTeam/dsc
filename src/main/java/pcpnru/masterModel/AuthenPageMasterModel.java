package pcpnru.masterModel;

public class AuthenPageMasterModel {

	private String authen_type;
	private String authen_name;
	private String page_code;
	private String page_name;

	private String add;
	private String update;
	private String delete;

	public AuthenPageMasterModel() {

	}

	public AuthenPageMasterModel(String authen_type, String authen_name, String page_code, String page_name) {
		super();
		this.authen_type = authen_type;
		this.authen_name = authen_name;
		this.page_code = page_code;
		this.page_name = page_name;
	}

	public AuthenPageMasterModel(String page_code, String page_name) {
		super();
		this.page_code = page_code;
		this.page_name = page_name;
	}

	public void reset() {
		this.authen_type = "";
		this.page_code = "";
	}

	public String getAuthen_type() {
		return authen_type;
	}

	public void setAuthen_type(String authen_type) {
		this.authen_type = authen_type;
	}

	public String getAuthen_name() {
		return authen_name;
	}

	public void setAuthen_name(String authen_name) {
		this.authen_name = authen_name;
	}

	public String getPage_code() {
		return page_code;
	}

	public void setPage_code(String page_code) {
		this.page_code = page_code;
	}

	public String getPage_name() {
		return page_name;
	}

	public void setPage_name(String page_name) {
		this.page_name = page_name;
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
