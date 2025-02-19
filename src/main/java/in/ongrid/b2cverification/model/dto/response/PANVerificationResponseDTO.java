package in.ongrid.b2cverification.model.dto.response;


import in.ongrid.b2cverification.model.enums.State;
import lombok.*;

import java.util.Stack;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class PANVerificationResponseDTO {

    private int requestId;
    private State state;
    private PANReportDTO report;
    private String closedReason;
    private String closedRemarks;
    private String created;
    private String dataSufficiencyDate;
    private String completedDate;
    private String closedDate;

}