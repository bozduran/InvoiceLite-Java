package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "manufacturer")
public class Manufacturer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @Email
    private String email;

    private BigInteger phoneNumber;

    @OneToMany
    @JoinColumn(name = "manufacturer_id")
    private List<Product> products;

    public void addProducts(@Valid Product product) {

        if (!products.contains(product)) {
            product.setManufacturer(this);
            products.add(product);
        }

    }

}
