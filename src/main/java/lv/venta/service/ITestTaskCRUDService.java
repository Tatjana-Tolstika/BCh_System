package lv.venta.service;

import java.util.ArrayList;
import java.util.List;

import lv.venta.model.CourseTests;
import lv.venta.model.TestTask;

public interface ITestTaskCRUDService {

	public abstract void createTask(CourseTests test, String description, double points, String notes) throws Exception;

	public abstract TestTask retrieveTaskById(long id) throws Exception;

	public abstract void updateTaskById(long id, long testId, String description, double points, String notes) throws Exception;

	public abstract void deleteTaskById(long id) throws Exception;

	public abstract ArrayList<TestTask> selectAllTasksByTest(long testId) throws Exception;

	public abstract List<CourseTests> selectAllTests();

	public abstract CourseTests getTestById(long testId) throws Exception;

	public abstract List<TestTask> selectAllTasks();



}
