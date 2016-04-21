package pcpnru.masterAction;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Assert;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.RecordApproveDB;
import pcpnru.masterModel.RecordApproveModel;
import pcpnru.utilities.DateUtil;
import pcpnru.utilities.ThaiNumber;
import pcpnru.utilities.Validate;

public class RecordApproveAction extends ActionSupport {

	RecordApproveModel recordApproveModel;

	public RecordApproveModel getRecordApproveModel() {
		return recordApproveModel;
	}

	public void setRecordApproveModel(RecordApproveModel recordApproveModel) {
		this.recordApproveModel = recordApproveModel;
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

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		RecordApproveDB ra = new RecordApproveDB();
		String docno = recordApproveModel.getDocno();
		String year = recordApproveModel.getYear();

		String add = request.getParameter("add");
		String send_approve = request.getParameter("send_approve");
		String delete = request.getParameter("delete");
		String save = request.getParameter("save");
		String next = request.getParameter("next");
		String forwardText = "success";

		DateUtil dateUtil = new DateUtil();
		String record_approve_date = recordApproveModel.getRecord_approve_date();
		record_approve_date = dateUtil.CnvToYYYYMMDDEngYear(record_approve_date, '-');
		String record_approve_send = recordApproveModel.getRecord_approve_send();
		record_approve_send = dateUtil.CnvToYYYYMMDDEngYear(record_approve_send, '-');
		Validate valid = new Validate();
		if (save != null) {

			try {

				if (!recordApproveModel.equals("True")) {
					ra.AddRecordApprovehd(docno, year, record_approve_date, record_approve_send, 
							username, recordApproveModel.getVendor_id(), recordApproveModel.getCredit_day());

					recordApproveModel.setSaved("True");
				} else {
					ra.UpdateApprovehd(docno, year, record_approve_date, record_approve_send, 
							username, recordApproveModel.getVendor_id(), recordApproveModel.getApprove_status(), recordApproveModel.getCredit_day());
				}
				recordApproveModel.setApprove_status("CA");
				recordApproveModel.reset_ListItem();
				recordApproveModel.reset_alert();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (delete != null) {

			String[] itemno = request.getParameterValues("itemno");

			for (String del_itemno : itemno) {
				ra.DeleteRecordApprovedt(docno, year, del_itemno);
			}
			ra.update_itemno(docno, year);

			recordApproveModel.setFromwindow(recordApproveModel.getFromwindow());
			recordApproveModel.reset_ListItem();

		} else if (add != null) {

			String product_code = recordApproveModel.getProduct_code().trim();
			String qty = recordApproveModel.getQty();

			String unit_id = ra.Get_Product(product_code);

			if (ra.CheckHaveAddHD(docno)) {
				
				ra.AddRecordApprovedt(docno, year, product_code, qty, unit_id, username);
				
			} else {
				
				ra.AddRecordApprovehd(docno, year, record_approve_date, record_approve_send, 
						username, recordApproveModel.getVendor_id(), recordApproveModel.getCredit_day());
				
				ra.AddRecordApprovedt(docno, year, product_code, qty, unit_id, username);

				recordApproveModel.setSaved("True");
				
			}
			recordApproveModel.setApprove_status("CA");

			recordApproveModel.reset_ListItem();
		} else if (next != null) {

			recordApproveModel.reset_ListItem();

			create();
		} else if (send_approve != null) {
			
			ra.UpdateApprovehd(docno, year, record_approve_date, record_approve_send, 
					username, recordApproveModel.getVendor_id(), recordApproveModel.getApprove_status(), recordApproveModel.getCredit_day());
			
			recordApproveModel.setApprove_status(recordApproveModel.getApprove_status());
		}

		List ListRecordApproveDT = ra.ListRecordApproveDT(docno, "", year);
		request.setAttribute("ListRecordApproveDT", ListRecordApproveDT);

		return forwardText;
	}

	public String create() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		RecordApproveDB ra = new RecordApproveDB();
		recordApproveModel = new RecordApproveModel();

		DateUtil dateUtil = new DateUtil();
		String docno = ra.SelectUpdateDocNo("pr");

		recordApproveModel.setDocno(docno);
		recordApproveModel.setYear(dateUtil.curYear());

		return SUCCESS;
	}

	public String windowcreate() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		RecordApproveDB ra = new RecordApproveDB();
		String fromwindow = recordApproveModel.getFromwindow();
		recordApproveModel = new RecordApproveModel();

		DateUtil dateUtil = new DateUtil();
		String docno = ra.SelectUpdateDocNo("pr");

		recordApproveModel.setDocno(docno);
		recordApproveModel.setYear(dateUtil.curYear());
		recordApproveModel.setFromwindow(fromwindow);
		recordApproveModel.setCreatewindow("true");

		return SUCCESS;
	}

	public String entrancSearch() throws IOException, Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		DateUtil dateutil = new DateUtil();

		request.setAttribute("ListResultPRSearch",
				new RecordApproveDB().GetListPR_Header("", "", dateutil.curMonth(), dateutil.curYear()));
		recordApproveModel = new RecordApproveModel();
		recordApproveModel.setFromwindow("true");

