package se331.project2.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import se331.project2.DTO.Comment.CommentDTO;

import se331.project2.entity.Comment;
import se331.project2.security.user.User;
import se331.project2.security.user.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "author" , source = "author" ,  qualifiedByName = "userToUserDTO")
    CommentDTO toCommentDTO(Comment comment);

    @Named("userToUserDTO")
    default UserDTO userToUserDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}
