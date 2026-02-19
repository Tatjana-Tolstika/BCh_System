package lv.venta.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "MyAuthorityTable")
@Entity

public class Authorities {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "AuthoritiesId")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long authoritiesId;
	
	@NotNull
	@Pattern(regexp = "[A-Z]{3,10}")
	@Column(name = "Title")
	private String title;
	
	@OneToMany(mappedBy = "authority")
	@ToString.Exclude
	private Collection<Users> users;
	
	
	
	public Authorities(String inputTitle) {
		
		setTitle(inputTitle);
	}
	
}
