package cl.bci.techtest.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "telefono")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "citycode", nullable = false)
    private String cityCode;

    @Column(name = "countrycode", nullable = false)
    private String countryCode;
}
