package se331.project2.security.user;

import se331.project2.security.user.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User u) {
        if (u == null) return null;
        return UserDTO.builder()
                .id(u.getId())
                .name(u.getName())
                .surname(u.getSurname())
                .username(u.getUsername())
                .email(u.getEmail())
                .profileImage(u.getProfileImage())
                .role(u.getRole() != null ? u.getRole().name() : null)
                .enabled(u.getEnabled())
                .build();
    }
}
