package application;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class UserSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createGymRatUserTest () {
        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                delete("/users/test");

        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test");
            payload.put("password", "password");
            payload.put("firstName", "firstName");
            payload.put("lastName", "lastName");
            payload.put("accountType", "Gym Rat");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("False", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAthleteUserTest () {
        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                delete("/users/test2");

        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test2");
            payload.put("password", "password");
            payload.put("firstName", "firstName");
            payload.put("lastName", "lastName");
            payload.put("accountType", "Athlete");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("False", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCoachUserTest () {
        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                delete("/users/test3");

        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test3");
            payload.put("password", "password");
            payload.put("firstName", "firstName");
            payload.put("lastName", "lastName");
            payload.put("accountType", "Coach");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("False", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUserTest () {
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test3");
            payload.put("password", "password");
            payload.put("firstName", "test");
            payload.put("lastName", "tester");
            payload.put("accountType", "Coach");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(payload.toString()).
                when().
                put("/users");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("test", returnObj.get("firstName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // test the null
        payload = new JSONObject();
        try {
            payload.put("userName", "nothing");
            payload.put("password", "password");
            payload.put("firstName", "test");
            payload.put("lastName", "tester");
            payload.put("accountType", "Coach");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(payload.toString()).
                when().
                put("/users");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();

        assertEquals("", returnString);
    }

    @Test
    public void userLoginTest () {
        // Gym Rat
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test");
            payload.put("password", "password");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Athlete
        payload = new JSONObject();
        try {
            payload.put("userName", "test2");
            payload.put("password", "password");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users/login");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Coach
        payload = new JSONObject();
        try {
            payload.put("userName", "test3");
            payload.put("password", "password");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/users/login");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void userLoginTest_Invalid() {
//        // Invalid User
//        JSONObject payload = new JSONObject();
//        try {
//            payload.put("userName", "invalidUser");
//            payload.put("password", "invalidPassword");
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        Response response = RestAssured.given().
//                header("Content-Type", "application/json").
//                header("charset","utf-8").
//                body(payload.toString()).
//                when().
//                post("/users/login");
//
//        int statusCode = response.getStatusCode();
//        assertEquals(200, statusCode);
//
//        String returnString = response.getBody().asString();
//        try {
//            JSONObject returnObj = new JSONObject(returnString);
//            assertEquals("False", returnObj.get("isValid"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }



}
