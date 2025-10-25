package se331.project2.DAO.Comment;

import se331.project2.entity.Comment;
import se331.project2.entity.VoteType;

import java.util.Optional;

public interface CommentDao {

    Optional<Comment> findById(Long id);
    boolean existsByNewsIdAndAuthorId(Long newsId, Long authorId);
    
    Comment save(Comment comment);
    long countByNews_IdAndVoteTypeAndDeletedFalse(Long newsId, VoteType voteType);
    void softdeleteById(Long id);
    void harddeleteById(Long id);
    void restore(Long id);
    
}