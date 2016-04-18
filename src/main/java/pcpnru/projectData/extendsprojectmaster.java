package pcpnru.projectData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pcpnru.projectModel.ProjectModel;

public class extendsprojectmaster extends ProjectMasterDB {
	public List getListProject_Join_Projecthead(String project_code, String project_name, String year, String target)
			throws IOException, Exception {

		String sqlWhere = "";
		if (!project_code.equals("")) {
			sqlWhere += "project_master.project_code = '" + project_code + "' AND ";
		}
		if (!project_name.equals("")) {
			sqlWhere += "project_master.project_name = '" + project_name + "' AND ";
		}
		if (!year.equals("")) {
			sqlWhere += "projectplan_header.year = '" + year + "' AND ";
		}
		if (!target.equals("")) {
			sqlWhere += "projectplan_header.target = '" + target + "' AND ";
		}

		String sqlQuery = "SELECT " + "project_master.project_code, " + "project_master.project_name, "
				+ "projectplan_header.`year`, " + "projectplan_header.target, "
				+ "projectplan_header.datetime_response " + "FROM " + "projectplan_header "
				+ "INNER JOIN project_master ON project_master.project_code = projectplan_header.project_code where ";
		sqlQuery += sqlWhere;
		sqlQuery += "project_master.project_code <> '' order by project_master.project_code ";

		System.out.println(sqlQuery);

		conn = agent.getConnectMYSql();
		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlQuery);

		List getListProject_Join_Projecthead = new ArrayList();
		String forwhat = "getListProject_Join_Projecthead";
		while (rs.next()) {
			project_code = rs.getString("project_code");
			project_name = rs.getString("project_name");
			year = rs.getString("year");
			target = rs.getString("target");
			getListProject_Join_Projecthead
					.add(new ProjectModel(forwhat, project_code, project_name, year, target, forwhat));

		}

		rs.close();
		pStmt.close();
		conn.close();

		return getListProject_Join_Projecthead;
	}
}
