package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Students;

public interface IStudentsRepo extends CrudRepository<Students, Long>{

	public abstract boolean existsByNameAndSurnameAndMatriculationNrAndEmail(String name, String surname, long matriculationNr,
			String email);

	public abstract Students findByNameAndSurnameAndMatriculationNrAndEmail(String name, String surname, long matriculationNr,
			String email);

}
