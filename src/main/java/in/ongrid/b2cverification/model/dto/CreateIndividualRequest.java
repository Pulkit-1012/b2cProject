package in.ongrid.b2cverification.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CreateIndividualRequest {
    private String individualName;
    private long phoneNumber;
}
