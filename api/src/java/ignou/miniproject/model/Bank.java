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
