package lv.venta.dto;

import lv.venta.model.Students;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentsDTO {

    @Setter(AccessLevel.NONE)
    private long studentId;

    private String studentName;
    private String studentSurname;
    private String matriculationNr;
    private String email;

    public StudentsDTO(Students student) {
        this.studentId = student.getStudentId();
        this.studentName = student.getStudentName();
        this.studentSurname = student.getStudentSurname();
        this.matriculationNr = student.getMatriculationNr();
        this.email = student.getEmail();
    }
}