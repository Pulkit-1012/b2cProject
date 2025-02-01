package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(CreateUserRequest request);
    void deleteById(Long id);
}
