package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.project2.DTO.Comment.CommentDTO;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;
import se331.project2.repository.CommentRepository;
import se331.project2.service.CommentService;
import se331.project2.service.NewsStatusService;
import se331.project2.util.CommentMapper;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    final CommentService commentService;
    final CommentMapper commentMapper;
    final CommentRepository commentRepository;
    final NewsStatusService newsStatusService;

    @PostMapping("/{newsId}")
    public ResponseEntity<CommentDTO> createNewComment(
            @PathVariable Long newsId,
            @RequestBody CreateCommentRequestDTO req
    ) {
        Comment comment = commentService.createCommentWithVote(newsId, req.getUserId(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toCommentDTO(comment));
    }

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getAllComments(
            Pageable pageable) {

        Page<Comment> page = commentRepository.findAll(pageable);
        Page<CommentDTO> dtoPage = page.map(commentMapper::toCommentDTO);
        return ResponseEntity.ok(dtoPage);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequestDTO req
    ) {
        Comment comment = commentService.updateMyComment(id, req.getUserId(), req);
        return ResponseEntity.ok(commentMapper.toCommentDTO(comment));
    }

    @DeleteMapping("/delete/{id}/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id, @PathVariable Long newsId) {
        commentService.softDeleteComment(id);
        newsStatusService.recalcAndUpdateStatus(newsId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteFromDatabase/{id}")
    public ResponseEntity<Void> deleteNewsFromDatabase(@PathVariable Long id) {
        commentService.hardDeleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}/{newsId}")
    public ResponseEntity<Void> restoreComment(@PathVariable Long id, @PathVariable Long newsId) {
        commentService.restoreComment(id);
        newsStatusService.recalcAndUpdateStatus(newsId);
                
        return ResponseEntity.ok().build();

    }
}

    
