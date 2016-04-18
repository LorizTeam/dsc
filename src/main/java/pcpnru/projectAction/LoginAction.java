package pcpnru.projectAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import pcpnru.projectData.Login_ChangePasswordData;
import pcpnru.projectModel.EmployeeModel;

public class LoginAction extends ActionSupport {
	HttpServletRequest request = ServletActionContext.getRequest();
	private EmployeeModel empmodel;

	public EmployeeModel getEmpmodel() {
		return empmodel;
	}

	public void setEmpmodel(EmployeeModel empmodel) {
		this.empmodel = empmodel;
	}

	public String execute() throws IOException, Exception {
		Login_ChangePasswordData login_changepass = new Login_ChangePasswordData();
		List userprofile = login_changepass.CheckLogin(empmodel);
		HttpSession session = request.getSession();
		String forwardText = "";
		if (userprofile.size() != 0) {
			forwardText = "success";
			session.setAttribute("project_code", userprofile.get(0));
			session.setAttribute("username", userprofile.get(1));
			session.setAttribute("authen_type", userprofile.get(2));
		} else {

			forwardText = "false";

		}

		return forwardText;
	}
}
