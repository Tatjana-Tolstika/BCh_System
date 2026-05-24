package lv.venta.repo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import lv.venta.model.CourseTests;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestStatus;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseTestRepoTest {

	 @Autowired
	 private ICourseTestRepo courseTestRepo;

    @Autowired
    private IStudyCourseRepo studyCourseRepo;

    @Test
    void testCreateCourseTest() {
        StudyCourses course = studyCourseRepo.save(new StudyCourses("Programming c#", 4));
        CourseTests ct = new CourseTests("Test 1", "First test", 20, course);
        CourseTests saved = courseTestRepo.save(ct);

        assertEquals("Test 1", saved.getTestTitle());
        assertEquals("First test", saved.getTestDescription());
        assertEquals(20, saved.getPoints());
        assertNotEquals(0,saved.getTestId());
    }

    @Test
    void testFindByCourse() {
        StudyCourses course = studyCourseRepo.save(new StudyCourses("Programming Java", 3));
        courseTestRepo.save(new CourseTests("Test A", "description test A", 10, course));
        courseTestRepo.save(new CourseTests("Test B", "description test B",  15, course));

        List<CourseTests> result = courseTestRepo.findByCourse(course);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByCourseAndStatus() {
        StudyCourses course = studyCourseRepo.save(new StudyCourses("Programming C++", 2));
        CourseTests ct = new CourseTests("Last test", "Last last test", 30, course);
        courseTestRepo.save(ct);

        List<CourseTests> result = courseTestRepo.findByCourseAndStatus(course, TestStatus.IN_PROCESS);
        assertEquals(1, result.size());
        assertEquals("Last test", result.get(0).getTestTitle());
    }

    @Test
    void testExistsByAllFields() {
        StudyCourses course = studyCourseRepo.save(new StudyCourses("Programming Python", 3));
        courseTestRepo.save(new CourseTests("Not last test", "Not last last test", 25, course));

        assertTrue(courseTestRepo.existsByTestTitleAndTestDescriptionAndPointsAndCourse(
                "Not last test", "Not last last test", 25, course));
        assertFalse(courseTestRepo.existsByTestTitleAndTestDescriptionAndPointsAndCourse(
                "Not last test", "Not last last test", 99, course));
    }
}