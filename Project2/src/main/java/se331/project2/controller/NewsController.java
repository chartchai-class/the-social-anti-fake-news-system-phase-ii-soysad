package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.project2.DTO.News.NewsDetailDTO;
import se331.project2.DTO.News.NewsHomepageDTO;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.service.NewsService;
import se331.project2.util.NewsMapper;
import se331.project2.util.SupabaseStorageService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    final NewsService newsService;
    final NewsMapper newsMapper;
    
    @GetMapping
    public ResponseEntity<Page<NewsHomepageDTO>> getAllNews(Pageable pageable) {
        Page<News> page = newsService.getAllNews(pageable);
        Page<NewsHomepageDTO> dtoPage = page.map(newsMapper::getNewsHomepageDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/Deleted")
    public ResponseEntity<Page<NewsHomepageDTO>> getAllNewsDeleted(Pageable pageable) {
        Page<News> page = newsService.getAllDeletedNews(pageable);
        return ResponseEntity.ok(page.map(newsMapper::getNewsHomepageDTO));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NewsDetailDTO> getNewsById(@PathVariable Long id) {
        Optional<News> newsOutput = newsService.getNewsById(id);

        return newsOutput
                .map(news -> ResponseEntity.ok(newsMapper.getNewsDetailDTO(news)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<NewsHomepageDTO>> getNewsByStatus(
            @PathVariable String status, Pageable pageable) {
        try {
            NewsStatus enumStatus = NewsStatus.valueOf(status.toUpperCase());
            Page<News> page = newsService.getNewsByStatus(enumStatus, pageable);
            return ResponseEntity.ok(page.map(newsMapper::getNewsHomepageDTO));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid news status: " + status);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NewsHomepageDTO>> searchNews(
            @RequestParam String keyword, Pageable pageable) {
        Page<News> page = newsService.getNewsByTopicOrShortDetailOrFullDetailOrReporter(keyword, keyword,keyword,keyword,keyword, pageable);
        return ResponseEntity.ok(page.map(newsMapper::getNewsHomepageDTO));
    }
    
    @PostMapping
    public ResponseEntity<NewsDetailDTO> addNews(@RequestBody News news) {
        News savedNews = newsService.saveNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.getNewsDetailDTO(savedNews));
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<Void> restoreNews(@PathVariable Long id) {
        newsService.restoreNews(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteFromDatabase/{id}")
    public ResponseEntity<Void> deleteNewsFromDatabase(@PathVariable Long id) {
        newsService.deleteNewsFromDatabase(id);
        return ResponseEntity.noContent().build();
    }
    
}
