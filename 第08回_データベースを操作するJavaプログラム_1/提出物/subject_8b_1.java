import java.sql.*;

public class subject_8b_1 {
	static String userid = "puser";
	static String password = "1234";
	static Connection sqlCon;
	static Statement sqlStmt;

	public static void main (String argv[]){
		connectDB();

		try {
			String sql = "SELECT * FROM practice.student;";
			ResultSet rs = sqlStmt.executeQuery(sql);
			while (rs.next()) {
				String student_id = rs.getString("student_id");
				String second_name = rs.getString("second_name");
				String first_name = rs.getString("first_name");
				String birthday = rs.getString("birthday");
				String res = student_id + "  " + second_name + "  " + first_name + "  " + birthday;
				System.out.println(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		closeDB();
	}

	private static void connectDB(){
		try {
			Class.forName("org.gjt.mm.mysql.Driver");

			String url = "jdbc:mysql://localhost?usrUnicode=true&characterEncoding=SJIS";
			sqlCon = DriverManager.getConnection(url, userid, password);
			sqlStmt = sqlCon.createStatement();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void closeDB() {
		try {
			sqlStmt.close();
			sqlCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
