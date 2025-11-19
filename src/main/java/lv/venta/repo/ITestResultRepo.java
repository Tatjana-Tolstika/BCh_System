package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.TestResult;

public interface ITestResultRepo extends CrudRepository<TestResult, Long>{

}
