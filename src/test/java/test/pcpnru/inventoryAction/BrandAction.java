package test.pcpnru.inventoryAction;

import java.io.IOException;
import java.util.List;

import org.junit.*;
import org.junit.Assert;

import test.pcpnru.inventoryData.BrandDB;
import test.pcpnru.inventoryModel.BrandModel;

public class BrandAction {

	static String brand_name = "Test Brand";
	String update_brand_name = "Test UpdateBrand";
	static String create_by = "00001";
	String update_by = "00002";

	@Test
	public void TestAddBrand() throws IOException, Exception {
		String brand_id = AddBrand();
		Assert.assertTrue(new BrandDB().Get_BrandList(brand_id, "Test").size() > 0);
	}

	@Test
	public void TestDeleteBrand() throws IOException, Exception {
		String brand_id = AddBrand();
		BrandDB bdb = new BrandDB();
		bdb.DeleteBrand(brand_id);
		Assert.assertTrue(new BrandDB().Get_BrandList(brand_id, "Test").size() == 0);
	}

	@Test
	public void TestUpdateBrand() throws IOException, Exception {
		String brand_id = AddBrand();
		BrandDB bdb = new BrandDB();
		bdb.UpdateBrand(brand_id, update_brand_name, update_by);
		List<BrandModel> ResultList = bdb.Get_BrandList(brand_id, "");
		for (BrandModel brandmodel : ResultList) {
			Assert.assertEquals(brand_id, brandmodel.getBrand_id());
			Assert.assertEquals(update_brand_name, brandmodel.getBrand_name());
			Assert.assertEquals(update_by, brandmodel.getUpdate_by());
		}
	}

	public static String AddBrand() throws IOException, Exception {
		BrandDB branddb = new BrandDB();
		String brand_id = branddb.GetHighest_BrandID();
		brand_id = branddb.PlusOneID_FormatID(brand_id);
		branddb.AddBrand(brand_id, brand_name, create_by);
		return brand_id;
	}

	public static void main(String[] args) throws IOException, Exception {
		String brand_id = AddBrand();
		Assert.assertTrue(new BrandDB().Get_BrandList(brand_id, "Test").size() > 0);
	}
}
