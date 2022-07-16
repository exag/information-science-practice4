package client_system;

import java.sql.*;

public class ReservationControl {

    static String userid = "reservation_user";
    static String password = "pass0004";
    static Connection sqlCon;
    static Statement sqlStmt;

    public String getReservationOn(String facility, String ryear_str, String rmonth_str, String rday_str){
        String res = "";

        try {
            int ryear = Integer.parseInt(ryear_str);
            int rmonth = Integer.parseInt(rmonth_str);
            int rday = Integer.parseInt(rday_str);
        } catch(NumberFormatException e){
            res = "年月日には数字を指定してください";
            return res;
        }

        res = facility + "　予約状況\n\n";

        // 月と日が一桁だったら、前に0をつける処理
        if (rmonth_str.length() == 1){
            rmonth_str = "0" + rmonth_str;
        }
        if (rday_str.length() == 1){
            rday_str = "0" + rday_str;
        }
        String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;

        connectDB();

        try {
            String sql = "SELECT * FROM db_reservation.reservation WHERE date = '" + rdate + "' AND facility_name = '" + facility + "' ORDER BY start_time;";
            ResultSet rs = sqlStmt.executeQuery(sql);
            boolean exist = false;
            while (rs.next()){
                String start = rs.getString("start_time");
                String end = rs.getString("end_time");
                res += "　　" + start + " -- " + end + "\n";
                exist = true;
            }
            if (!exist) {
                res = "予約はありません";
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        closeDB();

        return res;
    }

    public String getExplanation(String facility){
        String res = "";

        connectDB();

        try {
            String sql = "SELECT explanation FROM db_reservation.facility WHERE facility_name = '" + facility + "';";
            ResultSet rs = sqlStmt.executeQuery(sql);
            boolean exist = false;
            if (rs.next()){
                String explanation = rs.getString("explanation");
                res = explanation;
                exist = true;
            }
            if (!exist) {
                res = "該当する施設概要はありません";
            }
        } catch(Exception e){
            e.printStackTrace();
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
