package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PANDoc extends Document{

//    @Id
//    private long id;


    private String panNumber;



}
