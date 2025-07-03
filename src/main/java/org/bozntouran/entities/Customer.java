package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigInteger;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Positive
    private int afm;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private BigInteger phoneNumber;

    public Customer(int afm, String name, String email, BigInteger phoneNumber) {
        this.afm = afm;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
