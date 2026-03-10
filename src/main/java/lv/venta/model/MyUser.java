package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "UserTable")
@Entity
public class MyUser {
	
	@Setter(value = AccessLevel.NONE) 
	@Column(name = "IDu") 
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@NotNull
	@Pattern(regexp =  "[A-Za-z.0-9]{3,30}")
	@Column(name = "Username")
	private String username;
	
	@NotNull
	//@Pattern(regexp =  "[A-Za-z0-9]{2,100}")
	@Column(name = "Password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "AuthoritiesId")
	private MyAuthority authority;
	
	@OneToOne
	@JoinColumn(name = "lecturer_id", unique = true)
    private Lecturers lecturer;
	
	@OneToOne
	@JoinColumn(name = "student_id", unique = true)
    private Students student;
	
	
	public MyUser(String username, String password, MyAuthority authority, Lecturers lecturer) {
	    setUsername(username);
	    setPassword(password);
	    setAuthority(authority);
	    setLecturer(lecturer);
	    this.student = null;
	}

	public MyUser(String username, String password, MyAuthority authority, Students student) {
	    setUsername(username);
	    setPassword(password);
	    setAuthority(authority);
	    setStudent(student);
	    this.lecturer = null;
	}

}
