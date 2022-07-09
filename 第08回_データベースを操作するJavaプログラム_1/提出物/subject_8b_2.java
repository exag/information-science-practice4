import java.io.*;
import java.sql.*;

public class subject_8b_2 {
	static String userid = "puser";
	static String password = "1234";
	static Connection sqlCon;
	static Statement sqlStmt;

	public static void main (String argv[]){

		String student_id = "";
		String second_name = "";
		String first_name = "";
		String birthday = "";

		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in), 1);

			System.out.print("学生ID　?");
			student_id = r.readLine();

			System.out.print("姓　?");
			second_name = r.readLine();

			System.out.print("名　?");
			first_name = r.readLine();

			System.out.print("誕生日（YYYY-MM-DDの形式で）　?");
			birthday = r.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		connectDB();

		try {
			String sql = "INSERT INTO practice.student (student_id, second_name, first_name, birthday) VALUES (";
			sql += "'" + student_id + "',";
			sql += "'" + second_name + "',";
			sql += "'" + first_name + "',";
			sql += "'" + birthday + "'";
			sql += ");";

			int rs_int = sqlStmt.executeUpdate(sql);
			System.out.println(rs_int + "行登録しました");
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