package fr.eni.cave.bo.client;

import fr.eni.cave.bo.vin.Bouteille;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "CAV_AVIS")
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVIS_ID")
    private Integer id;
    @NotBlank
    @Size(min = 10, max = 250)
    @Column(name = "COMMENT", length = 250, nullable = false)
    private String commentaire;
    @Min(1)
    @Max(5)
    @Column(name = "NOTE")
    private int note;
    @NotNull
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "BOTTLE_ID")
    private Bouteille bouteille;
}
