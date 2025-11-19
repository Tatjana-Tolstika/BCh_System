package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.StudentProgram;

public interface IStudentProgramRepo  extends CrudRepository<StudentProgram, Long>{

}
