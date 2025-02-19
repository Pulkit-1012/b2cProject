package in.ongrid.b2cverification.mappers;

import in.ongrid.b2cverification.model.dto.response.VerificationCardDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;

public final class VerificationMapper {

    private VerificationMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static VerificationCardDTO toDto(BaseVerification baseVerification) {
        return new VerificationCardDTO(
                baseVerification.getOfferingType(),
                baseVerification.getState(),
                baseVerification.getId()
        );
    }
}
