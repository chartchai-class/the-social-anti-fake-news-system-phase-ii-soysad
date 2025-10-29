package se331.project2.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.project2.security.user.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String confirmPassword;
  private Role roles ;
  private String username;
  private String profileImageUrl;
}
