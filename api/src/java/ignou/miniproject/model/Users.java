/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ignou.miniproject.model;

import java.sql.Connection;



/**
 *
 * @author Deepak
 */
public class Users {

    private String username;
    private String password;
    private Connection conn;
    /**
     * @return the username
     */
    
    public Boolean auth(String username, String password){
        this.username = username;
        this.password = password;
        
        if(this.username.equals("Deepak") && this.password.equals("Deepak")){
            return true;
        }
        
//        this.conn = new Conn 
        
        
        
        return false;
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
