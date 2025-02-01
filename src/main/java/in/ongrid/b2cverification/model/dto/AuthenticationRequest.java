package in.ongrid.b2cverification.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
