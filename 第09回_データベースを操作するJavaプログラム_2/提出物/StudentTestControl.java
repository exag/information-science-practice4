import java.sql.*;

public class StudentTestControl {
    static String userid = "puser";
    static String password = "1234";
    static Connection sqlCon;
    static Statement sqlStmt;

    public StudentTestControl(){
    }

    public String insertStudent(String student_id, String second_name, String first_name, String y, String m, String d){
        String res = "";

        try {
            int rYear = Integer.parseInt(y);
            int rMonth = Integer.parseInt(m);
            int rDay = Integer.parseInt(d);
        } catch (NumberFormatException e){
            res = "年月日には数字を指定してください";
            return res;
        }

        if (m.length() == 1){
            m = "0" + m;
        }
        if (d.length() == 1){
            d = "0" + d;
        }
        String birthday = y + "-" + m + "-" + d;

        connectDB();

        try {
            String sql = "INSERT INTO practice.student (student_id, second_name, first_name, birthday) VALUES (";
            sql += "'" + student_id + "',";
            sql += "'" + second_name + "',";
            sql += "'" + first_name + "',";
            sql += "'" + birthday + "'";
            sql += ");";

            int rs_int = sqlStmt.executeUpdate(sql);
            res = rs_int + "行登録しました";

        } catch (Exception e) {
            res = "エラーが生じたため、登録できませんでした";
        }

        closeDB();

        return res;
    }

    public String selectStudent(){
        String res = "";

        connectDB();

        try {
            String sql = "SELECT * FROM practice.student;";
            ResultSet rs = sqlStmt.executeQuery(sql);
            while (rs.next()) {
                String student_id = rs.getString("student_id");
                String second_name = rs.getString("second_name");
                String first_name = rs.getString("first_name");
                String birthday = rs.getString("birthday");
                res += student_id + "  " + second_name + "  " + first_name + "  " + birthday + "\n";
            }
        } catch (Exception e) {
            res = "データ検索においてエラーが生じました";
        }

        closeDB();

        return res;
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

    private static void closeDB(){
        try {
            sqlStmt.close();
            sqlCon.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
