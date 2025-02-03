package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "individual_name", nullable = false)
    private String individualName;

    @Column(name = "phone_number", nullable = false)
    private long phoneNumber;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;




    //no attribute constructor

    public Individual( String individualName, long phoneNumber, String fatherName, String motherName, String address, Date dob, String gender) {
        this.individualName = individualName;
        this.phoneNumber = phoneNumber;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
    }

}
