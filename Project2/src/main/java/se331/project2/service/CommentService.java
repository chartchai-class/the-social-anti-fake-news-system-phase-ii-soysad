package se331.project2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;
import se331.project2.repository.CommentRepository;
import se331.project2.repository.NewsRepository;
import se331.project2.security.user.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final NewsRepository newsRepo;
    private final CommentRepository commentRepo;
    private final NewsStatusService newsStatusService;
    private final UserRepository userRepo;

    @Transactional
    public Comment createCommentWithVote(Long newsId, String username, CreateCommentRequestDTO req) {
        var author = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        var news = newsRepo.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (commentRepo.existsByNews_IdAndAuthor_Id(newsId, author.getId().longValue()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already commented this news");

        Comment c = Comment.builder()
                .news(news)
                .author(author)
                .body(req.getBody())
                .voteType(req.getVoteType())
                .attachments(req.getAttachments() == null ? new ArrayList<>() : new ArrayList<>(req.getAttachments()))
                .build();

        commentRepo.save(c);
        newsStatusService.recalcAndUpdateStatus(newsId);
        return c;
    }

    @Transactional
    public Comment updateMyComment(Long commentId, String username, UpdateCommentRequestDTO req) {
        var author = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        // ✅ อนุญาตเจ้าของหรือ ADMIN
//        if (!c.getAuthor().getId().equals(author.getId())
//                && author.getRoles().stream().noneMatch(r -> r.name().contains("ADMIN")))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your comment");

        // (ถ้าใช้ optimistic locking) ถ้า req มี version ให้เช็คเท่ากันก่อน
        if (req.getVersion() != null && !req.getVersion().equals(c.getVersion()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Version mismatch");

        c.setBody(req.getBody());
        c.setVoteType(req.getVoteType());
        c.setAttachments(req.getAttachments() == null ? c.getAttachments() : req.getAttachments());
        Comment saved = commentRepo.save(c);

        newsStatusService.recalcAndUpdateStatus(c.getNews().getId());
        return saved;
    }
}
