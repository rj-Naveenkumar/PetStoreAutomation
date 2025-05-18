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
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;
	import org.testng.Assert;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import com.github.javafaker.Faker;

	import api.endpoints.UserEndpoints2;
	import api.payload.User;
	import io.restassured.response.Response;

	import static org.testng.Assert.*;

	/**
	 * End‑to‑end API tests for the Pet‑store user workflow.
	 * <p>
	 *  Workflow order:
	 *  <ol>
	 *      <li>Create user</li>
	 *      <li>Read user</li>
	 *      <li>Update user</li>
	 *      <li>Delete user</li>
	 *  </ol>
	 *  Each step <em>depends</em> on the previous one so that, if creation fails, the rest are skipped.
	 */
public class UserTests3chatGPT {

	    private final Faker faker = new Faker();
	    private User userPayload;
	    private Logger logger;

	    /**
	     * Build the user payload once for the entire class run.
	     */
	    @BeforeClass
	    public void setup() {
	        userPayload = new User();
	        userPayload.setId(faker.idNumber().hashCode());
	        userPayload.setUsername(faker.name().username());
	        userPayload.setFirstName(faker.name().firstName());
	        userPayload.setLastName(faker.name().lastName());
	        userPayload.setEmail(faker.internet().emailAddress());
	        userPayload.setPassword(faker.internet().password());
	        userPayload.setPhone(faker.phoneNumber().cellPhone());

	        logger = LogManager.getLogger(this.getClass());
	        logger.info("Generated test user: {}", userPayload.getUsername());
	    }

	    /**
	     * Create the user – must succeed for the rest of the suite to run.
	     */
	    @Test(priority = 1)
	    public void testPostUser() {
	        logger.info("⬆️  Creating user");
	        Response res = UserEndpoints2.createUser(userPayload);
	        res.then().log().all();
	        assertEquals(res.getStatusCode(), 200, "User creation failed");
	    }

	    /**
	     * Fetch the user we just created.  Includes a small retry loop because the
	     * public swagger demo server is eventually consistent.
	     */
	    @Test(priority = 2, dependsOnMethods = {"testPostUser"})
	    public void testGetUserByName() throws InterruptedException {
	        logger.info("👀 Reading user info for {}", userPayload.getUsername());

	        Response res = null;
	        int attempts = 0;
	        while (attempts < 3) {
	            res = UserEndpoints2.readUser(userPayload.getUsername());
	            if (res.getStatusCode() == 200) break;
	            logger.warn("Attempt {} – user not yet available (status {}). Retrying …", attempts + 1, res.getStatusCode());
	            Thread.sleep(1000);
	            attempts++;
	        }

	        res.then().log().all();
	        assertEquals(res.getStatusCode(), 200, "Failed to retrieve user info");
	    }

	    /**
	     * Update a couple of fields and verify they changed on the server.
	     */
	    @Test(priority = 3, dependsOnMethods = {"testGetUserByName"})
	    public void testUpdateUser() {
	        logger.info("✏️  Updating user {}");

	        userPayload.setFirstName(faker.name().firstName());
	        userPayload.setLastName(faker.name().lastName());
	        userPayload.setEmail(faker.internet().emailAddress());

	        Response res = UserEndpoints2.updateUser(userPayload.getUsername(), userPayload);
	        res.then().log().body().statusCode(200);

	        // pull fresh copy and verify
	        Response updated = UserEndpoints2.readUser(userPayload.getUsername());
	        updated.then().log().all();

	        assertEquals(updated.getStatusCode(), 200, "Failed to get updated user");
	        assertEquals(updated.jsonPath().getString("firstName"), userPayload.getFirstName(), "firstName not updated");
	        assertEquals(updated.jsonPath().getString("email"), userPayload.getEmail(), "email not updated");
	    }

	    /**
	     * Delete the user and verify status.  Some swagger servers return an empty
	     * body for DELETE – handle that gracefully.
	     */
	    @Test(priority = 4, dependsOnMethods = {"testUpdateUser"})
	    public void testDeleteUserByName() throws InterruptedException {
	        logger.info("🗑️  Deleting user {}");

	        Response res = UserEndpoints2.deleteUser(userPayload.getUsername());
	        String body = res.getBody().asString();

	        if (body == null || body.isEmpty()) {
	            logger.info("Delete returned empty body – treating status {} as success", res.getStatusCode());
	            assertEquals(res.getStatusCode(), 200, "Delete user failed");
	            return;
	        }

	        String message = res.jsonPath().getString("message");
	        if ("User not found".equalsIgnoreCase(message)) {
	            fail("User was not found during deletion – possibly already deleted.");
	        }
	        assertEquals(res.getStatusCode(), 200, "Delete user failed");

	        // final consistency check – user should now return 404
	        Response lookup = UserEndpoints2.readUser(userPayload.getUsername());
	        lookup.then().log().all();
	        assertEquals(lookup.getStatusCode(), 404, "User still exists after deletion");
	    }
	}

	