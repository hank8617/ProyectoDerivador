package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import play.db.DB;

public class Connector {

	private static Connection conn = null;

	public static Connection getConnection() {

		try {
			if (conn == null) {
				conn = DB.getConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection con) {
		try {
			if(con != null)
				con.close();
		} catch (SQLException e) {
		}
	}

	public static void closeResultSet(ResultSet res) {
		try {
			if(res != null)
				res.close();
		} catch (SQLException e) {
		}
	}
	
	public static void closeStatement(PreparedStatement pre) {
		try {
			if(pre != null)
				pre.close();
		} catch (SQLException e) {
		}
	}
}
