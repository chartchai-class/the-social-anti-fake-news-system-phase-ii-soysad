package se331.project2.DAO.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.project2.entity.Comment;
import se331.project2.entity.News;
import se331.project2.repository.CommentRepository;
import se331.project2.repository.NewsRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
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
    
    @Override
    public void softdeleteById(Long id) {
        
        commentRepository.findById(id).ifPresent(comment -> {
            comment.setDeleted(true);
            commentRepository.save(comment);
        });
    }
    
    @Override
    public void harddeleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
