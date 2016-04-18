package pcpnru.inventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.inventoryData.ProductGroupDB;
import pcpnru.inventoryModel.ProductGroupModel;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.Validate;

public class ProductGroupAction extends ActionSupport {
	ProductGroupModel pdgmodel;

	public ProductGroupModel getPdgmodel() {
		return pdgmodel;
	}

	public void setPdgmodel(ProductGroupModel pdgmodel) {
		this.pdgmodel = pdgmodel;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "024";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		String add = request.getParameter("add");
		String delete = request.getParameter("delete");
		String update = request.getParameter("update");
		ProductGroupDB ProductGroupDB = new ProductGroupDB();
		String progroup_id = "";
		List progroupList = null;

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(pdgmodel.getAlertmsg()))
				pdgmodel.setAlertmsg("");

			progroup_id = ProductGroupDB.GetHighest_AddProductGroupID();
			progroup_id = ProductGroupDB.PlusOneID_FormatID(progroup_id);
			String progroup_name = pdgmodel.getProgroup_name();
			ProductGroupDB.AddProductGroup(progroup_id, progroup_name, username);
			pdgmodel.ClearProductGroupModel();

		} else if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(pdgmodel.getAlertmsg()))
				pdgmodel.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delprogroup = request.getParameterValues("delprogroup");

			if (new Validate().Check_String_notnull_notempty(delprogroup)) {

				for (String progroupid : delprogroup) {

					delete_status.add(ProductGroupDB.DeleteProductGroup(progroupid));

				}

			} else {
				pdgmodel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					pdgmodel.setAlertmsg("ไม่สามารถลบหมวดสินค้าที่ถูกใช้งานอยู่ได้");
				}
			}

			pdgmodel.ClearProductGroupModel();

		} else if (update != null) {

			if (new Validate().Check_String_notnull_notempty(pdgmodel.getAlertmsg()))
				pdgmodel.setAlertmsg("");

			ProductGroupDB.UpdateProductGroup(pdgmodel.getProgroup_id(), pdgmodel.getProgroup_name(), username);
			pdgmodel.ClearProductGroupModel();
		}

		progroupList = ProductGroupDB.Get_ProductGroupList("", "");
		request.setAttribute("progroupList", progroupList);
		return SUCCESS;
	}

	public String entrancbrand() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "024";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductGroupDB ProductGroupDB = new ProductGroupDB();
		List progroupList = null;
		progroupList = ProductGroupDB.Get_ProductGroupList("", "");
		request.setAttribute("progroupList", progroupList);
		return SUCCESS;
	}

	public String windows_entrancprogroup() throws IOException, Exception {

		if (!CheckLogin())
			return "nologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "024";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		ProductGroupDB ProductGroupDB = new ProductGroupDB();
		List progroupList = null;
		progroupList = ProductGroupDB.Get_ProductGroupList("", "");
		request.setAttribute("progroupList", progroupList);
		pdgmodel = new ProductGroupModel();
		pdgmodel.setFromwindow("true");
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
