package cl.bci.techtest.infrastructure.presentation.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDataRequest {
    String email;
    String password;
    List<PhoneRequest> phones;
}
