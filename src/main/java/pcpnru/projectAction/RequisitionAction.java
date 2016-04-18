package pcpnru.projectAction;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.RequisitionDB;
import pcpnru.projectModel.RequisitionModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class RequisitionAction extends ActionSupport {

	RequisitionModel requisition1model;

	public RequisitionModel getRequisition1model() {
		return requisition1model;
	}

	public void setRequisition1model(RequisitionModel requisition1model) {
		this.requisition1model = requisition1model;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String timeofday = dateFormat.format(cal.getTime());

		String day = requisition1model.getDay(), project_code = "", project_year = requisition1model.getProject_year(),
				type_requisition = "", description = "", gcostcode = "";

		int unit = 0;
		double priceperunit = 0, frombalance = 0, tobalance = 0;

		if (request.getParameter("project_code") != "") {
			project_code = request.getParameter("project_code");
		}

		if (request.getParameter("type_requisition") != "") {
			type_requisition = request.getParameter("type_requisition");
		}

		if (request.getParameter("description") != "") {
			description = request.getParameter("description");
		}

		if (request.getParameter("gcostcode") != "") {
			gcostcode = request.getParameter("gcostcode");
		}

		if (request.getParameter("unit") != "") {
			unit = Integer.parseInt(request.getParameter("unit"));
		}

		if (request.getParameter("priceperunit") != "") {
			priceperunit = Double.parseDouble(request.getParameter("priceperunit"));
		}

		if (request.getParameter("frombalance") != "") {
			frombalance = Double.parseDouble(request.getParameter("frombalance"));
		}

		if (request.getParameter("tobalance") != "") {
			tobalance = Double.parseDouble(request.getParameter("tobalance"));
		}

		day += " " + timeofday;

		RequisitionDB requisitiondb = new RequisitionDB();
		return null;
	}

}
