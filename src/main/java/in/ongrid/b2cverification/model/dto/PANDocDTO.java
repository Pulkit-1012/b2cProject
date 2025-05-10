package in.ongrid.b2cverification.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class PANDocDTO {
    private String nameAsPerDocument;

    private String documentUID;
}
