package se331.project2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import se331.project2.security.user.User;

@Data
@Entity
@Table(name = "votes", uniqueConstraints = {
        // one vote per user per news
        @UniqueConstraint(name = "uk_vote_news_voter", columnNames = {"news_id", "voter_id"})
}, indexes = {
        @Index(name = "idx_votes_news_id", columnList = "news_id")
})

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    @JsonIgnore
    private News news;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VoteType type;
}
