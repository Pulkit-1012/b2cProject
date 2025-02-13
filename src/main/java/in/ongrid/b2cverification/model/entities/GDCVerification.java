package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.MatchType;
import in.ongrid.b2cverification.model.enums.Result;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GDCVerification extends BaseVerification{

    @Column(name = "result")
    private Result result;

    @Column(name = "reason")
    private String reason;

    @Column(name = "pdf_serving_url")
    private String pdfServingUrl;
}
