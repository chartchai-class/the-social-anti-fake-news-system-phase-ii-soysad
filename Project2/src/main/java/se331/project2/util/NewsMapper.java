package se331.project2.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se331.project2.DTO.News.NewsDetailDTO;
import se331.project2.DTO.News.NewsHomepageDTO;
import se331.project2.entity.News;
import se331.project2.entity.Vote;
import se331.project2.entity.VoteType;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "reporter", expression = "java(news.getReporter() != null ? news.getReporter().getName() : null)")
    @Mapping(target = "publishedAt", source = "createdAt")
    @Mapping(target = "fakeCount",    expression = "java(countFake(news))")
    @Mapping(target = "notFakeCount", expression = "java(countNotFake(news))")

    NewsHomepageDTO getNewsHomepageDTO(News news);

    NewsDetailDTO getNewsDetailDTO(News news);

    default Long countFake(News news) {
        return countVotes(news != null ? news.getVotes() : null, se331.project2.entity.VoteType.FAKE);
    }

    default Long countNotFake(News news) {
        return countVotes(news != null ? news.getVotes() : null, se331.project2.entity.VoteType.NOT_FAKE);
    }

    default Long countVotes(List<Vote> votes, se331.project2.entity.VoteType type) {
        if (votes == null) return 0L;
        return votes.stream().filter(v -> v.getType() == type).count();
    }

}
