package lv.venta.service;

public interface IStudentsCRUDService {

	public abstract void CreateStudent(String name, String surname, long matriculationNr, String email) throws Exception;

}
