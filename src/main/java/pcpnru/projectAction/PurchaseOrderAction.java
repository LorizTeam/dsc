package pcpnru.projectAction;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.Request;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.projectData.PurchaseOrderDB;
import pcpnru.projectModel.PurchaseOrderModel;
import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;

public class PurchaseOrderAction extends ActionSupport {

	PurchaseOrderModel pomodel;

	public PurchaseOrderModel getPomodel() {
		return pomodel;
	} 
	public void setPomodel(PurchaseOrderModel pomodel) {
		this.pomodel = pomodel;
	}

	private File toBeUploaded; 
	private String toBeUploadedFileName;  
    private String toBeUploadedContentType;  
	
    public File getToBeUploaded() {
		return toBeUploaded;
	}

	public void setToBeUploaded(File toBeUploaded) {
		this.toBeUploaded = toBeUploaded;
	}
	public String getToBeUploadedFileName() {
		return toBeUploadedFileName;
	}

	public void setToBeUploadedFileName(String toBeUploadedFileName) {
		this.toBeUploadedFileName = toBeUploadedFileName;
	}

	public String getToBeUploadedContentType() {
		return toBeUploadedContentType;
	}

	public void setToBeUploadedContentType(String toBeUploadedContentType) {
		this.toBeUploadedContentType = toBeUploadedContentType;
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
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
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

			List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
			request.setAttribute("ResultImageList", ResultImageList);
			
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
			
			List PRList = purchaseOrderDB.GetPRList(po_docno, year);
			request.setAttribute("PRList", PRList);
			
			List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
			request.setAttribute("ResultImageList", ResultImageList);
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

				List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
				request.setAttribute("ResultImageList", ResultImageList);
				
				String po_docdate = pomodel.getPo_docdate();
				String quotation_date = pomodel.getQuotation_date();
				if(po_docdate.equals("-")) pomodel.setPo_docdate("");
				if(quotation_date.equals("-")) pomodel.setQuotation_date("");
				
				pomodel.setPo_docno(po_docno);
				String type = pomodel.getType();
				request.setAttribute("type", type);
			}

		}

		if (savehd != null) {
			
			String type = "";
			String ref_pr 	  = request.getParameter("ref_pr");
			String ref_prdate = request.getParameter("ref_prdate");
			
			if (!pomodel.getPo_docno().equals("")) {
				po_docno = pomodel.getPo_docno();
				year = dateUtil.curYear();
				
				String po_docdate = pomodel.getPo_docdate();
				if (!po_docdate.equals(""))
					po_docdate = dateUtil.CnvToYYYYMMDDEngYear(po_docdate, '-');
				type = pomodel.getType();
				String vender = pomodel.getVender();
				int credit_day = pomodel.getCredit_day();
				double mulct_day = pomodel.getMulct_day();
				String quotation_number = pomodel.getQuotation_number();
				String quotation_date = pomodel.getQuotation_date();
				if (!quotation_date.equals("") || !quotation_date.equals("-"))
					quotation_date = dateUtil.CnvToYYYYMMDDEngYear(quotation_date, '-');

				purchaseOrderDB.UpdatePOHD(ref_pr, ref_prdate, po_docno, year, po_docdate, type, vender, credit_day, mulct_day, quotation_number, quotation_date);
			} else {
				year = dateUtil.curYear();
				po_docno = purchaseOrderDB.SelectUpdateDocNo(year);
				
				String po_docdate = pomodel.getPo_docdate();
				if (!po_docdate.equals(""))
					po_docdate = dateUtil.CnvToYYYYMMDDEngYear(po_docdate, '-');
				type = pomodel.getType();
				String vender = pomodel.getVender();
				int credit_day = pomodel.getCredit_day();
				double mulct_day = pomodel.getMulct_day();
				String quotation_number = pomodel.getQuotation_number();
				String quotation_date = pomodel.getQuotation_date();
				if (!quotation_date.equals("") || !quotation_date.equals("-"))
					quotation_date = dateUtil.CnvToYYYYMMDDEngYear(quotation_date, '-');

				purchaseOrderDB.AddPOHD(ref_pr, ref_prdate, po_docno, year, po_docdate, type, vender, credit_day, mulct_day, quotation_number, quotation_date, username);
			}  

			Validate valid = new Validate();
			String filePath = request.getSession().getServletContext().getRealPath("/")+"img/";
	        String filename = this.toBeUploadedFileName;
	        String pathimg_todb = "";
	        if(valid.Check_String_notnull_notempty(filename)){
	        	String[] nameimg = filename.split("[.]");
	            if(nameimg.length > 2){
	            	pomodel.setAlertmsg("กรุณาทำการลบ . ในชื่อของรูปภาพด้วยค่ะ");
	            	return "alertmsg";
	            }
	            File fileToCreate = new File(filePath,dateUtil.GetDatetime_YYYY_MM_DD_HH_MM_SS()+"."+nameimg[1]);
	            pathimg_todb = "img/"+fileToCreate.getName();
	            FileUtils.copyFile(this.toBeUploaded, fileToCreate);
	            
	        purchaseOrderDB.Add_PurchaseRequest_Image(po_docno,year,pathimg_todb);
	        }
			
			pomodel.setPo_docno(po_docno);

			request.setAttribute("type", type);

			List PRList = purchaseOrderDB.GetPRList(po_docno, year);
			request.setAttribute("PRList", PRList);
			
			List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
			request.setAttribute("ResultImageList", ResultImageList);
		}
		 

		return SUCCESS;
	}
	
	public String history() throws IOException, Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String forwardText = "";
		
		String save = request.getParameter("save");
		String search = request.getParameter("search");
		String next = request.getParameter("next");

		if (search != null) { 
			request.setAttribute("ListResultPOSearch",
					new PurchaseOrderDB().GetListPO_Header(pomodel.getPo_docno(), pomodel.getVender(), pomodel.getPo_day(),
							pomodel.getPo_month(),pomodel.getPo_year(), pomodel.getApprove_status(), pomodel.getType()));

			forwardText = "success";
			
		} else if (save != null) {

			if (request.getParameterValues("chkrow") != null) {
				String[] chkrow = request.getParameterValues("chkrow");
				String[] approveStatus = request.getParameterValues("approveStatus");

				for (String data_row : chkrow) {
					String[] split_value = data_row.split("-");
					String docno = split_value[0];
					String year = String.valueOf(Integer.parseInt(split_value[1]) - 543);
					int array = Integer.parseInt(split_value[2]);
					new PurchaseOrderDB().approve_po(docno, year, approveStatus[array]);
				}

			}
			request.setAttribute("ListResultPOSearch", new PurchaseOrderDB().GetListPO_Header("", "", "", "", "", "", ""));
			
			forwardText = "success";
		} else {
			request.setAttribute("ListResultPOSearch", new PurchaseOrderDB().GetListPO_Header("", "", "", "", "", "", ""));
			
			forwardText = "success";
		}
		if(next!=null){
			PurchaseOrderDB purchaseOrderDB = new PurchaseOrderDB();
			
			String po_docno = pomodel.getPo_docno();
			String year 	= pomodel.getYear();
			year = String.valueOf(Integer.parseInt(year)-543);
			
			Map MapResultValue = purchaseOrderDB.GetAllValueHeader_byDocno(po_docno, year);
			
			pomodel = new PurchaseOrderModel();
			pomodel.setPo_docno(MapResultValue.get("po_docno").toString());
			pomodel.setPo_docdate(MapResultValue.get("docdate").toString());
			pomodel.setRef_pr(MapResultValue.get("ref_pr").toString());
			pomodel.setRef_prdate(MapResultValue.get("ref_prdate").toString());
			pomodel.setMulct_day(Double.valueOf(MapResultValue.get("penaltype_perday").toString()));
			pomodel.setCredit_day(Integer.valueOf(MapResultValue.get("creditday").toString()));
			pomodel.setVendor_name(MapResultValue.get("vender").toString()); 
			pomodel.setQuotation_number(MapResultValue.get("ref_quotation").toString()); 
			pomodel.setQuotation_date(MapResultValue.get("ref_quotationdate").toString());  
			pomodel.setYear(MapResultValue.get("year").toString());
			
			String type = MapResultValue.get("type").toString();
			request.setAttribute("type", type);
			
			String chkA = purchaseOrderDB.chk_poapprove(po_docno, year);
			request.setAttribute("chkA", chkA);
			
			List PRList = purchaseOrderDB.GetPRList(po_docno, year);
			request.setAttribute("PRList", PRList);
			
			List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
			request.setAttribute("ResultImageList", ResultImageList);
			
			forwardText = "next";
		} 

		return forwardText;
	}
	
	public String entrancPoApprove() throws IOException, Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String save = request.getParameter("save");
		String search = request.getParameter("search");

		if (search != null) { 
			request.setAttribute("ListResultPOSearch",
					new PurchaseOrderDB().GetListPO_Header(pomodel.getPo_docno(), pomodel.getVender(), pomodel.getPo_day(),
							pomodel.getPo_month(),pomodel.getPo_year(), pomodel.getApprove_status(), pomodel.getType()));

		} else if (save != null) {

			if (request.getParameterValues("chkrow") != null) {
				String[] chkrow = request.getParameterValues("chkrow");
				String[] approveStatus = request.getParameterValues("approveStatus");

				for (String data_row : chkrow) {
					String[] split_value = data_row.split("-");
					String docno = split_value[0];
					String year = String.valueOf(Integer.parseInt(split_value[1]) - 543);
					int array = Integer.parseInt(split_value[2]);
					new PurchaseOrderDB().approve_po(docno, year, approveStatus[array]);
				}

			}
			request.setAttribute("ListResultPOSearch", new PurchaseOrderDB().GetListPO_Header("", "", "", "", "", "", ""));
		} else {
			request.setAttribute("ListResultPOSearch", new PurchaseOrderDB().GetListPO_Header("", "", "", "", "", "", ""));
		}

		return SUCCESS;
	}
	
	public String window_viewDetail() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String po_docno = request.getParameter("po_docno");
		String year		= request.getParameter("year");
		if(year!=null) year = String.valueOf(Integer.parseInt(year)-543);
		String docdate = request.getParameter("docdate");
		String type		= request.getParameter("type");
		
		String pr_docno 	= request.getParameter("pr_docno");
		String pr_docdate	= request.getParameter("pr_docdate");
		String vendor 		= request.getParameter("vendor");
		String credit	= request.getParameter("credit");
		String mulct 	= request.getParameter("mulct");
		String qut_n	= request.getParameter("qut_n");
		String qut_d 	= request.getParameter("qut_d"); 
		
		PurchaseOrderDB purchaseOrderDB = new PurchaseOrderDB();
		
		List PRList = purchaseOrderDB.GetPRList(po_docno, year);
		request.setAttribute("PRList", PRList);

		List ResultImageList = purchaseOrderDB.GET_PurchaseRequest_Image(po_docno, year, "");
		request.setAttribute("ResultImageList", ResultImageList);
		   
		request.setAttribute("pr_docno", pr_docno);
		request.setAttribute("pr_docdate", pr_docdate);
		request.setAttribute("po_docno", po_docno);
		request.setAttribute("docdate", docdate);
		request.setAttribute("type", type);
		request.setAttribute("vender", vendor);
		request.setAttribute("credit_day", credit);
		request.setAttribute("mulct_day", mulct);
		request.setAttribute("quotation_number", qut_n);
		request.setAttribute("quotation_date", qut_d); 
		return SUCCESS;
	}
}
