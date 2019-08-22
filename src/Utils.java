import java.awt.Container;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Utils {

	static String ID;

	private static Connection con = null;
	private static Statement stmt = null;

	public static String safeInput(String string) {
		return string;
	}

	public static boolean isProper(String string, boolean type) {
		Pattern IDPattern = Pattern.compile("^[a-zA-Z0-9]*$");
		Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9~`!@#$%\\^&*()_-]*$");

		if (type) {
			Matcher possibleID = IDPattern.matcher(string);
			if (possibleID.find())
				return true;
			else
				return false;
		} else {
			Matcher possiblePassword = passwordPattern.matcher(string);
			if (possiblePassword.find())
				return true;
			else
				return false;
		}
	}

	public static void saveSetting(int cctvImp, int shelterImp, int convenienceImp, int widthImp, int brightnessImp,
			int adultEntImp, int constructionImp, int reputationImp) {
		try {
			String user = "guest";
			String password = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();

			stmt.executeUpdate("UPDATE user SET cctvImp = " + cctvImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET shelterImp = " + shelterImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET convenienceImp = " + convenienceImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET widthImp = " + widthImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET brightnessImp = " + brightnessImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET adultEntImp = " + adultEntImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET constructionImp = " + constructionImp + " WHERE ID = '" + ID + "'");
			stmt.executeUpdate("UPDATE user SET reputationImp = " + reputationImp + " WHERE ID = '" + ID + "'");

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	public static int[] findUser(String ID, String password) {
		int[] result = { -1, 0, 0, 0, 0, 0, 0, 0 };
		ResultSet rs = null;

		try {
			String user = "guest";
			String dbPassword = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, dbPassword);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM user WHERE ID = '" + ID + "' AND password = '" + password + "'");
			if (rs.next())
				for (int i = 0; i < 8; i++)
					result[i] = Integer.parseInt(rs.getString(i + 3));

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return result;
	}

	public static boolean addUser(Container container, String ID, String password) {
		ResultSet rs = null;
		boolean result = true;

		try {
			String user = "guest";
			String dbPassword = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, dbPassword);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM user WHERE ID = '" + ID + "'");
			if (!rs.next())
				stmt.executeUpdate("INSERT INTO user (ID, password) VALUES ('" + ID + "', '" + password + "');");
			else {
				JOptionPane.showMessageDialog(container, "동일한 아이디가 있습니다.");
				result = false;
			}

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return result;
	}

	public static void saveReputation(int node1ID, int node2ID, int starPoint) {
		ResultSet rs = null;

		try {
			String user = "guest";
			String password = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM user_reput WHERE ID = '" + ID + "' AND node1ID = " + node1ID
					+ " AND node2ID =" + node2ID);
			if (rs.next())
				stmt.executeUpdate("UPDATE user_reput SET reput=" + starPoint + " WHERE ID = '" + ID
						+ "' AND node1ID = " + node1ID + " AND node2ID = " + node2ID);
			else
				stmt.executeUpdate("INSERT INTO user_reput (ID, node1ID, node2ID, reput) VALUES ('" + ID + "', "
						+ node1ID + ", " + node2ID + ", " + starPoint + ")");

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null) {
					rs.close();
					stmt.close();
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<int[]> getReputation() {
		ArrayList<int[]> reputList = new ArrayList<>();
		ResultSet rs = null;
		try {
			String user = "guest";
			String password = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT node1ID, node2ID, reput FROM user_reput");
			while (rs.next()) {
				int[] reputInfo = new int[3];
				for (int i = 0; i < 3; i++)
					reputInfo[i] = Integer.parseInt(rs.getString(i + 1));
				reputList.add(reputInfo);
			}

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return reputList;
	}

	public static double getAverageReputation(String vid1, String vid2) {
		int count = 0;
		double sum = 0;
		ResultSet rs = null;

		try {
			String user = "guest";
			String password = "a1234567";
			String url = "jdbc:mysql://20.41.79.154:3306/user_info?serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM user_reput WHERE node1ID=" + Integer.parseInt(vid1) + " AND node2ID="
					+ Integer.parseInt(vid2));
			while (rs.next()) {
				count = count + 1;
				sum = sum + rs.getInt("reput");
			}
			if (count == 0)
				return 5;
			else
				return sum / (double) count;

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return 5;
	}
}