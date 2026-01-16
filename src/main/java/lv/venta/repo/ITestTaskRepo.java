package lv.venta.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.CourseTests;
import lv.venta.model.TestTask;

public interface ITestTaskRepo extends CrudRepository<TestTask, Long>{

	public abstract boolean existsByTestAndTaskDescriptionAndMaxPoints(CourseTests test, String description, double points);

	public abstract boolean existsByTaskId(long id);


	public abstract ArrayList<TestTask> findByTest(CourseTests test);

}
