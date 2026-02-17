package lv.venta.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Lecturers;

public interface ILecturersRepo extends CrudRepository<Lecturers, Long> {

	public abstract boolean existsByLecturerNameAndLecturerSurnameAndLecturerDegree(String name, String surname,
			String degree);

	public abstract Optional<Lecturers> findByUsername(String username);

}
