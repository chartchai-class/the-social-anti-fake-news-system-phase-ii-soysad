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
    @Mapping(target = "fakeCount", expression = "java(countVotes(news.getVotes(), VoteType.FAKE))")
    @Mapping(target = "notFakeCount", expression = "java(countVotes(news.getVotes(), VoteType.NOT_FAKE))")
//  NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    //NEWS HOMEPAGE DTO
    NewsHomepageDTO getNewsHomepageDTO(News news);

    //NEWS DETAIL DTO
    NewsDetailDTO getNewsDetailDTO(News news);

    default Long countVotes(List<Vote> votes, VoteType type) {
        if (votes == null) return 0L;
        return votes.stream().filter(v -> v.getType() == type).count();
    }

}
