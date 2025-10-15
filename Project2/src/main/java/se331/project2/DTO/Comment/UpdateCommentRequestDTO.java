package se331.project2.DTO.Comment;


import lombok.Data;
import se331.project2.entity.VoteType;

import java.util.List;

@Data
public class UpdateCommentRequestDTO {
    String body;
    VoteType voteType;
    List<String> attachments;
    Long version;
}
