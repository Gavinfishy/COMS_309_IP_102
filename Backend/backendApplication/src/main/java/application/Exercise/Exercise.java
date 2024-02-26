package application.Exercise;

import javax.persistence.*;

@Entity
public class Exercise {
    @Id
    private String exerciseName;

    private String description;

//    public Exercise(String exerciseName, String description) {
//        this.exerciseName = exerciseName;
//        this.description = description;
//    }

    public Exercise() {
    }

    public Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getDescription() {
        return description;
    }
}
