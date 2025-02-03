package in.ongrid.b2cverification.mappers;

import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.entities.User;

public final class UserMapper {
    private UserMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getPhoneNumber());
    }
}
