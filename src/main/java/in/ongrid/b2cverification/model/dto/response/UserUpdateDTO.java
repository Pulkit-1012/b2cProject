package in.ongrid.b2cverification.model.dto.response;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class UserUpdateDTO {
    private String userName;
    private long phoneNumber;
}
