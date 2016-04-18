package pcpnru.projectAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.Receive1DB;
import pcpnru.projectData.Receive2DB;
import pcpnru.projectModel.ReceiveForm;

public class Receive2Action extends ActionSupport {

	private ReceiveForm receiveform;

	public ReceiveForm getReceiveform() {
		return receiveform;
	}

	public void setReceiveform(ReceiveForm receiveform) {
		this.receiveform = receiveform;
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		// HD
		String docNo = request.getParameter("docNo");
		String vol = receiveform.getVol();
		String projectCode = request.getParameter("projectCode");
		String dateTime = request.getParameter("dateTime");
		String gcostCode = request.getParameter("gcostCode");
		// String amountFrom = request.getParameter("amountFrom");
		String amountfrom = receiveform.getAmountfrom();
		// String local = request.getParameter("local");
		String local = receiveform.getLocal();
		// DT
		String itemNo = request.getParameter("itemNo").trim();
		String receivedetail = request.getParameter("receivedetail");
		String description = request.getParameter("description");
		String qty = request.getParameter("qty");
		String amount = request.getParameter("amount");
		String amountTotal = request.getParameter("amountTotal");
		String costcode = receiveform.getCostcode();
		String standardprice = receiveform.getStandardprice();
		String[] splitgcostcode = gcostCode.split(" - ");
		// String receiveAmt = request.getParameter("receiveAmt");
		String[] splitgprojectcode = projectCode.split(" - ");

		if (qty != null)
			qty = qty.replace(",", "");
		if (amount != null)
			amount = amount.replace(",", "");
		if (amountTotal != null)
			amountTotal = amountTotal.replace(",", "");

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");

		String forwardText = null;
		Receive1DB receive1DB = new Receive1DB();
		Receive2DB receive2DB = new Receive2DB();
		if (add != null) {

			receive2DB.AddReceiveDT(docNo, vol, splitgprojectcode[0], receivedetail, description, qty, amount,
					amountTotal, costcode);

			forwardText = "success";
		}
		if (update != null) {

			receive2DB.UpdateReceive(docNo, vol, splitgprojectcode[0], itemNo, receivedetail, description, qty, amount,
					amountTotal);
			forwardText = "success";
		}
		if (delete != null) {

			receive2DB.DeleteReceiveDT(docNo, vol, splitgprojectcode[0], itemNo);
			forwardText = "success";
		}

		String amtt = receive2DB.getSumAmount(docNo, splitgprojectcode[0]);
		// HD
		request.setAttribute("docNo", docNo);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("dateTime", dateTime);
		request.setAttribute("gcostCode", gcostCode);
		request.setAttribute("standardprice", standardprice);

		List Lcostcode_forreceive2 = receive1DB.ShowCostCodeforReceive2(splitgcostcode[0], "");
		request.setAttribute("Lcostcode_forreceive2", Lcostcode_forreceive2);

		receiveform.setVol(vol);
		receiveform.setAmountfrom(amountfrom);
		receiveform.setLocal(local);
		receiveform.setStandardprice(standardprice);
		request.setAttribute("amtt", amtt);

		List ReceiveList = receive2DB.GetReceiveList(docNo, splitgprojectcode[0]);
		request.setAttribute("ReceiveList", ReceiveList);

		return forwardText;
	}

}