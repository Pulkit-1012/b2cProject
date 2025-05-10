package in.ongrid.b2cverification.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CreateIndividualRequest {
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
