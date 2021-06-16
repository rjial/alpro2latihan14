
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rjial
 */
public class Fungsi {
    java.sql.Connection con = null;
    String namadb = "mahasiswa_sakti";
    public Fungsi() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/" + namadb, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * iki metu ne resultset dadi isok kyk gae getString, getInt, dll iki digae
     * kyk select tok
     */
    public ResultSet executeResult(String query) {
        ResultSet result = null;
        try {
            Statement state = con.createStatement();
            result = state.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * iki metu ne integer dadi nek 0 iku gagal nek 1 utowo luwih berarti isok
     * iki digae kyk insert, update, delete
     */
    public int executeUpdate(String query) {
        int result = 0;
        try {
            Statement state = con.createStatement();
            result = state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
}
