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
import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudentProgramCourseRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.repo.ITestTaskRepo;
import lv.venta.service.impl.CourseTestCRUDServiceImpl;
import lv.venta.service.impl.LectViewServiceImpl;

class LectViewServiceTest {

    @Mock private ILecturersRepo lecturersRepo;
    @Mock private IStudyCourseRepo coursesRepo;
    @Mock private ICourseTestRepo testRepo;
    @Mock private IMyUserRepo userRepo;
    @Mock private ITestTaskRepo taskRepo;
    @Mock private IStudentProgramCourseRepo spcRepo;
    @Mock private ITestResultRepo resultRepo;
    @Mock private CourseTestCRUDServiceImpl courseTestService;

    @InjectMocks
    private LectViewServiceImpl lectViewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllCoursesForLecturerSuccess() {
        Lecturers lecturer = new Lecturers("Jānis", "Bērziņš", "Dr.");
        StudyCourses c1 = new StudyCourses("Programming Java", 4);
        StudyCourses c2 = new StudyCourses("Programming Python", 3);

        when(lecturersRepo.findById(1L)).thenReturn(Optional.of(lecturer));
        when(coursesRepo.findByLecturers(lecturer)).thenReturn(Arrays.asList(c1, c2));

        try {
            List<StudyCourses> result = lectViewService.allCoursesForLecturer(1);
            assertEquals(2, result.size());
        } catch (Exception e) {
             e.getMessage();
        }
    }

    @Test
    void testAllCoursesForLecturerNotFound() {
        when(lecturersRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> lectViewService.allCoursesForLecturer(99));
    }

    @Test
    void testAllTestsByCourseSuccess() {
        StudyCourses course = new StudyCourses("Programming Java", 4);
        CourseTests t1 = new CourseTests("Test 1", "Description for first", 10, course);
        CourseTests t2 = new CourseTests("Test 2", "Description for second",  10, course);

        when(coursesRepo.findById(1L)).thenReturn(Optional.of(course));
        when(testRepo.findByCourse(course)).thenReturn(Arrays.asList(t1, t2));

        try {
            List<CourseTests> result = lectViewService.allTestsByCourse(1);
            assertEquals(2, result.size());
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testAllTestsByCourseNotFound() {
        when(coursesRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> lectViewService.allTestsByCourse(99));
    }

    @Test
    void testTestPointsCounterCourseNotFound() {
        when(testRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> lectViewService.testPointsCounter(99));
    }
}