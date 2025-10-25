package se331.project2.security.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
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
                .profileImageUrl(user.getProfileImageUrl())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .enabled(user.getEnabled())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    @Mapping(target = "roles", expression = "java(user.getRole() != null ? java.util.Collections.singletonList(user.getRole().name()) : java.util.Collections.emptyList())")
    @Mapping(source = "profileImageUrl", target = "profileImageUrl")
    UserAuthDTO toUserAuthDTO(User user);
}