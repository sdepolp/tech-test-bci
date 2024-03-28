package cl.bci.techtest.infrastructure.presentation.rest.dto;

import lombok.Data;

@Data
public class PhoneRequest {
    String number;
    String cityCode;
    String countryCode;
}
