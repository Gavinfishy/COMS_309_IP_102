package application.Coach;

import application.Athlete.Athlete;
import application.Exercise.Exercise;
import application.Team.Team;
import application.Users.User;
import application.Workout.Workout;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Coach {
    @Id
    private String userName;
    private String teamName;
    @ManyToOne
    @JoinColumn(name = "teamName", insertable = false, updatable = false)
    private Team team;
    @OneToOne(mappedBy = "coach")
    private User user;

    public Coach(String userName, String teamName) {
        this.userName = userName;
        this.teamName = teamName;
    }

    public Coach(String userName) {
        this.userName = userName;
    }

    public Coach() {}

    public String getUserName() {
        return userName;
    }

    public String getTeamName() {
        if (teamName == null) {
            return "No team";
        }
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
