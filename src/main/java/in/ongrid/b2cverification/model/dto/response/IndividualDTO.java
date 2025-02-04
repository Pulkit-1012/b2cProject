package in.ongrid.b2cverification.model.dto.response;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class IndividualDTO {
    private String name;
    private String city;
    private String gender;
    private String phone;
    private String professionId;
    private LocalDate dob;
    private boolean hasConsent = true;
    private String consentText;
    private String fathersName;
    private String mothersName;


}
