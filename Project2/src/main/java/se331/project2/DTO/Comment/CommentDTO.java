package se331.project2.DTO.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import se331.project2.entity.VoteType;
import se331.project2.security.user.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    Long id;
    UserDTO author;
    String body;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    VoteType voteType;
    boolean deleted;

    @Builder.Default
    List<String> attachments = new ArrayList<>();
    Long version;

}
