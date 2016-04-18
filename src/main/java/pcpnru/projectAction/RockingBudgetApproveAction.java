package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.RequisitionDB;
import pcpnru.projectData.RockingBudgetApproveDB;
import pcpnru.projectData.RockingBudgetDB;
import pcpnru.projectData.extendsprojectmaster;
import pcpnru.projectModel.RequisitionModel;
import pcpnru.projectModel.RockingBudgetForm;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class RockingBudgetApproveAction extends ActionSupport {

	RockingBudgetForm rockingBudgetForm;

	public RockingBudgetForm getRockingBudgetForm() {
		return rockingBudgetForm;
	}

	public void setRockingBudgetForm(RockingBudgetForm rockingBudgetForm) {
		this.rockingBudgetForm = rockingBudgetForm;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		RockingBudgetDB rbg = new RockingBudgetDB();
		DateUtil dateUtil = new DateUtil();
		String docno = "", project_code = "", year = "", gcostcode = "", frombalance = "", gcostcode_rock = "",
				frombalance_rock = "", rocking_budget = "", balance = "", docdate = "", remark = "",
				approve_status = "";

		project_code = rockingBudgetForm.getProject_code();
		if (!project_code.equals("")) {
			String[] spPC = project_code.split(" - ");
			project_code = spPC[0];
			year = spPC[1];
		} else {
			project_code = "";
			year = dateUtil.curTHYear();
		}

		gcostcode = rockingBudgetForm.getGcostcode();

		RockingBudgetApproveDB rbga = new RockingBudgetApproveDB();

		String save = request.getParameter("save");

		List RockingBudgetDList = rbga.GetRockingBudgetDList("", project_code, dateUtil.curTHYear(), gcostcode);

		if (save != null) {

			if (request.getParameterValues("archk") != null) {
				String[] archk = request.getParameterValues("archk");
				String[] approveStatus = request.getParameterValues("approveStatus");

				for (String data_row : archk) {
					String[] split_value = data_row.split("-");
					String arDocno = split_value[0];
					String arGcostCode = split_value[1];

					int array = Integer.parseInt(split_value[2]);

					rbga.UpdateStatusRockingBudget(arDocno, project_code, year, arGcostCode, approveStatus[array]);
				}
			}
		}

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead("", "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		List groupcostCodeList = rbg.GetGroupCostCodeList(project_code, year, "");
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		RockingBudgetDList = rbga.GetRockingBudgetDList("", project_code, dateUtil.curTHYear(), gcostcode);
		request.setAttribute("RockingBudgetDList", RockingBudgetDList);

		request.setAttribute("projectcode", project_code);
		request.setAttribute("gcostcode", gcostcode);

		return SUCCESS;
	}

	public String begin() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		String username = "", project_code = "", forwardText = "";

		if (session.getAttribute("username") == null) {
			forwardText = "login";
		} else {
			username = session.getAttribute("username").toString();
			boolean chkAuthen = false;
			String page_code = "014";

			CheckAuthenPageDB capDB = new CheckAuthenPageDB();

			chkAuthen = capDB.getCheckAuthen(username, page_code);

			if (chkAuthen == false) {
				forwardText = "authen";
			} else {
				project_code = capDB.getProjectCode(username);

				RockingBudgetDB rbg = new RockingBudgetDB();

				rockingBudgetForm = new RockingBudgetForm();

				DateUtil dateUtil = new DateUtil();
				String docno = rbg.SelectUpdateDocNo(project_code, dateUtil.curTHYear());

				rockingBudgetForm.setDocno(docno);
				rockingBudgetForm.setYear(dateUtil.curTHYear());

				extendsprojectmaster ext = new extendsprojectmaster();
				List projectMasterList = ext.getListProject_Join_Projecthead("", "", "", "");
				request.setAttribute("projectMasterList", projectMasterList);

				List groupcostCodeList = rbg.GetGroupCostCodeList("", dateUtil.curTHYear(), "");
				request.setAttribute("groupcostCodeList", groupcostCodeList);

				RockingBudgetApproveDB rbga = new RockingBudgetApproveDB();
				List RockingBudgetDList = rbga.GetRockingBudgetDList("", "", dateUtil.curTHYear(), "");
				request.setAttribute("RockingBudgetDList", RockingBudgetDList);

				request.setAttribute("project_code", project_code);

				forwardText = "success";
			}
		}

		return forwardText;
	}

}
