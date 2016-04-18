package test.pcpnru.inventoryAction;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.*;

import test.pcpnru.inventoryData.BrandDB;
import test.pcpnru.inventoryData.ProductGroupDB;
import test.pcpnru.inventoryModel.BrandModel;
import test.pcpnru.inventoryModel.ProductGroupModel;

public class ProductGroupAction {

	static String progroup_name = "Test ProductGroup";
	String update_progroup_name = "Test UpdateProductGroup";
	static String create_by = "00001";
	String update_by = "00002";

	@Test
	public void TestAddProductGroup() throws IOException, Exception {
		String progroup_id = AddProductGroup();
		Assert.assertTrue(new ProductGroupDB().Get_ProductGroupList(progroup_id, "Test").size() > 0);
	}

	@Test
	public void TestDeleteProductGroup() throws IOException, Exception {
		String progroup_id = AddProductGroup();
		ProductGroupDB pgdb = new ProductGroupDB();
		pgdb.DeleteProductGroup(progroup_id);
		Assert.assertTrue(pgdb.Get_ProductGroupList(progroup_id, "Test").size() == 0);
	}

	@Test
	public void TestUpdateProductGroup() throws IOException, Exception {
		String progroup_id = AddProductGroup();
		ProductGroupDB pgdb = new ProductGroupDB();
		pgdb.UpdateProductGroup(progroup_id, update_progroup_name, update_by);
		List<ProductGroupModel> ResultList = pgdb.Get_ProductGroupList(progroup_id, "");
		for (ProductGroupModel pgmodel : ResultList) {
			Assert.assertEquals(progroup_id, pgmodel.getProgroup_id());
			Assert.assertEquals(update_progroup_name, pgmodel.getProgroup_name());
			Assert.assertEquals(update_by, pgmodel.getUpdate_by());
		}
	}

	public static String AddProductGroup() throws IOException, Exception {
		ProductGroupDB pgdb = new ProductGroupDB();
		String progroup_id = pgdb.GetHighest_AddProductGroupID();
		progroup_id = pgdb.PlusOneID_FormatID(progroup_id);
		pgdb.AddProductGroup(progroup_id, progroup_name, create_by);
		return progroup_id;
	}
}
