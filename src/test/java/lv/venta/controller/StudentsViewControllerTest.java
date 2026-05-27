package lv.venta.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import lv.venta.model.CourseTests;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestStatus;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.ILectViewService;
import lv.venta.service.IStudentViewService;
import lv.venta.service.ITestResultCRUDService;

class StudentsViewControllerTest {

    @Mock private IStudentViewService studentService;
    @Mock private ILectViewService lectService;
    @Mock private ITestResultCRUDService resultService;
    @Mock private ICourseTestCRUDService testService;

    @InjectMocks
    private StudentViewController studentViewController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentViewController).build();
    }

    @Test
    void testGetAllCoursesSuccess() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "p@ex.lv");
        List<StudyCourses> courses = Arrays.asList(
                new StudyCourses("Databases", 4),
                new StudyCourses("Software Engineering", 3));

        try {
            when(studentService.getAuthorisedId()).thenReturn(student);
            when(studentService.coursesForStudent(student.getStudentId())).thenReturn(courses);

            mockMvc.perform(get("/student/courses"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("student-courses"))
                    .andExpect(model().attributeExists("courses"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllCoursesError() {
        try {
            when(studentService.getAuthorisedId()).thenThrow(new RuntimeException("Not authorized!"));

            mockMvc.perform(get("/student/courses"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetCourseTestsSuccess() {
        Students student = new Students("Peteris", "Kalnins", "20231234", "p@ex.lv");
        StudyCourses course = new StudyCourses("Databases", 4);
        CourseTests t1 = new CourseTests("Test 1", "SQL and SQL", 10, course);
        t1.setStatus(TestStatus.PUBLISHED);

        try {
            when(studentService.getAuthorisedId()).thenReturn(student);
            when(studentService.allTestsByCourseAndStatus(1)).thenReturn(Arrays.asList(t1));
            when(lectService.getStudentResult(t1.getTestId(), student.getStudentId())).thenReturn(8.0);

            mockMvc.perform(get("/student/courses/1/tests"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("student-courseTests"))
                    .andExpect(model().attributeExists("allTests"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetCourseTestsNotFound() {
        try {
            Students student = new Students("Peteris", "Kalnins", "20231234", "p@ex.lv");
            when(studentService.getAuthorisedId()).thenReturn(student);
            when(studentService.allTestsByCourseAndStatus(99))
                    .thenThrow(new Exception("Course not found!"));

            mockMvc.perform(get("/student/courses/99/tests"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }
}