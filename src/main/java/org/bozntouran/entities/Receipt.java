package org.bozntouran.entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/*
Υποχρεωτικά στοιχεία:
Κάθε απόδειξη πρέπει να περιλαμβάνει ΑΦΜ εκδότη,
ονοματεπώνυμο/εταιρία,
ποσό,
ΦΠΑ,
ημερομηνία και μοναδικό αριθμό απόδειξης.
*/

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor  // πρεπει να υπαρχει παντα
@Table(name = "receipt")
public class Receipt {
    public Receipt(double price,LocalDateTime date,String filename) {
        this.price = price;
        this.date = date;
        this.filename = filename;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @NotEmpty
    @Positive
    private double price;

    @NotBlank
    @NotEmpty
    private String filename;
    @NotBlank
    @NotEmpty
    private LocalDateTime date;


}
