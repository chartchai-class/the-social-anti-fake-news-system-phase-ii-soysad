package se331.project2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.DAO.Comment.CommentDao;
import se331.project2.DAO.News.NewsDao;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;
import se331.project2.entity.News;
import se331.project2.repository.CommentRepository;
import se331.project2.security.user.Role;
import se331.project2.security.user.UserDao;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {
    private final NewsDao newsDao;
    private final CommentDao commentDao;
    private final NewsStatusService newsStatusService;
    private final UserDao userDao;
    private final CommentRepository commentRepository;
    
    
    @Transactional
    public Comment createCommentWithVote(Long newsId, Integer userId, CreateCommentRequestDTO req) {
        
        var author = userDao.findById(userId);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var news = newsDao.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (commentDao.existsByNewsIdAndAuthorId(newsId, author.getId().longValue()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already commented this news");

        Comment c = Comment.builder()
                .news(news)
                .author(author)
                .body(req.getBody())
                .voteType(req.getVoteType())
                .attachments(req.getAttachments() == null ? new ArrayList<>() : new ArrayList<>(req.getAttachments()))
                .build();

        commentDao.save(c);
        newsStatusService.recalcAndUpdateStatus(newsId);
        return c;
    }

    @Transactional
    public Comment updateMyComment(Long commentId, String username, UpdateCommentRequestDTO req) {
        var author = userDao.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Comment c = commentDao.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!c.getAuthor().getId().equals(author.getId())
                && author.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your comment");
        }

        if (req.getVersion() != null && !req.getVersion().equals(c.getVersion()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Version mismatch");

        c.setBody(req.getBody());
        c.setVoteType(req.getVoteType());
        c.setAttachments(req.getAttachments() == null ? c.getAttachments() : req.getAttachments());
        Comment saved = commentDao.save(c);

        newsStatusService.recalcAndUpdateStatus(c.getNews().getId());
        return saved;
    }

    @Transactional
    public void addAttachments(Long id, List<String> urls){
        Comment comments = commentDao.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));;
                
        if (comments.getAttachments() == null) {
            comments.setAttachments(new ArrayList<>());
        }
        comments.getAttachments().addAll(urls); 
        commentRepository.save(comments);
        
;
    }
}
