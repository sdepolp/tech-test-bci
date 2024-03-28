package cl.bci.techtest.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDataDto {
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
}
