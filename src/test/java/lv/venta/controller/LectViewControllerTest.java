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
import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;
import lv.venta.service.ICourseTestCRUDService;
import lv.venta.service.ICoursesCRUDService;
import lv.venta.service.ILectViewService;
import lv.venta.service.IStudentsCRUDService;
import lv.venta.service.ITestResultCRUDService;
import lv.venta.service.ITestTaskCRUDService;

class LectViewControllerTest {

    @Mock private ILectViewService lectService;
    @Mock private ICourseTestCRUDService testService;
    @Mock private ICoursesCRUDService courseService;
    @Mock private ITestTaskCRUDService taskService;
    @Mock private ITestResultCRUDService resultService;
    @Mock private IStudentsCRUDService studentService;

    @InjectMocks
    private LectViewController lectViewController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lectViewController).build();
    }

    @Test
    void testGetAllCoursesSuccess() {
        Lecturers lecturer = new Lecturers("Janis", "Berzins", "Dr.");
        List<StudyCourses> courses = Arrays.asList(
                new StudyCourses("Databases", 4),
                new StudyCourses("Software Engineering", 3));

        try {
            when(lectService.getAuthorisedId()).thenReturn(lecturer);
            when(lectService.allCoursesForLecturer(lecturer.getLecturersId())).thenReturn(courses);

            mockMvc.perform(get("/professor/courses"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("lecturers-courses"))
                    .andExpect(model().attributeExists("courses"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllCoursesError() {
        try {
            when(lectService.getAuthorisedId()).thenThrow(new RuntimeException("Not authorized!"));

            mockMvc.perform(get("/professor/courses"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetCourseTestsSuccess() {
        StudyCourses course = new StudyCourses("Databases", 4);
        List<CourseTests> tests = Arrays.asList(
                new CourseTests("Test 1", "SQL and SQL", 10, course));

        try {
            when(lectService.allTestsByCourse(1)).thenReturn(tests);

            mockMvc.perform(get("/professor/courses/1/tests"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("lecturers-courseTests"))
                    .andExpect(model().attributeExists("allTests"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetCourseTestsNotFound() {
        try {
            when(lectService.allTestsByCourse(99)).thenThrow(new Exception("Course not found!"));

            mockMvc.perform(get("/professor/courses/99/tests"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAddTestForm() {
        StudyCourses course = new StudyCourses("Databases", 4);

        try {
            when(courseService.retrieveCourseById(1)).thenReturn(course);

            mockMvc.perform(get("/professor/courses/1/tests/add"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("lecturers-create-courseTest"))
                    .andExpect(model().attributeExists("course"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }
}