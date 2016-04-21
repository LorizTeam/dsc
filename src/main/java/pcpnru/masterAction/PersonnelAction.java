package pcpnru.masterAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.masterData.PersonnelMasterDB;
import pcpnru.masterModel.PersonnelMasterModel;
import pcpnru.projectAction.LogoutAction;
import pcpnru.utilities.CheckAuthenPageDB;
import pcpnru.utilities.DateUtil;

public class PersonnelAction extends ActionSupport {

	PersonnelMasterModel personnelMasterModel;

	public PersonnelMasterModel getPersonnelMasterModel() {
		return personnelMasterModel;
	}

	public void setPersonnelMasterModel(PersonnelMasterModel personnelMasterModel) {
		this.personnelMasterModel = personnelMasterModel;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();

		PersonnelMasterDB pn = new PersonnelMasterDB();

		String personnel_id = personnelMasterModel.getPersonnel_id(),
				personnel_name = personnelMasterModel.getPersonnel_name(),
				personnel_lastname = personnelMasterModel.getPersonnel_lastname(), dow = personnelMasterModel.getDow(),
				dob = personnelMasterModel.getDob(), telephone = personnelMasterModel.getTelephone(),
				address = personnelMasterModel.getAddress(),manday = personnelMasterModel.getManday();

		String add = request.getParameter("add");
		String update = request.getParameter("update");
		String delete = request.getParameter("delete");
		String forwardText = "success";

		String project_code = request.getParameter("project_code");
		String authen_type = request.getParameter("authen_type");
		String position = request.getParameter("position");

		DateUtil dateUtil = new DateUtil();
		dow = dateUtil.CnvToYYYYMMDDEngYear(dow, '-');
		dob = dateUtil.CnvToYYYYMMDDEngYear(dob, '-');

		if (add != null) {

			try {
				pn.AddPersonnelMaster(project_code, personnel_id, personnel_name, personnel_lastname, authen_type, dow,
						dob, telephone, address, position, manday);
				personnelMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (update != null) {
			try {
				pn.UpdatePersonnelMaster(project_code, personnel_id, personnel_name, personnel_lastname, authen_type,
						dow, dob, telephone, address, position, manday);
				personnelMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (delete != null) {
			try {
				pn.DeletePersonnelMaster(personnel_id);
				personnelMasterModel.reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return forwardText;
	}

	public String execute_profile() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		String username = "", project_code = "", forwardText = "";

		if (session.getAttribute("username") == null) {
			forwardText = "login";
		} else {
			username = session.getAttribute("username").toString();
			CheckAuthenPageDB capDB = new CheckAuthenPageDB();
			project_code = capDB.getProjectCode(username);

			PersonnelMasterDB pn = new PersonnelMasterDB();
			List grouPersonnelMasterList = pn.GetPersonnelList(project_code, username, "", "", "");

			if (grouPersonnelMasterList.size() == 1) {

				personnelMasterModel = (PersonnelMasterModel) grouPersonnelMasterList.get(0);

				personnelMasterModel.setPersonnel_id(personnelMasterModel.getPersonnel_id());
				personnelMasterModel.setPersonnel_name(personnelMasterModel.getPersonnel_name());
				personnelMasterModel.setPersonnel_lastname(personnelMasterModel.getPersonnel_lastname());

				DateUtil dateUtil = new DateUtil();
				personnelMasterModel.setDob(personnelMasterModel.getDob());

				personnelMasterModel.setTelephone(personnelMasterModel.getTelephone());
				personnelMasterModel.setAddress(personnelMasterModel.getAddress());

				request.setAttribute("position", personnelMasterModel.getPosition());
			}

			forwardText = "success";

		}
		return forwardText;
	}

	public String update_profile() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String personnel_id = personnelMasterModel.getPersonnel_id();

		String save = request.getParameter("save");
		String clear = request.getParameter("clear");
		String change = request.getParameter("change");
		String forwardText = "success";

		PersonnelMasterDB pn = new PersonnelMasterDB();
		if (change != null) {
			String password_old = request.getParameter("password_old");
			String password_new = request.getParameter("password_new");
			boolean chkpass = false;
			chkpass = pn.checkPassword(personnel_id, password_old);
			if (chkpass == true) {
				pn.UpdatePassword(personnel_id, password_new);

				new LogoutAction().execute();
			}

		} else if (save != null) {
			String personnel_name = personnelMasterModel.getPersonnel_name(),
					personnel_lastname = personnelMasterModel.getPersonnel_lastname(),
					dob = personnelMasterModel.getDob(), telephone = personnelMasterModel.getTelephone(),
					address = personnelMasterModel.getAddress();
			DateUtil dateUtil = new DateUtil();

			String position = request.getParameter("position");

			pn.UpdatePersonnel_Profile(personnel_id, personnel_name, personnel_lastname,
					dateUtil.CnvToYYYYMMDDEngYear(dob, '-'), telephone, address, position);

			personnelMasterModel.setPersonnel_id(personnel_id);
			personnelMasterModel.setPersonnel_name(personnel_name);
			personnelMasterModel.setPersonnel_lastname(personnel_lastname);

			personnelMasterModel.setDob(dob);

			personnelMasterModel.setTelephone(telephone);
			personnelMasterModel.setAddress(address);

			request.setAttribute("position", position);

		} else if (clear != null) {

			String project_code = new CheckAuthenPageDB().getProjectCode(personnel_id);
			List grouPersonnelMasterList = pn.GetPersonnelList(project_code, personnel_id, "", "", "");

			if (grouPersonnelMasterList.size() == 1) {

				personnelMasterModel = (PersonnelMasterModel) grouPersonnelMasterList.get(0);

				personnelMasterModel.setPersonnel_id(personnelMasterModel.getPersonnel_id());
				personnelMasterModel.setPersonnel_name(personnelMasterModel.getPersonnel_name());
				personnelMasterModel.setPersonnel_lastname(personnelMasterModel.getPersonnel_lastname());
				personnelMasterModel.setDob(personnelMasterModel.getDob());

				personnelMasterModel.setTelephone(personnelMasterModel.getTelephone());
				personnelMasterModel.setAddress(personnelMasterModel.getAddress());

				request.setAttribute("position", personnelMasterModel.getPosition());
			}
		}

		return forwardText;
	}

}
