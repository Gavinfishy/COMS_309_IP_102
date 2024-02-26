package application.Athlete;

import application.Team.Team;
import application.Users.User;
import application.Workout.Workout;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Athlete {
    @Getter
    @Id
    private String userName;
    @Getter
    private int height;
    @Getter
    private int weight;
    private String teamName;
    @ManyToOne
    @JoinColumn(name = "teamName", insertable = false, updatable = false)
    private Team team;
    @OneToOne(mappedBy = "athlete")
    private User user;

//    public Athlete(String userName, String teamName) {
//        this.userName = userName;
//        this.teamName = teamName;
//        this.height = 0;
//        this.weight = 0;
//    }

    public Athlete(String userName) {
        this.userName = userName;
    }

//    public Athlete(String userName, int height, int weight) {
//        this.userName = userName;
//        this.height = height;
//        this.weight = weight;
//    }

    public Athlete() {}


    public String getTeamName() {
        if (teamName == null) {
            return "No team";
        }
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setHeight(int height) {
        this.height = height;
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
