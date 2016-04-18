package pcpnru.masterAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.GroupcostcodeMasterDB;
import pcpnru.masterModel.GroupCostCodeMasterModel;
import pcpnru.projectData.ProjectMasterDB;

public class GroupcostcodeAction extends ActionSupport {

	GroupCostCodeMasterModel groupcostcodemastermodel;

	public GroupCostCodeMasterModel getGroupcostcodemastermodel() {
		return groupcostcodemastermodel;
	}

	public void setGroupcostcodemastermodel(GroupCostCodeMasterModel groupcostcodemastermodel) {
		this.groupcostcodemastermodel = groupcostcodemastermodel;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		GroupcostcodeMasterDB groupcostcodemasterdb = new GroupcostcodeMasterDB();
		ProjectMasterDB projM = new ProjectMasterDB();
		String forwardText = "";

		String project_code = request.getParameter("projectCode");

		String grp_gcostcode = request.getParameter("grp_gcostcode");

		String groupcostCode = "";// groupcostcodemastermodel.getCostCode()
		String groupcostName = groupcostcodemastermodel.getCostName(),
				groupcostCodeHD = groupcostcodemastermodel.getCostCodeHD(),
				standardprice = groupcostcodemastermodel.getStandardprice(),
				fundprice = groupcostcodemastermodel.getFundprice(), amount = groupcostcodemastermodel.getAmount(),
				type_gcostcode = groupcostcodemastermodel.getType_gcostcode();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		forwardText = "success";

		if (standardprice == null && fundprice == null) {
			groupcostCode = groupcostCode.replace("C", "");
			standardprice = "0";
			fundprice = "0";
			forwardText = "requisition";
			groupcostCode = "C" + groupcostCode;
			amount = amount.replace(",", "");
			if (grp_gcostcode == null)
				grp_gcostcode = "";
		} else {
			groupcostCode = groupcostCode.replace("R", "");
			groupcostCode = "R" + groupcostCode;

			standardprice = standardprice.replace(",", "");
			fundprice = fundprice.replace(",", "");
			amount = "0";
		}

		if (add != null) {

			try {
				groupcostCode = groupcostcodemasterdb.SelectUpdateDocNo(project_code, type_gcostcode);
				groupcostcodemasterdb.AddCostCodeMaster(project_code, groupcostCode, groupcostName, standardprice,
						fundprice, amount, type_gcostcode, grp_gcostcode);
				groupcostcodemastermodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null && !groupcostCodeHD.equals("")) {
			try {
				groupcostcodemasterdb.UpdateCostCodeMaster(project_code, groupcostCodeHD, groupcostName,
						groupcostCodeHD, standardprice, fundprice, amount);
				groupcostcodemastermodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				groupcostcodemasterdb.DeleteCostCodeMaster(project_code, groupcostCodeHD);
				groupcostcodemastermodel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("project_code", project_code);

		return forwardText;
	}
}
