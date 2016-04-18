package pcpnru.projectAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.ProjectDTReceiveDB;
import pcpnru.projectModel.ProjectModel;

public class ProjectDTReceivePCCAction extends ActionSupport {

	private ProjectModel projModel;

	public ProjectModel getProjModel() {
		return projModel;
	}

	public void setProjModel(ProjectModel projModel) {
		this.projModel = projModel;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ProjectDTReceiveDB projDtR = new ProjectDTReceiveDB();

		String add = (String) request.getParameter("add");
		String project_code = (String) request.getParameter("project_code");
		String year = (String) request.getParameter("year");
		String gcostcode = "", gcostname = "";

		if (project_code != null && !project_code.equals("")) {
			String[] spiltp = project_code.split("-");
			gcostcode = spiltp[0];
			gcostname = spiltp[1];
		}

		String freeze = projDtR.SelectProjFreeze(gcostcode, year);

		if (freeze.equals("N")) {

			if (add != null) {

				String budget = (String) request.getParameter("budget");
				if (budget.equals(""))
					budget = "0";

				String csubjob = (String) request.getParameter("csubjob");
				String subjob = "0003";

				String target = projDtR.getSumAmountPCC(gcostcode, year);

				budget = Double.toString(((Double.parseDouble(target) * Double.parseDouble(budget)) / 100));

				projDtR.AddProjDTReceive("PCC", year, subjob, csubjob, gcostcode, gcostname, budget);

			} else {
				String project_codehd = (String) request.getParameter("project_codehd");
				if (project_codehd != null && !project_codehd.equals("")) {
					projDtR.DeleteProjDTReceive("PCC", project_codehd, year);
				}

			}
			request.setAttribute("project_code", "PCC");
			request.setAttribute("year", year);
		}
		return SUCCESS;
	}

}
