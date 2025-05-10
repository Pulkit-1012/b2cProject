package in.ongrid.b2cverification.model.dto.response;


import in.ongrid.b2cverification.model.enums.Result;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class PANReportDTO {
    private Result result;
    private String reason;
    private String pdfServingUrl;
}
