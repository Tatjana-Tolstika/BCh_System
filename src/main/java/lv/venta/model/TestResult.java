package lv.venta.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="TestResults")
@Entity
public class TestResult {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "ResultID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long resultID;
	
	@NotNull
	@Column(name = "Comments")
	private String comments;
	
	@NotNull
	@Column(name="Minus")
	private double minus;
	
	//-----------Table Connections-------------------------------------
	@ManyToOne
	@JoinColumn(name="TaskID")
	private TestTask task;
	
}
