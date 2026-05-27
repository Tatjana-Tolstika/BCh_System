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
import lv.venta.model.StudyProgram;
import lv.venta.repo.IStudyProgramRepo;
import lv.venta.service.impl.ProgramCRUDServiceImpl;

class ProgramCRUDServiceTest {

    @Mock
    private IStudyProgramRepo programRepo;

    @InjectMocks
    private ProgramCRUDServiceImpl programService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ==================== createProgram ====================

    @Test
    void testCreateProgramSuccess() {
        when(programRepo.existsByProgramTitleAndProgramDegreeAndAbbreviation(
                "Computer Science", "Bachelor", "CS")).thenReturn(false);
        when(programRepo.save(any())).thenReturn(new StudyProgram("Computer Science", "Bachelor", "CS", 4));

        assertDoesNotThrow(() -> programService.createProgram("Computer Science", "Bachelor", "CS", 4));
        verify(programRepo, times(1)).save(any());
    }

    @Test
    void testCreateProgramAlreadyExists() {
        when(programRepo.existsByProgramTitleAndProgramDegreeAndAbbreviation(
                "Computer Science", "Bachelor", "CS")).thenReturn(true);

        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", "CS", 4));
        verify(programRepo, never()).save(any());
    }

    @Test
    void testCreateProgramTitleNull() {
        assertThrows(Exception.class, () -> programService.createProgram(null, "Bachelor", "CS", 4));
    }

    @Test
    void testCreateProgramDegreeNull() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", null, "CS", 4));
    }

    @Test
    void testCreateProgramAbbreviationNull() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", null, 4));
    }

    @Test
    void testCreateProgramTitleWithDigits() {
        assertThrows(Exception.class, () -> programService.createProgram("CS1", "Bachelor", "CS", 4));
    }

    @Test
    void testCreateProgramAbbreviationLowerCase() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", "cs", 4));
    }

    @Test
    void testCreateProgramLengthZero() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", "CS", 0));
    }

    @Test
    void testCreateProgramLengthNegative() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", "CS", -1));
    }

    @Test
    void testCreateProgramLengthTooLarge() {
        assertThrows(Exception.class, () -> programService.createProgram("Computer Science", "Bachelor", "CS", 5));
    }

    // ==================== retrieveProgramById ====================

    @Test
    void testRetrieveProgramByIdSuccess() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        try {
            StudyProgram result = programService.retrieveProgramById(1L);
            assertEquals("Computer Science", result.getProgramTitle());
            assertEquals("CS",               result.getAbbreviation());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    void testRetrieveProgramByIdNotFound() {
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> programService.retrieveProgramById(99L));
    }

    @Test
    void testRetrieveProgramByIdNegative() {
        assertThrows(Exception.class, () -> programService.retrieveProgramById(-1L));
    }

    // ==================== deleteProgram ====================

    @Test
    void testDeleteProgramSuccess() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertDoesNotThrow(() -> programService.deleteProgram(1L));
        verify(programRepo, times(1)).delete(sp);
    }

    @Test
    void testDeleteProgramNotFound() {
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> programService.deleteProgram(99L));
        verify(programRepo, never()).delete(any());
    }

    // ==================== updateProgramById ====================

    @Test
    void testUpdateProgramSameValues() {
        // If values are the same, setters are not called but save still runs
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertDoesNotThrow(() -> programService.updateProgramById(1L, "Computer Science", "Bachelor", "CS", 4));
        verify(programRepo, times(1)).save(sp);
    }

    @Test
    void testUpdateProgramAllNewValues() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertDoesNotThrow(() -> programService.updateProgramById(1L, "Business Administration", "Master", "BA", 2));
        verify(programRepo, times(1)).save(sp);
        assertEquals("Business Administration", sp.getProgramTitle());
        assertEquals("Master",                  sp.getProgramDegree());
        assertEquals("BA",                      sp.getAbbreviation());
        assertEquals(2,                         sp.getLength());
    }

    @Test
    void testUpdateProgramTitleNull() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertThrows(Exception.class, () -> programService.updateProgramById(1L, null, "Bachelor", "CS", 4));
    }

    @Test
    void testUpdateProgramDegreeNull() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertThrows(Exception.class, () -> programService.updateProgramById(1L, "Computer Science", null, "CS", 4));
    }

    @Test
    void testUpdateProgramAbbreviationNull() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertThrows(Exception.class, () -> programService.updateProgramById(1L, "Computer Science", "Bachelor", null, 4));
    }

    @Test
    void testUpdateProgramAbbreviationLowerCase() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertThrows(Exception.class, () -> programService.updateProgramById(1L, "Computer Science", "Bachelor", "cs", 4));
    }

    @Test
    void testUpdateProgramLengthTooLarge() {
        StudyProgram sp = new StudyProgram("Computer Science", "Bachelor", "CS", 4);
        when(programRepo.findById(1L)).thenReturn(Optional.of(sp));

        assertThrows(Exception.class, () -> programService.updateProgramById(1L, "Computer Science", "Bachelor", "CS", 5));
    }

    @Test
    void testUpdateProgramNotFound() {
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> programService.updateProgramById(99L, "Computer Science", "Bachelor", "CS", 4));
    }

    // ==================== selectAllPrograms ====================

    @Test
    void testSelectAllProgramsSuccess() {
        ArrayList<StudyProgram> programs = new ArrayList<>(Arrays.asList(
                new StudyProgram("Computer Science",      "Bachelor", "CS", 4),
                new StudyProgram("Business Administration", "Master",  "BA", 2)));

        when(programRepo.count()).thenReturn(2L);
        when(programRepo.findAll()).thenReturn(programs);

        try {
            ArrayList<StudyProgram> result = programService.selectAllPrograms();
            assertEquals(2, result.size());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    void testSelectAllProgramsEmpty() {
        when(programRepo.count()).thenReturn(0L);
        assertThrows(Exception.class, () -> programService.selectAllPrograms());
    }
}