package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.*;
import pcpnru.projectModel.*;
import pcpnru.utilities.DateUtil;

public class ChildSubjobMasterAction extends ActionSupport {

	private ChildSubjobMasterForm childSubjobMaster;

	public ChildSubjobMasterForm getChildSubjobMaster() {
		return childSubjobMaster;
	}

	public void setChildSubjobMaster(ChildSubjobMasterForm childSubjobMaster) {
		this.childSubjobMaster = childSubjobMaster;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		ChildSubjobMasterDB childSubjobMasterDB = new ChildSubjobMasterDB();

		String subjobcode = request.getParameter("subjobcode");
		String subjobcodehd = childSubjobMaster.getSubjobcodehd();
		String childsubjobcode = childSubjobMaster.getChildsubjobcode();
		String childsubjobname = childSubjobMaster.getChildsubjobname();

		String[] sj = subjobcode.split(" - ");
		subjobcode = sj[0].trim();
		String[] sjhd = subjobcodehd.split(" - ");
		subjobcodehd = sjhd[0].trim();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String alertMassage = "";

		if (add != null) {
			if (!subjobcode.equals("") && !childsubjobname.equals("")) {
				String gen_childsubjobcode = childSubjobMasterDB.SelectUpdateDocNo(subjobcode);
				childSubjobMasterDB.AddChildSubjobMaster(subjobcode, gen_childsubjobcode, childsubjobname);
				childSubjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (update != null) {
			String childsubjobcodehd = childSubjobMaster.getChildsubjobcodehd();
			if (!childsubjobcode.equals("") && !childsubjobname.equals("") && !childsubjobcodehd.equals("")) {
				childSubjobMasterDB.UpdateChildSubjobMaster(subjobcode, childsubjobcodehd, childsubjobname,
						subjobcodehd, childsubjobcodehd);
				childSubjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (delete != null) {
			String childsubjobcodehd = childSubjobMaster.getChildsubjobcodehd();
			if (!subjobcode.equals("") && !childsubjobcodehd.equals("")) {
				childSubjobMasterDB.DeleteChildSubjobMaster(subjobcode, childsubjobcodehd);
				childSubjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}

		return SUCCESS;
	}
}