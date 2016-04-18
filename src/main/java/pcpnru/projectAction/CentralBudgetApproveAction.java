package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.CentralBudgetDB;
import pcpnru.projectData.RequisitionDB;
import pcpnru.projectData.RockingBudgetApproveDB;
import pcpnru.projectData.RockingBudgetDB;
import pcpnru.projectData.extendsprojectmaster;
import pcpnru.projectModel.CentralBudgetForm;
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

public class CentralBudgetApproveAction extends ActionSupport {

	CentralBudgetForm centralBudgetForm;

	public CentralBudgetForm getCentralBudgetForm() {
		return centralBudgetForm;
	}

	public void setCentralBudgetForm(CentralBudgetForm centralBudgetForm) {
		this.centralBudgetForm = centralBudgetForm;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		CentralBudgetDB cbga = new CentralBudgetDB();
		DateUtil dateUtil = new DateUtil();
		String docno = "", project_code = "", year = "", gcostcode = "", frombalance = "", gcostcode_rock = "",
				frombalance_rock = "", rocking_budget = "", balance = "", docdate = "", remark = "",
				approve_status = "";

		project_code = centralBudgetForm.getProject_code();
		if (project_code != null) {
			String[] spPC = project_code.split(" - ");
			project_code = spPC[0];
			year = spPC[1];
		} else {
			project_code = "";
			year = dateUtil.curTHYear();
		}
		approve_status = centralBudgetForm.getApprove_status();
		gcostcode = centralBudgetForm.getGcostcode();

		String save = request.getParameter("save");

		if (save != null) {

			if (request.getParameterValues("archk") != null) {
				String[] archk = request.getParameterValues("archk");
				String[] approveStatus = request.getParameterValues("approveStatus");

				for (String data_row : archk) {

					String[] split_value = data_row.split("-");
					String arDocno = split_value[0];
					String arYear = split_value[1];

					int array = Integer.parseInt(split_value[2]);

					cbga.UpdateStatusCentralBudget(arDocno, arYear, approveStatus[array]);
				}
			}
		}

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead("", "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		List groupcostCodeList = cbga.GetGroupCostCodeList(project_code, year, "");
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		List CentralBudgetApproveList = cbga.GetCentralBudgetApproveList("", "", dateUtil.curTHYear(), "",
				approve_status);
		request.setAttribute("CentralBudgetApproveList", CentralBudgetApproveList);

		request.setAttribute("projectcode", project_code);
		request.setAttribute("gcostcode", gcostcode);
		request.setAttribute("ap_status", approve_status);

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

				CentralBudgetDB cbga = new CentralBudgetDB();

				DateUtil dateUtil = new DateUtil();

				extendsprojectmaster ext = new extendsprojectmaster();
				List projectMasterList = ext.getListProject_Join_Projecthead("", "", "", "");
				request.setAttribute("projectMasterList", projectMasterList);

				List groupcostCodeList = cbga.GetGroupCostCodeList("", dateUtil.curTHYear(), "");
				request.setAttribute("groupcostCodeList", groupcostCodeList);

				List CentralBudgetApproveList = cbga.GetCentralBudgetApproveList("", "", dateUtil.curTHYear(), "",
						"WA");
				request.setAttribute("CentralBudgetApproveList", CentralBudgetApproveList);

				request.setAttribute("project_code", project_code);
				request.setAttribute("ap_status", "WA");

				forwardText = "success";
			}
		}

		return forwardText;
	}

}
