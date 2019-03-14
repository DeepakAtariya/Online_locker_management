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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Deepak
 */
public class Bank {
    
    private int id;
    
    // store the name of the customer who is applying for locker 
    private String customer;
    private Date request_date;
    private Date approved_date;
    private Date cancellation_date;
    private Connection conn;

    public int locker_request(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
        PreparedStatement stmt=this.conn.prepareStatement("insert into addlocker_request (`customer`, `request_date`) values((SELECT id FROM users where username=?),?) ");
        
        stmt.setString(1,username);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        stmt.setString(2,dateFormat.format(new Date()));
        
        int rs = stmt.executeUpdate();
        
        return rs;
    }
    
    public int check_locker_request(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
//        PreparedStatement stmt=this.conn.prepareStatement("insert into addlocker_request (`customer`, `request_date`) values((SELECT id FROM users where username=?),?) ");
        PreparedStatement stmt=this.conn.prepareStatement("SELECT * FROM addlocker_request where customer = (SELECT id FROM users where username=?)");
        
        stmt.setString(1,username);
        
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            Date request_date = rs.getDate("request_date");
            if(request_date!=null){
                
                Date approved_date = rs.getDate("approved_date");
                if(approved_date!=null){
                    return 4;
                }else{
                    return 3;
                }
            }
            
        }
        
        return 0;
        
        
    }
        
    public int getBalance(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
//        PreparedStatement stmt=this.conn.prepareStatement("insert into addlocker_request (`customer`, `request_date`) values((SELECT id FROM users where username=?),?) ");
        PreparedStatement stmt=this.conn.prepareStatement("SELECT * FROM users where username=?");
        
        stmt.setString(1,username);
        
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            int balance = rs.getInt("balance");
            return balance;
        }
        
        return 0;
        
        
    }
    
    public ArrayList locker_issued_expiry_date(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
//        PreparedStatement stmt=this.conn.prepareStatement("insert into addlocker_request (`customer`, `request_date`) values((SELECT id FROM users where username=?),?) ");
        PreparedStatement stmt=this.conn.prepareStatement("SELECT * FROM addlocker_request where customer = (SELECT id FROM users where username=?)");
        
        stmt.setString(1,username);
        
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            Date approved_date = rs.getDate("approved_date");
            Date expiry_date = rs.getDate("expiry_date");
            
            String approved = new SimpleDateFormat("yyyy/MM/dd").format(approved_date);
            String expiry = new SimpleDateFormat("yyyy/MM/dd").format(expiry_date);
            
            ArrayList datelist = new ArrayList();
            datelist.add(approved);
            datelist.add(expiry);
            
            return datelist;
        }
        
        return null;
        
        
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * @return the request_date
     */
    public Date getRequest_date() {
        return request_date;
    }

    /**
     * @param request_date the request_date to set
     */
    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    /**
     * @return the approved_date
     */
    public Date getApproved_date() {
        return approved_date;
    }

    /**
     * @param approved_date the approved_date to set
     */
    public void setApproved_date(Date approved_date) {
        this.approved_date = approved_date;
    }

    /**
     * @return the cancellation_date
     */
    public Date getCancellation_date() {
        return cancellation_date;
    }

    /**
     * @param cancellation_date the cancellation_date to set
     */
    public void setCancellation_date(Date cancellation_date) {
        this.cancellation_date = cancellation_date;
    }
}
