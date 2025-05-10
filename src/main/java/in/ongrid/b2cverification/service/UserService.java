package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.dto.response.UserUpdateDTO;
import in.ongrid.b2cverification.model.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    UserDTO findById(long id, String token);
    User save(CreateUserRequest request);
    void deleteById(long id);

    UserDTO findByEmail(String email, String token);

    void updateUser(User user, UserUpdateDTO userUpdateDTO);
}
