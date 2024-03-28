package cl.bci.techtest.application;

import cl.bci.techtest.infrastructure.presentation.rest.dto.UserDataRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> create(UserDataRequest userDataRequest);
}
