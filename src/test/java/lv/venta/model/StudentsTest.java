package lv.venta.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StudentsTest {

    private static Students studentGood;
    private static Students studentDefault;
    private static Students studentNull;

    @BeforeAll
    static void setUp() {
        studentGood    = new Students("Pēteris", "Kalniņš", "20231234", "peteris@example.com");
        studentDefault = new Students();
        studentNull    = null;
    }

    @Test
    void testStudentGood() {
        assertEquals("Pēteris",             studentGood.getStudentName());
        assertEquals("Kalniņš",             studentGood.getStudentSurname());
        assertEquals("20231234",            studentGood.getMatriculationNr());
        assertEquals("peteris@example.com", studentGood.getEmail());
    }

    @Test
    void testStudentNull() {
        assertThrows(NullPointerException.class, () -> studentNull.getStudentId());
    }

    @Test
    void testStudentSetter() {
        studentDefault.setStudentName("Liga");
        studentDefault.setStudentSurname("Ozola");
        studentDefault.setMatriculationNr("20241111");
        studentDefault.setEmail("liga@example.com");
        assertEquals("Liga",           studentDefault.getStudentName());
        assertEquals("Ozola",          studentDefault.getStudentSurname());
        assertEquals("20241111",       studentDefault.getMatriculationNr());
        assertEquals("liga@example.com", studentDefault.getEmail());
    }

    @Test
    void testStudentIdDefault() {
        assertEquals(0L, studentDefault.getStudentId());
    }
}