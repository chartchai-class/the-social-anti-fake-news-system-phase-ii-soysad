package se331.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import se331.project2.security.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "news", indexes = {
        @Index(name = "idx_news_status", columnList = "status"),
        @Index(name = "idx_news_slug", columnList = "slug")
})
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String slug;
    String topic;
    String shortDetail;

    @Column(columnDefinition = "TEXT")
    String fullDetail;

    String mainImageUrl;

    @ElementCollection
    @Builder.Default
    List<String> galleryImages = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = false)
    @OrderBy("createdAt DESC")
    private List<Comment> comments;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Vote> votes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NewsStatus status = NewsStatus.UNVERIFIED;

    int fakeCount;
    int notFakeCount;
}
