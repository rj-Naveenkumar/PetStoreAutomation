package api.endpoints;
import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
/*
 UserEndPOint.java  - CRUD methods
  Created for perform Create,Read,Update,Delete requests with the user
 
 */
public class UserEndpoints2 {
     
	//created for getting the URL's from properties file
	static ResourceBundle getURL() // make this static to access directly access from userEndpoints2
	{
		//ResourceBundle is the class name  
		ResourceBundle routes = ResourceBundle.getBundle("Routes"); // will load the property file
		return routes;		
				
	}
	
	
	
	//Implementing create user method
	public static Response createUser(User payload)
	{
		String post_url = getURL().getString("post_url");  // return the post url from the property file
		
		Response res=given()
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(payload)
		   
		.when()
		// here i want access the post_url which is in the property file
		  .post(post_url);
		  
		return res;	
	}
	
	
	//Implementing Read user Method
	public static Response readUser(String username)
	{
		String get_url = getURL().getString("get_url");  // return the get url from the property file
		
		Response res=given()
				// in pathParam("name",value)
		   .pathParam("username",username )
		   
		.when()
		// here i want access the get_url which is in the Routes.class
		  .get(get_url);
		  
		return res;	
	}
	
	//Implementing update user
	public static Response updateUser(String username,User payload)
	{
		String update_url= getURL().getString("update_url");  // return the update url from the property file
		
		Response res=given()
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(payload)
		   .pathParam("username", username)
		   
		.when()
		// here i want access the post_url which ic in the Routes.class
		  .put(update_url);
		  
		return res;	
	}
	
	//Implementing delete user
	public static Response deleteUser(String username)
	{
		String delete_url = getURL().getString("delete_url");  // return the post url from the property file
		
		Response res=given()
		   .pathParam("username", username)
		   
		.when()
		// here i want access the post_url which ic in the Routes.class
		  .delete(delete_url);
		  
		return res;	
	}
	
	
	
	
}
