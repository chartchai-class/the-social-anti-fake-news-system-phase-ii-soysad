package se331.project2.security.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import se331.project2.security.user.Role;

@Data
public class UpdateRoleRequest {
    @NotNull
    private Role role; // READER MEMBER ADMIN
}
