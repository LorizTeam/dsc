package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.RequisitionDB;
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

public class RockingBudgetAction extends ActionSupport {

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

		/*
		 * if(request.getParameter("project_code") != ""){ project_code =
		 * request.getParameter("project_code"); }
		 * if(request.getParameter("gcostcode") != "") gcostcode =
		 * request.getParameter("project_code");
		 * if(request.getParameter("frombalance") != "") frombalance =
		 * request.getParameter("frombalance");
		 * if(request.getParameter("gcostcode_rock") != "") gcostcode_rock =
		 * request.getParameter("gcostcode_rock");
		 * if(request.getParameter("frombalance_rock") != "") frombalance_rock =
		 * request.getParameter("frombalance_rock");
		 * if(request.getParameter("rocking_budget") != "") rocking_budget =
		 * request.getParameter("rocking_budget");
		 * if(request.getParameter("balance") != "") balance =
		 * request.getParameter("balance");
		 */

		String g1 = (String) request.getParameter("g1");
		String g2 = (String) request.getParameter("g2");

		docno = rockingBudgetForm.getDocno();
		year = rockingBudgetForm.getYear();
		project_code = rockingBudgetForm.getProject_code();
		String[] spPC = project_code.split(" - ");
		project_code = spPC[0];

		gcostcode = rockingBudgetForm.getGcostcode();
		gcostcode_rock = rockingBudgetForm.getGcostcode_rock();

		if (g1 != null && !g1.equals("") && g2 != null && !g2.equals("")) {
			rbg.DeleteRockingBudget(docno, project_code, year, g1, g2);
		} else {

			frombalance = rockingBudgetForm.getFrombalance();
			frombalance = frombalance.replace("฿", "").replace(",", "");
			frombalance_rock = rockingBudgetForm.getFrombalance_rock();
			frombalance_rock = frombalance_rock.replace("฿", "").replace(",", "");
			rocking_budget = rockingBudgetForm.getRocking_budget();
			rocking_budget = rocking_budget.replace(",", "");
			balance = rockingBudgetForm.getBalance();
			balance = balance.replace(",", "");
			docdate = rockingBudgetForm.getDocdate();
			docdate = dateUtil.CnvToYYYYMMDDEngYear(docdate, '-');
			remark = rockingBudgetForm.getRemark();
			approve_status = "WA";

			String add = request.getParameter("add");

			if (add != null) {

				rbg.AddRockingBudget(docno, project_code, year, gcostcode, frombalance, gcostcode_rock,
						frombalance_rock, rocking_budget, balance, docdate, remark, approve_status);
			}
		}

		frombalance = rbg.AmountRockingBudget(project_code, year, gcostcode);
		// rockingBudgetForm.setFrombalance(frombalance);
		request.setAttribute("frombalance", frombalance);

		// frombalance_rock = rbg.AmountRockingBudget(project_code, year,
		// gcostcode_rock);
		// rockingBudgetForm.setFrombalance_rock(frombalance_rock);
		// request.setAttribute("frombalance_rock", frombalance_rock);

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		List groupcostCodeList = rbg.GetGroupCostCodeList(project_code, year, gcostcode);
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		List groupcostCode2List = rbg.GetGroupCostCode2List(project_code, year, gcostcode);
		request.setAttribute("groupcostCode2List", groupcostCode2List);

		List RockingBudgetList = rbg.GetRockingBudgetList(docno, project_code, year);
		request.setAttribute("RockingBudgetList", RockingBudgetList);

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

				RockingBudgetDB rbg = new RockingBudgetDB();

				rockingBudgetForm = new RockingBudgetForm();

				DateUtil dateUtil = new DateUtil();
				String docno = rbg.SelectUpdateDocNo(project_code, dateUtil.curTHYear());

				rockingBudgetForm.setDocno(docno);
				rockingBudgetForm.setYear(dateUtil.curTHYear());

				List RockingBudgetList = rbg.GetRockingBudgetList(docno, project_code, dateUtil.curTHYear());
				request.setAttribute("RockingBudgetList", RockingBudgetList);

				extendsprojectmaster ext = new extendsprojectmaster();
				List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
				request.setAttribute("projectMasterList", projectMasterList);

				request.setAttribute("project_code", project_code);
				request.setAttribute("frombalance", "0");
				request.setAttribute("frombalance_rock", "0");

				forwardText = "success";
			}
		}

		return forwardText;
	}

}
