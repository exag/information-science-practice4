package initialize;

import java.sql.*;


public class DataCreater {

    static String userid = "reservation_user";
    static String password = "pass0004";
    static Connection sqlCon;
    static Statement sqlStmt;

	public static void main (String argv[]){

        connectDB();

        try {
        	String sql;
            int rs_int;

            // facility
        	sql = "DELETE FROM db_reservation.facility;";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("facility: " + rs_int + "行削除しました");

            // 小ホール
            sql = "INSERT INTO db_reservation.facility (facility_name, open_time, close_time, explanation) VALUES (";
            sql += "'小ホール',";
            sql += "'10:00:00',";
            sql += "'21:00:00',";
            sql += "'小ホール 客席数:500 利用可能時間 10時から21時'";
            sql += ");";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("user: " + rs_int + "行登録しました");
            // 大会議室
            for (int i=1; i<=2; i++){
                sql = "INSERT INTO db_reservation.facility (facility_name, open_time, close_time, explanation) VALUES (";
                sql += "'" + String.format("大会議室%d", i) + "',";
                sql += "'9:00:00',";
                sql += "'20:00:00',";
                sql += "'" + String.format("大会議室%d 客席数:80 利用可能時間 9時から20時", i) + "'";
                sql += ");";
                rs_int = sqlStmt.executeUpdate(sql);
                System.out.println("user: " + rs_int + "行登録しました");
            }
            // 小会議室
            for (int i=1; i<=6; i++){
                sql = "INSERT INTO db_reservation.facility (facility_name, open_time, close_time, explanation) VALUES (";
                sql += "'" + String.format("小会議室%d", i) + "',";
                sql += "'9:00:00',";
                sql += "'20:00:00',";
                sql += "'" + String.format("小会議室%d 客席数:20 利用可能時間 9時から20時", i) + "'";
                sql += ");";
                rs_int = sqlStmt.executeUpdate(sql);
                System.out.println("user: " + rs_int + "行登録しました");
            }

            // user
        	sql = "DELETE FROM db_reservation.user;";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("user: " + rs_int + "行削除しました");

            for (int i=1; i<=10; i++){
                sql = "INSERT INTO db_reservation.user (user_id, user_name, address, telephone, mail_address, password) VALUES (";
                sql += "'" + String.format("user%04d", i) + "',";
                sql += "'" + String.format("ユーザー%04d", i) + "',";
                sql += "'" + String.format("ユーザー住所%04d", i) + "',";
                sql += "'" + String.format("00-0000-%04d", i) + "',";
                sql += "'" + String.format("user%04d@example.com", i) + "',";
                sql += "'" + String.format("pass%04d", i) + "'";
                sql += ");";
                rs_int = sqlStmt.executeUpdate(sql);
                System.out.println("user: " + rs_int + "行登録しました");
            }

            // reservation
        	sql = "DELETE FROM db_reservation.reservation;";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("reservation: " + rs_int + "行削除しました");

            sql = "INSERT INTO db_reservation.reservation (date, start_time, end_time, user_id, facility_name) VALUES (";
            sql += "'2022-07-10',";
            sql += "'09:00:00',";
            sql += "'12:00:00',";
            sql += "'user0001',";
            sql += "'小会議室1'";
            sql += ");";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("user: " + rs_int + "行登録しました");
            sql = "INSERT INTO db_reservation.reservation (date, start_time, end_time, user_id, facility_name) VALUES (";
            sql += "'2022-07-10',";
            sql += "'10:00:00',";
            sql += "'12:00:00',";
            sql += "'user0002',";
            sql += "'小会議室2'";
            sql += ");";
            rs_int = sqlStmt.executeUpdate(sql);
            System.out.println("user: " + rs_int + "行登録しました");


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

    private static void closeDB(){
        try {
            sqlStmt.close();
            sqlCon.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
