package se331.project2.DAO.News;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.repository.NewsRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsDaoImpl implements NewsDao {

    final NewsRepository newsRepository;

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public Page<News> findAllDeleted(Pageable pageable) {
        return newsRepository.findAllByIsDeletedTrue(pageable);
    }
    
    @Override
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }
    
    @Override
    public Page<News> findByStatus(NewsStatus status, Pageable pageable) {
        return newsRepository.findByStatusAndIsDeletedFalse(status, pageable);
    }

    @Override
    public Page<News> findByTopicOrShortDetailOrFullDetailOrReporter(
            String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, Pageable pageable) {
        return newsRepository.
                findByIsDeletedFalseAndTopicContainingIgnoreCaseOrShortDetailContainingIgnoreCaseOrFullDetailContainingIgnoreCaseOrReporter_NameContainingIgnoreCaseOrReporter_SurnameContainingIgnoreCase
                        (keyword1, keyword2, keyword3, keyword4, keyword5, pageable);
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }
    
    @Override
    public void deleteNewsFromDatabase(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        news.setIsDeleted(true);
        newsRepository.save(news);
    }

    @Override
    public void restoreNews(Long id) {
        News news = newsRepository.findByIdAndIsDeletedTrue(id)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        news.setIsDeleted(false);
        newsRepository.save(news);
    }
}
