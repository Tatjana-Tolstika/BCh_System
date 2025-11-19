package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Lecturers;

public interface ILecturersRepo extends CrudRepository<Lecturers, Long> {

}
