package se331.project2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.project2.entity.Comment;
import se331.project2.entity.VoteType;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByNews_IdAndAuthor_Id(Long newsId, Long authorId);

    long countByNews_IdAndVoteTypeAndDeletedFalse(Long newsId, VoteType voteType);
    
}
