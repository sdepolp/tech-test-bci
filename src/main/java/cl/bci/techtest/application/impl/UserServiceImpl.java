package cl.bci.techtest.application.impl;

import cl.bci.techtest.domain.Phone;
import cl.bci.techtest.domain.User;
import cl.bci.techtest.infrastructure.persistence.PhoneRepository;
import cl.bci.techtest.infrastructure.persistence.UserRepository;
import cl.bci.techtest.infrastructure.presentation.rest.dto.PhoneRequest;
import cl.bci.techtest.infrastructure.presentation.rest.dto.UserDataRequest;
import cl.bci.techtest.infrastructure.security.jwt.JwtUtil;
import cl.bci.techtest.application.UserService;
import cl.bci.techtest.domain.UserDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;


    public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }
    @Override
    public ResponseEntity<?> create(UserDataRequest userDataRequest) {
        String validationError = validateUserData(userDataRequest);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        User user = createUser(userDataRequest);
        saveUser(user);

        if (userDataRequest.getPhones() != null) {
            savePhones(user, userDataRequest.getPhones());
        }

        UserDataDto userDataDto = mapToUserDataDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDataDto);
    }

    private String validateUserData(UserDataRequest userDataRequest) {
        if (userDataRequest == null ||
                StringUtils.isEmpty(userDataRequest.getEmail()) ||
                StringUtils.isEmpty(userDataRequest.getPassword())) {
            return "Correo electrónico y contraseña son obligatorios.";
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(userDataRequest.getEmail());
        if (!emailMatcher.matches()) {
            return "El correo electrónico no tiene un formato válido.";
        }

        if (userRepository.existsByEmail(userDataRequest.getEmail())) {
            return "El correo electrónico ya está registrado.";
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).*$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(userDataRequest.getPassword());
        if (!passwordMatcher.matches()) {
            return "La contraseña no cumple con el formato requerido.";
        }

        return null;
    }

    private User createUser(UserDataRequest userDataRequest) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(userDataRequest.getEmail());
        user.setPassword(userDataRequest.getPassword());
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setToken(JwtUtil.generateToken(userDataRequest.getEmail()));
        user.setActive(true);
        return user;
    }

    private void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            log.error("Error al guardar el usuario en el repositorio: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error interno al guardar el usuario.");
        }
    }

    private void savePhones(User user, List<PhoneRequest> phoneRequests) {
        try {
            for (PhoneRequest phoneRequest : phoneRequests) {
                Phone phone = new Phone();
                phone.setUserEmail(user.getEmail());
                phone.setNumber(phoneRequest.getNumber());
                phone.setCityCode(phoneRequest.getCityCode());
                phone.setCountryCode(phoneRequest.getCountryCode());
                phoneRepository.save(phone);
            }
        } catch (DataAccessException e) {
            log.error("Error al guardar los teléfonos en el repositorio: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error interno al guardar los teléfonos.");
        }
    }

    private UserDataDto mapToUserDataDto(User user) {
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setId(user.getId().toString());
        userDataDto.setCreated(user.getCreated());
        userDataDto.setModified(user.getModified());
        userDataDto.setLastLogin(user.getLastLogin());
        userDataDto.setToken(user.getToken());
        userDataDto.setActive(user.isActive());
        return userDataDto;
    }
}
