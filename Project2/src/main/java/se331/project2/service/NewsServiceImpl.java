package se331.project2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.DAO.News.NewsDao;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    final NewsDao newsDao;
    final NewsRepository newsRepository;

    @Override
    public Optional<News> getNewsById(Long id) {
        return newsDao.findById(id);
    }

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        return newsDao.findAll(pageable);
    }
    
    @Override
    public Page<News> getAllDeletedNews(Pageable pageable) { return newsDao.findAllDeleted(pageable);}
    
    @Override
    public Page<News> getNewsByStatus(NewsStatus status, Pageable pageable) {
        return newsDao.findByStatus(status, pageable);
    }

    @Override
    public Page<News> getNewsByTopicOrShortDetailOrFullDetailOrReporter(
            String keyword1, String keyword2,String keyword3,String keyword4,String keyword5, Pageable pageable) {
        return newsDao.findByTopicOrShortDetailOrFullDetailOrReporter(keyword1, keyword2,keyword3,keyword4,keyword5, pageable);
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

    @Override
    public void restoreNews(Long id) {
        newsDao.restoreNews(id);
    }

    @Transactional
    public void setMainImage(Long newsId, String url) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        news.setMainImageUrl(url);
        newsRepository.save(news);
    }

    @Transactional
    public void addGalleryImages(Long newsId, List<String> urls) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (news.getGalleryImages() == null) {
            news.setGalleryImages(new ArrayList<>());
        }
        news.getGalleryImages().addAll(urls);
        newsRepository.save(news);
    }
    
}
