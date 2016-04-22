package test.pcpnru.inventoryAction;

import java.io.IOException;
import java.util.List;

import org.junit.*;
import org.junit.Assert;

import test.pcpnru.inventoryData.StockDB;
import test.pcpnru.inventoryModel.StockModel;

public class StockAction {

	static String stock_name = "Test Stock name";
	String update_stock_name = "Test Update Stock name";
	static String create_by = "00001";
	String update_by = "00002";

	@Test
	public void TestAddStock() throws IOException, Exception {
		String stock_id = AddBrand();
		Assert.assertTrue(new StockDB().Get_StockList(stock_id, "").size() > 0);
	}

	@Test
	public void TestDeleteBrand() throws IOException, Exception {
		String stock_id = AddBrand();
		StockDB stockdb = new StockDB();
		stockdb.DeleteBrand(stock_id);
		Assert.assertTrue(new StockDB().Get_StockList(stock_id, "Test").size() == 0);
	}

	@Test
	public void TestUpdateBrand() throws IOException, Exception {
		String stock_id = AddBrand();
		StockDB bdb = new StockDB();
		bdb.UpdateBrand(stock_id, update_stock_name, update_by);
		List<StockModel> ResultList = bdb.Get_StockList(stock_id, "");
		for (StockModel StockModel : ResultList) {
			Assert.assertEquals(stock_id, StockModel.getStock_id());
			Assert.assertEquals(update_stock_name, StockModel.getStock_name());
			Assert.assertEquals(update_by, StockModel.getUpdate_by());
		}
	}

	public static String AddBrand() throws IOException, Exception {
		StockDB StockDB = new StockDB();
		String stock_id = StockDB.GetHighest_StockID();
		stock_id = StockDB.PlusOneID_FormatID(stock_id);
		StockDB.AddStock(stock_id, stock_name, create_by);
		return stock_id;
	}

	public static void main(String[] args) throws IOException, Exception {
		String stock_id = AddBrand();
		Assert.assertTrue(new StockDB().Get_StockList(stock_id, "").size() > 0);
	}
}
