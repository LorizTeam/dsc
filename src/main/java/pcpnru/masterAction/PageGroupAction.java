package pcpnru.masterAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.PageGroupMasterDB;
import pcpnru.masterModel.PageGroupMasterModel;

public class PageGroupAction extends ActionSupport {

	PageGroupMasterModel pageGroupMasterModel;

	public PageGroupMasterModel getPageGroupMasterModel() {
		return pageGroupMasterModel;
	}

	public void setPageGroupMasterModel(PageGroupMasterModel pageGroupMasterModel) {
		this.pageGroupMasterModel = pageGroupMasterModel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		PageGroupMasterDB pm = new PageGroupMasterDB();

		String pagegroup_code = pageGroupMasterModel.getPagegroup_code(),
				pagegroup_name = pageGroupMasterModel.getPagegroup_name();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		if (add != null) {

			try {
				String gen_page_code = pm.SelectUpdateDocNo();
				pm.AddPageGroupMaster(gen_page_code, pagegroup_name);
				pageGroupMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				pm.UpdatePageGroupMaster(pagegroup_code, pagegroup_name);
				pageGroupMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				pm.DeletePageGroupMaster(pagegroup_code);
				pageGroupMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}
}
