package se331.project2.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.DAO.Comment.CommentDao;
import se331.project2.DAO.News.NewsDao;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.entity.VoteType;

@Service
@RequiredArgsConstructor
public class NewsStatusService {
    final CommentDao commentDao;
    final NewsDao newsDao;

    @Transactional
    public News recalcAndUpdateStatus(Long newsId) {
        News news = newsDao.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        long fake    = commentDao.countByNews_IdAndVoteTypeAndDeletedFalse(newsId, VoteType.FAKE);
        long notFake = commentDao.countByNews_IdAndVoteTypeAndDeletedFalse(newsId, VoteType.NOT_FAKE);

        NewsStatus ns = (fake > notFake) ? NewsStatus.FAKE :
                (notFake > fake) ? NewsStatus.NOT_FAKE : NewsStatus.UNVERIFIED;

        news.setStatus(ns);
        news.setFakeCount((int) fake);
        news.setNotFakeCount((int) notFake);
        return newsDao.save(news);
    }
}
