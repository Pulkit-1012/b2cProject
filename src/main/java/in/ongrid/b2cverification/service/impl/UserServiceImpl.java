package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.BadRequestException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.mappers.UserMapper;
import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.dto.response.UserUpdateDTO;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.UserService;
import org.apache.commons.lang3.StringUtils;
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



    @Override
    public UserDTO findById(long id, String token) {
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());

        Optional<User> dbUser = userRepository.findById(id);

        if (dbUser.isEmpty() || !Objects.equals(emailFromToken, dbUser.get().getEmail())) {
            throw new UnauthorizedException("Access denied you cannot view this user's details!");
        }

        return UserMapper.toDTO(dbUser.get());
    }




    @Override
    public User save(CreateUserRequest request) {
        // validations
//        if(StringUtils.isBlank(request.getEmail())) throw new IllegalArgumentException("Email is required");
//        else if (StringUtils.isBlank(request.getPassword())) throw new IllegalArgumentException("Password is required");
//        else if (StringUtils.isBlank(request.getUserName())) throw new IllegalArgumentException("UserName is required");

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

    @Override
    public void updateUser(User user, UserUpdateDTO userUpdateDTO) {
        if(userUpdateDTO.getUserName() != null) {
            user.setUserName(userUpdateDTO.getUserName());
        }

        if(String.valueOf(userUpdateDTO.getPhoneNumber()).length()!= 10) {
            throw new BadRequestException("Phone number must be 10 digits");
        }
        else user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
    }
}
