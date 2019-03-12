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
 
@Path("/login")
public class LoginService {
    
    private Users users= null;
    
    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(
        @PathParam("username") String username
    ){
        
        return Response.status(200)
                .entity("got"+username)
                .build();
    }
    
    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response consumeJSON( String login_json ) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(login_json);
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        
        // pass the username and password for authentication from database
        this.users = new Users();
        Boolean result = this.users.auth(username, password);
        
        JSONObject result_json = new JSONObject();
        result_json.put("auth", result);
        return Response.status(200).entity(result_json.toString()).build();
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