package application.GymRat;

import application.Exercise.Exercise;
import application.Exercise.ExerciseRepository;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymRatService {
    @Autowired
    GymRatRepository gymRatRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    WorkoutRepository workoutRepository;

//    public void assignWorkout(String userName, String exerciseName, int reps, int weight) {
//        GymRat gymRat = gymRatRepository.findByUserName(userName);
//        Exercise exercise = exerciseRepository.findByExerciseName(exerciseName);
//
//        if (gymRat != null && exercise != null) {
//            Workout workout = new Workout(userName + " " + exerciseName, gymRat, exercise, reps, weight);
//            workoutRepository.save(workout);
//            gymRat.assignWorkout(workout);
//            gymRatRepository.save(gymRat);
//        }
//    }
//
//    public void deleteWorkout(String userName, String workoutName) {
//        GymRat gymRat = gymRatRepository.findByUserName(userName);
//        Workout workout = workoutRepository.findByWorkoutName(workoutName);
//
//        if (gymRat != null && workout != null) {
//            gymRat.getWorkoutHistory().remove(workout);
//            workoutRepository.delete(workout);
//            gymRatRepository.save(gymRat);
//        }
//    }

}
