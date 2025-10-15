package se331.project2.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public Page<User> findAll(Pageable pageable) {
//        return userRepository.findByIsDeletedFalse(pageable);
//    }
//
//    @Override
//    public User findById(Integer id) {
//        return userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
//    }
@Override
public Page<User> findAll(Pageable pageable) {
    return userRepository.findByIsDeletedFalse(pageable);
}

    @Override
    public User findById(Integer id) {
        return userRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    }
}