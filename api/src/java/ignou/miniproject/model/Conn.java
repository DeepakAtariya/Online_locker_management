package ignou.miniproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
