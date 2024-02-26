package application.Exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findByExerciseName(String exerciseName);

    @Transactional
    void deleteByExerciseName(String exerciseName);
}
