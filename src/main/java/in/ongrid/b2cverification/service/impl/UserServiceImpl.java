package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.mappers.UserMapper;
import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    //setting up basic constructor injection
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, JwtService jwtService) {
        this.userRepository = theUserRepository;
        this.jwtService = jwtService;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }



    //NOT WORKING (ILLEGAL BASE64 URL ERROR) 403forbidden on postman [earlier it was working]
    @Override
    public UserDTO findById(long id, String token) {
//        String tokenWithoutBearer = token.replace("Bearer ", "").trim();
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());

//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if (!Objects.equals(emailFromToken, user.getEmail())) {
//            throw new RuntimeException("Access denied: You can only view your own details.");
//        }

        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isEmpty() || !Objects.equals(emailFromToken, dbUser.get().getEmail())) {
            throw new RuntimeException("Unauthorized access or user not found");
        }

        return UserMapper.toDTO(dbUser.get());
    }

//    public UserDTO findById(long id) {
//
//        Optional<User> dbUser = userRepository.findById(id);
//
//
//
//        return UserMapper.toDTO(dbUser.get());
//    }



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
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByEmail(String email, String token) {
        return null;
    }
}
