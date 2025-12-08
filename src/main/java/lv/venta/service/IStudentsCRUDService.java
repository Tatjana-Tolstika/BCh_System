package lv.venta.service;

import lv.venta.model.Students;

public interface IStudentsCRUDService {

	public abstract void CreateStudent(String name, String surname, long matriculationNr, String email) throws Exception;

	public abstract void DeleteStudent(long id) throws Exception;

	public abstract Students retrieveById(long Id) throws Exception;

	

}
