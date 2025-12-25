package lv.venta.service;

import java.util.ArrayList;


import lv.venta.model.Students;


public interface IStudentsCRUDService {

	public abstract void CreateStudent(String name, String surname, String matriculationNr, String email) throws Exception;

	public abstract void DeleteStudentById(long id) throws Exception;

	public abstract Students retrieveById(long Id) throws Exception;

	public abstract void updateStudentById(long id, String name, String surname, String email, String matriculationNr) throws Exception;

	public abstract ArrayList<Students> selectAllStudents() throws Exception;


	

	

}
