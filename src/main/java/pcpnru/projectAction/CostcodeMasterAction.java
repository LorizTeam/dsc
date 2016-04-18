package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.CostCodeMasterDB;
import pcpnru.projectModel.CostCodeMasterForm;

public class CostcodeMasterAction extends ActionSupport {

	CostCodeMasterForm costcodemasterform;

	public CostCodeMasterForm getCostcodemasterform() {
		return costcodemasterform;
	}

	public void setCostcodemasterform(CostCodeMasterForm costcodemasterform) {
		this.costcodemasterform = costcodemasterform;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		CostCodeMasterDB costcodemasterdb = new CostCodeMasterDB();
		String costCode = costcodemasterform.getCostCode();
		String costName = costcodemasterform.getCostName();
		String costCodeHD = costcodemasterform.getCostCodeHD();
		String gcostcode = request.getParameter("gcostcode"), percentprice = costcodemasterform.getPercentprice();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");

		if (add != null) {
			try {

				costcodemasterdb.AddCostCodeMaster(gcostcode, costCode, costName, percentprice);
				costcodemasterform.reset();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (update != null) {
			try {

				costcodemasterdb.UpdateCostCodeMaster(costCode, costName, costCodeHD, gcostcode, percentprice);
				costcodemasterform.reset();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (delete != null) {
			try {

				costcodemasterdb.DeleteCostCodeMaster(costCode);
				costcodemasterform.reset();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return SUCCESS;
	}
}
