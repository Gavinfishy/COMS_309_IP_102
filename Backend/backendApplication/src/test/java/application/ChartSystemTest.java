package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import application.Chart.ChartService.Pair;
import application.Athlete.Athlete;
import application.Athlete.AthleteController;
import application.Athlete.AthleteRepository;
import application.Chart.ChartController;
import application.Chart.ChartService;
import application.Coach.Coach;
import application.Coach.CoachRepository;
import application.Exercise.Exercise;
import application.Exercise.ExerciseRepository;
import application.Team.Team;
import application.Team.TeamRepository;
import application.Users.User;
import application.Users.UserRepository;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import io.restassured.response.Response;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class ChartSystemTest {
    @LocalServerPort
    int port;

    @Autowired
    private ChartController chartController;

    @MockBean
    private ChartService chartService;

    @MockBean
    private AthleteRepository athleteRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private CoachRepository coachRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @MockBean
    private ExerciseRepository exerciseRepository;

    @Mock
    private AthleteController athleteController;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }


    @Test
    public void testCreateChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String exerciseName = "Test Exercise";
        JFreeChart chart = ChartService.createChart(dataset, exerciseName);
        assertNotNull(chart);
        assertEquals(exerciseName, chart.getTitle().getText());
        assertTrue(chart.getPlot() instanceof CategoryPlot);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        assertTrue(plot.getRenderer() instanceof LineAndShapeRenderer);
    }

    @Test
    public void testGetWorkoutsForExercise() {
        String userName = "testUser";
        String exerciseName = "testExercise";
        Workout workout1 = new Workout();
        Workout workout2 = new Workout();
        List<Workout> expectedWorkouts = Arrays.asList(workout1, workout2);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        WorkoutRepository workoutRepository = Mockito.mock(WorkoutRepository.class);
        User user = new User();
        Mockito.when(userRepository.findByUserName(userName)).thenReturn(user);
        Mockito.when(workoutRepository.findByUserNameAndExerciseName(userName, exerciseName)).thenReturn(expectedWorkouts);
        ChartService chartService = new ChartService(userRepository, workoutRepository);
        List<Workout> actualWorkouts = chartService.getWorkoutsForExercise(userName, exerciseName);
        assertEquals(expectedWorkouts, actualWorkouts);
    }


    @Test
    public void testPair() {
        String date = "01/01/2023";
        Double totalWeight = 50.0;
        Integer count = 1;
        Pair<String, Pair<Double, Integer>> pair = new Pair<>(date, new Pair<>(totalWeight, count));
        assertEquals(date, pair.getKey());
        assertEquals(totalWeight, pair.getValue().getKey());
        assertEquals(count, pair.getValue().getValue());
    }


    @Test
    public void getChartAthleteTest() {
        String userName = "athlete3";
        String exerciseName = "Dumbbell Curls";
        Athlete athlete = new Athlete(userName);
        User user = new User();
        user.setAccountType("Athlete");
        user.setUserName(userName);
        userRepository.save(user);
        athleteRepository.save(athlete);
        Mockito.when(athleteRepository.findByUserName(userName)).thenReturn(athlete);
        Mockito.when(userRepository.findByUserName(userName)).thenReturn(user);
        JFreeChart chart = new JFreeChart(new CategoryPlot());
        Mockito.when(chartService.getExerciseProgressChart(userName, exerciseName)).thenReturn(chart);
        byte[] result = chartController.getChart(userName, exerciseName, new MockHttpServletResponse());
        Assert.assertNull(result);
    }

//    @Test
//    public void testWorkoutHistory() {
//        // Create and save an Athlete with a WorkoutHistory
//        Athlete athlete = new Athlete("athlete3");
//        Workout workout = new Workout();
//        // Set properties for the workout
//        athlete.getWorkoutHistory().add(workout);
//        athleteRepository.save(athlete);
//
//        // Retrieve the Athlete and check the WorkoutHistory
//        Athlete retrievedAthlete = athleteRepository.findByUserName("athlete3");
//        assertEquals(1, retrievedAthlete.getWorkoutHistory().size());
//        // Add more assertions to check the properties of the workout
//    }


