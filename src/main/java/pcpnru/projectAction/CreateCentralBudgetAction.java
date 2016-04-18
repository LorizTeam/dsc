package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.CreateCentralBudgetDB;
import pcpnru.projectModel.CentralBudgetForm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class CreateCentralBudgetAction extends ActionSupport {

	CentralBudgetForm centralBudgetForm;

	public CentralBudgetForm getCentralBudgetForm() {
		return centralBudgetForm;
	}

	public void setCentralBudgetForm(CentralBudgetForm centralBudgetForm) {
		this.centralBudgetForm = centralBudgetForm;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		CreateCentralBudgetDB ccb = new CreateCentralBudgetDB();
		String year = centralBudgetForm.getYear();
		String amt = centralBudgetForm.getAmt();
		amt = amt.replace(",", "");

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");

		if (add != null) {
			if (!year.equals("")) {
				boolean checkhave = ccb.checkYear(year);
				if (checkhave == true)
					ccb.AddCentralBudget(year, amt);
				centralBudgetForm.reset();
			}
		}
		if (update != null) {
			if (!year.equals("") && !amt.equals("")) {
				ccb.UpdateCentralBudget(year, amt);
				centralBudgetForm.reset();
			}
		}
		if (delete != null) {
			if (!year.equals("")) {
				ccb.DeleteCentralBudget(year);
				centralBudgetForm.reset();
			}
		}
		String forwardText = "success";

		return forwardText;
	}

}
