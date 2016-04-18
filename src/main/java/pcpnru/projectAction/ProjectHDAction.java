package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.ProjectData;
import pcpnru.projectModel.ProjectModel;

public class ProjectHDAction extends ActionSupport {

	private ProjectModel projectmodel;

	public ProjectModel getProjectmodel() {
		return projectmodel;
	}

	public void setProjectmodel(ProjectModel projectmodel) {
		this.projectmodel = projectmodel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		ProjectData pjData = new ProjectData();

		String project_target = projectmodel.getTarget();
		project_target = project_target.replace(",", "");
		String project_percen = projectmodel.getPercen();
		String project_year = projectmodel.getYear();
		String project_code = request.getParameter("project_code");

		if (request.getParameter("submit") != null) {

			try {
				pjData.AddProjectHD(project_code, project_target, project_percen, project_year);
				projectmodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (request.getParameter("update") != null) {
			try {
				pjData.UpdateProjectHD(project_code, project_target, project_percen, project_year);
				projectmodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				pjData.DeleteProjectHD(project_code, project_year);
				projectmodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String freeze() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ProjectData pjData = new ProjectData();

		String freeze = request.getParameter("freeze");
		String projectcode = request.getParameter("projectcode");
		String year = request.getParameter("year");

		pjData.UpdateFreeze(projectcode, year, freeze);

		return SUCCESS;
	}
}
