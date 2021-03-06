
package ignou.miniproject;


import ignou.miniproject.model.Users;
import ignou.miniproject.model.Bank;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
 
import javax.ws.rs.POST;
//import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
@Path("/customer2bank")
public class CustomerService {
    
    private Users users=null;
    private Bank bank=null;
       
    @POST
    @Path("/open_account")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response open_account( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String name = (String) json.get("name");
        String contact = (String) json.get("contact");
        String address = (String) json.get("address");
        String openingamount =  Long.toString((Long)json.get("openingamount"));
        String accounttype = (String) json.get("accounttype");
        String password = (String) json.get("password");
        JSONObject result_json = new JSONObject();

        this.bank = new Bank();
        
        String account_number = this.bank.openAccount(name, contact, address, openingamount, accounttype, password);
        
        result_json.put("account_number", account_number);
        
        
        return Response.status(200).entity(result_json.toString()).build();
    }

    
    @POST
    @Path("/locker_request")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response locker_Request( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");

        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int is_requested;
            is_requested = this.bank.locker_request(username);
            
            result_json.put("auth", is_requested);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }
    }

    @POST
    @Path("/check_locker_request")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response check_locker_Request( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int is_requested;
            is_requested = this.bank.check_locker_request(username);
            
            ArrayList list = this.bank.getLockerDetails(username);
            
            result_json.put("locker_id",list.get(1));
            result_json.put("issued_on",list.get(2));
            result_json.put("expired_on",list.get(3));
            result_json.put("balance",list.get(0));
            result_json.put("status", is_requested);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }

//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
    }


    @POST
    @Path("/check_locker_approval")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response check_locker_Approval( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int is_requested;
            is_requested = this.bank.check_locker_request(username);
        
            result_json.put("status", is_requested);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }

//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
    }

    
    @POST
    @Path("/balance")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBalance( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int balance;
            balance = this.bank.getBalance(username);
            
            result_json.put("balance", balance);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }
//
//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
    }
    
    @POST
    @Path("/locker_issued_expiry_date")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response locker_issued_expiry_date( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            ArrayList dates;
            dates = this.bank.locker_issued_expiry_date(username);
            
            result_json.put("issued", dates.get(0));
            result_json.put("expiry", dates.get(1));
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }

//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
    }
    
    @POST
    @Path("/lockeraccessrequest")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response lockerAccessRequest( String request_data_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(request_data_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        String date = (String)json.get("Date");
        String time = (String)json.get("Time");
//        
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int requested;
            requested = this.bank.addLockerAccessRequest(username, date, time);
            
            result_json.put("requested", requested);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }

//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
    }
    
    @POST
    @Path("/checkpendinglockeraccessrequest")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkPendingLockerAccessRequest( String request_data_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(request_data_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        
        // pass the username and password for authen-tication from database
        this.users = new Users();
        
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            int locker_access_id;
            locker_access_id = this.bank.checkPendingLockerAccessRequest(username);
            int is_rejected;
            is_rejected = this.bank.checkRejectedLockerAccessRequest(username);
            
            // if locker_access_id is 0 then no pending request is there
            result_json.put("LockerAccessId", locker_access_id);
            result_json.put("is_rejected", is_rejected);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }
        
    }
    
    @POST
    @Path("/approvedlockerrequests")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approvedlockerrequests( String request_data_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(request_data_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        
        // pass the username and password for authentication from database
        this.users = new Users();
        
        this.bank = new Bank();
        Boolean result = this.users.auth(username, password);
        JSONObject result_json = new JSONObject();
        
        if(result){
            
            //insert user into addlocker_request with dates
            JSONObject approvals;
            approvals = this.bank.approvedLockerRequests(username);
            
            // if locker_access_id is 0 then no pending request is there
//            result_json.put("checking", "checked");
            return Response.status(200).entity(approvals.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }
        
    }
    
     
}