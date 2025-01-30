package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "user_type")
    private UserType userType;



    //no attribute constructor
    public User() {};

    public User(String userName, String email, String password, long phoneNumber, UserType userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }



}
