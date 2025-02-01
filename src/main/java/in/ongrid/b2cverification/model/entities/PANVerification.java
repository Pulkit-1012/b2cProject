package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class PANVerification extends BaseVerification {

    @OneToOne
    @JoinColumn(name = "pan_doc_document_id")
    private PANDoc panDoc;



    public PANVerification() {};

}
