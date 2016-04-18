package pcpnru.masterAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.AuthenMasterDB;
import pcpnru.masterData.PageMasterDB;
import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.masterModel.PageMasterModel;
import pcpnru.utilities.DateUtil;

public class PageAction extends ActionSupport {

	PageMasterModel pageMasterModel;

	public PageMasterModel getPageMasterModel() {
		return pageMasterModel;
	}

	public void setPageMasterModel(PageMasterModel pageMasterModel) {
		this.pageMasterModel = pageMasterModel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		PageMasterDB pm = new PageMasterDB();

		String pagegroup_code = pageMasterModel.getPagegroup_code(), page_code = pageMasterModel.getPage_code(),
				page_name = pageMasterModel.getPage_name();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		if (add != null) {

			try {
				String gen_page_code = pm.SelectUpdateDocNo();
				pm.AddPageMaster(pagegroup_code, gen_page_code, page_name);
				pageMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				pm.UpdatePageMaster(pagegroup_code, page_code, page_name);
				pageMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				pm.DeletePageMaster(pagegroup_code, page_code);
				pageMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}
}
