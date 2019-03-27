
package ignou.miniproject;

import ignou.miniproject.model.Bank;
import ignou.miniproject.model.Users;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
 
import javax.ws.rs.POST;
//import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
@Path("/bank")
public class BankService {
    
    private Users users= null;
    private Bank bank = null;

    @GET
    @Path("/active_accounts")
    public Response activeAccounts() throws SQLException{
        
        this.bank = new Bank();
        
        JSONObject locker_details = this.bank.allActiveAccounts();
        
        
        return Response.status(200).entity(locker_details.toJSONString()).build();
    }
    
    
// rejectAppointment
    @POST
    @Path("/rejectAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rejectAppointment( String login_json ) throws ParseException, SQLException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        int access_id =  Integer.parseInt(json.get("access_id").toString()) ;
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            
            Boolean rejectAppointment;
            rejectAppointment = this.bank.rejectAppointment(access_id);
            
            if(rejectAppointment){
                result_json.put("locker", "approved");
                JSONObject locker_details;
                locker_details = this.bank.allAppointments();
                return Response.status(200).entity(locker_details.toString()).build();
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

// approveAppointment
    @POST
    @Path("/approveAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approveAppointment( String login_json ) throws ParseException, SQLException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        int access_id =  Integer.parseInt(json.get("access_id").toString()) ;
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            
            Boolean apporveAppointment;
            apporveAppointment = this.bank.setPermission2One(access_id);
            
            if(apporveAppointment){
                result_json.put("locker", "approved");
                JSONObject locker_details;
                locker_details = this.bank.allAppointments();
                return Response.status(200).entity(locker_details.toString()).build();
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

    
 
//    appointments
    @POST
    @Path("/appointments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response appoointments( String login_json ) throws ParseException, SQLException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        int customer_id =  Integer.parseInt(json.get("customer_id").toString()) ;
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            JSONObject locker_details;
            locker_details = this.bank.allAppointments();
            System.out.println("-------------------------"+locker_details+"--------------------------------------------------------");
            return Response.status(200).entity(locker_details.toString()).build();
            
        }else{
            result_json.put("status", "login error");
            return Response.status(200).entity(result_json.toString()).build();
        }
    }

    
    //cancel_locker_application
    @POST
    @Path("/cancel_locker_application")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelLocker( String login_json ) throws ParseException, SQLException {

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
            
            Boolean removeCustomerId = this.bank.removeCustomerId(customer_id);
            
            Boolean insertCancellationDataIntoAddLocker_requestTable = this.bank.insertCancellationDataIntoAddLocker_requestTable(customer_id);
            
            if(removeCustomerId && insertCancellationDataIntoAddLocker_requestTable){
                JSONObject locker_details;
                locker_details = this.bank.allLockerTableData();
                System.out.println("-------------------------"+locker_details+"--------------------------------------------------------");
                return Response.status(200).entity(locker_details.toString()).build();
            }else{
                result_json.put("status","cannot cancel");
                return Response.status(200).entity(result_json.toString()).build();
            }
            
            
            
        }else{
            result_json.put("status", "login error");
            return Response.status(200).entity(result_json.toString()).build();
        }
        
//        JSONObject result_json = new JSONObject();
//        result_json.put("auth", result);
//        return Response.status(200).entity(result_json.toString()).build();
    }

    
    
//    overalllocker
    @POST
    @Path("/overalllocker")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response overalllocker( String login_json ) throws ParseException, SQLException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
//        int customer_id =  Integer.parseInt(json.get("customer_id").toString()) ;
        
        JSONObject result_json = new JSONObject();
        // pass the username and password for authentication from database
        this.users = new Users();
        this.bank = new Bank();
        Boolean result = this.users.bankauth(username, password);
        
        if(result){
            
            JSONObject approvals;
            approvals = this.bank.allLockerTableData();
            
            return Response.status(200).entity(approvals.toString()).build();
            
        }else{
            result_json.put("status", "login error");
            return Response.status(200).entity(result_json.toString()).build();
        }
        
//        JSONObject result_json = new JSONObject();
//        result_json.put("auth", result);
//        return Response.status(200).entity(result_json.toString()).build();
    }

    
    
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
            
            if(updatedLockerTable){
                result_json.put("locker", "approved");
                Boolean updatedAddLockerRequest = this.bank.updatedAddLockerRequestTable(customer_id);
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