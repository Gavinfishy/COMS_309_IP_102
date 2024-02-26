package application.Athlete;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Athlete findByUserName(String userName);

    boolean existsAthleteByUserName(String UserName);
    @Transactional
    void deleteByUserName(String userName);
}
