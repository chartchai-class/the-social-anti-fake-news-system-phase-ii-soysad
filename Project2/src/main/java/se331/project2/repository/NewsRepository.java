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

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findByIdAndIsDeletedFalse(Long id);
    Optional<News> findByIdAndIsDeletedTrue(Long id);
                   
    Page<News> findAllByIsDeletedFalse(Pageable pageable);
    Page<News> findAllByIsDeletedTrue(Pageable pageable);

    Page<News> findByStatusAndIsDeletedFalse(NewsStatus status, Pageable pageable);
    Page<News> 
    findByIsDeletedFalseAndTopicContainingIgnoreCaseOrShortDetailContainingIgnoreCaseOrFullDetailContainingIgnoreCaseOrReporter_NameContainingIgnoreCaseOrReporter_SurnameContainingIgnoreCase
        (String keyword1,
         String keyword2,
         String keyword3,
         String keyword4,
         String keyword5,
         Pageable pageable);
    
}
