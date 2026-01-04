package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudyProgram;

public interface IStudyProgramRepo extends CrudRepository<StudyProgram, Long>{


	public abstract boolean existsByProgramTitleAndProgramDegreeAndAbbreviation(String title, String degree,
			String abbreviation);

	

}
