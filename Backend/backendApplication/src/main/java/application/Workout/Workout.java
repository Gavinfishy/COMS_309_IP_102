package application.Workout;

import application.Users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Workout {
    @Id
    private String workoutName;
    private String userName;
    private String exerciseName;
    private int reps;
    private int actualReps;
    private int weight;
    private int actualWeight;
    private Date dateCreated;
    private Date dateLogged;

//    public Workout(String workoutName, String userName, String exerciseName, int reps, int weight) {
//        this(workoutName, userName, exerciseName, reps, weight, 0, 0);
//        this.dateCreated = new Date();
//    }
//
//    public Workout(String workoutName, String userName, String exerciseName, int reps, int weight, int actualReps, int actualWeight) {
//        this.workoutName = workoutName;
//        this.userName = userName;
//        this.exerciseName = exerciseName;
//        this.reps = reps;
//        this.weight = weight;
//        this.actualReps = actualReps;
//        this.actualWeight = actualWeight;
//        this.dateCreated = new Date();
//    }

    public void logWorkout(int actualReps, int actualWeight) {
        setActualReps(actualReps);
        setActualWeight(actualWeight);
        dateLogged = new Date();
    }

    public void setActualReps(int actualReps) {
        this.actualReps = actualReps;
    }

    public void setActualWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Workout() {
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getUserName() {
        return userName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public int getActualReps() {
        return actualReps;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public Date setDateLogged() {
        dateLogged = new Date();
        return dateLogged;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "workoutName='" + workoutName + '\'' +
                ", userName='" + userName + '\'' +
                ", exerciseName='" + exerciseName + '\'' +
                ", actualReps=" + actualReps +
                ", actualWeight=" + actualWeight +
                '}';
    }
}
