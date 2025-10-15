package se331.project2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.project2.DAO.News.NewsDao;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    final NewsDao newsDao;

    @Override
    public Optional<News> getNewsById(Long id) {
        return newsDao.findById(id);
    }

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    @Override
    public Optional<News> getNewsBySlug(String slug) {
        return newsDao.findBySlug(slug);
    }

    @Override
    public Page<News> getNewsByStatus(NewsStatus status, Pageable pageable) {
        return newsDao.findByStatus(status, pageable);
    }

    @Override
    public Page<News> getNewsByTopicOrShortDetail(String keyword1, String keyword2, Pageable pageable) {
        return newsDao.findByTopicOrShortDetail(keyword1, keyword2, pageable);
    }

    @Override
    public Page<News> getNewsByReporterName(String reporterName, Pageable pageable) {
        return newsDao.findByReporterName(reporterName, pageable);
    }

    @Override
    public News saveNews(News news) {
        return newsDao.save(news);
    }

    @Override
    public void deleteNewsFromDatabase(Long id) {
        newsDao.deleteNewsFromDatabase(id);
    }

    @Override
    public void deleteNews(Long id) {
        newsDao.deleteNews(id);
    }
}
