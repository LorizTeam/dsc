package pcpnru.utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static Connection conn = null;

	public Connection getConnectMYSql() throws Exception, IOException {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName ("org.gjt.mm.mysql.Driver");
			String dbName = "dsc";
			// String hostname =
			// "pcpnru.cre4njgwawzc.ap-southeast-1.rds.amazonaws.com"; // amazon
			 String hostname = "smartict.ar-bro.net"; // smart server
			//String hostname = "localhost";
			String port = "3306";
			String dbUserName = "root";
			// String dbPassword = "a8s5T5d4"; // amazon
			 String dbPassword = "a010103241c"; // smart server
			//String dbPassword = "1234";
			String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName
					+ "?useUnicode=yes&characterEncoding=UTF-8&user=" + dbUserName + "&password=" + dbPassword;

			conn = DriverManager.getConnection(jdbcUrl);

			return conn;

		} catch (ClassNotFoundException e) {
			throw new Exception("class not found " + e);

		} catch (SQLException se) {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ignore) {
				}
			throw new Exception(se);
		}

		// finally {
		// if (conn != null) try { conn.close(); } catch (SQLException ignore)
		// {}

		// }
	}

	public Connection closeConnection() {

		try {
			conn.close();

		} catch (SQLException se) {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ignore) {
				}
			try {
				throw new Exception(se);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}

	public Connection getConnectPostgreSqlSrv() throws Exception, IOException {
		try {
			Class.forName("org.postgresql.Driver"); // load the driver
			// Real Database

			String dbUserName = "samarts";
			String dbPassword = "wip1234";
			String url = "jdbc:postgresql://203.149.44.21:5432/worldsip";

			// Test Database
			// String dbUserName = "admin";
			// String dbPassword = "Stu250i";
			// String url = "jdbc:postgresql://203.149.44.2:5432/worldsip";

			/*
			 * // Test Database localhost String dbUserName = "admin"; String
			 * dbPassword = "Stu250i"; String url =
			 * "jdbc:postgresql://localhost/worldsip";
			 */
			// Test Database 203.149.0.20
			// String dbUserName = "admin";
			// String dbPassword = "wip12345";
			// String url = "jdbc:postgresql://203.149.0.20:5432/worldsip";

			// Test DatabaseDum
			// String dbUserName = "admin";
			// String dbPassword = "samartwip1234";
			// String url = "jdbc:postgresql://192.168.15.64:5432/worldsip";
			conn = DriverManager.getConnection(url, dbUserName, dbPassword);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new Exception("Class not found" + e);
		} catch (Exception se) {
			throw new Exception(se);
		}
	}

	public Connection getConnectMYSqlSrv() throws Exception, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName ("org.gjt.mm.mysql.Driver");
			// String dbUserName = "voipuser";
			// String dbPassword = "connect!123";
			String dbUserName = "kittipong";
			String dbPassword = "$SQLmy@Host";
			String url = "jdbc:mysql://210.246.255.40/voip";
			// String dbUserName = "root";
			// String dbPassword = "";
			// String url = "jdbc:mysql://localhost/voip";
			// String url = "jdbc:mysql://192.168.15.182/voip";
			conn = DriverManager.getConnection(url, dbUserName, dbPassword);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new Exception("Class not found" + e);
		} catch (SQLException se) {
			throw new Exception(se);
		}
	}
}