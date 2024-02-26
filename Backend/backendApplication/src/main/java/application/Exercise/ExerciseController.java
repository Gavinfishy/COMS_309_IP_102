package application.Exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    ExerciseRepository exerciseRepository;

    private final String success = "{\"message\":\"success\"}";

    private final String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/exercises")
    List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @GetMapping(path = "/exercises/{exerciseName}")
    Exercise getExerciseByName(@PathVariable String exerciseName) {
        return exerciseRepository.findByExerciseName(exerciseName);
    }

    @PostMapping(path = "/exercises")
    String createExercise(@RequestBody Exercise exercise) {
        if (exercise == null) {
            return failure;
        }
        exerciseRepository.save(exercise);
        return success;
    }

    @DeleteMapping(path = "/exercises/{exerciseName}")
    String deleteExercise(@PathVariable String exerciseName) {
        exerciseRepository.deleteByExerciseName(exerciseName);
        return success;
    }
}

