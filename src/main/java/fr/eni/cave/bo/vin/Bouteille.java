package fr.eni.cave.bo.vin;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder

@Entity
@Table(name = "CAV_BOTTLE")
public class Bouteille {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOTTLE_ID")
	private Integer id;

	@NotBlank
	@Size(min = 1, max = 250)
	@Column(name = "NAME", length = 250, unique = true, nullable = false)
	private String nom;
	
	@Column(name = "SPARKLING")
	private boolean petillant;
	
	@Size(max = 100)
	@Column(name = "VINTAGE", length = 100)
	private String millesime;
	
	@Min(1)
	@Column(name = "QUANTITY")
	private int quantite;

	@Min(1)
	@Column(name = "PRICE", precision = 2)
	private float prix;
	
	@NotNull
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	private Region region;

	@NotNull
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "COLOR_ID")
	private Couleur couleur;

}
