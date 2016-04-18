package pcpnru.projectAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.Receive1DB;
import pcpnru.projectData.RelationBank;
import pcpnru.projectModel.ReceiveForm;

public class SelectReceive2Action extends ActionSupport {

	private ReceiveForm receiveform;

	public ReceiveForm getReceiveform() {
		return receiveform;
	}

	public void setReceiveform(ReceiveForm receiveform) {
		this.receiveform = receiveform;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String approve_tobank = request.getParameter("approve_tobank");
		if (approve_tobank != null) {

			String[] valueapprove_tobank = request.getParameterValues("valueapprove_tobank");

			String projectcode = receiveform.getProject(), project_year = receiveform.getProject_year(),
					gcostcode = receiveform.getCost(), docdate = receiveform.getDocdate(),
					custname = receiveform.getAmountfrom(), location = receiveform.getLocal();

			List allList = new ArrayList();
			for (int i = 0; i < valueapprove_tobank.length; i++) {
				System.out.println(valueapprove_tobank[i]);
				String[] splitvalueapprove_tobank = valueapprove_tobank[i].split(" - ");

				List detail = new ArrayList();
				detail.add(splitvalueapprove_tobank[0]);
				detail.add(splitvalueapprove_tobank[1]);
				detail.add(splitvalueapprove_tobank[3]);
				detail.add("1");
				allList.add(detail);

			}
			RelationBank relationbank = new RelationBank();
			relationbank.UpdateStatusReceive(allList);
			Receive1DB receive1db = new Receive1DB();
			List SelectReceiveList = receive1db.GetSelectReceiveList("", projectcode, project_year, gcostcode, docdate,
					custname, location);
			request.setAttribute("SelectReceiveList", SelectReceiveList);
			receiveform.setProject(projectcode);
			receiveform.setProject_year(project_year);
			receiveform.setCost(gcostcode);
			receiveform.setDocdate(docdate);
			receiveform.setAmountfrom(custname);
			receiveform.setLocal(location);
		}
		String docno = request.getParameter("docno");
		String alertMassage = "";

		String forwardText = null;

		if (docno != null) {
			String project = request.getParameter("project");
			String[] splitgprojectcode = project.split(" - ");
			String cost = request.getParameter("cost");
			String[] splitgcostcode = cost.split(" - ");
			String dateTime = request.getParameter("datetime");
			// String amountfrom = request.getParameter("amountfrom");
			// String local = request.getParameter("local");
			String amountfrom = receiveform.getAmountfrom();
			String local = receiveform.getLocal();
			String vol = receiveform.getVol();

			Receive1DB receive1DB = new Receive1DB();

			request.setAttribute("docNo", docno);
			receiveform.setVol(vol);
			request.setAttribute("projectCode", project);
			request.setAttribute("dateTime", dateTime);
			request.setAttribute("gcostCode", cost);
			// request.setAttribute("amountfrom", amountfrom);
			// request.setAttribute("local", local);

			receiveform.setAmountfrom(amountfrom);
			receiveform.setLocal(local);
			// receiveform.setAmtt("0");
			List Lcostcode_forreceive2 = receive1DB.ShowCostCodeforReceive2(splitgcostcode[0], "");
			String standardprice = receive1DB.SelectPriceStandard_fromgcostcode(splitgcostcode[0]);
			request.setAttribute("standardprice", standardprice);
			request.setAttribute("Lcostcode_forreceive2", Lcostcode_forreceive2);
			receiveform.setStandardprice(standardprice);

			forwardText = "success";
		} else {
			forwardText = "error";
		}
		return forwardText;
	}
}