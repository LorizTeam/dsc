package pcpnru.inventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.inventoryData.ProductDB;
import pcpnru.inventoryModel.ProductModel;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.Validate;

public class ProductAction extends ActionSupport {
	ProductModel productModel;

	public ProductModel getProductModel() {
		return productModel;
	}

	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "025";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		String add = request.getParameter("add");
		String delete = request.getParameter("delete");
		String update = request.getParameter("update");
		ProductDB productdb = new ProductDB();

		List<ProductModel> productList = null;

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(productModel.getAlertmsg()))
				productModel.setAlertmsg("");

			productModel.setCreate_by(username);
			productdb.AddProduct(productModel);
			productModel.Clear_InputProduct();

		} else if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(productModel.getAlertmsg()))
				productModel.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delproduct = request.getParameterValues("delproduct");

			if (new Validate().Check_String_notnull_notempty(delproduct)) {

				for (String productid : delproduct) {

					delete_status.add(productdb.DeleteProduct(productid) > 0 ? true : false);

				}

			} else {
				productModel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					productModel.setAlertmsg("ไม่สามารถลบหมวดสินค้าที่ถูกใช้งานอยู่ได้");
				}
			}

			productModel.Clear_InputProduct();

		} else if (update != null) {

			if (new Validate().Check_String_notnull_notempty(productModel.getAlertmsg()))
				productModel.setAlertmsg("");

			productdb.UpdateProduct(productModel);
			productModel.Clear_InputProduct();
		}

		productList = productdb.Get_ProductList(productModel);
		request.setAttribute("productList", productList);
		return SUCCESS;
	}

	public String entrancbrand() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "025";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductDB productdb = new ProductDB();
		List productList = null;
		productList = productdb.Get_ProductList(productModel);
		request.setAttribute("productList", productList);
		return SUCCESS;
	}

	public String windows_entrancprogroup() throws IOException, Exception {

		if (!CheckLogin())
			return "nologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "025";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductDB productdb = new ProductDB();
		List productList = null;
		productModel = new ProductModel();

		productList = productdb.Get_ProductList(productModel);
		request.setAttribute("productList", productList);

		productModel.setFromwindow("true");

		return SUCCESS;
	}

	public boolean CheckLogin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		boolean login = true;

		if (session.getAttribute("username") == null)
			return login = false;

		return login;
	}

}
