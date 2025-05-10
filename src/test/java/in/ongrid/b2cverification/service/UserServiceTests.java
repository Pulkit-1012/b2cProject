package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.mappers.UserMapper;
import in.ongrid.b2cverification.model.dto.response.UserDTO;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.model.enums.UserType;
import in.ongrid.b2cverification.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    @InjectMocks
    UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, jwtService);
    }

    @Test
    public void userService_findAll_shouldReturnAll() {

        //ARRANGE
        User user1 = new User(1, "name1", "email1", "password1", 989898989, UserType.USER);
        User user2 = new User(2, "name2", "email2", "password2", 878787878, UserType.USER);
        List<User> mockUsersList = Arrays.asList(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(mockUsersList);

        //ACT
        List<User> resultList = userService.findAll();

        //ASSERT
        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(mockUsersList.size(), resultList.size());
        Assertions.assertEquals(mockUsersList.get(0), resultList.get(0));
        Assertions.assertEquals(mockUsersList.get(1), resultList.get(1));
        Assertions.assertEquals("name1", resultList.get(0).getUserName());
        Assertions.assertEquals("name2", resultList.get(1).getUserName());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    private User user;
    private UserDetails userDetails;

    @BeforeEach
    void setUpUserDetailsObject() {
        user = new User(1, "name1", "email1", "password1", 989898989, UserType.USER);
        userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Test
    public void userService_findById_shouldReturnUser() {

        //ARRANGE
        String mockToken = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(userDetails, user.getId())).thenReturn(mockToken);
        Mockito.when(jwtService.extractUsername(mockToken)).thenReturn(user.getUsername());

        //ACT
        UserDTO result = userService.findById(user.getId(), mockToken);

        //ASSERT
        Assertions.assertNotNull(result);
    }

}
