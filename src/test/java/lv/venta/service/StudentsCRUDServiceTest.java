package lv.venta.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import lv.venta.model.Students;
import lv.venta.repo.IStudentsRepo;
import lv.venta.service.impl.StudentsCRUDServiceImpl;

class StudentsCRUDServiceTest {

    @Mock
    private IStudentsRepo studentsRepo;

    @InjectMocks
    private StudentsCRUDServiceImpl studentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudentSuccess() {
        when(studentsRepo.existsByStudentNameAndStudentSurnameAndMatriculationNrAndEmail(
                "Peteris", "Kalnins", "20231234", "peteris@example.com")).thenReturn(false);

        assertDoesNotThrow(() -> studentsService.createStudent(
                "Peteris", "Kalnins", "20231234", "peteris@example.com"));
        verify(studentsRepo, times(1)).save(any());
    }

    @Test
    void testCreateStudentNullInput() {
        assertThrows(Exception.class, () -> studentsService.createStudent(
                null, "Kalnins", "20231234", "peteris@example.com"));
    }

    @Test
    void testCreateStudentAlreadyExists() {
        when(studentsRepo.existsByStudentNameAndStudentSurnameAndMatriculationNrAndEmail(
                "Peteris", "Kalnins", "20231234", "peteris@example.com")).thenReturn(true);

        assertThrows(Exception.class, () -> studentsService.createStudent(
                "Peteris", "Kalnins", "20231234", "peteris@example.com"));
        verify(studentsRepo, never()).save(any());
    }

    @Test
    void testRetrieveByIdSuccess() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "peteris@example.com");
        when(studentsRepo.findById(1L)).thenReturn(Optional.of(student));

        try {
            Students result = studentsService.retrieveById(1L);
            assertEquals("Peteris", result.getStudentName());
        } catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void testRetrieveByIdNegative() {
        assertThrows(Exception.class, () -> studentsService.retrieveById(-1L));
    }

    @Test
    void testRetrieveByIdNotFound() {
        when(studentsRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> studentsService.retrieveById(99L));
    }

    @Test
    void testUpdateStudentSuccess() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "peteris@example.com");
        when(studentsRepo.findById(1L)).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> studentsService.updateStudentById(
                1L, "Liga", "Ozola", "liga@example.com", "20241111"));
        verify(studentsRepo, times(1)).save(student);
        assertEquals("Liga", student.getStudentName());
    }

    @Test
    void testUpdateStudentInvalidEmail() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "peteris@example.com");
        when(studentsRepo.findById(1L)).thenReturn(Optional.of(student));

        assertThrows(Exception.class, () -> studentsService.updateStudentById(
                1L, "Liga", "Ozola", "notanemail", "20241111"));
    }

    @Test
    void testUpdateStudentInvalidMatriculation() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "peteris@example.com");
        when(studentsRepo.findById(1L)).thenReturn(Optional.of(student));

        assertThrows(Exception.class, () -> studentsService.updateStudentById(
                1L, "Liga", "Ozola", "liga@example.com", "AB"));
    }

    @Test
    void testDeleteStudentSuccess() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "peteris@example.com");
        when(studentsRepo.findById(1L)).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> studentsService.deleteStudentById(1L));
        verify(studentsRepo, times(1)).delete(student);
    }

    @Test
    void testSelectAllStudentsSuccess() {
        when(studentsRepo.count()).thenReturn(2L);
        when(studentsRepo.findAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Students("Peteris", "Kalnins",  "20231234", "peteris@example.com"),
                new Students("Liga",    "Ozola",     "20241111", "liga@example.com"))));

        try {
            assertEquals(2, studentsService.selectAllStudents().size());
        } catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void testSelectAllStudentsEmpty() {
        when(studentsRepo.count()).thenReturn(0L);
        assertThrows(Exception.class, () -> studentsService.selectAllStudents());
    }
}