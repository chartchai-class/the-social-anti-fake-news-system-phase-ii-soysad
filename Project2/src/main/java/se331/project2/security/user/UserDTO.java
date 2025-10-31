package se331.project2.security.user;

// se331.project2.security.user.dto/UserDTO.java

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Builder
public class UserDTO {
    Integer id;
    String name;
    String surname;
    String username;
    String email;
    String profileImageUrl;
    String role;
    Boolean enabled;
}
