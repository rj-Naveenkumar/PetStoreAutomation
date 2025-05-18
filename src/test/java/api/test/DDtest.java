package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDtest {

	// post users
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userID,String userName,String fname,String lname,String userEmail,String pwd,String ph)
	{
		//need to create a payload - pojo
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		//creating a post request 
		Response res = UserEndpoints.createUser(userPayload);
		Assert.assertEquals(res.getStatusCode(),200);
	
		
	}
	//delete users
	@Test(priority=2,dataProvider="UserName",dataProviderClass=DataProviders.class)
	public void testDeleteUserbyName(String userName)
	{ 
		// deleting user by passing the user name 
		
		Response res = UserEndpoints.deleteUser(userName);
		int statcode=res.getStatusCode();
		
		Assert.assertTrue(statcode== 200 || statcode== 404, "Expected status code 200 or 404 but found : " +statcode);
	}
}

