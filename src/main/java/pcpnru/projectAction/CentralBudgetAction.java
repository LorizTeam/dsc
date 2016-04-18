package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.CentralBudgetDB;
import pcpnru.projectData.RequisitionDB;
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

public class CentralBudgetAction extends ActionSupport {

	CentralBudgetForm centralBudgetForm;

	public CentralBudgetForm getCentralBudgetForm() {
		return centralBudgetForm;
	}

	public void setCentralBudgetForm(CentralBudgetForm centralBudgetForm) {
		this.centralBudgetForm = centralBudgetForm;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		CentralBudgetDB cbg = new CentralBudgetDB();
		DateUtil dateUtil = new DateUtil();
		String docno = "", project_code = "", year = "", gcostcode = "", frombalance = "", rocking_budget = "",
				balance = "", docdate = "", remark = "", approve_status = "";

		String g1 = (String) request.getParameter("g1");
		String g2 = (String) request.getParameter("g2");

		docno = centralBudgetForm.getDocno();
		year = centralBudgetForm.getYear();
		project_code = centralBudgetForm.getProject_code();
		String[] spPC = project_code.split(" - ");
		project_code = spPC[0];

		gcostcode = centralBudgetForm.getGcostcode();

		if (g1 != null && !g1.equals("") && g2 != null && !g2.equals("")) {
			cbg.DeleteCentralBudget(docno, g1, year, g2);
			centralBudgetForm.reset();
		} else {
			frombalance = centralBudgetForm.getFrombalance();
			frombalance = frombalance.replace("฿", "").replace(",", "");
			rocking_budget = centralBudgetForm.getRocking_budget();
			rocking_budget = rocking_budget.replace("฿", "").replace(",", "");
			balance = centralBudgetForm.getBalance();
			balance = balance.replace(",", "");
			remark = centralBudgetForm.getRemark();
			approve_status = "WA";
			docdate = centralBudgetForm.getDocdate();
			docdate = dateUtil.CnvToYYYYMMDDEngYear(docdate, '-');

			String add = request.getParameter("add");

			if (add != null) {
				cbg.AddCentralBudget(docno, project_code, year, gcostcode, frombalance, rocking_budget, balance,
						docdate, remark, approve_status);
			}
		}

		List projectCentralList = cbg.getListProject(project_code, "", "", "");
		request.setAttribute("projectCentralList", projectCentralList);

		List CentralBudgetList = cbg.GetCentralBudgetList(docno, "", dateUtil.curTHYear());
		request.setAttribute("CentralBudgetList", CentralBudgetList);

		String amt = cbg.AmountBudget(dateUtil.curTHYear());
		centralBudgetForm.setAmt(amt);
		centralBudgetForm.setYear(year);
		centralBudgetForm.setDocno(docno);

		return SUCCESS;
	}

	public String create() throws Exception {

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

				CentralBudgetDB cbg = new CentralBudgetDB();

				DateUtil dateUtil = new DateUtil();
				String docno = cbg.SelectUpdateDocNo(project_code, dateUtil.curTHYear());

				List CentralBudgetList = cbg.GetCentralBudgetList(docno, "", dateUtil.curTHYear());
				request.setAttribute("CentralBudgetList", CentralBudgetList);

				extendsprojectmaster ext = new extendsprojectmaster();
				List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
				request.setAttribute("projectMasterList", projectMasterList);

				List projectCentralList = cbg.getListProject(project_code, "", "", "");
				request.setAttribute("projectCentralList", projectCentralList);

				String amt = cbg.AmountBudget(dateUtil.curTHYear());

				centralBudgetForm = new CentralBudgetForm();
				centralBudgetForm.setYear(dateUtil.curTHYear());
				centralBudgetForm.setDocno(docno);
				centralBudgetForm.setAmt(amt);

				forwardText = "success";
			}
		}

		return forwardText;
	}

}
