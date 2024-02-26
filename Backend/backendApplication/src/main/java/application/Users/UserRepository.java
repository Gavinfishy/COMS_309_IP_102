package application.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String userName);

    Boolean existsByUserNameAndPassword(String userName, String password);

    Boolean existsByUserName(String userName);

    @Transactional
    void deleteByUserName(String userName);
}
