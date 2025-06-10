package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public Customer(int afm , String name, String email ,BigInteger phoneNumber){
        this.afm = afm;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Positive
    private int afm;

    @NotBlank
    @NotNull
    private String name;

    @Email
    private String email;

    @NotBlank
    @NotNull
    @Positive
    private BigInteger phoneNumber;
}
