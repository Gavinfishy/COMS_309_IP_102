package application;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class AthleteSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    @Test
    public void getAthleteByUserNameTest () {
        Response response = RestAssured.given()
                .when()
                .get("/athletes/test2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("test2", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void athleteSetHeightWeightTest () {
        Random rand = new Random();
        int h = rand.nextInt();
        int w = rand.nextInt();

        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test2");
            payload.put("height", h);
            payload.put("weight", w);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                put("/athletes/setHeightWeight");

        Response response = RestAssured.given()
                .when()
                .get("/athletes/test2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals(h, returnObj.get("height"));
            assertEquals(w, returnObj.get("weight"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // test for null
        payload = new JSONObject();
        try {
            payload.put("userName", null);
            payload.put("height", 1);
            payload.put("weight", 2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                put("/athletes/setHeightWeight");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();

        assertEquals("", returnString);
    }

    @Test
    public void athleteJoinAndLeaveTeamTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                put("/athlete/joinTeam/test2/Bucks/1234");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("True", returnObj.get("isValid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // leave team
        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                put("/athlete/leaveTeam/test2");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("No team", returnObj.get("teamName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAthleteWorkoutHistoryTest () {
        Response response = RestAssured.given()
                .when()
                .get("/athletes/AthleteTest/workoutHistory");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("AthleteTest workout 1", returnObj.get("workoutName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void athleteLogWorkoutTest () {
        Random rand = new Random();
        int reps = rand.nextInt();
        int weight = rand.nextInt();

        JSONObject payload = new JSONObject();
        try {
            payload.put("workoutName", "AthleteTest workout 1");
            payload.put("actualReps", reps);
            payload.put("actualWeight", weight);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset", "utf-8").
                body(payload.toString()).
                when().
                put("/athletes/logWorkout");

        Response response = RestAssured.given()
                .when()
                .get("/athletes/AthleteTest/workoutHistory");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals(reps, returnObj.get("actualReps"));
            assertEquals(weight, returnObj.get("actualWeight"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWorkoutTest () {
        Response response = RestAssured.given()
                .when()
                .get("/athletes/getWorkout/athlete2/Abs workout - Athlete");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("Push Ups", returnObj.get("exerciseName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}