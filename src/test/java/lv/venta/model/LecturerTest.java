package lv.venta.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LecturersTest {

    private static Lecturers lecturerGood;
    private static Lecturers lecturerDefault;
    private static Lecturers lecturerNull;

    @BeforeAll
    static void setUp() {
        lecturerGood    = new Lecturers("Jānis", "Bērziņš", "Dr.sc.ing.");
        lecturerDefault = new Lecturers();
        lecturerNull    = null;
    }

    @Test
    void testLecturerGood() {
        assertEquals("Jānis",       lecturerGood.getLecturerName());
        assertEquals("Bērziņš",    lecturerGood.getLecturerSurname());
        assertEquals("Dr.sc.ing.", lecturerGood.getLecturerDegree());
    }

    @Test
    void testLecturerNull() {
        assertThrows(NullPointerException.class, () -> lecturerNull.getLecturersId());
    }

    @Test
    void testLecturerSetter() {
        lecturerDefault.setLecturerName("Anna");
        lecturerDefault.setLecturerSurname("Kalniņa");
        lecturerDefault.setLecturerDegree("Mg.");
        assertEquals("Anna",    lecturerDefault.getLecturerName());
        assertEquals("Kalniņa", lecturerDefault.getLecturerSurname());
        assertEquals("Mg.",     lecturerDefault.getLecturerDegree());
    }

    @Test
    void testLecturerCoursesNotNull() {
        assertNotNull(lecturerGood.getCourses());
    }
}