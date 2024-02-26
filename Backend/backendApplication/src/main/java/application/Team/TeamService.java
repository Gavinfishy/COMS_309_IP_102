package application.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

//    public Map<String, String> newTeam(String teamName){
//        Map<String, String> response = new HashMap<>();
//
//        if (teamRepository.existsByTeamName(teamName)) {
//            response.put("isValid", "False");
//        }
//        else {
//            response.put("isValid", "True");
//        }
//        return response;
//    }
}
