package lt.bit.products.ui.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    boolean existsByUsernameAndPassword(String username, String password);
}
