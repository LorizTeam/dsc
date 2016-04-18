package pcpnru.masterAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.AuthenPageMasterDB;
import pcpnru.masterModel.AuthenPageMasterModel;
import pcpnru.utilities.DateUtil;

public class AuthenPageAction extends ActionSupport {

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String authen_type = request.getParameter("authen_type");

		String add = request.getParameter("add");
		String forwardText = "success";

		if (add != null) {

			if (request.getParameterValues("chkpage") != null) {
				AuthenPageMasterDB apm = new AuthenPageMasterDB();
				String[] chkpage = request.getParameterValues("chkpage");
				apm.unSaveAuthenPage(authen_type);

				for (int i = 0; i < chkpage.length; i++) {
					apm.SaveAuthenPage(authen_type, chkpage[i]);
				}
			}
		}

		request.setAttribute("authen_type", authen_type);

		return forwardText;
	}

	public String backup() {
		HttpServletRequest request = ServletActionContext.getRequest();

		AuthenPageMasterDB apm = new AuthenPageMasterDB();

		String authen_type = request.getParameter("authen_type");
		String page_code = request.getParameter("page_code");

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		if (add != null) {

			try {
				apm.AddAuthenPageMaster(authen_type, page_code);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				apm.UpdateAuthenPageMaster(authen_type, page_code);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				apm.DeleteAuthenPageMaster(authen_type, page_code);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}
}
