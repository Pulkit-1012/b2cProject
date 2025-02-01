package in.ongrid.b2cverification.controllers;

import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    //basic constructor ijections
    private final UserService userService;

    public UserRestController(UserService theUserService) {
        this.userService = theUserService;
    }


    //adding a GET mapping endpoint "users" to return the list of all users
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }


    //adding Post mapping to add a user
    @PostMapping("/users")
    public User create(@RequestBody CreateUserRequest request) {

        User dbUser = userService.save(request);

        return dbUser;
    }
}
