package se331.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import se331.project2.security.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "comments", indexes = {
        @Index(name = "idx_comments_news_id", columnList = "news_id")
})
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private News news;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String comments;


    @ElementCollection
    @Builder.Default
    List<String> attachments = new ArrayList<>();

}
