package in.ongrid.b2cverification.controllers;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.dto.response.UserUpdateDTO;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.IndividualService;
import in.ongrid.b2cverification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    //basic constructor ijections
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final IndividualService individualService;

    public UserRestController(UserService theUserService, JwtService jwtService, UserRepository userRepository, IndividualService individualService) {
        this.userService = theUserService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.individualService = individualService;
    }


    //adding a GET mapping endpoint "users" to return the list of all users (can only be viewed by the admin)
    @GetMapping("/")
    public List<User> findAll() {
        return userService.findAll();
    }




    //User visits his page OR Admin views a user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long userId, @RequestHeader("Authorization") String token) {
        UserDTO userDTO = userService.findById(userId, token);
        return ResponseEntity.ok(userDTO);
    }


    //returning all the individuals of the user
    @GetMapping("/{userId}/individuals")
    public ResponseEntity<List<IndividualDTO>> getIndividualsByUser(@PathVariable long userId, @RequestHeader("Authorization") String token) {
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User does not exist!");
        }

        if(!user.get().getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("You do not have permission to access this user!");
        }

        List<IndividualDTO> individuals = individualService.getIndividualsByUserId(userId);

        return ResponseEntity.ok(individuals);
    }


    //updating user details
    @PostMapping("/{userId}/update")
    public ResponseEntity<String> updateUser(@PathVariable long userId, @RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String token) {

        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User does not exist!");
        }

        if(!user.get().getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("You do not have permission to access this user!");
        }

        userService.updateUser(user.get(), userUpdateDTO);

        return ResponseEntity.ok("User updated successfully!");
    }
}
