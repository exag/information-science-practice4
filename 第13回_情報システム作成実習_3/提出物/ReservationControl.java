package client_system;

import java.awt.Dialog.ModalityType;
import java.sql.*;

public class ReservationControl {

    static String userid = "reservation_user";
    static String password = "pass0004";
    static Connection sqlCon;
    static Statement sqlStmt;

    String reservation_userid;
    private boolean flagLogin;

    ReservationControl(){
        flagLogin = false;
    }

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


    public String loginLogout(MainFrame frame){
        String res = "";

        if (flagLogin){
            // ログアウトを行う処理
            flagLogin = false;
            frame.buttonLog.setLabel("　ログイン　");
        } else {
            // ログインを行う処理
            // ログインダイアログの生成と表示
            LoginDialog ld = new LoginDialog(frame);
            ld.setVisible(true);
            ld.setModalityType(ModalityType.APPLICATION_MODAL);
            // IDとパスワードの入力がキャンセルされたら、空文字列を結果として終了
            if (ld.canceled){
                return "";
            }

            // IDとパスワードが入力された場合の処理
            // IDは他の機能のときに使用するのでメンバー変数に代入
            reservation_userid = ld.tfUserID.getText();
            // パスワードはここでしか使わないので、ローカル変数に代入
            String password = ld.tfPassword.getText();

            connectDB();

            try {
                String sql = "SELECT * FROM db_reservation.user WHERE user_id = '" + reservation_userid +"'";
                ResultSet rs = sqlStmt.executeQuery(sql);
                if (rs.next()){
                    String password_from_db = rs.getString("password");
                    if (password_from_db.equals(password)){
                        flagLogin = true;
                        frame.buttonLog.setLabel("ログアウト");
                        res = "";
                    } else {
                        // 認証失敗：パスワードが不一致
                        res = "ログインできません. IDパスワードが違います.";
                    }
                } else {
                    // 認証失敗：ユーザIDがデータベースに存在しない
                    res = "ログインできません. IDパスワードが違います.";
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            closeDB();
        }
        return res;
    }

    public String getReservationOfUser(){
        String res = "";

        
        if (flagLogin) {
            connectDB();
            try {
                String sql = "SELECT DATE(date) date, TIME_FORMAT(start_time, '%H:%i') start_time, TIME_FORMAT(end_time, '%H:%i') end_time, facility_name FROM db_reservation.reservation WHERE user_id = '" + reservation_userid +"'";
                ResultSet rs = sqlStmt.executeQuery(sql);
    
                while (rs.next()){
                    String date = rs.getString("date");
                    String start_time = rs.getString("start_time");
                    String end_time = rs.getString("end_time");
                    String facility_name = rs.getString("facility_name");
                    if (res.length() == 0) {
                        res += "予約状況\n\n";
                    }
                    res += String.format("　　%s　%s　%s　--　%s\n", date, facility_name, start_time, end_time);
                }

                if (res.length() == 0) {
                    res = "予約はありません";
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            closeDB();

        } else {
            res = "ログインしてください";
        }
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
