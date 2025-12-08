package lv.venta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.model.Students;
import lv.venta.repo.IStudentsRepo;
import lv.venta.service.IStudentsCRUDService;

public class StudentsCRUDServiceImpl implements IStudentsCRUDService {
	@Autowired
	private IStudentsRepo studentsRepo;
	
	//=======================CRUD====================================================================================
	
	//----------------CREATE-------------------------------------------------------------
	@Override
	public void CreateStudent(String name, String surname, long matriculationNr, String email ) throws Exception {
		if(name == null || surname == null || email == null || matriculationNr < 1000000 ) {
			throw new Exception("Incorrect input parameters!");
		}
		
		if(studentsRepo.existsByNameAndSurnameAndMatriculationNrAndEmail(name,surname,matriculationNr, email)) {
			throw new Exception("Student you want to create already exists!");
		}
		else {
			Students newStudent = new Students(name, surname, matriculationNr, email);
			studentsRepo.save(newStudent);
		}
	}
	//------------------------------------------------------------------------------------
	
	//--------------DELETE----------------------------------------------------------------
	@Override 
	public void DeleteStudent(long id)throws Exception{
		Students person = retrieveById(id);
		studentsRepo.delete(person);
	}
	//--------------------------------------------------------------------------------------
	//================================================================================================================
	
	@Override 
	public Students retrieveById(long id) throws Exception{
		if(id < 0) {
			throw new Exception("Choose correect ID!");
		}
		if(!studentsRepo.existsById(id)) {
			throw new Exception("ID doesn't exists!");
		}
		Students retrievedStudent = studentsRepo.findById(id).get();
		
		return retrievedStudent;
	}
	
}
