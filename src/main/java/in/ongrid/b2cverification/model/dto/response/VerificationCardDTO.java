package in.ongrid.b2cverification.model.dto.response;

import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.model.enums.State;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class VerificationCardDTO {
    private OfferingType offeringType;
    private State state;
//    private int requestId; //removed
    private long id;//added
}
