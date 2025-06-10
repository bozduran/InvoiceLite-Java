package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product {

    public Product(String name,String description ,String barcode
    ,double price ,int quantity ,Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Manufacturer manufacturer;

    @NotBlank
    @NotEmpty
    private String barcode;

    @Positive
    @NotEmpty
    @NotNull
    private double price;

    @PositiveOrZero
    @NotEmpty
    private int quantity;


    public String getBarcode(){
        return barcode;
    }
}
