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
import ignou.miniproject.model.Users;
import ignou.miniproject.model.Bank;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
 
import javax.ws.rs.POST;
//import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
@Path("/customer2bank")
public class BankService {
    
    private Users users=null;
    private Bank bank=null;
       
    @POST
    @Path("/locker_request")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response locker_Request( String login_json ) throws ParseException, SQLException {
        
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
            is_requested = this.bank.locker_request(username);
            
            result_json.put("auth", is_requested);
            return Response.status(200).entity(result_json.toString()).build();
        }else{
            result_json.put("status", result);
            return Response.status(200).entity(result_json.toString()).build();
        }

//        return Response.status(200)
//                .entity((login_json))
//                .build();
        
        
            }
    
    /*
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJSONData(
        @FormParam("name") String name
    ){
        
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("code","1010011");
        json.put("age",21);
//        return String.valueOf(json);
        return Response.status(200)
            .entity("got"+name)
            .build();

    }
     */
     
}