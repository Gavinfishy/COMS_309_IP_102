package application.GymRat;

import application.Exercise.Exercise;
import application.Users.User;
import application.Workout.Workout;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GymRat {
    @Id
    private String userName;
    private int height;
    private int weight;
//    @OneToMany(mappedBy = "userName")
//    private List<Workout> workoutHistory;
    @OneToOne(mappedBy = "gymRat")
    private User user;

    public GymRat(String userName) {
        this.userName = userName;
        this.height = 0;
        this.weight = 0;
    }

//    public GymRat(String userName, int height, int weight) {
//        this.userName = userName;
//        this.height = height;
//        this.weight = weight;
//    }

    public GymRat() {

    }

    public String getUserName() {
        return userName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Workout> getWorkoutHistory() {
        return user.getWorkoutHistory();
    }

    public Workout getWorkout(String workoutName) {
        for (Workout workout : this.getWorkoutHistory()) {
            if (workout.getWorkoutName().equals(workoutName)) {
                return workout;
            }
        }
        return null;
    }
}
