package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se331.project2.DTO.Comment.CommentDTO;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;
import se331.project2.service.CommentServiceImpl;
import se331.project2.util.CommentMapper;
import org.springframework.security.core.userdetails.User;
import se331.project2.util.StorageFileDto;
import se331.project2.util.SupabaseStorageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentServiceImpl commentService;
    private final CommentMapper commentMapper;
    private final SupabaseStorageService supabaseStorageService;
    
    @PostMapping("/{newsId}")
    public ResponseEntity<CommentDTO> createNewComment(
            @PathVariable Long newsId,
            @RequestBody CreateCommentRequestDTO req
            )
    {
        
        Comment comment = commentService.createCommentWithVote(newsId, req.getUserId(), req);

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
