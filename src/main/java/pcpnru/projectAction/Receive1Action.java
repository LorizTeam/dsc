package pcpnru.projectAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.CostCodeMasterDB;
import pcpnru.projectData.ProjectMasterDB;
import pcpnru.projectData.Receive1DB;
import pcpnru.projectModel.ProjectMasterForm;
import pcpnru.projectModel.ReceiveForm;
import pcpnru.utilities.DateUtil;

public class Receive1Action extends ActionSupport {

	private ReceiveForm receiveform;
	private List projectMasterList = new ArrayList();
	private ProjectMasterForm ProjectMasterForm;
	ProjectMasterDB projectMasterDB = new ProjectMasterDB();

	public ProjectMasterForm getProjectMasterForm() {
		return ProjectMasterForm;
	}

	public void setProjectMasterForm(ProjectMasterForm projectMasterForm) {
		ProjectMasterForm = projectMasterForm;
	}

	public List getProjectMasterList() {
		try {

			projectMasterList = projectMasterDB.GetProjectMasterList("", "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("In getProject MasterList: " + projectMasterList);
		return projectMasterList;
	}

	public void setProjectMasterList(List projectMasterList) {
		this.projectMasterList = projectMasterList;
	}

	public ReceiveForm getReceiveform() {
		return receiveform;
	}

	public void setReceiveform(ReceiveForm receiveform) {
		this.receiveform = receiveform;
	}

	private String countryId;

	private List<Object> countryList = new ArrayList<Object>();

	public void SelectTagExample() {
		countryList.add(new CountryVO("IndiaKey", "India"));
		countryList.add(new CountryVO("USAKey", "USA"));
		countryList.add(new CountryVO("UKKey", "UK"));
		System.out.println("In Constructor: " + countryList);
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public List<Object> getCountryList() {
		System.out.println("In getCountryList(): " + countryList);
		return countryList;

	}

	public void setCountryList(List<Object> countryList) {
		System.out.println("In setCountryList(): " + countryList);
		this.countryList = countryList;
	}

	// inner class
	public class CountryVO {
		String countryKey;
		String countryName;

		public String getCountryKey() {
			return countryKey;
		}

		public void setCountryKey(String countryKey) {
			this.countryKey = countryKey;
		}

		public String getCountryName() {
			return countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public CountryVO(String countryKey, String countryName) {
			super();
			this.countryKey = countryKey;
			this.countryName = countryName;
		}

	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ok = request.getParameter("ok");
		String alertMassage = "";

		String forwardText = null;

		if (ok != null) {

			DateUtil dateUtil = new DateUtil();
			// String date =
			// dateUtil.CnvToDDMMYYYYThaiYear(dateUtil.CnvToYYYYMMDD(dateUtil.curDate(),
			// '-')); //01/11/2557
			// String docDate = dateUtil.CnvToYYYYMMDD(dateUtil.curDate(), '-');
			// String day = date.substring(0,2); // 01
			// String month = date.substring(3,5); // 12
			// String year = date.substring(6); // 2559

			String projectCode = request.getParameter("projectCode");
			String[] splitgprojectcode = projectCode.split(" - ");
			String[] splitYear = projectCode.split(" - ");

			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String timeofday = dateFormat.format(cal.getTime());

			String dateTime = request.getParameter("dateTime");
			String[] splitDate = dateTime.split("-");
			String day = splitDate[0]; // 01
			String month = splitDate[1]; // 12
			String year = splitDate[2]; // 2559
			dateTime = dateUtil.CnvToYYYYMMDDEngYear(dateTime, '-') + " " + timeofday;

			String gcostCode = request.getParameter("gcostCode");
			String amountfrom = receiveform.getAmountfrom();
			// String project_year = receiveform.getProject_year();
			System.out.println(amountfrom);
			String local = receiveform.getLocal();

			String docNo = receiveform.getDocNo();
			String vol = receiveform.getVol();

			Receive1DB receive1DB = new Receive1DB();
			// String docNo =
			// receive1DB.SelectUpdateDocNo(splitYear[2],"receive",splitgprojectcode[0],splitYear[2]);

			receive1DB.AddReceiveHD(docNo, vol, splitgprojectcode[0], splitYear[2], gcostCode, dateTime, day, month,
					year, amountfrom, local);

			String[] splitgcostcode = gcostCode.split(" - ");
			List Lcostcode_forreceive2 = receive1DB.ShowCostCodeforReceive2(splitgcostcode[0], "");
			String standardprice = receive1DB.SelectPriceStandard_fromgcostcode(splitgcostcode[0]);
			request.setAttribute("standardprice", standardprice);
			request.setAttribute("Lcostcode_forreceive2", Lcostcode_forreceive2);
			request.setAttribute("docNo", docNo);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("dateTime", dateTime);
			request.setAttribute("gcostCode", gcostCode);
			// request.setAttribute("amountFrom", amountFrom);
			// request.setAttribute("local", local);

			receiveform.setStandardprice(standardprice);
			receiveform.setAmountfrom(amountfrom);
			receiveform.setLocal(local);
			receiveform.setAmtt("0");
			receiveform.setVol(vol);

			forwardText = "success";
		} else {

			setProjectMasterList(getProjectMasterList());

			List projectMasterList = projectMasterDB.GetProjectMasterList("", "");

			request.setAttribute("projectMasterList", projectMasterList);

			CostCodeMasterDB costCodeMasterDB = new CostCodeMasterDB();
			List costCodeMasterList = costCodeMasterDB.GetCostCodeMasterList("", "", "");
			request.setAttribute("costCodeMasterList", costCodeMasterList);

			SelectTagExample();
			setCountryList(getCountryList());

			forwardText = "error";
		}
		return forwardText;
	}
}