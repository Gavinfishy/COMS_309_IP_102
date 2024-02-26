package application.Coach;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface CoachRepository extends JpaRepository<Coach, Long>{
    Coach findByUserName(String userName);

    boolean existsCoachByUserName(String UserName);
    @Transactional
    void deleteByUserName(String userName);
}
