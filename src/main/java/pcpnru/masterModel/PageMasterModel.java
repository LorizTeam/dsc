package pcpnru.masterModel;

public class PageMasterModel {

	private String pagegroup_code;
	private String pagegroup_name;
	private String page_code;
	private String page_name;

	private String add;
	private String update;
	private String delete;

	public PageMasterModel() {

	}

	public PageMasterModel(String pagegroup_code, String pagegroup_name, String page_code, String page_name) {
		super();
		this.pagegroup_code = pagegroup_code;
		this.pagegroup_name = pagegroup_name;
		this.page_code = page_code;
		this.page_name = page_name;
	}

	public void reset() {
		this.pagegroup_code = "";
		this.pagegroup_name = "";
		this.page_code = "";
		this.page_name = "";
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
