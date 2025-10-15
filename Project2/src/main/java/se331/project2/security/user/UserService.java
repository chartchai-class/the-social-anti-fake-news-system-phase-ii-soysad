package se331.project2.security.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);

    @Transactional
    User findByUsername(String username);

    Page<User> findAll(Pageable pageable);
    User findById(Integer id);
    User updateRole(Integer id, Role newRole);

}