package pcpnru.masterModel;

public class PageGroupMasterModel {

	private String pagegroup_code;
	private String pagegroup_name;

	public PageGroupMasterModel() {

	}

	public PageGroupMasterModel(String pagegroup_code, String pagegroup_name) {
		super();
		this.pagegroup_code = pagegroup_code;
		this.pagegroup_name = pagegroup_name;
	}

	public void reset() {
		this.pagegroup_code = "";
		this.pagegroup_name = "";
	}

	public String getPagegroup_code() {
		return pagegroup_code;
	}

	public void setPagegroup_code(String pagegroup_code) {
		this.pagegroup_code = pagegroup_code;
	}

	public String getPagegroup_name() {
		return pagegroup_name;
	}

	public void setPagegroup_name(String pagegroup_name) {
		this.pagegroup_name = pagegroup_name;
	}

}
