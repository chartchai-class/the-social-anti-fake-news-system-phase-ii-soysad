package se331.project2.DTO.News;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project2.DTO.Comment.CommentDTO;
import se331.project2.DTO.Comment.UserDTO;
import se331.project2.entity.NewsStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDetailDTO {

    Long id;
    String slug;
    String topic;
    String shortDetail;
    String fullDetail;
    String mainImageUrl;
    Date publishedAt;
    UserDTO reporter;

    @Builder.Default
    List<String> galleryImages = new ArrayList<>();

    NewsStatus status;

    @Builder.Default
    List<CommentDTO> comments = new ArrayList<>();

    Long fakeCount;
    Long notFakeCount;
}

