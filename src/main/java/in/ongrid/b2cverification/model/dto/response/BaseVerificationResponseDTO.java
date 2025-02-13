package in.ongrid.b2cverification.model.dto.response;

import in.ongrid.b2cverification.model.enums.State;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BaseVerificationResponseDTO {
    private int requestId;
    private State state;
    private String closedReason;
    private String closedRemarks;
    private String created;
    private String dataSufficiencyDate;
    private String completedDate;
    private String closed;
    private GDCVerificationResponseDTO gdcReport;
}
