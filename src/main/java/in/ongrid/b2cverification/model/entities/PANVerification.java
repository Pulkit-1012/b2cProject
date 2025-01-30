package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "pan_verification")
public class PANVerification extends BaseVerification {


    @ManyToOne
    @JoinColumn(name = "bv_id")
    private BaseVerification verification;



    public PANVerification() {};

}
