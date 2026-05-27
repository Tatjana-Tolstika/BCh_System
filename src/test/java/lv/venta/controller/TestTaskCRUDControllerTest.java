
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
import lv.venta.model.TestTask;
import lv.venta.service.ITestTaskCRUDService;

class TestTaskCRUDControllerTest {

    @Mock
    private ITestTaskCRUDService taskService;

    @InjectMocks
    private TestTaskCRUDController taskController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testGetAddTaskFormSuccess() {
        StudyCourses course = new StudyCourses("Software Engineering", 3);
        CourseTests test = new CourseTests("Test 1", "Design", 10, course);

        try {
            when(taskService.getTestById(1L)).thenReturn(test);

            mockMvc.perform(get("/admin/testTask/crud/add/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("create-TestTask"))
                    .andExpect(model().attributeExists("testTask"))
                    .andExpect(model().attributeExists("test"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAddTaskFormError() {
        try {
            when(taskService.getTestById(99L)).thenThrow(new Exception("Test not found!"));

            mockMvc.perform(get("/admin/testTask/crud/add/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }


    @Test
    void testGetDeleteTaskError() {
        try {
            doThrow(new Exception("Task not found!")).when(taskService).deleteTaskById(99L);

            mockMvc.perform(get("/admin/testTask/crud/delete/1/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetUpdateTaskFormSuccess() {
        StudyCourses course = new StudyCourses("Software Engineering", 3);
        CourseTests test = new CourseTests("Test 1", "Design", 10, course);
        TestTask task = new TestTask();
        task.setTest(test);

        try {
            when(taskService.retrieveTaskById(1L)).thenReturn(task);
            when(taskService.selectAllTests()).thenReturn(Arrays.asList(test));

            mockMvc.perform(get("/admin/testTask/crud/update/1/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("update-testTask"))
                    .andExpect(model().attributeExists("testTask"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetUpdateTaskFormError() {
        try {
            when(taskService.retrieveTaskById(99L)).thenThrow(new Exception("Task not found!"));

            mockMvc.perform(get("/admin/testTask/crud/update/1/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllTasksSuccess() {
        StudyCourses course = new StudyCourses("Software Engineering", 3);
        CourseTests test = new CourseTests("Test 1", "Design", 10, course);
        TestTask task = new TestTask();
        task.setTest(test);

        try {
            when(taskService.selectAllTasksByTest(1L)).thenReturn(new ArrayList<>(Arrays.asList(task)));

            mockMvc.perform(get("/admin/testTask/crud/all/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("testTasks-all"))
                    .andExpect(model().attributeExists("testTasks"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }

    @Test
    void testGetAllTasksError() {
        try {
            when(taskService.selectAllTasksByTest(99L)).thenThrow(new Exception("No tasks found!"));

            mockMvc.perform(get("/admin/testTask/crud/all/99"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("show-error"));
        } catch (Exception e) {
        	e.getMessage();
        }
    }
}