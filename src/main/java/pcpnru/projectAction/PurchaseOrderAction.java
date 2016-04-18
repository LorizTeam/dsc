package pcpnru.projectAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.PurchaseOrderDB;
import pcpnru.projectModel.PurchaseOrderModel;
import pcpnru.utilities.DateUtil;

public class PurchaseOrderAction extends ActionSupport {

	PurchaseOrderModel pomodel;

	public PurchaseOrderModel getPomodel() {
		return pomodel;
	}

	public void setPomodel(PurchaseOrderModel pomodel) {
		this.pomodel = pomodel;
	}

	public String checkauthen() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		pomodel = new PurchaseOrderModel();
		if (session.getAttribute("username") == null)
			return "nologin";
		pomodel.setProject_code(session.getAttribute("project_code").toString());
		return SUCCESS;
	}

	public String pullDetailPR() throws Exception {

		return SUCCESS;
	}

	public String execute() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		PurchaseOrderDB purchaseOrderDB = new PurchaseOrderDB();
		String po_docno = "";
		String year = "";
		String pre_loadpr = pomodel.getPre_loadpr();
		DateUtil dateUtil = new DateUtil();

		String savehd = request.getParameter("savehd");
		String pull_detailpr = request.getParameter("pull_detailpr");
		String delete_detailpr = request.getParameter("delete_detailpr");
		String update_price = request.getParameter("update_price");

		if (pull_detailpr != null) {
			po_docno = pomodel.getPo_docno();
			year = dateUtil.curYear();
			boolean ck = false;
			ck = purchaseOrderDB.chk_pr(pre_loadpr, year);
			if (ck == true)
				po_docno = purchaseOrderDB.AddPODT(pre_loadpr, po_docno, year);

			List PRList = purchaseOrderDB.GetPRList(po_docno, year);
			request.setAttribute("PRList", PRList);

			pomodel.setPo_docno(po_docno);
			String type = pomodel.getType();
			request.setAttribute("type", type);
		}
		if (delete_detailpr != null) {
			year = dateUtil.curYear();
			po_docno = pomodel.getPo_docno();

			purchaseOrderDB.DeletePRDetail(po_docno, year);
			pomodel.setPre_loadpr("");
			String type = pomodel.getType();
			request.setAttribute("type", type);
		}
		if (update_price != null) {

			if (request.getParameterValues("itemno") != null) {
				po_docno = pomodel.getPo_docno();
				year = dateUtil.curYear();
				String[] itemno = request.getParameterValues("itemno");
				String[] qty = request.getParameterValues("qty");
				String[] amount = request.getParameterValues("amount");
				String[] remark = request.getParameterValues("remark");

				for (int i = 0; i < itemno.length; i++) {
					purchaseOrderDB.UpdatePOPrice(po_docno, year, itemno[i], qty[i], amount[i], remark[i]);
				}

				List PRList = purchaseOrderDB.GetPRList(po_docno, year);
				request.setAttribute("PRList", PRList);

				pomodel.setPo_docdate("");
				pomodel.setQuotation_date("");
			}

		}

		if (savehd != null) {
			if (!pomodel.getPo_docno().equals("")) {
				po_docno = pomodel.getPo_docno();
				year = dateUtil.curYear();
			} else {
				year = dateUtil.curYear();
				po_docno = purchaseOrderDB.SelectUpdateDocNo(year);
			}

			String po_docdate = pomodel.getPo_docdate();
			if (!po_docdate.equals(""))
				po_docdate = dateUtil.CnvToYYYYMMDDEngYear(po_docdate, '-');
			String type = pomodel.getType();
			String vender = pomodel.getVender();
			int credit_day = pomodel.getCredit_day();
			double mulct_day = pomodel.getMulct_day();
			String quotation_number = pomodel.getQuotation_number();
			String quotation_date = pomodel.getQuotation_date();
			if (!quotation_date.equals("") || !quotation_date.equals("-"))
				quotation_date = dateUtil.CnvToYYYYMMDDEngYear(quotation_date, '-');

			purchaseOrderDB.AddPOHD(po_docno, year, po_docdate, type, vender, credit_day, mulct_day, quotation_number,
					quotation_date);

			pomodel.setPo_docno(po_docno);

			request.setAttribute("type", type);

			List PRList = purchaseOrderDB.GetPRList(po_docno, year);
			request.setAttribute("PRList", PRList);
		}

		return SUCCESS;
	}
}
