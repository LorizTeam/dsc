package pcpnru.projectModel;

public class CostCodeMasterForm {

	private String costCodeHD;
	private String costCode;
	private String costName;
	private String dateTime;
	private String percentprice, gcostcode, gcostcode_name;

	private String add;
	private String update;
	private String delete;

	public CostCodeMasterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CostCodeMasterForm(String costCode, String costName, String dateTime, String gcostcode,
			String gcostcode_name, String percentprice) {
		super();
		this.costCode = costCode;
		this.costName = costName;
		this.dateTime = dateTime;
		this.gcostcode = gcostcode;
		this.gcostcode_name = gcostcode_name;
		this.percentprice = percentprice;
	}

	public String getGcostcode() {
		return gcostcode;
	}

	public void setGcostcode(String gcostcode) {
		this.gcostcode = gcostcode;
	}

	public String getGcostcode_name() {
		return gcostcode_name;
	}

	public void setGcostcode_name(String gcostcode_name) {
		this.gcostcode_name = gcostcode_name;
	}

	public String getCostCodeHD() {
		return costCodeHD;
	}

	public void setCostCodeHD(String costCodeHD) {
		this.costCodeHD = costCodeHD;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
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

	public void reset() {
		this.costCodeHD = "";
		this.costCode = "";
		this.costName = "";
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getPercentprice() {
		return percentprice;
	}

	public void setPercentprice(String percentprice) {
		this.percentprice = percentprice;
	}
}
