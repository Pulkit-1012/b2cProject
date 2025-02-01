package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    //setting up basic constructor injection
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        this.userRepository = theUserRepository;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> dbUser = userRepository.findById(id);

        if(dbUser.isPresent()) {
            return dbUser.get();
        }

        else throw new RuntimeException("User with id " + id + " not found");
    }

    @Override
    public User save(CreateUserRequest request) {
        // Add validations

        User user = new User();
        user.setUserType(request.getUserType());
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
