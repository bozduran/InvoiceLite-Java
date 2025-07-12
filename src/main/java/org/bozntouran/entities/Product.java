package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    private String description;

    @ManyToOne
    private Manufacturer manufacturer;

    @NotBlank
    private String barcode;

    @Positive
    @NotNull
    private double price;

    @PositiveOrZero
    private int quantity;

    public Product(String name, String description, String barcode
            , double price, int quantity, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

}
