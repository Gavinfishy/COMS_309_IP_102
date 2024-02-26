package application.Users;

import application.Athlete.Athlete;
import application.Coach.Coach;
import application.Exercise.Exercise;
import application.GymRat.GymRat;
import application.Workout.Workout;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String accountType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private GymRat gymRat;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Athlete athlete;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private Coach coach;
    @OneToMany(mappedBy = "userName")
    private List<Workout> workoutHistory = new ArrayList<>();

//    public User(String userName, String password, String firstName, String lastName, String accountType) {
//        this.userName = userName;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.accountType = accountType;
//        this.workoutHistory = new ArrayList<>();
//
//    }

    public User() {
    }

    public GymRat getGymRat() {
        return this.gymRat;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Coach getCoach() {
        return coach;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Workout> getWorkoutHistory() {
        return workoutHistory;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
