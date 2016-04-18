package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.Receive2DB;
import pcpnru.projectData.ThaiBaht;
import pcpnru.projectModel.ReceiveForm;

public class ReceiveReportAction extends ActionSupport {

	private ReceiveForm receiveform;

	public ReceiveForm getReceiveform() {
		return receiveform;
	}

	public void setReceiveform(ReceiveForm receiveform) {
		this.receiveform = receiveform;
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		Receive2DB receive2DB = new Receive2DB();
		String docNoHD = request.getParameter("docNoHD");
		String projectcode = request.getParameter("projectcode");
		String torn = request.getParameter("torn");
		String receiveAmt = request.getParameter("receiveAmt");

		String alertMassage = "";

		String forwardText = null;

		if (docNoHD != null && projectcode != null) {
			// String amtt = receive.getAmtt();
			// String amtt = request.getParameter("amtt");

			if (torn != null & receiveAmt != null) {
				receive2DB.UpdateReceiveMoney(docNoHD, projectcode, torn, receiveAmt);
			}

			String amtt = receive2DB.SumReceive(docNoHD, projectcode);

			ThaiBaht thaiBaht = new ThaiBaht();
			String valueTHB = thaiBaht.getText(amtt);

			request.setAttribute("valueTHB", valueTHB);
			request.setAttribute("docNoHD", docNoHD);
			request.setAttribute("projectcode", projectcode);
			forwardText = "print";
		} else {
			forwardText = "success";
		}
		return forwardText;
	}

}