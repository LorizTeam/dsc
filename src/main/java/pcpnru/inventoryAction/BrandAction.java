package pcpnru.inventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.inventoryData.BrandDB;
import pcpnru.inventoryModel.BrandModel;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.Validate;

public class BrandAction extends ActionSupport {
	BrandModel brandmodel;

	public BrandModel getBrandmodel() {
		return brandmodel;
	}

	public void setBrandmodel(BrandModel brandmodel) {
		this.brandmodel = brandmodel;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		String add = request.getParameter("add");
		String delete = request.getParameter("delete");
		String update = request.getParameter("update");
		BrandDB branddb = new BrandDB();
		String brand_id = "";
		List brandList = null;

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(brandmodel.getAlertmsg()))
				brandmodel.setAlertmsg("");

			brand_id = branddb.GetHighest_BrandID();
			brand_id = branddb.PlusOneID_FormatID(brand_id);
			String brand_name = brandmodel.getBrand_name();
			branddb.AddBrand(brand_id, brand_name, username);
			brandmodel.ClearBrandModel();

		} else if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(brandmodel.getAlertmsg()))
				brandmodel.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delbrand = request.getParameterValues("delbrand");

			if (new Validate().Check_String_notnull_notempty(delbrand)) {

				for (String brandid : delbrand) {

					delete_status.add(branddb.DeleteBrand(brandid));

				}

			} else {
				brandmodel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					brandmodel.setAlertmsg("ไม่สามารถลบแบรนด์ที่ถูกใช้งานอยู่ได้");
				}
			}

			brandmodel.ClearBrandModel();

		} else if (update != null) {

			if (new Validate().Check_String_notnull_notempty(brandmodel.getAlertmsg()))
				brandmodel.setAlertmsg("");

			branddb.UpdateBrand(brandmodel.getBrand_id(), brandmodel.getBrand_name(), username);
			brandmodel.ClearBrandModel();
		}

		brandList = branddb.Get_BrandList("", "");
		request.setAttribute("brandList", brandList);
		return SUCCESS;
	}

	public String entrancbrand() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		BrandDB branddb = new BrandDB();
		List brandList = null;
		brandList = branddb.Get_BrandList("", "");
		request.setAttribute("brandList", brandList);
		return SUCCESS;
	}

	public String windows_entrancbrand() throws IOException, Exception {

		if (!CheckLogin())
			return "nologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		BrandDB branddb = new BrandDB();
		List brandList = null;
		brandList = branddb.Get_BrandList("", "");
		request.setAttribute("brandList", brandList);
		brandmodel = new BrandModel();
		brandmodel.setFromwindow("true");
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
