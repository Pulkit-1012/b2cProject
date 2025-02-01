package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PANDoc extends Document{

//    @Id
//    private long id;


    private String panNumber;

    public PANDoc() {};


}
