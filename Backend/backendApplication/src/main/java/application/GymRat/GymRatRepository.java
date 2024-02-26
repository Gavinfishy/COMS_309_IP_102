package application.GymRat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GymRatRepository extends JpaRepository<GymRat, Long> {
    GymRat findByUserName(String userName);

    boolean existsGymRatByUserName(String UserName);

    @Transactional
    void deleteByUserName(String userName);
}
