package lv.venta.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;
import lv.venta.service.ICourseTestCRUDService;

class CourseTestCRUDControllerTest {

    @Mock
    private ICourseTestCRUDService testService;

    @InjectMocks
    private CourseTestCRUDController testController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    void testGetAddFormSuccess() {
        when(testService.selectAllCourse()).thenReturn(Arrays.asList(
                new StudyCourses("Databases", 4)));
        try {
            mockMvc.perform(get("/admin/courseTests/crud/add"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("create-courseTest"))
                    .andExpect(model().attributeExists("courseTest"))
                    .andExpect(model().attributeExists("courses"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }


    @Test
    void testGetDeleteError() {
        try {
            doThrow(new Exception("Test not found!")).when(testService).deleteTest(99L);

            mockMvc.perform(get("/admin/courseTests/crud/delete/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetUpdateFormSuccess() {
        StudyCourses course = new StudyCourses("Databases", 4);
        CourseTests test = new CourseTests("Last test", "SQL and SQL", 10, course);

        try {
            when(testService.retrieveTestById(1L)).thenReturn(test);
            when(testService.selectAllCourse()).thenReturn(Arrays.asList(course));

            mockMvc.perform(get("/admin/courseTests/crud/update/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("update-courseTest"))
                    .andExpect(model().attributeExists("courseTest"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetUpdateFormError() {
        try {
            when(testService.retrieveTestById(99)).thenThrow(new Exception("Test not found!"));

            mockMvc.perform(get("/admin/courseTests/crud/update/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllTestsSuccess() {
        StudyCourses course = new StudyCourses("Databases", 4);
        try {
			when(testService.selectAllTests()).thenReturn(new ArrayList<>(Arrays.asList(
			        new CourseTests("Last test", "SQL and SQL", 10, course))));
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
            mockMvc.perform(get("/admin/courseTests/crud/all"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("courseTests-all"))
                    .andExpect(model().attributeExists("courseTests"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllTestsError() {
        try {
            when(testService.selectAllTests()).thenThrow(new Exception("List is empty!"));

            mockMvc.perform(get("/admin/courseTests/crud/all"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }
}