package application;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
//import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class getChartSystemTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }
    @Test
    public void getChartTest () {
        String username = "MasterSnapper";
        String exerciseName = "Dumbbell Curls";

        Response response = RestAssured.given().
                when().
                get("/charts/MasterSnapper/Dumbbell Curls");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        byte[] imageBytes = response.asByteArray();

        int aSum = 0;
        for (byte b : imageBytes) {
            aSum += b;
        }

        // sum of all the bytes in the expected image is 3718, so if the sum of the actual image is the same
        // then most likely they are the same image
        assertEquals(3718, aSum);
    }


}