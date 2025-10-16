package se331.project2.DAO.Comment;

import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;

import java.util.Optional;

public interface CommentDao {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    boolean existsByNewsIdAndAuthorId(Long newsId, Long authorId);
}