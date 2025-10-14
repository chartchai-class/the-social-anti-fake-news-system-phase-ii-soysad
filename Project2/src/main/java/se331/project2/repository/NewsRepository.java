package se331.project2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findBySlug(String slug);
    Page<News> findByStatus(NewsStatus status, Pageable pageable);
    Page<News> findByTopicContainingIgnoreCaseOrShortDetailContainingIgnoreCase(
            String keyword1, String keyword2, Pageable pageable);
    Page<News> findByReporter_NameContainingIgnoreCase(String reporterName, Pageable pageable);
}
