package test.pcpnru.inventoryAction;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import test.pcpnru.inventoryData.ProductGroupDB;
import test.pcpnru.inventoryData.ProductTypeDB;
import test.pcpnru.inventoryModel.ProductTypeModel;

public class ProductTypeAction {
	static String protype_name = "Test ProductType";
	String update_protype_name = "Test UpdateProductType";
	static String create_by = "00001";
	String update_by = "00002";

	@Test
	public void TestAddProductType() throws IOException, Exception {
		String protype_id = AddProductType();
		Assert.assertTrue(new ProductTypeDB().Get_ProductTypeList(protype_id, "Test").size() > 0);
	}

	@Test
	public void TestDeleteProductType() throws IOException, Exception {
		String protype_id = AddProductType();
		ProductTypeDB ptdb = new ProductTypeDB();
		ptdb.DeleteProductType(protype_id);
		Assert.assertTrue(ptdb.Get_ProductTypeList(protype_id, "Test").size() == 0);
	}

	@Test
	public void TestUpdateProductType() throws IOException, Exception {
		String protype_id = AddProductType();
		ProductTypeDB ptdb = new ProductTypeDB();
		ptdb.UpdateProductType(protype_id, update_protype_name, update_by);
		List<ProductTypeModel> ResultList = ptdb.Get_ProductTypeList(protype_id, "");
		for (ProductTypeModel pgmodel : ResultList) {
			Assert.assertEquals(protype_id, pgmodel.getProtype_id());
			Assert.assertEquals(update_protype_name, pgmodel.getProtype_name());
			Assert.assertEquals(update_by, pgmodel.getUpdate_by());
		}
	}

	@Test
	public void GetProductTypeEmpty_protype_id() throws IOException, Exception {
		String protype_id = "";
		ProductTypeDB ptdb = new ProductTypeDB();
		List<ProductTypeModel> ResultList = ptdb.Get_ProductTypeList(protype_id, "Test");
		Assert.assertTrue(ResultList.size() > 0);
	}

	@Test
	public void GetProductTypeNull_protype_id() throws IOException, Exception {
		String protype_id = null;
		ProductTypeDB ptdb = new ProductTypeDB();
		List<ProductTypeModel> ResultList = ptdb.Get_ProductTypeList(null, null);
		Assert.assertTrue(ResultList.size() > 0);
	}

	public static String AddProductType() throws IOException, Exception {
		ProductTypeDB ptdb = new ProductTypeDB();
		String protype_id = ptdb.GetHighest_AddProductTypeID();
		protype_id = ptdb.PlusOneID_FormatID(protype_id);
		ptdb.AddProductType(protype_id, protype_name, create_by);
		return protype_id;
	}
}
