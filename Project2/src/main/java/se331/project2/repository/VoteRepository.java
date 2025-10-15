package se331.project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.project2.entity.Vote;
import se331.project2.entity.VoteType;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    long countByNews_IdAndType(Long newsId, VoteType voteType);
    boolean existsByNews_IdAndType(Long newsId, VoteType voteType);
}
