package in.ongrid.b2cverification.model.dto.response;

import in.ongrid.b2cverification.model.enums.Result;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GDCVerificationResponseDTO {
    private Result result;
    private String reason;
    private String pdfServingUrl;
}
