package in.ongrid.b2cverification.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pan_doc") //name may change
public class PANDoc extends Document{

//    @Id
//    private long id;

    @ManyToOne
    @JoinColumn(name = "doc_id")
    private Document document;

    private String panNumber;

    public PANDoc() {};


}
