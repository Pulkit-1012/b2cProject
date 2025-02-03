package in.ongrid.b2cverification.model.dto.response;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class IndividualDTO {
    private String individualName;
    private long phoneNumber;
    private String fatherName;
    private String motherName;
    private String address;
    private Date dob;
    private String gender;

}
