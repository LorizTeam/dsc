package pcpnru.projectData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import pcpnru.utilities.DBConnect;
import pcpnru.utilities.DateUtil;

public class RelationBank {
	DBConnect agent = new DBConnect();
	Connection conn = null;
	Statement pStmt = null;
	ResultSet rs = null;
	DateUtil dateUtil = new DateUtil();

	public boolean UpdateStatusReceive(List allList) throws IOException, Exception {
		boolean statusupdate = false;
		String sqlQuery = "";
		int rowsupdate = 0;

		for (Iterator IterList = allList.iterator(); IterList.hasNext();) {
			List detail = (List) IterList.next();
			sqlQuery = "update receivehd set approve_tobank = '" + detail.get(3) + "' " + "where docno = '"
					+ detail.get(0) + "' ";
			if (!detail.get(1).equals(""))
				sqlQuery += " and projectcode = '" + detail.get(1) + "' ";
			if (!detail.get(2).equals(""))
				sqlQuery += " and project_year = '" + detail.get(2) + "' ";

			conn = agent.getConnectMYSql();
			pStmt = conn.createStatement();
			rowsupdate = rowsupdate + pStmt.executeUpdate(sqlQuery);

			conn.close();
			pStmt.close();
		}

		if (rowsupdate > 0) {
			statusupdate = true;
		}

		return statusupdate;
	}
}
