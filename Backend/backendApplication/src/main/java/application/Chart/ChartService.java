package application.Chart;

import java.awt.*;
import application.Users.User;
import application.Users.UserRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    public ChartService(UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> getWorkoutsForExercise(String userName, String exerciseName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return workoutRepository.findByUserNameAndExerciseName(userName, exerciseName);
    }

    public JFreeChart getExerciseProgressChart(String userName, String exerciseName) {
        System.out.println(userName);
        System.out.println(exerciseName);
        List<Workout> workouts = getWorkoutsForExercise(userName, exerciseName);
        System.out.println("PLEASE PRINT");
        Map<String, Pair<Double, Integer>> dateWeights = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        for (Workout workout : workouts) {
            if (workout.getDateLogged() != null) {
                String date = sdf.format(workout.getDateLogged());
                double weight = workout.getActualWeight();
                if (dateWeights.containsKey(date)) {
                    Pair<Double, Integer> pair = dateWeights.get(date);
                    dateWeights.put(date, new Pair<>(pair.getKey() + weight, pair.getValue() + 1));
                } else {
                    dateWeights.put(date, new Pair<>(weight, 1));
                }
            }
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Pair<Double, Integer>> entry : dateWeights.entrySet()) {
            double averageWeight = entry.getValue().getKey() / entry.getValue().getValue();
            dataset.addValue(averageWeight, "Weight", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                exerciseName,
                "Date",
                "Weight",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        getLineAndShapeRenderer(chart);
        return chart;
    }

    public static void combineDataset(DefaultCategoryDataset dataset1, DefaultCategoryDataset dataset2) {
        for (int i = 0; i < dataset2.getRowCount(); i++) {
            for (int j = 0; j < dataset2.getColumnCount(); j++) {
                Number existingValue = null;
                if (dataset1.getColumnIndex(dataset2.getColumnKey(j)) != -1) {
                    existingValue = dataset1.getValue(dataset2.getRowKey(i), dataset2.getColumnKey(j));
                }
                Number newValue = dataset2.getValue(i, j);
                if (existingValue != null) {
                    newValue = (existingValue.doubleValue() + newValue.doubleValue()) / 2;
                }
                dataset1.addValue(newValue, dataset2.getRowKey(i), dataset2.getColumnKey(j));
            }
        }
    }

    public static JFreeChart createChart(DefaultCategoryDataset dataset, String exerciseName) {
        JFreeChart chart = ChartFactory.createLineChart(
                exerciseName,
                "Date",
                "Weight",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        getLineAndShapeRenderer(chart);
        return chart;
    }

    private static void getLineAndShapeRenderer(JFreeChart chart) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        chart.setBackgroundPaint(Color.white);
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.MAGENTA);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        plot.setRenderer(renderer);
    }

    public static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
