package test.pcpnru.inventoryAction;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import test.pcpnru.inventoryData.ProductDB;
import test.pcpnru.inventoryModel.ProductModel;

public class ProductAction {
	static ProductModel productmodel = new ProductModel();
	static ProductDB productdb = new ProductDB();

	@Test
	public void AddProduct() throws IOException, Exception {
		AddProduct_Method();
		Assert.assertEquals(true, productdb.Get_ProductList(productmodel).size() > 0);
		Delete_Method();
	}

	@Test
	public void DeleteProduct() {
		AddProduct_Method();
		Assert.assertEquals(true, Delete_Method() > 0);
	}

	@Test
	public void UpdateProduct() throws IOException, Exception {
		AddProduct_Method();
		productmodel.setProduct_name("ปากกาลูกลื่นAAZ");
		productmodel.setUpdate_by("00002");
		productdb.UpdateProduct(productmodel);

		List<ProductModel> productList = productdb.Get_ProductList(productmodel);
		Assert.assertTrue(productList.size() > 0);
		Delete_Method();
		productList = productdb.Get_ProductList(productmodel);
		Assert.assertTrue(productList.size() <= 0);
	}

	@Test
	public void NullModel() throws IOException, Exception {
		AddProduct_Method();
		productmodel.setProduct_name("ปากกาลูกลื่นAAZ");
		productmodel.setUpdate_by("00002");
		productdb.UpdateProduct(productmodel);
		ProductModel productmodel1 = new ProductModel();
		productmodel1 = null;
		List<ProductModel> productList = productdb.Get_ProductList(productmodel1);
		Assert.assertTrue(productList.size() > 0);
		Delete_Method();
	}

	public static int AddProduct_Method() {
		productmodel.setProduct_code("AX-54T");
		productmodel.setProduct_name("ปากกาลูกลื่น");
		productmodel.setStatus_id("01");
		productmodel.setProgroup_id("0001");
		productmodel.setProtype_id("0001");
		productmodel.setUnit_id("0001");
		productmodel.setBrand_id("0001");
		productmodel.setCreate_by("00001");
		int rowsupdate = productdb.AddProduct(productmodel);
		return rowsupdate;
	}

	public static int Delete_Method() {
		int rowsupdate = productdb.DeleteProduct(productmodel.getProduct_code());
		return rowsupdate;
	}
}
