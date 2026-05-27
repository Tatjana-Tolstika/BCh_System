package lv.venta.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestStatus;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.service.impl.StudentViewServiceImpl;

class StudentViewServiceTest {

    @Mock private IStudyCourseRepo courseRepo;
    @Mock private IMyUserRepo userRepo;
    @Mock private ITestResultRepo resultsRepo;
    @Mock private ICourseTestRepo testRepo;

    @InjectMocks
    private StudentViewServiceImpl studentViewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCoursesForStudentSuccess() {
        StudyCourses c1 = new StudyCourses("Matemātika", 4);
        StudyCourses c2 = new StudyCourses("Fizika", 3);

        when(courseRepo.findByStudentProgramCourseStudentProgramStudentStudentId(1L))
                .thenReturn(Arrays.asList(c1, c2));

        try {
            List<StudyCourses> result = studentViewService.coursesForStudent(1L);
            assertEquals(2, result.size());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    void testAllTestsByCourseAndStatusSuccess() {
        StudyCourses course = new StudyCourses("Programming Java", 4);
        CourseTests t1 = new CourseTests("Test 1", "Description for first test", 10, course);
        t1.setStatus(TestStatus.PUBLISHED);

        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        when(testRepo.findByCourseAndStatus(course, TestStatus.PUBLISHED))
                .thenReturn(Arrays.asList(t1));

        try {
            List<CourseTests> result = studentViewService.allTestsByCourseAndStatus(1L);
            assertEquals(1, result.size());
            assertEquals(TestStatus.PUBLISHED, result.get(0).getStatus());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    void testAllTestsByCourseAndStatusNotFound() {
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> studentViewService.allTestsByCourseAndStatus(99L));
    }

    @Test
    void testResultsForStudentSuccess() {
        when(resultsRepo.findByTaskTestTestIdAndStudentProgramCourseStudentProgramStudentStudentId(1L, 1L))
                .thenReturn(Arrays.asList());

        try {
            var result = studentViewService.resultsForStudent(1L, 1L);
            assertNotNull(result);
        } catch (Exception e) {
        	e.getMessage();
        }
    }
}