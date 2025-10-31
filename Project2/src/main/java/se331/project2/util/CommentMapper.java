package se331.project2.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se331.project2.DTO.Comment.CommentDTO;
import se331.project2.entity.Comment;
import se331.project2.security.user.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CommentMapper {

    @Mapping(source = "author", target = "author" )
    @Mapping(source = "deleted", target = "deleted")
    CommentDTO toCommentDTO(Comment comment);

}
