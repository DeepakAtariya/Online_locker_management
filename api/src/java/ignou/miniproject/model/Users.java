/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ignou.miniproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author Deepak
 */
public class Users {

    private String username;
    private String password;
    private Connection conn;
    /**
     * @param username
     * @param password
     * @return the username
     * @throws java.sql.SQLException
     */
    
    public Boolean auth(String username, String password) throws SQLException{
        this.username = username;
        this.password = password;
        
        this.conn = Conn.getMysqlConnection();
        PreparedStatement stmt=this.conn.prepareStatement("select * from users where nature=? and username=? and password=?");
        stmt.setString(1,"customer");
        stmt.setString(2,username);
        stmt.setString(3,password);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }

    public Boolean bankauth(String username, String password) throws SQLException{
        this.username = username;
        this.password = password;
        
        this.conn = Conn.getMysqlConnection();
        PreparedStatement stmt=this.conn.prepareStatement("select * from users where nature=? and username=? and password=?");
        stmt.setString(1,"banker");
        stmt.setString(2,username);
        stmt.setString(3,password);
        
        ResultSet rs = stmt.executeQuery();
        
        return rs.next();
    }

    
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
