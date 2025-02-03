package in.ongrid.b2cverification.model.dto.response;


import lombok.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class UserDTO {
    private long id;
    private String userName;
    private String email;
    private long phoneNumber;

    
}
