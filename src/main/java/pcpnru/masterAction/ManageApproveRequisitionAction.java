package pcpnru.masterAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.ManageApproveRequisitionDB;
import pcpnru.masterModel.ManageApproveRequisitionModel;
import pcpnru.utilities.DateUtil;

public class ManageApproveRequisitionAction extends ActionSupport {

	ManageApproveRequisitionModel marModel;

	public ManageApproveRequisitionModel getMarModel() {
		return marModel;
	}

	public void setMarModel(ManageApproveRequisitionModel marModel) {
		this.marModel = marModel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		ManageApproveRequisitionDB mardb = new ManageApproveRequisitionDB();

		String manageapprove = marModel.getManageapprove(), manageapprovename = marModel.getManageapprovename(),
				budget = marModel.getBudget();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		if (add != null) {

			try {
				String manageapproveno = mardb.SelectUpdateDocNo();
				mardb.AddManageApproveRequisition(manageapproveno, manageapprovename, budget);
				marModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				// am.UpdateAuthenMaster(authen_type, authen_type_name);
				// authenMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				// am.DeleteAuthenMaster(authen_type);
				// authenMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}
}
