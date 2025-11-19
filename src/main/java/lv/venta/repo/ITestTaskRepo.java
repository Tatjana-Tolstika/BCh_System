package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.TestTask;

public interface ITestTaskRepo extends CrudRepository<TestTask, Long>{

}
