package se331.project2.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import se331.project2.DTO.Comment.CommentDTO;

//import se331.project2.DTO.Comment.UserDTO;
import se331.project2.entity.Comment;
import se331.project2.security.user.User;
import se331.project2.security.user.UserDTO;
import se331.project2.security.user.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CommentMapper {

    @Mapping(target = "author" , source = "author")
    @Mapping(source = "deleted", target = "deleted")
    CommentDTO toCommentDTO(Comment comment);

//    @Named("userToUserDTO")
//    default UserDTO userToUserDTO(User user) {
//        if (user == null) return null;
//        return UserDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .role(user.getRole().name())
//                .build();
//    }
}
