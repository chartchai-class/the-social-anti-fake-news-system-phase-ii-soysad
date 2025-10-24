package se331.project2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import se331.project2.security.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(
        name = "comments",
        indexes = @Index(name = "idx_comments_news_id", columnList = "news_id"),
        uniqueConstraints = {
                // บังคับ 1 คน/ข่าว = 1 คอมเมนต์ (จึง 1 โหวต)
                @UniqueConstraint(name = "uk_comment_news_author", columnNames = {"news_id","author_id"})
        }
)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    @JsonIgnore
    private News news;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;

    @Column(name = "attachments", length = 2048)
    @ElementCollection
    @Builder.Default
    List<String> attachments = new ArrayList<>();

    @Version
    private Long version;

}
