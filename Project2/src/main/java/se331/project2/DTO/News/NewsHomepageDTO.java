package se331.project2.DTO.News;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project2.entity.NewsStatus;
import se331.project2.entity.Vote;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsHomepageDTO {
    Long id;
    String slug;
    String topic;
    String shortDetail;
    String fullDetail;
    String mainImageUrl;
    String reporter;
    Date publishedAt;

    NewsStatus status;
    int fakeCount;
    int notFakeCount;

}
