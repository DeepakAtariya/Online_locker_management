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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        
//        Calendar cal = Calendar.getInstance();
//        Date today = cal.getTime();
//        System.out.println("================================="+today+"================================================================");
//        cal.add(Calendar.YEAR, 1); // to get previous year add 1
//        cal.add(Calendar.DAY_OF_MONTH, -1); // to get previous day add -1
//        Date expiryDate = cal.getTime();
//        System.out.println("================================="+expiryDate+"================================================================");
//        
//
//        stmt.setString(3,dateFormat.format(expiryDate));
 
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
    
    public int addLockerAccessRequest(String username, String date, String time) throws SQLException {
        this.conn = Conn.getMysqlConnection();
        PreparedStatement stmt=this.conn.prepareStatement("insert into access_locker (`locker_id`,`date`,`time`,`permission`,`charges`) values((SELECT addlocker_request.id FROM addlocker_request INNER JOIN users ON users.id=addlocker_request.customer where users.username=?),?,?,?,?) ");
        
        stmt.setString(1,username);
        stmt.setString(2,date);
        stmt.setString(3,time);
        stmt.setInt(4,0);
        
        PreparedStatement countappointment=this.conn.prepareStatement("SELECT count(*) FROM access_locker INNER JOIN addlocker_request ON addlocker_request.id=(SELECT addlocker_request.id FROM addlocker_request INNER JOIN users ON users.id=addlocker_request.customer WHERE users.username=?) WHERE access_locker.permission=1 ");
        
        countappointment.setString(1, username);
        
//        ResultSet rs = stmt.executeQuery();
        ResultSet countResult = countappointment.executeQuery();
        
        if(countResult.next()){
            int free_access = countResult.getInt(1);
            if(free_access>12){
                stmt.setInt(5,50);
            }else{
                stmt.setInt(5,0);
            }   
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        stmt.setString(2,dateFormat.format(new Date()));
        
        int rs = stmt.executeUpdate();
        
        if(rs == 1){
            // deduct money from account
            PreparedStatement fetchCurrentBalance=this.conn.prepareStatement("Select balance from users username=?");
            fetchCurrentBalance.setString(1, username);
            ResultSet currentBalance = fetchCurrentBalance.executeQuery();
            
            int c_balance = 0;
            if(currentBalance.next()){
                c_balance = currentBalance.getInt("balance");
                PreparedStatement deductLockerAccessFee=this.conn.prepareStatement("UPDATE users set balance=? where username=? ");
                //charges = 200 per visit after 12 free access 
                deductLockerAccessFee.setInt(1,(c_balance-200));
                deductLockerAccessFee.setString(2, username);
                
                int balance_deducted = deductLockerAccessFee.executeUpdate();
                
                return balance_deducted;
            }
            
        }else{
            
        }
        return 0;
    }
    
    /**
     * checkPendingLockerAccessRequest : this method checks for pending request and return 'access id' of that request
     */
    
        
    public int checkPendingLockerAccessRequest(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
        
        PreparedStatement stmt=this.conn.prepareStatement("SELECT access_locker.id FROM access_locker INNER JOIN addlocker_request ON addlocker_request.id=(SELECT addlocker_request.id FROM addlocker_request INNER JOIN users ON users.id=addlocker_request.customer WHERE users.username=?) WHERE access_locker.permission=0");
        
        stmt.setString(1,username);
        
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            
            int access_id = rs.getInt("id");
            return access_id;
            
        }
        
        return 0;
        
        
    }
    
    /**
     * checkPendingLockerAccessRequest : this method checks for pending request and return 'access id' of that request
     */
    
        
    public JSONObject approvedLockerRequests(String username) throws SQLException {
        
        this.conn = Conn.getMysqlConnection();
        
        PreparedStatement stmt=this.conn.prepareStatement("SELECT access_locker.* FROM access_locker INNER JOIN addlocker_request ON addlocker_request.id=(SELECT addlocker_request.id FROM addlocker_request INNER JOIN users ON users.id=addlocker_request.customer WHERE users.username=?) WHERE access_locker.permission=1");
        
        stmt.setString(1,username);
        
        
        ResultSet rs = stmt.executeQuery();
        
//        if(rs.next()){
//            approvals.put("access_id", rs.getInt("locker_id"));
//            approvals.put("date", rs.getString("date"));
//        }
        JSONArray jsonList = new JSONArray();

        while(rs.next()){
            JSONObject approvals = new JSONObject();
            approvals.put("access_id", rs.getInt("id"));
            approvals.put("date", rs.getString("date"));
            approvals.put("charges",rs.getInt("charges"));
//            jsonList.put(approvals);
            jsonList.add(approvals);
        }
        
        System.out.println("======================================================"+jsonList);
        
        JSONObject data=new JSONObject();
        data.put("approvals", jsonList);
        
        System.out.println("======================================================"+data);
        
        return data;
//        return (JSONObject) new JSONObject().put("approvals", jsonList);
        
        
    }
    
