package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.model.enums.Result;
import in.ongrid.b2cverification.model.enums.State;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "base_verification")
public class BaseVerification extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User requestedByUserId;

    @ManyToOne
    @JoinColumn(name = "individual_id")
    private Individual individual;

    @Enumerated(EnumType.STRING)
    @Column(name = "offering_type")
    private OfferingType offeringType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private State state;


    @Column(name = "ongrid_gdc_request_id")
    private int requestId;

    @Column(name = "closed_reason")
    private String closedReason;

    @Column(name = "closed_remarks")
    private String closedRemarks;

    @Column(name = "created")
    private String created;

    @Column(name = "data_sufficiency_date")
    private String dataSufficiencyDate;

    @Column(name = "completed_date")
    private String completedDate;

    @Column(name = "closed")
    private String closed;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "result")
//    private Result result;

//    @Column(name = "pdf_url")
//    private String pdfReportUrl;




    //no attribute constructor

    public BaseVerification(OfferingType offeringType, State state, int requestId, String closedReason, String closedRemarks, String created, String dataSufficiencyDate, String completedDate, String closed) {
        this.offeringType = offeringType;
        this.state = state;
        this.requestId = requestId;
        this.closedReason = closedReason;
        this.closedRemarks = closedRemarks;
        this.created = created;
        this.dataSufficiencyDate = dataSufficiencyDate;
        this.completedDate = completedDate;
        this.closed = closed;
    }

}
