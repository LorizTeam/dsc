package pcpnru.projectAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.inventoryData.ProductTypeDB;
import pcpnru.inventoryModel.ProductTypeModel;
import pcpnru.projectData.*;
import pcpnru.projectModel.*;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;

public class UnitMasterAction extends ActionSupport {

	private UnitMasterForm unitMaster;

	public UnitMasterForm getUnitMaster() {
		return unitMaster;
	}

	public void setUnitMaster(UnitMasterForm unitMaster) {
		this.unitMaster = unitMaster;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "021";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		UnitMasterDB unitMasterDB = new UnitMasterDB();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");

		String unit_id = "";

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(unitMaster.getAlertmsg()))
				unitMaster.setAlertmsg("");

			unit_id = unitMasterDB.GetHighest_AddUnitID();
			unit_id = unitMasterDB.PlusOneID_FormatID(unit_id);
			String unit_name = unitMaster.getUnit_name();
			unitMasterDB.AddUnitMaster(unit_id, unit_name, username);

			/*
			 * if(!unit.equals("")){ boolean checkhave =
			 * unitMasterDB.checkunit(unit); if(checkhave==true)
			 * unitMasterDB.AddUnitMaster(unit); unitMaster.reset(); }
			 */
		}
		if (update != null) {

			if (new Validate().Check_String_notnull_notempty(unitMaster.getAlertmsg()))
				unitMaster.setAlertmsg("");

			unitMasterDB.UpdateUnitMaster(unitMaster.getUnit_id(), unitMaster.getUnit_name(), username);
		}
		if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(unitMaster.getAlertmsg()))
				unitMaster.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delunit = request.getParameterValues("delunit");

			if (new Validate().Check_String_notnull_notempty(delunit)) {

				for (String unitid : delunit) {

					delete_status.add(unitMasterDB.DeleteUnitMaster(unitid));

				}

			} else {
				unitMaster.setAlertmsg("กรุณาติ๊กถูกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					unitMaster.setAlertmsg("ไม่สามารถลบผู้ขายที่ถูกใช้งานอยู่ได้ค่ะ");
				}
			}
		}

		return SUCCESS;
	}

	public String windows_entrancprotype() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "021";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		UnitMasterDB unitM = new UnitMasterDB();
		List<UnitMasterForm> unitMasterList = unitM.Get_UnitList("", "");
		request.setAttribute("unitMasterList", unitMasterList);
		unitMaster = new UnitMasterForm();
		unitMaster.setFromwindow("true");
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