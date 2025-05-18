package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class UserTests2 {

	Faker faker;
	User userPayload;
	
	public Logger logger;  // creating a logger variable
	
	// this is the first thing we need to do !
	@BeforeClass  // it will generate the class data
	public void setup()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());		

		
		//To initiate the logs
		logger=LogManager.getLogger(this.getClass()); // this will return the logger
		
		
		
	}
	
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*********creating user************");
		Response res = UserEndpoints2.createUser(userPayload);
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(),200);
	
		logger.info("********user is created********");
		
	}
	
	//how to get the data from the user
	@Test(priority=2)
	public void testGetUserbyName()
	{
		logger.info("********* Reading user info *********");
		
		Response res = UserEndpoints2.readUser(this.userPayload.getUsername());
		res.then().log().all();
		
		Assert.assertEquals(res.getStatusCode(), 200,"Failed to retrieve user info");
		
		logger.info("********* user info is displayed *********");

	}
	
	
	
	
	// Update user name
	@Test(priority=3,dependsOnMethods= {"testPostUser","testGetUserbyName"})
	public void testUpdateuser()
	{
		logger.info("********* updating user *********");

		
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response res = UserEndpoints2.updateUser(this.userPayload.getUsername(), userPayload);
		//validating status code 
		res.then().log().body().statusCode(200);
		
		logger.info("********* user is updated *********");

		
		//same but using assertion
		//Assert.assertEquals(res.getStatusCode(), 200);
		
		//check data after updation
		Response responseAfterUpdation =UserEndpoints2.readUser(this.userPayload.getUsername());
		//Assert.assertEquals(responseAfterUpdation.getStatusCode(), 200);
		responseAfterUpdation.then().log().all();

		    Assert.assertEquals(responseAfterUpdation.getStatusCode(), 200, "Failed to get updated user");
		    Assert.assertEquals(responseAfterUpdation.jsonPath().getString("firstName"), userPayload.getFirstName());
		    Assert.assertEquals(responseAfterUpdation.jsonPath().getString("email"), userPayload.getEmail());
		
	}
	
	@Test(priority=4)
	public void testDeleteUserbyName()
	{
	    logger.info("********* deleting user*********");

	    Response res = UserEndpoints2.deleteUser(this.userPayload.getUsername());
	    String responseBody = res.getBody().asString();

	    if (responseBody == null || responseBody.isEmpty()) {
	        logger.warn("Empty response body after delete. Status code: " + res.getStatusCode());
	        Assert.assertEquals(res.getStatusCode(), 200);
	    } else {
	        String responseMessage = res.jsonPath().getString("message");

	        if (responseMessage.equalsIgnoreCase("User not found")) {
	            logger.warn("User was already deleted or not found: " + userPayload.getUsername());
	            Assert.fail("User was not found during deletion, possibly already deleted.");
	        } else {
	            Assert.assertEquals(res.getStatusCode(), 200);
	            logger.info("********* User deleted successfully: " + userPayload.getUsername() + " *********");
	        }
	    }

	    logger.info("********* user deleted *********");
	}
	
	/*{
		logger.info("********* deleting user*********");

		
		Response res = UserEndpoints2.deleteUser(this.userPayload.getUsername());
		//Assert.assertEquals(res.getStatusCode(), 200);
	
		String responseMessage = res.jsonPath().getString("message");

	    if (responseMessage.equalsIgnoreCase("User not found")) {
	        logger.warn("User was already deleted or not found: " + userPayload.getUsername());
	        Assert.fail("User was not found during deletion, possibly already deleted.");
	    } else {
	        Assert.assertEquals(res.getStatusCode(), 200);
	        logger.info("********* User deleted successfully: " + userPayload.getUsername() + " *********");
	    }
		logger.info("********* user deleted *********");

	}
	*/
	
	
	
	
}
