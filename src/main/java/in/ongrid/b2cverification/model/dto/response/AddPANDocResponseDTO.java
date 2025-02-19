package in.ongrid.b2cverification.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class AddPANDocResponseDTO {
    private Long id;
    private String documentUID;
    private String nameAsPerDocument;
}
