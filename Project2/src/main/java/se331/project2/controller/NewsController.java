package se331.project2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.project2.DTO.News.NewsDetailDTO;
import se331.project2.entity.News;
import se331.project2.util.NewsMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {


    @GetMapping
    public ResponseEntity<?> getAllNews(Pageable pageable) {
        Page<News> page = newsService.getAllNews(pageable);
        return ResponseEntity.ok(NewsMapper.INSTANCE.getNewsHomepageDTO(page.getContent()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<NewsDetailDTO> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);
        if (news == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(NewsMapper.INSTANCE.getNewsDetailDTO(news));
    }


    @PostMapping
    public void addNews (){

    }

}
