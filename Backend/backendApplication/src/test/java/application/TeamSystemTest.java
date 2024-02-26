package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import application.Team.Team;
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
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class TeamSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createTeamTest () {
        Random rand = new Random();
        int a = rand.nextInt();
        int c = rand.nextInt();
        JSONObject payload = new JSONObject();
        try {
            payload.put("teamName", "testTeam");
            payload.put("athleteInviteId", a);
            payload.put("coachInviteId", c);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(payload.toString()).
                when().
                post("/teams");
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"failure\"}", returnString);
    }

    @Test
    public void getTeamTest() {
        Response response = RestAssured.given().
                when().
                get("/teams/Team");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            assertEquals("Team", returnObj.get("teamName"));
            assertEquals(1, returnObj.get("athleteInviteId"));
            assertEquals(2, returnObj.get("coachInviteId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAthletesOnTeamTest() {
        Response response = RestAssured.given().
                when().
                get("/team/getAthletes/Team");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        System.out.println(returnString);
        try {
            JSONArray returnArr = new JSONArray(returnString);
            System.out.println(returnArr);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("AthleteTest", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCoachesOnTeamTest() {
        Response response = RestAssured.given().
                when().
                get("/team/getCoaches/Team");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        System.out.println(returnString);
        try {
            JSONArray returnArr = new JSONArray(returnString);
            System.out.println(returnArr);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("CoachTest", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUsersOnTeamTest() {
        Response response = RestAssured.given().
                when().
                get("/team/getUsers/Team");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Map<String, Object>> list = mapper.readValue(returnString, List.class);

            for (Map<String, Object> map : list) {
                String userName = (String) map.get("userName");

                assertTrue(userName.equals("AthleteTest") || userName.equals("CoachTest"));
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testAddAnnouncement() {
        Team team = new Team();
        team.addAnnouncement("Announcement 1");
        team.addAnnouncement("Announcement 2");
        team.addAnnouncement("Announcement 3");
        assertEquals(3, team.getAnnouncements().size());
        assertEquals("Announcement 1", team.getAnnouncements().get(0));
        assertEquals("Announcement 2", team.getAnnouncements().get(1));
        assertEquals("Announcement 3", team.getAnnouncements().get(2));
        team.addAnnouncement("Announcement 4");
        assertEquals(3, team.getAnnouncements().size());
        assertEquals("Announcement 2", team.getAnnouncements().get(0));
        assertEquals("Announcement 3", team.getAnnouncements().get(1));
        assertEquals("Announcement 4", team.getAnnouncements().get(2));
    }


}
