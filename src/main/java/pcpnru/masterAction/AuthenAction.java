package pcpnru.masterAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.AuthenMasterDB;
import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.utilities.DateUtil;

public class AuthenAction extends ActionSupport {

	AuthenMasterModel authenMasterModel;

	public AuthenMasterModel getAuthenMasterModel() {
		return authenMasterModel;
	}

	public void setAuthenMasterModel(AuthenMasterModel authenMasterModel) {
		this.authenMasterModel = authenMasterModel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		AuthenMasterDB am = new AuthenMasterDB();

		String authen_type = authenMasterModel.getAuthen_type(),
				authen_type_name = authenMasterModel.getAuthen_type_name();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		if (add != null) {

			try {
				String gen_authen_type = am.SelectUpdateDocNo();
				am.AddAuthenMaster(gen_authen_type, authen_type_name);
				authenMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				am.UpdateAuthenMaster(authen_type, authen_type_name);
				authenMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				am.DeleteAuthenMaster(authen_type);
				authenMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}
}
