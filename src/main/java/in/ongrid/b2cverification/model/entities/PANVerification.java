package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.Result;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PANVerification extends BaseVerification {

    @OneToOne
    @JoinColumn(name = "pan_doc_document_id")
    private PANDoc panDoc;

    //editing this from below
    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column(name = "reason")
    private String reason;

    @Column(name = "pdf_serving_url")
    private String pdfServingUrl;



}
