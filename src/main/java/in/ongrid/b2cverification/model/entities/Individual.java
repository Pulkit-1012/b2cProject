package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Individual")
public class Individual extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_id")
    private long id;

    @Column(name = "individual_name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "professionsId")
    private String professionId;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "father_name")
    private String fathersName;

    @Column(name = "hasConsent")
    private boolean hasConsent;

    @Column(name = "consentText")
    private String consentText;

    @Column(name = "mother_name")
    private String mothersName;


    @Column(name = "ongrid_individual_id")
    private long onGridIndividualId;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;




    //no attribute constructor

    public Individual( String name, String city, String gender, String phone, String professionId, LocalDate dob, String fathersName, boolean hasConsent, String consentText, String mothersName, long onGridIndividualId) {
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.phone = phone;
        this.professionId = professionId;
        this.dob = dob;
        this.fathersName = fathersName;
        this.hasConsent = hasConsent;
        this.consentText = consentText;
        this.mothersName = mothersName;
        this.onGridIndividualId = onGridIndividualId;
    }

}
