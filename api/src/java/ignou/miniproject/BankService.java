/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ignou.miniproject;

/**
 *
 * @author Deepak
 */
import ignou.miniproject.model.Bank;
import ignou.miniproject.model.Users;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
 
import javax.ws.rs.POST;
//import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
@Path("/bank")
public class BankService {
    
    private Users users= null;
    private Bank bank = null;
    
//    approve_locker_application
        @POST
    @Path("/approve_locker_application")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approveLocker( String login_json ) throws ParseException, SQLException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        int customer_id =  Integer.parseInt(json.get("customer_id").toString()) ;
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            
            //update the locker table with customer id 
//            Boolean updatedLockerTable=true;
            Boolean updatedLockerTable;
            updatedLockerTable = this.bank.updateLockerTable(customer_id);
            
            //and change availablity message into 'no'
            Boolean updatedAddLockerRequest = this.bank.updatedAddLockerRequestTable(customer_id);
//              Boolean updatedAddLockerRequest = true;
            
            if(updatedLockerTable && updatedAddLockerRequest){
                result_json.put("locker", "approved");
//                return Response.status(200).entity(result_json.toString()).build();
                JSONObject approvals;
                approvals = this.bank.allTheLockerRequest();
                return Response.status(200).entity(approvals.toString()).build();
            }else{
                result_json.put("locker", "NA");
                return Response.status(200).entity(result_json.toString()).build();
            }
            
            
        }else{
            result_json.put("status", "false_l");
            return Response.status(200).entity(result_json.toString()).build();
        }
        
//        JSONObject result_json = new JSONObject();
//        result_json.put("auth", result);
//        return Response.status(200).entity(result_json.toString()).build();
    }
    
    
    
    @POST
    @Path("/queue")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response consumeJSON( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            
            //get all the data from addlocker_request 
            JSONObject approvals;
            approvals = this.bank.allTheLockerRequest();
            
            return Response.status(200).entity(approvals.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }
        
//        JSONObject result_json = new JSONObject();
//        result_json.put("auth", result);
//        return Response.status(200).entity(result_json.toString()).build();
    }
}