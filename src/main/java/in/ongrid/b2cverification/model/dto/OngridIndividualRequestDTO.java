package in.ongrid.b2cverification.model.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class OngridIndividualRequestDTO {

    @Value("${}")
    private String individualName;
    private String city;
    private String gender;
    private String phoneNumber;
    private String professionalId;
    private Date dob;
    private boolean hasConsent;
    private String consentText;
    private String fatherName;


}
