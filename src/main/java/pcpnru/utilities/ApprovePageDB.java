package pcpnru.utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pcpnru.masterModel.AuthenMasterModel;
import pcpnru.utilities.*;

public class ApprovePageDB {

	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public List getAP() throws Exception {

		List<String> listAP = new ArrayList<String>();

		conn = agent.getConnectMYSql();

		String sqlStmt = "SELECT count(approve_status) as cap FROM rocking_budget a RIGHT JOIN projectplan_header b on(a.project_code = b.project_code) WHERE approve_status in ('WA') "
				+ "UNION ALL "
				+ "SELECT count(approve_status) as cap FROM rocking_budget_central a RIGHT JOIN projectplan_header b on(a.project_code = b.project_code) WHERE approve_status in ('WA') "
				+ "UNION ALL "
				+ "SELECT count(approve_status) as cap FROM record_approve_hd a WHERE approve_status in ('WA') ";

		pStmt = conn.createStatement();
		rs = pStmt.executeQuery(sqlStmt);

		while (rs.next()) {
			listAP.add(rs.getString("cap"));
		}

		rs.close();
		pStmt.close();
		conn.close();

		return listAP;
	}

}
