package in.ongrid.b2cverification.model.dto;

import in.ongrid.b2cverification.model.enums.UserType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CreateUserRequest {
    private String userName;
    private String password;
    private String email;
    private long phoneNumber;
    private UserType userType;
}
