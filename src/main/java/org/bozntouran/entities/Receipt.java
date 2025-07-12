package org.bozntouran.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor  // πρεπει να υπαρχει παντα
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @PositiveOrZero
    private double price;

    @NotBlank
    private String filename;

    @PastOrPresent
    private LocalDateTime date;

    public Receipt(double price, LocalDateTime date, String filename) {
        this.price = price;
        this.date = date;
        this.filename = filename;
    }


}