		return SUCCESS;
	}

	public String entrancSearch_byPOpage() throws IOException, Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		request.setAttribute("ListResultPRSearch", new RecordApproveDB().GetListPR_Header("", "", "", "WA"));
		recordApproveModel = new RecordApproveModel();
		recordApproveModel.setFromwindow("po");

		return SUCCESS;
	}

	public String HistoryPR() throws IOException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String search = request.getParameter("search");
		String viewdetail = request.getParameter("viewdetail");
		String create = request.getParameter("create");

		String forwardText = "search";
		if (create != null) {

			windowcreate();
			forwardText = "create";
		} else if (search != null) {// Check Submit By button

			boolean passvalidate = true;
			Validate validate = new Validate();

			if (!validate.CheckRegexNumberOnly(recordApproveModel.getDocno())) {
				passvalidate = false;
			}
			String year = recordApproveModel.getYear();
			if (!year.equals("")) {
				year = String.valueOf(Integer.parseInt(year) - 543);
			}

			if (passvalidate) {

				if (recordApproveModel.getFromwindow().equals("po")) {

					request.setAttribute("ListResultPRSearch",
							new RecordApproveDB().GetListPR_Header(recordApproveModel.getDocno(), new DateUtil().CnvToYYYYMMDDEngYear(recordApproveModel.getRecord_approve_date(), '-'),
							recordApproveModel.getRecord_approve_month(), year, "AP"));


				} else {
					
					recordApproveModel.setFromwindow("view");
					
					request.setAttribute("ListResultPRSearch",
							new RecordApproveDB().GetListPR_Header(recordApproveModel.getDocno(), new DateUtil().CnvToYYYYMMDDEngYear(recordApproveModel.getRecord_approve_date(), '-'),
							recordApproveModel.getRecord_approve_month(), year));
					
				}

			}

		} else if (viewdetail != null) {
			RecordApproveDB ra = new RecordApproveDB();
			String year = recordApproveModel.getYear();
			if (!year.equals("")) {
				year = String.valueOf(Integer.parseInt(year) - 543);
			}
			Map MapResultValue = ra.GetAllValueHeader_byDocno(recordApproveModel.getDocno(), year);

			String fromwindow = recordApproveModel.getFromwindow();

			recordApproveModel = new RecordApproveModel();
			recordApproveModel.setDocno(MapResultValue.get("docno").toString());
			recordApproveModel.setYear(year);
			recordApproveModel.setRecord_approve_date(MapResultValue.get("record_approve_date").toString());
			recordApproveModel.setRecord_approve_send(MapResultValue.get("record_approve_send").toString());
			recordApproveModel.setCreate_by(MapResultValue.get("create_by").toString());
			recordApproveModel.setVendor_id(MapResultValue.get("vendor_id").toString());
			recordApproveModel.setVendor_name(MapResultValue.get("vendor_name").toString());
			recordApproveModel.setApprove_status(MapResultValue.get("approve_status").toString());
			recordApproveModel.setCredit_day((Integer) MapResultValue.get("credit_day"));
			recordApproveModel.setFromwindow(fromwindow);
			List ListRecordApproveDT = ra.ListRecordApproveDT(recordApproveModel.getDocno(), "", year);
			request.setAttribute("ListRecordApproveDT", ListRecordApproveDT);

			forwardText = "viewdetail";
		}
		return forwardText;
	}

	public String entrancPrApprove() throws IOException, Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String save = request.getParameter("save");
		String search = request.getParameter("search");

		if (search != null) {

			request.setAttribute("ListResultPRSearch",
					new RecordApproveDB().GetListPR_Header(recordApproveModel.getDocno(), recordApproveModel.getRecord_approve_date(),
							recordApproveModel.getRecord_approve_month(), recordApproveModel.getYear()));
			recordApproveModel.Approve_pr();

		} else if (save != null) {

			if (request.getParameterValues("chkrow") != null) {
				String[] chkrow = request.getParameterValues("chkrow");
				String[] approveStatus = request.getParameterValues("approveStatus");

				for (String data_row : chkrow) {
					String[] split_value = data_row.split("-");
					String docno = split_value[0];
					String year = String.valueOf(Integer.parseInt(split_value[1]) - 543);
					int array = Integer.parseInt(split_value[2]);
					new RecordApproveDB().approve_pr(docno, year, approveStatus[array]);
				}

			}
			request.setAttribute("ListResultPRSearch", new RecordApproveDB().GetListPR_Header("", "", "", ""));
		} else {
			request.setAttribute("ListResultPRSearch", new RecordApproveDB().GetListPR_Header("", "", "", ""));
		}

		return SUCCESS;
	}

	public String window_viewDetail() throws IOException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		RecordApproveDB ra = new RecordApproveDB();
		String year = request.getParameter("year");
		if (new Validate().Check_String_notnull_notempty(year)) {
			year = String.valueOf(Integer.parseInt(year) - 543);
		}
		Map MapResultValue = ra.GetAllValueHeader_byDocno(request.getParameter("docno"), year);

		String fromwindow = "view";

		recordApproveModel = new RecordApproveModel();
		recordApproveModel.setDocno(MapResultValue.get("docno").toString());
		recordApproveModel.setYear(year);
		recordApproveModel.setRecord_approve_date(MapResultValue.get("record_approve_date").toString());
		recordApproveModel.setRecord_approve_send(MapResultValue.get("record_approve_send").toString());
		recordApproveModel.setCreate_by(MapResultValue.get("create_by").toString());
		recordApproveModel.setVendor_id(MapResultValue.get("vendor_id").toString());
		recordApproveModel.setVendor_name(MapResultValue.get("vendor_name").toString());
		recordApproveModel.setApprove_status(MapResultValue.get("approve_status").toString());
		recordApproveModel.setCredit_day((Integer) MapResultValue.get("credit_day"));
		recordApproveModel.setFromwindow(fromwindow);
		List ListRecordApproveDT = ra.ListRecordApproveDT(recordApproveModel.getDocno(), "", year);
		request.setAttribute("ListRecordApproveDT", ListRecordApproveDT);
		
		return "viewdetail";
	}
}
