package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.*;
import pcpnru.projectModel.*;
import pcpnru.utilities.DateUtil;

public class SubjobMasterAction extends ActionSupport {

	private SubjobMasterForm subjobMaster;

	public SubjobMasterForm getSubjobMaster() {
		return subjobMaster;
	}

	public void setSubjobMaster(SubjobMasterForm subjobMaster) {
		this.subjobMaster = subjobMaster;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		SubjobMasterDB subjobMasterDB = new SubjobMasterDB();

		String subjobCode = subjobMaster.getSubjobCode();
		String subjobName = subjobMaster.getSubjobName();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String alertMassage = "";

		DateUtil dateUtil = new DateUtil();
		String dateTime = dateUtil.curDateTime();

		if (add != null) {
			if (!subjobName.equals("")) {
				String gen_subjobCode = subjobMasterDB.SelectUpdateDocNo(subjobCode);
				subjobMasterDB.AddSubjobMaster(gen_subjobCode, subjobName);
				subjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (update != null) {
			String subjobCodeHD = subjobMaster.getSubjobCodeHD();
			if (!subjobName.equals("") && !subjobCodeHD.equals("")) {
				subjobMasterDB.UpdateSubjobMaster(subjobCodeHD, subjobName, subjobCodeHD);
				subjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (delete != null) {
			if (!subjobCode.equals("")) {
				subjobMasterDB.DeleteSubjobMaster(subjobCode);
				subjobMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}

		return SUCCESS;
	}
}