package test.pcpnru.inventoryModel;

public class UnitMasterForm extends Create_UpdateModel {

	private String unitHD;
	private String unit_name, unit_id;

	private String add;
	private String update;
	private String delete;

	public UnitMasterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnitMasterForm(String unit_id, String unit_name, String create_by, String create_datetime, String update_by,
			String update_datetime) {
		this.unit_id = unit_id;
		this.unit_name = unit_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public UnitMasterForm(String unit_name) {
		super();
		this.unit_name = unit_name;
	}

	public void reset() {
		this.unitHD = "";
		this.unit_name = "";

	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnitHD() {
		return unitHD;
	}

	public void setUnitHD(String unitHD) {
		this.unitHD = unitHD;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
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
