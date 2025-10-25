package se331.project2.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.entity.VoteType;
import se331.project2.repository.CommentRepository;
import se331.project2.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsStatusService {
    final CommentRepository commentRepository;
    final NewsRepository newsRepository;

    @Transactional
    public News recalcAndUpdateStatus(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        long fake    = commentRepository.countByNews_IdAndVoteTypeAndDeletedFalse(newsId, VoteType.FAKE);
        long notFake = commentRepository.countByNews_IdAndVoteTypeAndDeletedFalse(newsId, VoteType.NOT_FAKE);

        NewsStatus ns = (fake > notFake) ? NewsStatus.FAKE :
                (notFake > fake) ? NewsStatus.NOT_FAKE : NewsStatus.UNVERIFIED;

        news.setStatus(ns);
        news.setFakeCount((int) fake);
        news.setNotFakeCount((int) notFake);
        return newsRepository.save(news);
    }
}
