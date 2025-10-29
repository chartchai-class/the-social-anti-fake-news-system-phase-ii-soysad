package se331.project2.security.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se331.project2.entity.BaseEntity;
import se331.project2.security.token.Token;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean isDeleted = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Exclude
  private Integer id;

  private String name;
  private String surname;

  @Column(unique = true)
  private String username;
  private String email;
  private String password;
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private LocalDateTime updatedAt;


  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isMember() {
        return role == Role.MEMBER;
    }

    public boolean isReader() {
        return role == Role.READER;
    }






}
