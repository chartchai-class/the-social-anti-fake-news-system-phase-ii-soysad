package se331.project2.security.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    User save(User user);

    @Transactional
    Optional<User> findByUsername(String username);

    Page<User> findAll(Pageable pageable);
    User findById(Integer id);
    User updateRole(Integer id, Role newRole);
    void setProfileImage(Integer id, String url);

}