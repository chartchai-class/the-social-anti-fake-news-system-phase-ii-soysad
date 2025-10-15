package se331.project2.security.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao {
    User findByUsername(String username);

    User save(User user);
    Page<User> findAll(Pageable pageable);
    User findById(Integer id);
}