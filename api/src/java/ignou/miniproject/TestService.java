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
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getJSONData(){
        
        JSONObject json = new JSONObject();
        json.put("name","kajal");
        json.put("code","1010011");
        json.put("age",21);
// 
        return String.valueOf(json);
//        return "hgey ";
    }
     
     
}