package pcpnru.projectAction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.utilities.DateUtil;

public class ReceiveRequisitionReportAction extends ActionSupport {

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		DateUtil dateUtil = new DateUtil();

		String projectcode = request.getParameter("projectcode");
		String fromdate = request.getParameter("fromdate");
		fromdate = dateUtil.CnvToYYYYMMDDEngYear(fromdate, '-') + " 00:00:00";
		String todate = request.getParameter("todate");

		String fromday = "", today = "", frommonth = "", tomonth = "";

		if (todate == null) {
			todate = fromdate.substring(0, 10) + " 23:59:59";
			today = todate.substring(8, 10);
			tomonth = todate.substring(5, 7);
		} else {
			todate = dateUtil.CnvToYYYYMMDDEngYear(todate, '-') + " 23:59:59";
			today = todate.substring(8, 10);
			tomonth = todate.substring(5, 7);
		}
		fromday = fromdate.substring(8, 10);
		frommonth = fromdate.substring(5, 7);

		if (fromday.equals("01"))
			fromday = "1";
		else if (fromday.equals("02"))
			fromday = "2";
		else if (fromday.equals("03"))
			fromday = "3";
		else if (fromday.equals("04"))
			fromday = "4";
		else if (fromday.equals("05"))
			fromday = "5";
		else if (fromday.equals("06"))
			fromday = "6";
		else if (fromday.equals("07"))
			fromday = "7";
		else if (fromday.equals("08"))
			fromday = "8";
		else if (fromday.equals("09"))
			fromday = "9";

		if (today.equals("01"))
			today = "1";
		else if (today.equals("02"))
			today = "2";
		else if (today.equals("03"))
			today = "3";
		else if (today.equals("04"))
			today = "4";
		else if (today.equals("05"))
			today = "5";
		else if (today.equals("06"))
			today = "6";
		else if (today.equals("07"))
			today = "7";
		else if (today.equals("08"))
			today = "8";
		else if (today.equals("09"))
			today = "9";

		String forwardText = null;

		if (projectcode != null & fromdate != null & todate != null) {
			request.setAttribute("projectcode", projectcode);
			request.setAttribute("fromdate", fromdate);
			request.setAttribute("todate", todate);
			request.setAttribute("fromday", fromday);
			request.setAttribute("frommonth", frommonth);
			request.setAttribute("today", today);
			request.setAttribute("tomonth", tomonth);

			forwardText = "print";
		} else {
			forwardText = "success";
		}
		return forwardText;
	}

}