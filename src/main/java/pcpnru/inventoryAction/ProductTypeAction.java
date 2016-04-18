package pcpnru.inventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import pcpnru.inventoryData.ProductTypeDB;
import pcpnru.inventoryModel.ProductModel;
import pcpnru.inventoryModel.ProductTypeModel;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.Validate;

public class ProductTypeAction extends ActionSupport {
	ProductTypeModel protypemodel;

	public ProductTypeModel getProtypemodel() {
		return protypemodel;
	}

	public void setProtypemodel(ProductTypeModel protypemodel) {
		this.protypemodel = protypemodel;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "022";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		String add = request.getParameter("add");
		String delete = request.getParameter("delete");
		String update = request.getParameter("update");
		ProductTypeDB pdtdb = new ProductTypeDB();
		String protype_id = "";
		List protypeList = null;

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(protypemodel.getAlertmsg()))
				protypemodel.setAlertmsg("");

			protype_id = pdtdb.GetHighest_AddProductTypeID();
			protype_id = pdtdb.PlusOneID_FormatID(protype_id);
			String protype_name = protypemodel.getProtype_name();
			pdtdb.AddProductType(protype_id, protype_name, username);
			protypemodel.ClearProType();

		} else if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(protypemodel.getAlertmsg()))
				protypemodel.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delprotype = request.getParameterValues("delprotype");

			if (new Validate().Check_String_notnull_notempty(delprotype)) {

				for (String protypeid : delprotype) {

					delete_status.add(pdtdb.DeleteProductType(protypeid));

				}

			} else {
				protypemodel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					protypemodel.setAlertmsg("ไม่สามารถลบประเภทสินค้าที่ถูกใช้งานอยู่ได้");
				}
			}

			protypemodel.ClearProType();

		} else if (update != null) {

			if (new Validate().Check_String_notnull_notempty(protypemodel.getAlertmsg()))
				protypemodel.setAlertmsg("");

			pdtdb.UpdateProductType(protypemodel.getProtype_id(), protypemodel.getProtype_name(), username);
			protypemodel.ClearProType();
		} else {
			protypemodel = new ProductTypeModel();
			protypemodel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
		}
		protypeList = pdtdb.Get_ProductTypeList("", "");
		request.setAttribute("protypeList", protypeList);
		return SUCCESS;
	}

	public String entrancprotype() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "022";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductTypeDB pdtdb = new ProductTypeDB();
		List protypeList = null;
		protypeList = pdtdb.Get_ProductTypeList("", "");
		request.setAttribute("protypeList", protypeList);
		return SUCCESS;
	}

	public String windows_entrancprotype() throws IOException, Exception {

		if (!CheckLogin())
			return "nologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "022";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductTypeDB pdtdb = new ProductTypeDB();
		List protypeList = null;
		protypeList = pdtdb.Get_ProductTypeList("", "");
		request.setAttribute("protypeList", protypeList);
		protypemodel = new ProductTypeModel();
		protypemodel.setFromwindow("true");
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