//    @Test
//    public void getChartCoachTest () {
//        String coachUserName = "MasterSnapper";
//        String exerciseName = "Dumbbell Curls";
//        String teamName = "Bucks";
//        int coachInvite = 4321;
//        int athleteInvite = 1234;
//        Team team = new Team(teamName);
//        team.setCoachInviteId(coachInvite);
//        team.setAthleteInviteId(athleteInvite);
//        User user = new User();
//        User user1 = new User();
//        User user2 = new User();
//        user.setUserName(coachUserName);
//        user.setAccountType("Coach");
//        Coach coach = new Coach(coachUserName);
//        coach.setTeamName(team.getTeamName());
//        coachRepository.save(coach);
//        userRepository.save(user);
//        Athlete athlete1 = new Athlete("athlete3");
//        Athlete athlete2 = new Athlete("brother3");
//        athlete1.setTeamName(team.getTeamName());
//        athlete2.setTeamName(team.getTeamName());
//        user1.setUserName(athlete1.getUserName());
//        user1.setAccountType("Athlete");
//        user2.setUserName(athlete2.getUserName());
//        user2.setAccountType("Athlete");
//        userRepository.save(user1);
//        userRepository.save(user2);
//        Exercise exercise = new Exercise(exerciseName);
//        exerciseRepository.save(exercise);
//        Workout workout1 = new Workout();
//        workout1.setUserName(athlete1.getUserName());
//        workout1.setWorkoutName("Workout1");
//        workout1.setExerciseName("Dumbbell Curls");
//        workout1.setActualReps(10);
//        workout1.setActualWeight(50);
//        workout1.setDateLogged();
//        Workout workout2 = new Workout();
//        workout2.setUserName(athlete2.getUserName());
//        workout2.setWorkoutName("Workout2");
//        workout2.setExerciseName("Dumbbell Curls");
//        workout2.setActualReps(12);
//        workout2.setActualWeight(55);
//        workout2.setDateLogged();
//        workoutRepository.save(workout1);
//        workoutRepository.save(workout2);
//        user1.getWorkoutHistory().add(workout1);
//        user2.getWorkoutHistory().add(workout2);
//
////        System.out.println("Workout History for Athlete1: " + user1.getWorkoutHistory());
////        System.out.println("Workout History for Athlete2: " + user2.getWorkoutHistory());
////        Assert.assertEquals(1, user1.getWorkoutHistory().size());
////        Assert.assertEquals(1, user2.getWorkoutHistory().size());
////        Assert.assertTrue(user1.getWorkoutHistory().contains(workout1));
////        Assert.assertTrue(user2.getWorkoutHistory().contains(workout2));
//
//        team.getAthletes().add(athlete1);
//        team.getAthletes().add(athlete2);
//        team.getCoaches().add(coach);
//        athleteRepository.save(athlete1);
//        athleteRepository.save(athlete2);
//        teamRepository.save(team);
//        JFreeChart chart1 = new JFreeChart(new CategoryPlot());
//        JFreeChart chart2 = new JFreeChart(new CategoryPlot());
//        Mockito.when(coachRepository.findByUserName(coach.getUserName())).thenReturn(coach);
//        Mockito.when(teamRepository.findByTeamName(coach.getTeamName())).thenReturn(team);
////        Mockito.doReturn(chart1).when(chartService).getExerciseProgressChart(athlete1.getUserName(), exercise.getExerciseName());
////        Mockito.when(chartService.getExerciseProgressChart(athlete1.getUserName(), exercise.getExerciseName())).thenReturn(chart1);
////        Mockito.when(chartService.getExerciseProgressChart(athlete2.getUserName(), exercise.getExerciseName())).thenReturn(chart2);
//        Mockito.when(userRepository.findByUserName(coachUserName)).thenReturn(user);
//        Mockito.when(userRepository.findByUserName(athlete1.getUserName())).thenReturn(user1);
//        Mockito.when(userRepository.findByUserName(athlete2.getUserName())).thenReturn(user2);
//        byte[] result = chartController.getChart(coach.getUserName(), exercise.getExerciseName(), new MockHttpServletResponse());
//        ArgumentCaptor<DefaultCategoryDataset> datasetCaptor = ArgumentCaptor.forClass(DefaultCategoryDataset.class);
//        Mockito.verify(chartService, Mockito.times(2)).combineDataset(datasetCaptor.capture(), Mockito.any(DefaultCategoryDataset.class));
//        Mockito.verify(chartService).createChart(datasetCaptor.capture(), Mockito.eq(exercise.getExerciseName()));
//        Assert.assertNull(result);
//    }

}
