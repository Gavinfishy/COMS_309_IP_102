package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import application.GymRat.GymRat;
import application.Users.User;
import application.Workout.Workout;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class GymRatSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getGymRatByUserNameTest () {
        Response response = RestAssured.given()
                .when()
                .get("/gymRats/test");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("test", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gymRatSetHeightWeightTest () {
        Random rand = new Random();
        int h = rand.nextInt();
        int w = rand.nextInt();

        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "test");
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
                put("/gymRats/setHeightWeight");

        Response response = RestAssured.given()
                .when()
                .get("/gymRats/test");

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
                put("/gymRats/setHeightWeight");

        statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        returnString = response.getBody().asString();

        assertEquals("", returnString);
    }

    @Test
    public void getGymRatWorkoutHistoryTest () {
        Response response = RestAssured.given()
                .when()
                .get("/gymRats/GymRatTest/workoutHistory");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("GymRatTest workout 1", returnObj.get("workoutName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gymRatLogWorkoutTest () {
        Random rand = new Random();
        int reps = rand.nextInt();
        int weight = rand.nextInt();

        JSONObject payload = new JSONObject();
        try {
            payload.put("workoutName", "GymRatTest workout 1");
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
                put("/gymRats/logWorkout");

        Response response = RestAssured.given()
                .when()
                .get("/gymRats/GymRatTest/workoutHistory");

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
    public void gymRatCreateExerciseTest () {
        JSONObject payload = new JSONObject();
        try {
            payload.put("exerciseName", "dips");
            payload.put("description", "dip down");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/exercises");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("success", returnObj.get("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                when().
                delete("/exercises/dips");
    }

    @Test
    public void getWorkoutHistoryByExerciseTest() {
        String userName = "BigPookie";
        String exerciseName = "Dumbbell Curls";
        ResponseEntity<String> response = restTemplate.getForEntity("/gymRats/" + userName + "/workoutHistory/" + exerciseName, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getWorkoutHistoryByExerciseErrorTest() {
        String userName = "nonExistentUser";
        String exerciseName = "fakeTestExercise";
        ResponseEntity<String> response = restTemplate.getForEntity("/gymRats/" + userName + "/workoutHistory/" + exerciseName, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getWorkoutHistoryErrorTest() {
        String userName = "nonExistentUser";
        ResponseEntity<String> response = restTemplate.getForEntity("/gymRats/" + userName + "/workoutHistory", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void assignWorkoutTest () {
        JSONObject payload = new JSONObject();
        try {
            payload.put("userName", "BigPookie");
            payload.put("workoutName", "TestWorkout");
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
                post("/gymRats/assignWorkout");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                get("/gymRats/getWorkout/BigPookie/TestWorkout");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("TestWorkout", returnObj.get("workoutName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
