# PetStore API Testing with Rest Assured

This project demonstrates **API automation testing** using **Rest Assured** with Java and TestNG in **Eclipse IDE**. The project is designed to test the User module of the [Swagger PetStore API](https://petstore.swagger.io/  - Only for User API )

##  Project Structure

```
src/test/java
│
├── api.endpoints
│   ├── Routes.java               # Contains API URL paths for CRUD operations
│   ├── UserEndpoints.java        # Directly accesses URLs from Routes.java
│   └── UserEndpoints2.java       # Accesses URLs from a properties file using ResourceBundle
├── api.payloads
│   └── User.java               #POJO class-for creating and handling user data
├── api.test
│   ├── DDtest.java            # for Data Driven test 
│   ├── UserTestsjava          # create,get,update,delete users
│   └──  UserTests2.java
├── api.utilities
│   ├── DataProviders.java    # to support Data-Driven Testing (DDT) in TestNG by supplying  external test data from an Excel file to test methods.
│   ├── ExtentReportManager.java   #To generate a professional Extent HTML report that tracks the status of each test case run during the automation execution.
│   └──  XLUtility.java      # To provide reusable methods for reading and writing Excel data so that test cases can be executed with dynamic input from an Excel file
```

##  Features

- CRUD operations for User module:
  - Create User (POST)
  - Read User (GET)
  - Update User (PUT)
  - Delete User (DELETE)
- URL management via:
  - `Routes.java` (direct string access)
  - `Routes.properties` (external configuration)
- Uses `RestAssured`, `TestNG`, and `ResourceBundle`.

##  How to Run

1. Clone this repo:
   ```bash
   git clone https://github.com/yourusername/petstore-api-testing.git
   ```

2. Open in Eclipse.

3. Add dependencies (Rest Assured, TestNG) using Maven or manually.

4. Run TestNG test cases for each operation.

##  Tools & Technologies

- Java
- Rest Assured
- TestNG
- Eclipse
- Maven 
- Swagger PetStore API

##  Reference

[Swagger PetStore API Documentation](https://petstore.swagger.io/)

---

 *Created as part of learning API testing using Rest Assured.*
