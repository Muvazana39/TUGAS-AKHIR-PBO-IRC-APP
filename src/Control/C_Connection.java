/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ChaVaZaSRL
 */
public class C_Connection {
    static final String C_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ta_pbo";
    
    static final String USER = "root";
    static final String PASS = "";
    
    /**
     * @param args the command line arguments
     */
    
    public static Connection Koneksi(){
        Connection conn = null;
        
        try{
            Class.forName(C_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
        }catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(C_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
}
