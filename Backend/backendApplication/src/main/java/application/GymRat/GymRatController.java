package application.GymRat;

import application.Chart.ChartService;
import application.Exercise.Exercise;
import application.Exercise.ExerciseRepository;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
public class GymRatController {
    @Autowired
    GymRatRepository gymRatRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    ChartService chartService;
    GymRatService gymRatService;

    private final String success = "{\"message\":\"success\"}";

    private final String failure = "{\"message\":\"failure\"}";

//    @GetMapping(path = "/gymRats")
//    List<GymRat> getAllGymRats() {
//        return gymRatRepository.findAll();
//    }

    @GetMapping(path = "/gymRats/{userName}")
    GymRat getGymRatByUserName(@PathVariable String userName) {
        return gymRatRepository.findByUserName(userName);
    }

    @GetMapping("/gymRats/{userName}/workoutHistory")
    public List<Workout> getWorkoutHistory(@PathVariable String userName) {
        GymRat gymRat = gymRatRepository.findByUserName(userName);
        if (gymRat != null) {
            return gymRat.getWorkoutHistory();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GymRat not found");
        }
    }

    @GetMapping("/gymRats/{userName}/workoutHistory/{exerciseName}")
    public List<Workout> getWorkoutHistoryByExercise(@PathVariable String userName, @PathVariable String exerciseName) {
        GymRat gymRat = gymRatRepository.findByUserName(userName);
        if (gymRat != null) {
            return workoutRepository.findByUserNameAndExerciseName(userName, exerciseName);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GymRat not found");
        }
    }

//    @PostMapping(path = "/gymRats")
//    String createGymRat(@RequestBody GymRat gymRat) {
//        if (gymRat == null) {
//            return failure;
//        }
//        gymRatRepository.save(gymRat);
//        return success;
//    }

    @PostMapping("/gymRats/assignWorkout")
    public void assignWorkout(@RequestBody Workout workout) {
        GymRat gymRat = gymRatRepository.findByUserName(workout.getUserName());
        if (gymRat != null) {
            workoutRepository.save(workout);
        }
    }

    @PutMapping("/gymRats/logWorkout")
    public void logWorkout(@RequestBody Workout workoutInput) {
        Workout workout = workoutRepository.findByWorkoutName(workoutInput.getWorkoutName());
        if (workout != null) {
            workout.logWorkout(workoutInput.getActualReps(), workoutInput.getActualWeight());
            workoutRepository.save(workout);
        }
    }

    @PutMapping(path = "/gymRats/setHeightWeight")
    public GymRat setHeightWeight(@RequestBody GymRat request) {
        String userName = request.getUserName();
        if (userName == null) {
            return null;
        }
        GymRat gymRat = gymRatRepository.findByUserName(userName);
        gymRat.setWeight(request.getWeight());
        gymRat.setHeight(request.getHeight());
        gymRatRepository.save(gymRat);
        return gymRatRepository.findByUserName(userName);
    }

//    @DeleteMapping(path = "/gymRats/{userName}")
//    String deleteGymRat(@PathVariable String userName) {
//        gymRatRepository.deleteByUserName(userName);
//        return success;
//    }

    @GetMapping(path = "/gymRats/getWorkout/{userName}/{workoutName}")
    Workout getWorkout(@PathVariable String userName, @PathVariable String workoutName) {
       GymRat gymRat = gymRatRepository.findByUserName(userName);
       return gymRat.getWorkout(workoutName);
    }

}
