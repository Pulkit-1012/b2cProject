package in.ongrid.b2cverification.model.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CreateIndividualRequest {
    private String individualName;
    private long phoneNumber;
    private String fatherName;
    private String motherName;
    private String address;
    private Date dob;
    private String gender;
}
