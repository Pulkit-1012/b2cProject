package in.ongrid.b2cverification.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

}
