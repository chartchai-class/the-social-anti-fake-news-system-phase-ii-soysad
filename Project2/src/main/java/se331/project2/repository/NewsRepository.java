package se331.project2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT * FROM news WHERE id = :id", nativeQuery = true)
    Optional<News> findByIdIncludeDeletedNative(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE news SET is_deleted = false WHERE id = :id", nativeQuery = true)
    int restoreById(@Param("id") Long id);
}
