package se331.project2.security.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserDao userDao;

    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public User updateRole(Integer id, Role newRole) {
        User u = userDao.findById(id);
        if (u == null) return null;
        u.setRole(newRole);
        return userDao.save(u);
    }
}