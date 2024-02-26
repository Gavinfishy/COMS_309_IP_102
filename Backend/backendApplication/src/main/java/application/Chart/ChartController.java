package application.Chart;

import application.Athlete.Athlete;
import application.Coach.Coach;
import application.Coach.CoachRepository;
import application.Team.Team;
import application.Team.TeamRepository;
import application.Users.User;
import application.Users.UserRepository;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.jfree.chart.ChartUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
public class ChartController {
    @Autowired
    private ChartService chartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CoachRepository coachRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping(value = "/charts/{userName}/{exerciseName}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getChart(@PathVariable String userName, @PathVariable String exerciseName, HttpServletResponse response) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            JFreeChart chart;
            if (user.getAccountType().equals("Coach")) {
                // Coach
                Coach coach = coachRepository.findByUserName(userName);
                Team team = teamRepository.findByTeamName(coach.getTeamName());
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Athlete athlete : team.getAthletes()) {
                    JFreeChart athleteChart = chartService.getExerciseProgressChart(athlete.getUserName(), exerciseName);
                    CategoryPlot plot = (CategoryPlot) athleteChart.getPlot();
                    ChartService.combineDataset(dataset, (DefaultCategoryDataset) plot.getDataset());

                }
                chart = ChartService.createChart(dataset, exerciseName);
            }
            else {
                // Athlete or gymRat
                chart = chartService.getExerciseProgressChart(userName, exerciseName);
            }
            OutputStream out = response.getOutputStream();
            ChartUtils.writeChartAsPNG(out, chart, 390, 450);
            return null;
        } catch (Exception e) {

            throw new RuntimeException("There was an error while generating the chart.", e);
        }
    }
}
