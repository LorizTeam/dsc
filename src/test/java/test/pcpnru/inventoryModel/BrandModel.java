package test.pcpnru.inventoryModel;

public class BrandModel extends Create_UpdateModel {
	private String brand_id, brand_name;

	public BrandModel() {
	}

	public BrandModel(String brand_id, String brand_name, String create_by, String create_datetime, String update_by,
			String update_datetime) {
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

}
