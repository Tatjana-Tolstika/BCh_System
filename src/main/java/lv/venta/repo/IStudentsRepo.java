package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Students;

public interface IStudentsRepo extends CrudRepository<Students, Long>{

}
