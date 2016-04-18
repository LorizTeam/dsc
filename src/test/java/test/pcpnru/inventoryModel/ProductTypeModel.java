package test.pcpnru.inventoryModel;

public class ProductTypeModel extends Create_UpdateModel {
	private String protype_id, protype_name;

	public ProductTypeModel(String protype_id, String protype_name, String create_by, String create_datetime,
			String update_by, String update_datetime) {
		this.protype_id = protype_id;
		this.protype_name = protype_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public String getProtype_id() {
		return protype_id;
	}

	public void setProtype_id(String protype_id) {
		this.protype_id = protype_id;
	}

	public String getProtype_name() {
		return protype_name;
	}

	public void setProtype_name(String protype_name) {
		this.protype_name = protype_name;
	}

}
