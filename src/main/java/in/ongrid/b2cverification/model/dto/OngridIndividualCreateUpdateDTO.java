package in.ongrid.b2cverification.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class OngridIndividualCreateUpdateDTO {

//    @JsonProperty("name")
    private String name;

    private String city;

    private String gender;

    private String phone;

    @JsonProperty("professionId")
    private String professionId;

    private String dob;

    private boolean hasConsent = true;

    private String consentText;

    private String fathersName;

    @JsonProperty("id")
    private long onGridIndividualId;

}
