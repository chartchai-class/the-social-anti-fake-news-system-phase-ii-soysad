package se331.project2.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se331.project2.DTO.News.NewsDetailDTO;
import se331.project2.DTO.News.NewsHomepageDTO;
import se331.project2.entity.News;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface NewsMapper {

    @Mapping(target = "reporter", expression = "java(news.getReporter() != null ? news.getReporter().getName() : null)")
    @Mapping(target = "publishedAt", source = "createdAt")
    @Mapping(target = "fakeCount",    source = "fakeCount")
    @Mapping(target = "notFakeCount", source = "notFakeCount")
    NewsHomepageDTO getNewsHomepageDTO(News news);

    @Mapping(target = "publishedAt", source = "createdAt")
    @Mapping(target = "fakeCount",    source = "fakeCount")
    @Mapping(target = "notFakeCount", source = "notFakeCount")
    @Mapping(target = "comments" , source = "comments")
    NewsDetailDTO getNewsDetailDTO(News news);

}
