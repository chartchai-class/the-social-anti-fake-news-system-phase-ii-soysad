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
import se331.project2.service.CommentServiceImpl;
import se331.project2.util.CommentMapper;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentServiceImpl commentService;
    private final CommentMapper commentMapper;
    
    
    @PostMapping("/{newsId}")
    public ResponseEntity<CommentDTO> createNewComment(
            @PathVariable Long newsId,
            @RequestBody CreateCommentRequestDTO req
            ){
        Comment comment = commentService.createCommentWithVote(newsId, req.getUserId(), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toCommentDTO(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequestDTO req
            )
    {
        Comment comment = commentService.updateMyComment(id,req.getUserId(), req);
        return ResponseEntity.ok(commentMapper.toCommentDTO(comment)); 
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        commentService.softdeleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteFromDatabase/{id}")
    public ResponseEntity<Void> deleteNewsFromDatabase(@PathVariable Long id) {
        commentService.harddeletComment(id);
        return ResponseEntity.noContent().build();
    }
    
}
