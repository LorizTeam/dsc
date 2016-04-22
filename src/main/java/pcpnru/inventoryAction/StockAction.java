package pcpnru.inventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.inventoryModel.StockModel;
import pcpnru.inventoryData.StockDB;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.Validate;

public class StockAction extends ActionSupport {
	StockModel stockmodel;
	
	public StockModel getStockmodel() {
		return stockmodel;
	}

	public void setStockmodel(StockModel stockmodel) {
		this.stockmodel = stockmodel;
	}

	public String execute() throws Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		String add = request.getParameter("add");
		String delete = request.getParameter("delete");
		String update = request.getParameter("update");
		StockDB stockdb = new StockDB();
		String stock_id = "";
		List stockList = null;

		if (add != null) {

			if (new Validate().Check_String_notnull_notempty(stockmodel.getAlertmsg()))
				stockmodel.setAlertmsg("");

			stock_id = stockdb.GetHighest_StockID();
			stock_id = stockdb.PlusOneID_FormatID(stock_id);
			String stock_name = stockmodel.getStock_name();
			stockdb.AddStock(stock_id, stock_name, username);
			stockmodel.ClearStockModel();

		} else if (delete != null) {

			if (new Validate().Check_String_notnull_notempty(stockmodel.getAlertmsg()))
				stockmodel.setAlertmsg("");

			List<Boolean> delete_status = new ArrayList<Boolean>();
			String[] delbrand = request.getParameterValues("delbrand");

			if (new Validate().Check_String_notnull_notempty(delbrand)) {

				for (String brandid : delbrand) {

					delete_status.add(stockdb.DeleteBrand(brandid));

				}

			} else {
				stockmodel.setAlertmsg("กรุณาติ๊กถูกเลือกข้อมูลที่ต้องการลบด้วยค่ะ");
			}

			for (Boolean result_value : delete_status) {
				if (!result_value) {
					stockmodel.setAlertmsg("ไม่สามารถลบแบรนด์ที่ถูกใช้งานอยู่ได้");
				}
			}

			stockmodel.ClearStockModel();

		} else if (update != null) {

			if (new Validate().Check_String_notnull_notempty(stockmodel.getAlertmsg()))
				stockmodel.setAlertmsg("");

			stockdb.UpdateBrand(stockmodel.getStock_id(), stockmodel.getStock_name(), username);
			stockmodel.ClearStockModel();
		}

		stockList = stockdb.Get_StockList("", "");
		request.setAttribute("stockList", stockList);
		return SUCCESS;
	}

	public String entrancstock() throws IOException, Exception {

		if (!CheckLogin())
			return "gologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		StockDB stockdb = new StockDB();
		List stockList = null;
		stockList = stockdb.Get_StockList("", "");
		request.setAttribute("stockList", stockList);
		return SUCCESS;
	}

	public String windows_entrancstock() throws IOException, Exception {

		if (!CheckLogin())
			return "nologin";

		HttpServletRequest request = ServletActionContext.getRequest();
		String page_code = "023";

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (!new CheckAuthenPageDB().getCheckAuthen(username, page_code))
			return "noauth";

		StockDB stockdb = new StockDB();
		List stockList = null;
		stockList = stockdb.Get_StockList("", "");
		request.setAttribute("stockList", stockList);
		stockmodel = new StockModel();
		stockmodel.setFromwindow("true");
		return SUCCESS;
	}

	public boolean CheckLogin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		boolean login = true;

		if (session.getAttribute("username") == null)
			return login = false;

		return login;
	}
}
