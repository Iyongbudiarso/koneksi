package ib.perpustakaan.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Koneksi {
    
    private static Connection conn;
    private static int x = 0;
    
    public static String db         = "\\database\\perpustakaan.mdb";
    public static String password   = "password";
    
    public static Connection getConnection()
    {
        try
        {
            if(conn.isClosed())
            {
                conn = loadConnection();
            }
        }
        catch(NullPointerException | SQLException npe)
        {
            conn = loadConnection();
        }
        return conn;
    }
    
    private static Connection loadConnection()
    {
        try {
            /* cara koneksi ke access */
            String curDir = System.getProperty("user.dir");
            String filename = curDir + db;
            String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
            url += filename.trim() + ";"+
                "Pwd="+password+";";
            
            conn = DriverManager.getConnection(url);
            
            /* cek koneksi berapa kali */
            x++;
            System.out.println("koneksi di panggil " + x);
            
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
            if(JOptionPane.showConfirmDialog(null, "Koneksi Terhadap Database Gagal\nCoba lagi?", "KONEKSI GAGAL", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                return getConnection();
            }
            else
            {
                System.exit(0);
            }
            return null;
        }
    }
    
    public static boolean closeConnection()
    {
        boolean b = false;
        try {
            conn.close();
            b = true;
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe)
        {
            
        }
        return b;
    }
}
