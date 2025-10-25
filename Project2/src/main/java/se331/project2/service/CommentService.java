package se331.project2.service;

import org.springframework.stereotype.Service;
import se331.project2.DTO.Comment.CreateCommentRequestDTO;
import se331.project2.DTO.Comment.UpdateCommentRequestDTO;
import se331.project2.entity.Comment;

import java.util.List;


public interface CommentService {
    
    Comment createCommentWithVote(Long newsId, Integer userId, CreateCommentRequestDTO req);
    Comment updateMyComment(Long commentId, Integer userId, UpdateCommentRequestDTO req);
    
    void addAttachments(Long id, List<String> urls);
    void softDeleteComment(Long commentId);
    void hardDeleteComment(Long commentId);
    void restoreComment(Long commentId);
    
}
