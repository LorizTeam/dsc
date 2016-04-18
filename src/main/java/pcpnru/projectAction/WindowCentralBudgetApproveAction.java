package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.CentralBudgetDB;
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

public class WindowCentralBudgetApproveAction extends ActionSupport {

	RockingBudgetForm rockingBudgetForm;

	public RockingBudgetForm getRockingBudgetForm() {
		return rockingBudgetForm;
	}

	public void setRockingBudgetForm(RockingBudgetForm rockingBudgetForm) {
		this.rockingBudgetForm = rockingBudgetForm;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String docno = "", project_code = "", year = "", gcostcode = "";

		docno = (String) request.getParameter("docno");
		project_code = (String) request.getParameter("project_code");
		year = (String) request.getParameter("year");
		gcostcode = (String) request.getParameter("gcostcode");

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		CentralBudgetDB cbga = new CentralBudgetDB();

		List groupcostCodeList = cbga.GetGroupCostCodeList(project_code, year, gcostcode);
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		List WCentralBudgetApproveList = cbga.WindowCentralBudgetList(docno, "", year, gcostcode);
		request.setAttribute("WCentralBudgetApproveList", WCentralBudgetApproveList);

		request.setAttribute("gcostcode", gcostcode);

		return SUCCESS;
	}

	public String execute_projectdt() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		RockingBudgetDB rbg = new RockingBudgetDB();

		String docno = "", project_code = "", year = "", gcostcode = "";

		docno = "";
		project_code = (String) request.getParameter("project_code");
		year = (String) request.getParameter("year");
		gcostcode = (String) request.getParameter("gcostcode");

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		List groupcostCodeList = rbg.GetGroupCostCodeList(project_code, year, gcostcode);
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		CentralBudgetDB cbga = new CentralBudgetDB();
		List WCentralBudgetApproveList = cbga.WindowCentralBudget_DT("", project_code, year, gcostcode);
		request.setAttribute("WCentralBudgetApproveList", WCentralBudgetApproveList);

		request.setAttribute("gcostcode", gcostcode);

		return SUCCESS;
	}

}
