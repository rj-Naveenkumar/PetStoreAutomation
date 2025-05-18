package api.endpoints;
import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
/*
 UserEndPOint.java  - CRUD methods
  Created for perform Create,Read,Update,Delete requests with the user
 
 */
public class UserEndpoints {
     
	//Implementing create user method
	public static Response createUser(User payload)
	{
		Response res=given()
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(payload)
		   
		.when()
		// here i want access the post_url which ic in the Routes.class
		  .post(Routes.post_url);
		  
		return res;	
	}
	
	//Implementing Read user Method
	public static Response readUser(String username)
	{
		Response res=given()
				// in pathParam("name",value)
		   .pathParam("username",username )
		   
		.when()
		// here i want access the get_url which is in the Routes.class
		  .get(Routes.get_url);
		  
		return res;	
	}
	
	//Implementing update user
	public static Response updateUser(String username,User payload)
	{
		Response res=given()
		   .contentType(ContentType.JSON)
		   .accept(ContentType.JSON)
		   .body(payload)
		   .pathParam("username", username)
		   
		.when()
		// here i want access the post_url which ic in the Routes.class
		  .put(Routes.update_url);
		  
		return res;	
	}
	
	//Implementing delete user
	public static Response deleteUser(String username)
	{
		Response res=given()
		   .pathParam("username", username)
		   
		.when()
		// here i want access the post_url which ic in the Routes.class
		  .delete(Routes.delete_url);
		  
		return res;	
	}
	
	
	
	
}
