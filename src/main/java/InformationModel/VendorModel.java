package InformationModel;

public class VendorModel {
	private String vendor_id, vendor_name, create_by, create_datetime, update_by, update_datetime;
	private String vendor_address, vendor_road, vendor_subdistrict, vendor_district,
					vendor_province, vendor_postcode, vendor_mobile, vendor_tel,
					vendor_tel_ext;
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
	
	public VendorModel(String vendor_id, String vendor_name, String create_by, String create_datetime, String update_by,
			String update_datetime, String vendor_address, String vendor_road, String vendor_subdistrict,
			String vendor_district, String vendor_province, String vendor_postcode, String vendor_mobile,
			String vendor_tel, String vendor_tel_ext) {
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
		this.vendor_address = vendor_address;
		this.vendor_road = vendor_road;
		this.vendor_subdistrict = vendor_subdistrict;
		this.vendor_district = vendor_district;
		this.vendor_province = vendor_province;
		this.vendor_postcode = vendor_postcode;
		this.vendor_mobile = vendor_mobile;
		this.vendor_tel = vendor_tel;
		this.vendor_tel_ext = vendor_tel_ext;
	}

	public void clear_vendor() {
		this.vendor_id = "";
		this.vendor_name = "";
		this.vendor_address= ""; 
		this.vendor_road= "";
		this.vendor_subdistrict= ""; 
		this.vendor_district= ""; 
		this.vendor_province= ""; 
		this.vendor_postcode= "";
		this.vendor_mobile= ""; 
		this.vendor_tel = "";
		this.vendor_tel_ext= "";
	}
	//////////////////// 
	
	public String getFromwindow() {
		return fromwindow;
	}

	public String getVendor_address() {
		return vendor_address;
	}

	public void setVendor_address(String vendor_address) {
		this.vendor_address = vendor_address;
	}

	public String getVendor_road() {
		return vendor_road;
	}

	public void setVendor_road(String vendor_road) {
		this.vendor_road = vendor_road;
	}

	public String getVendor_subdistrict() {
		return vendor_subdistrict;
	}

	public void setVendor_subdistrict(String vendor_subdistrict) {
		this.vendor_subdistrict = vendor_subdistrict;
	}

	public String getVendor_district() {
		return vendor_district;
	}

	public void setVendor_district(String vendor_district) {
		this.vendor_district = vendor_district;
	}

	public String getVendor_province() {
		return vendor_province;
	}

	public void setVendor_province(String vendor_province) {
		this.vendor_province = vendor_province;
	}

	public String getVendor_postcode() {
		return vendor_postcode;
	}

	public void setVendor_postcode(String vendor_postcode) {
		this.vendor_postcode = vendor_postcode;
	}

	public String getVendor_mobile() {
		return vendor_mobile;
	}

	public void setVendor_mobile(String vendor_mobile) {
		this.vendor_mobile = vendor_mobile;
	}

	public String getVendor_tel() {
		return vendor_tel;
	}

	public void setVendor_tel(String vendor_tel) {
		this.vendor_tel = vendor_tel;
	}

	public String getVendor_tel_ext() {
		return vendor_tel_ext;
	}

	public void setVendor_tel_ext(String vendor_tel_ext) {
		this.vendor_tel_ext = vendor_tel_ext;
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
