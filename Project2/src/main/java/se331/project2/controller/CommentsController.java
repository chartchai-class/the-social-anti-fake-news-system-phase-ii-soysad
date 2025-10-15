package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se331.project2.DTO.Comment.CommentDTO;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;
import se331.project2.service.CommentService;
import se331.project2.util.CommentMapper;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentDTO> createNewComment(
            @RequestParam Long newsId,
            @RequestBody CreateCommentRequestDTO req,
            @AuthenticationPrincipal User principal)
    {

//        Comment comment = commentService.createCommentWithVote(newsId, principal.getUsername(), req);
        Comment comment = commentService.createCommentWithVote(newsId,"admin", req);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toCommentDTO(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequestDTO req,
            @AuthenticationPrincipal User principal)
    {
        Comment comment = commentService.updateMyComment(id, "admin", req);
        return ResponseEntity.ok(commentMapper.toCommentDTO(comment)); }
}
