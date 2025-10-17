package se331.project2.security.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDTO toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                // .profileImage(user.getProfileImageUrl()) // ยังไม่มี field นี้ใน User.java
                .role(user.getRole() != null ? user.getRole().name() : null) // <-- จุดสำคัญ
                .enabled(user.getEnabled())
                .build();
    }

    List<UserDTO> toUserDto(List<User> users);

    @Mapping(target = "roles", expression = "java(user.getRole() != null ? java.util.Collections.singletonList(user.getRole().name()) : java.util.Collections.emptyList())")
    UserAuthDTO toUserAuthDTO(User user);
}