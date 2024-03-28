package cl.bci.techtest.infrastructure.presentation.rest;

import cl.bci.techtest.infrastructure.presentation.rest.dto.UserDataRequest;
import cl.bci.techtest.application.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersControllerTest {

    private UsersController usersController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        usersController = new UsersController(userService);
    }

    @Test
    void createUser_ReturnsHttpStatus201_WhenUserDataIsValid() {

        UserDataRequest userDataRequest = new UserDataRequest();
        userDataRequest.setEmail("sdepolp@gmail.com");

        when(userService.create(userDataRequest)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));


        ResponseEntity<?> responseEntity = usersController.create(userDataRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void createUser_ReturnsHttpStatus400_WhenUserDataIsInvalid() {
        UserDataRequest userDataRequest = new UserDataRequest();
        userDataRequest.setEmail("invalidEmail");
        userDataRequest.setPassword("examplePassword");
        userDataRequest.setPhones(Collections.emptyList());
        when(userService.create(userDataRequest))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        ResponseEntity<?> responseEntity = usersController.create(userDataRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
