package in.ongrid.b2cverification.mappers;

import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;

public final class IndividualMapper {
    private IndividualMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static IndividualDTO toDTO(Individual individual) {
        return new IndividualDTO( individual.getIndividualName(), individual.getPhoneNumber(), individual.getFatherName(), individual.getMotherName(), individual.getAddress(), individual.getDob(), individual.getGender());
    }
}
