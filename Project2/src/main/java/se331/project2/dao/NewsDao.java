package se331.project2.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.Optional;

public interface NewsDao {
    Page<News> findAll(Pageable pageable);
    Optional<News> findById(Long id);
    Optional<News> findBySlug(String slug);

    Page<News> findByStatus(NewsStatus status, Pageable pageable);
    Page<News> findByTopicOrShortDetail(String keyword1, String keyword2, Pageable pageable);
    Page<News> findByReporterName(String reporterName, Pageable pageable);

    News save(News news);
    void deleteNewsFromDatabase(Long id);
    void deleteNews(Long id);
    void restoreNews(Long id);
}
