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
        return newsRepository.findAll(pageable);
    }

    @Override
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public Optional<News> findBySlug(String slug) {
        return newsRepository.findBySlug(slug);
    }

    @Override
    public Page<News> findByStatus(NewsStatus status, Pageable pageable) {
        return newsRepository.findByStatus(status, pageable);
    }

    @Override
    public Page<News> findByTopicOrShortDetail(String keyword1, String keyword2, Pageable pageable) {
        return newsRepository.findByTopicContainingIgnoreCaseOrShortDetailContainingIgnoreCase(
                keyword1, keyword2, pageable);
    }

    @Override
    public Page<News> findByReporterName(String reporterName, Pageable pageable) {
        return newsRepository.findByReporter_NameContainingIgnoreCase(reporterName, pageable);
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
}
