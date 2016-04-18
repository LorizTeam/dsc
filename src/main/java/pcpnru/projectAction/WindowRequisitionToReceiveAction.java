package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.GrpGcostcodeMasterDB;
import pcpnru.projectData.extendsprojectmaster;
import pcpnru.utilities.DateUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class WindowRequisitionToReceiveAction extends ActionSupport {

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		GrpGcostcodeMasterDB ccM = new GrpGcostcodeMasterDB();

		String docno = "", project_code = "", year = "", gcostcode = "", grp_gcostcode = "";

		year = new DateUtil().curTHYear();

		project_code = (String) request.getParameter("project_code");
		grp_gcostcode = (String) request.getParameter("grp_gcostcode");
		gcostcode = (String) request.getParameter("gcostcode");

		extendsprojectmaster ext = new extendsprojectmaster();
		List projectMasterList = ext.getListProject_Join_Projecthead(project_code, "", "", "");
		request.setAttribute("projectMasterList", projectMasterList);

		List groupcostCodeList = ccM.GetGroupCostCodeList(project_code, gcostcode);
		request.setAttribute("groupcostCodeList", groupcostCodeList);

		List GrpReqisToReceiveList = ccM.GrpReqisToReceive(project_code, year, grp_gcostcode);
		request.setAttribute("GrpReqisToReceiveList", GrpReqisToReceiveList);

		return SUCCESS;
	}
}
