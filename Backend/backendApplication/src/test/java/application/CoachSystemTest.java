package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import application.Coach.Coach;
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
public class CoachSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getCoachByUserNameTest () {
        Response response = RestAssured.given()
                .when()
                .get("/coaches/test3");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("test3", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void coachJoinAndLeaveTeamTest() {
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                put("/coach/joinTeam/test3/Team/2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("Team", returnObj.get("teamName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // leave team
        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                put("/coach/leaveTeam/test3");

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
    public void assignWorkoutTest () {
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "athlete2");
            payload.put("workoutName", "Athlete Test Workout");
            payload.put("exerciseName", "Push Ups");
            payload.put("weight", 100);
            payload.put("reps", 5);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/coaches/assignWorkout");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                get("/athletes/getWorkout/athlete2/Athlete Test Workout");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("Athlete Test Workout", returnObj.get("workoutName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCoachConstructor() {
        String userName = "testUser";
        String teamName = "testTeam";
        Coach coach = new Coach(userName, teamName);
        assertEquals(userName, coach.getUserName());
        assertEquals(teamName, coach.getTeamName());
    }

}