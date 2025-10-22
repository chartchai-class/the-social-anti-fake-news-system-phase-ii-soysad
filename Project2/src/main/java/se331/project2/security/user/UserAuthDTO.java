package se331.project2.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String profileImageUrl;
    private List<String> roles;
}
