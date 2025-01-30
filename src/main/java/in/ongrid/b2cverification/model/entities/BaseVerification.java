package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.model.enums.Result;
import in.ongrid.b2cverification.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "base_verification")
public class BaseVerification extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "individual_id")
    private Individual individual;

    @Column(name = "offering_type")
    private OfferingType offeringType;

    @Column(name = "status")
    private Status status;

    @Column(name = "result")
    private Result result;

    @Column(name = "pdf_url")
    private String pdfReportUrl;




    //no attribute constructor
    public BaseVerification() {};

    public BaseVerification(OfferingType offeringType, Status status, Result result, String pdfReportUrl) {
        this.offeringType = offeringType;
        this.status = status;
        this.result = result;
        this.pdfReportUrl = pdfReportUrl;
    }

}
