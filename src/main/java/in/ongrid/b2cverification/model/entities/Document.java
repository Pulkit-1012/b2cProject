package in.ongrid.b2cverification.model.entities;

import in.ongrid.b2cverification.model.enums.DocType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "individual_id")
    private Individual individual;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private DocType docType;



    public Document(DocType docType) {
        this.docType = docType;
    }


}
