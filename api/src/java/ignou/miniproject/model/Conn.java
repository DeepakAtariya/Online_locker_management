/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ignou.miniproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Deepak
 */
public class Conn {
    
    private static Connection con = null;
    
    public static Connection getMysqlConnection(){
        try{  
            Class.forName("com.mysql.jdbc.Driver");  
            Conn.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject","root",""); 
            return Conn.con;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
        } 
        
        
    }
    
}
