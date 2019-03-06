/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.api;

/**
 *
 * @author Deepak
 */
import java.util.ArrayList;
import java.util.List; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;  
import org.json.simple.JSONObject;
@Path("/service") 

public class TestService {  
   @GET 
   @Path("/users") 
   @Produces(MediaType.APPLICATION_XML) 
   public List<User> getUsers(){ 
       UserDao userDao = new UserDao();  
       return userDao.getAllUsers(); 
   } 
   @GET 
   @Path("/test") 
   @Produces(MediaType.APPLICATION_JSON) 
   public User test(){
       User u = new User();
       u.setId(1212);
       u.setName("s");
       
       return u;
   } 
}