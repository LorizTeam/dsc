package pcpnru.inventoryModel;

public class ProductGroupModel extends Create_UpdateModel {
	private String progroup_id, progroup_name;

	public ProductGroupModel() {
	}

	public ProductGroupModel(String progroup_id, String progroup_name, String create_by, String create_datetime,
			String update_by, String update_datetime) {
		this.progroup_id = progroup_id;
		this.progroup_name = progroup_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public void ClearProductGroupModel() {
		this.progroup_id = "";
		this.progroup_name = "";
	}

	public String getProgroup_id() {
		return progroup_id;
	}

	public void setProgroup_id(String progroup_id) {
		this.progroup_id = progroup_id;
	}

	public String getProgroup_name() {
		return progroup_name;
	}

	public void setProgroup_name(String progroup_name) {
		this.progroup_name = progroup_name;
	}

}
