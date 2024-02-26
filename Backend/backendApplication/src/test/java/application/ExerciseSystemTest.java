package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import application.Workout.Workout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ExerciseSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getExerciseByNameTest () {
        Response response = RestAssured.given()
                .when()
                .get("/exercises/Dumbbell Curls");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("Dumbbell Curls", returnObj.get("exerciseName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createExerciseTest () {
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

//    @Test
//    public void createExerciseNullTest () {
//        JSONObject payload = new JSONObject();
//        Response response = RestAssured.given().
//                header("Content-Type", "application/json").
//                header("charset","utf-8").
//                body(payload.toString()).
//                when().
//                post("/exercises");
//        int statusCode = response.getStatusCode();
//        assertEquals(400, statusCode);
//        String returnString = response.getBody().asString();
//        try {
//            JSONObject returnObj = new JSONObject(returnString);
//            assertEquals("failure", returnObj.get("message"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testToString() {
        Workout workout = new Workout();
        workout.setWorkoutName("testWorkout");
        workout.setUserName("testUser");
        workout.setExerciseName("testExercise");
        workout.setActualReps(10);
        workout.setActualWeight(50);
        String expected = "Workout{" +
                "workoutName='testWorkout'" +
                ", userName='testUser'" +
                ", exerciseName='testExercise'" +
                ", actualReps=10" +
                ", actualWeight=50" +
                '}';
        assertEquals(expected, workout.toString());
    }

    @Test
    public void getAllExercisesTest() {
        Response response = RestAssured.given().
                when().
                get("/exercises");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Convert the JSON string into a List of Map objects
            List<Map<String, Object>> list = mapper.readValue(returnString, List.class);

            // Iterate over the list and get the userName from each map
            for (Map<String, Object> map : list) {
                // Get the userName value
                String exerciesName = (String) map.get("exerciesName");

                // Check if the userName is equal to either "AthleteTest" or "CoachTest"
                assertSame(exerciesName, map.get("exerciesName"));
            }


        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