public JSONObject allTheLockerRequest() throws SQLException {
    
        System.out.println("======================================================check");
    
        this.conn = Conn.getMysqlConnection();
        
        PreparedStatement stmt=this.conn.prepareStatement("SELECT users.id, users.username, addlocker_request.* from addlocker_request INNER JOIN users on users.id=addlocker_request.customer where approved_date IS NULL");
        
        ResultSet rs = stmt.executeQuery();
        
        JSONArray jsonList = new JSONArray();

        while(rs.next()){
            JSONObject approvals = new JSONObject();
            approvals.put("locker_application_id", rs.getInt("addlocker_request.id"));
            approvals.put("username", rs.getString("username"));
            approvals.put("requested_date",rs.getString("request_date"));
            approvals.put("customer_id",rs.getInt("users.id"));
//            jsonList.put(approvals);
            jsonList.add(approvals);
        }
        
        System.out.println("======================================================"+jsonList);
        
        JSONObject data=new JSONObject();
        data.put("approvals", jsonList);
        
        System.out.println("======================================================"+data);
        
        return data;   
    }

    public Boolean updateLockerTable(int customer_id) throws SQLException {
        System.out.println("************************************** "+customer_id);
        
    
        this.conn = Conn.getMysqlConnection();
        int l_id = 0;
        
        //get the id of unused locker 
        PreparedStatement free_locker_id=this.conn.prepareStatement("SELECT id FROM `locker` WHERE customer is null limit 1");
        
        ResultSet rs = free_locker_id.executeQuery();
        
        if(rs.next()){
             l_id = rs.getInt("id");
        }
        
         System.out.println("************************free locke id******** "+l_id);
        
//        rs.close();
        
        
        PreparedStatement stmt=this.conn.prepareStatement("UPDATE `locker` SET `customer` = ?, `available`='no' WHERE `locker`.`id` = ?");
        
        stmt.setInt(1, customer_id);
        stmt.setInt(2, l_id);
        
        int rs_updation = stmt.executeUpdate();
        
//        stmt.close();
        return rs_updation==1;
  
    }
    
    public Boolean updatedAddLockerRequestTable(int customer_id) throws SQLException {
        this.conn = Conn.getMysqlConnection();
        
        PreparedStatement stmt=this.conn.prepareStatement("UPDATE `addlocker_request` SET `approved_date` = ? , `expiry_date` = ? WHERE `addlocker_request`.`customer` = ?;");
        
        
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        Date newDate = c.getTime();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        stmt.setString(1, dateFormat.format(new Date()));
        stmt.setString(2,dateFormat.format(newDate));
        stmt.setInt(3, customer_id);
        
        int rs = stmt.executeUpdate();
        
        return rs==1;
    }
    
    public ArrayList getLockerDetails(String username) throws SQLException {
        
        //get requested date - addlocker_request
        //get expired date - addlocker_request
        // balance - users
        // locker id - locker
        
        this.conn = Conn.getMysqlConnection();
        int l_id = 0;
        
        //get the id of unused locker 
        PreparedStatement locker_info=this.conn.prepareStatement("SELECT users.balance, locker.id, addlocker_request.approved_date, addlocker_request.expiry_date from addlocker_request inner join users on users.id=addlocker_request.customer inner join locker on users.id=locker.customer where users.username=?");
        
        locker_info.setString(1, username);
        
        
        ResultSet rs = locker_info.executeQuery();
        
        ArrayList locker_details = new ArrayList();
        
        if(rs.next()){
            locker_details.add(rs.getInt("balance"));
            locker_details.add(rs.getInt("id"));
            locker_details.add(rs.getInt("approved_date"));
            locker_details.add(rs.getInt("expiry_date"));
        }
        
        return locker_details;
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
