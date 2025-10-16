package se331.project2.security.user;

// se331.project2.security.user.dto/UserDTO.java

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    Integer id;
    String name;
    String surname;
    String username;
    String email;
    String profileImageUrl;
    String role;     // แปลง enum เป็น string ก่อนจะส่งขึ้นนะแจ๊ะ
    Boolean enabled;
}
