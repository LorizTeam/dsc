package InformationModel;

public class VendorModel {
	private String vendor_id, vendor_name, create_by, create_datetime, update_by, update_datetime;

	private String fromwindow, createwindow, alertmsg;

	public VendorModel() {
	}

	public VendorModel(String vendor_id, String vendor_name, String create_by, String create_datetime, String update_by,
			String update_datetime) {
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public void clear_vendor() {
		this.vendor_id = "";
		this.vendor_name = "";
	}

	public String getFromwindow() {
		return fromwindow;
	}

	public void setFromwindow(String fromwindow) {
		this.fromwindow = fromwindow;
	}

	public String getCreatewindow() {
		return createwindow;
	}

	public void setCreatewindow(String createwindow) {
		this.createwindow = createwindow;
	}

	public String getAlertmsg() {
		return alertmsg;
	}

	public void setAlertmsg(String alertmsg) {
		this.alertmsg = alertmsg;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

}
