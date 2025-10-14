package se331.project2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.Optional;

public interface NewsService {
    Page<News> getAllNews(Pageable pageable);
    Optional<News> getNewsById(Long id);
    Optional<News> getNewsBySlug(String slug);

    Page<News> getNewsByStatus(NewsStatus status, Pageable pageable);
    Page<News> getNewsByTopicOrShortDetail(String keyword1, String keyword2, Pageable pageable);
    Page<News> getNewsByReporterName(String reporterName, Pageable pageable);

    News saveNews(News news);
    void deleteNews(Long id);
}
