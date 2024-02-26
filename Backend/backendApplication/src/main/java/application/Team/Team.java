package application.Team;

import application.Athlete.Athlete;
import application.Coach.Coach;
import application.Users.User;
import application.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Team {
    private int athleteInviteId;
    private int coachInviteId;
    @Id
    private String teamName;
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private Set<Coach> coaches = new HashSet<>();
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private Set<Athlete> athletes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> announcements = new ArrayList<>();


//    public Team(String teamName, Coach coach, int athleteInviteId, int coachInviteId) {
//        this.teamName = teamName;
//        this.athleteInviteId = athleteInviteId;
//        this.coachInviteId = coachInviteId;
//    }

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team() {
    }

    public void addAnnouncement(String announcement) {
        this.announcements.add(announcement);
        if(this.announcements.size() > 3) {
            this.announcements.remove(0);
        }
    }

    public int getAthleteInviteId() {
        return athleteInviteId;
    }

    public int getCoachInviteId() {
        return coachInviteId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setAthleteInviteId(int athleteInviteId) {
        this.athleteInviteId = athleteInviteId;
    }

    public void setCoachInviteId(int coachInviteId) {
        this.coachInviteId = coachInviteId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public List<String> getAnnouncements() {
        return announcements;
    }
}
