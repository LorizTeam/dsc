package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.*;
import pcpnru.projectModel.*;
import pcpnru.utilities.DateUtil;

public class ProjectMasterAction extends ActionSupport {

	private ProjectMasterForm projectMaster;

	public ProjectMasterForm getProjectMaster() {
		return projectMaster;
	}

	public void setProjectMaster(ProjectMasterForm projectMaster) {
		this.projectMaster = projectMaster;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		ProjectMasterDB projectMasterDB = new ProjectMasterDB();

		String projectCode = projectMaster.getProjectCode();
		String projectName = projectMaster.getProjectName();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String alertMassage = "";

		DateUtil dateUtil = new DateUtil();
		String dateTime = dateUtil.curDateTime();

		if (add != null) {
			if (!projectName.equals("")) {
				projectCode = projectMasterDB.SelectUpdateDocNo();
				projectMasterDB.AddProjectMaster(projectCode, projectName);
				projectMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (update != null) {
			String projectCodeHD = projectMaster.getProjectCodeHD();
			if (!projectName.equals("") && !projectCodeHD.equals("")) {
				projectMasterDB.UpdateProjectMaster(projectCodeHD, projectName, projectCodeHD);
				projectMaster.reset();
			} else {
				alertMassage = "กรุณา กรอก ข้อมูลให้ครบถ้วน";
			}
		}
		if (delete != null) {
			projectMasterDB.DeleteProjectMaster(projectCode);
			projectMaster.reset();
		}

		return SUCCESS;
	}
}