package pcpnru.inventoryModel;

import pcpnru.inventoryModel.Create_UpdateModel;

public class StockModel extends Create_UpdateModel {
	private String stock_id, stock_name;

	public StockModel() {
	}

	public StockModel(String stock_id, String stock_name, String create_by, String create_datetime, String update_by,
			String update_datetime) {
		this.stock_id = stock_id;
		this.stock_name = stock_name;
		this.create_by = create_by;
		this.create_datetime = create_datetime;
		this.update_by = update_by;
		this.update_datetime = update_datetime;
	}

	public void ClearStockModel() {
		this.stock_id = "";
		this.stock_name = "";
	}

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	
}
