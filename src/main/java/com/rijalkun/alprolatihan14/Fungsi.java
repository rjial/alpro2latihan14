package com.rijalkun.alprolatihan14;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import java.awt.CardLayout;
import java.awt.Container;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.io.FileUtils;
import com.itextpdf.text.pdf.PdfDocument; 
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter; 
import java.io.FileOutputStream;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
    String nokrp = null;
    public Fungsi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahasiswa_stiki", "root", "");
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
    public String login(String username, String password) {
        ResultSet result = executeResult("select * from orang where NOKRP='" + username + "' and PASSWORD='" + password + "'");
        String nokrp = null;
        try {
            if (result.next()) {
                nokrp = result.getString("NOKRP");
            } else {
                JOptionPane.showMessageDialog(null, "No KRP atau password salah");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nokrp;
    }
    public int getJabatan(String nokrp) {
	int jabatan = 0;
	ResultSet result = null;
	try {
	    result = executeResult("select * from admin where NOKRP='" + nokrp + "'");
	    if (result.next()) {
		jabatan = 1;
	    }
	    result = executeResult("select * from dosen where NOKRP='" + nokrp + "'");
	    if (result.next()) {
		jabatan = 2;
	    }
	    result = executeResult("select * from mahasiswa where NOKRP='" + nokrp + "'");
	    if (result.next()) {
		jabatan = 3;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return jabatan;
    }
    
    public void loncatCard(Container container, String cardName) {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, cardName);
    }
    public void showReport(String url) {
	try {
	    HashMap param = new HashMap();
	    JasperReport jspR = JasperCompileManager.compileReport(url);
	    JasperPrint JPrint = JasperFillManager.fillReport(jspR, param, con);
	    JasperViewer.viewReport(JPrint, false);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    public String imgToBase64String(File file) {
	String encodedString = null;
	try {
	    byte[] fileContent = FileUtils.readFileToByteArray(file);
	    encodedString = Base64.getEncoder().encodeToString(fileContent);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return encodedString;
    }
    public void printPdf(JTable table, String filename, String judul) {
	DefaultTableModel model = (DefaultTableModel) table.getModel();
	String dest = filename;
	Document document = new Document();
	try {
	    PdfWriter.getInstance(document, new FileOutputStream(dest));

	    document.open();
	    Font font = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
	    Paragraph para = new Paragraph(judul, font);
	    para.setAlignment(Element.ALIGN_CENTER);
	    document.add(para);
	    float [] pointColumnWidths = {150F, 150F, 150F};
	    int columns = model.getColumnCount();
	    PdfPTable tablePdf = new PdfPTable(columns);
	    
	    document.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	
    }
}
