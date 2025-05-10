package in.ongrid.b2cverification.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
