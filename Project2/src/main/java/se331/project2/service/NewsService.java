package se331.project2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    Page<News> getAllNews(Pageable pageable);
    Page<News> getAllDeletedNews(Pageable pageable);
    Optional<News> getNewsById(Long id);

    Page<News> getNewsByStatus(NewsStatus status, Pageable pageable);
    Page<News> getNewsByTopicOrShortDetailOrFullDetailOrReporter(
            String keyword1, String keyword2,String keyword3,String keyword4,String keyword5, Pageable pageable);
    
    News saveNews(News news);
    void deleteNewsFromDatabase(Long id);
    void deleteNews(Long id);
    void restoreNews(Long id);

    void setMainImage(Long newsId, String url);
    void addGalleryImages(Long newsId, List<String> urls);
}
