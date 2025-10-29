package se331.project2.security.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);

    User save(User user);
    Page<User> findAll(Pageable pageable);
    User findById(Integer id);
}