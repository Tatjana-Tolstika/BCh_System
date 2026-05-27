package lv.venta.repo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import lv.venta.model.Students;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentsRepoTest {

    @Autowired
    private IStudentsRepo studentsRepo;

    @Test
    void testCreateStudent() {
        Students s = new Students("Pēteris", "Kalniņš", "20231234", "peteris@example.com");
        Students saved = studentsRepo.save(s);

        assertEquals("Pēteris",             saved.getStudentName());
        assertEquals("Kalniņš",             saved.getStudentSurname());
        assertEquals("20231234",            saved.getMatriculationNr());
        assertEquals("peteris@example.com", saved.getEmail());
        assertNotEquals(0, saved.getStudentId());
    }

    @Test
    void testExistsByStudentId() {
        Students s = new Students("Liga", "Ozola", "20241111", "liga@example.com");
        Students saved = studentsRepo.save(s);

        assertTrue(studentsRepo.existsByStudentId(saved.getStudentId()));
        assertFalse(studentsRepo.existsByStudentId(99999));
    }

    @Test
    void testExistsByAllFields() {
        Students s = new Students("Anna", "Bērziņa", "20231001", "anna@example.com");
        studentsRepo.save(s);

        assertTrue(studentsRepo.existsByStudentNameAndStudentSurnameAndMatriculationNrAndEmail(
                "Anna", "Bērziņa", "20231001", "anna@example.com"));
        assertFalse(studentsRepo.existsByStudentNameAndStudentSurnameAndMatriculationNrAndEmail(
                "Anna", "Bērziņa", "00000000", "anna@example.com"));
    }

    @Test
    void testUpdateStudent() {
        Students s = new Students("Jānis", "Kalniņš", "20231234", "janis@example.com");
        Students saved = studentsRepo.save(s);

        saved.setEmail("janis.new@example.com");
        Students updated = studentsRepo.save(saved);

        assertEquals("janis.new@example.com", updated.getEmail());
    }
}