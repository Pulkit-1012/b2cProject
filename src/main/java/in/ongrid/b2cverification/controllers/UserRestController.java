package in.ongrid.b2cverification.controllers;

import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    //basic constructor ijections
    private final UserService userService;

    public UserRestController(UserService theUserService) {
        this.userService = theUserService;
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
}
