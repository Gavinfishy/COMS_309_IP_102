package application.Workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, String> {
    Workout findByWorkoutName(String workoutName);
    List<Workout> findByUserNameAndExerciseName(String userName, String exerciseName);
    boolean existsWorkoutByWorkoutName(String workoutName);
    @Transactional
    void deleteByWorkoutName(String workoutName);
}
