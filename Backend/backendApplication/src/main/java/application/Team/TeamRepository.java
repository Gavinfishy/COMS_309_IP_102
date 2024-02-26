package application.Team;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByTeamName(String teamName);

    boolean existsByTeamName(String teamName);

    @Transactional
    void deleteByTeamName(String teamName);

}
