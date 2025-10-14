package se331.project2.security.user;

public interface UserDao {
    User findByUsername(String username);

    User save(User user);
}