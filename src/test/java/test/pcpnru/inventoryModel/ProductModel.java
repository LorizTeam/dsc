package test.pcpnru.inventoryModel;

public class ProductModel extends Create_UpdateModel {

	private String product_code, product_name, status_id, status_name, progroup_id, progroup_name, protype_id,
			protype_name, unit_id, unit_name, brand_id, brand_name;

	public ProductModel() {
	}

	public ProductModel(String product_code, String product_name, String status_id, String status_name,
			String progroup_id, String progroup_name, String protype_id, String protype_name, String unit_id,
			String unit_name, String brand_id, String brand_name, String create_by, String create_datetime,
			String update_by, String update_datetime) {
		this.product_code = product_code;
		this.product_name = product_name;
		this.status_id = status_id;
		this.status_name = status_name;
		this.progroup_id = progroup_id;
		this.progroup_name = progroup_name;
		this.protype_id = protype_id;
		this.protype_name = protype_name;
		this.unit_id = unit_id;
		this.unit_name = unit_name;
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getProgroup_name() {
		return progroup_name;
	}

	public void setProgroup_name(String progroup_name) {
		this.progroup_name = progroup_name;
	}

	public String getProtype_name() {
		return protype_name;
	}

	public void setProtype_name(String protype_name) {
		this.protype_name = protype_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getProgroup_id() {
		return progroup_id;
	}

	public void setProgroup_id(String progroup_id) {
		this.progroup_id = progroup_id;
	}

	public String getProtype_id() {
		return protype_id;
	}

	public void setProtype_id(String protype_id) {
		this.protype_id = protype_id;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

}
