package se331.project2.DAO.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project2.entity.Comment;
import se331.project2.repository.CommentRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public boolean existsByNewsIdAndAuthorId(Long newsId, Long authorId) {
        return commentRepository.existsByNews_IdAndAuthor_Id(newsId, authorId);
    }
}
