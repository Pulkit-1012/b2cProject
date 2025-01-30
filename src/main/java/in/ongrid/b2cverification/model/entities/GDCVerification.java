package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "gdc_verification")
public class GDCVerification extends BaseVerification{


    @ManyToOne
    @JoinColumn(name = "bv_id")
    private BaseVerification verification;

    public GDCVerification() {};

}
