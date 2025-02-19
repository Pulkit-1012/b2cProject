package in.ongrid.b2cverification.model.dto.response;


import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Data
public class PANReportDTO {
    private String result;
    private String reason;
    private String pdfServingUrl;
}
