package se331.project2.DAO.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.Optional;

public interface NewsDao {
    
    Page<News> findAll(Pageable pageable);
    Page<News> findAllDeleted(Pageable pageable);
    Optional<News> findById(Long id);

    Page<News> findByStatus(NewsStatus status, Pageable pageable);
    Page<News> findByTopicOrShortDetailOrFullDetailOrReporter(
            String keyword1, String keyword2,String keyword3,String keyword4,String keyword5, Pageable pageable);
    
    News save(News news);
    void deleteNewsFromDatabase(Long id);
    void deleteNews(Long id);
    void restoreNews(Long id);
}
