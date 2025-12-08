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
	//--------------RETRIEVE--------------------------------------------------------------
	@Override 
	public Students retrieveById(long id) throws Exception{
		if(id < 0) {
			throw new Exception("Choose correect ID!");
		}
		if(!studentsRepo.existsById(id)) {
			throw new Exception("Student with ID [ " + id + " ] doesn't exists!");
		}
		Students retrievedStudent = studentsRepo.findById(id).get();
		
		return retrievedStudent;
	}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
	@Override 
	public void updateStudentById(long id, String name, String surname, String email, long matriculationNr) throws Exception{
		Students studentForUpdate = retrieveById(id);
		if(name == null || surname == null || email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$") || matriculationNr == 0) {
			throw new Exception ("Incorrect input parameters!");
		}
		if(studentForUpdate.getStudentName() != name) {
			studentForUpdate.setStudentName(name);
		}
		if(studentForUpdate.getStudentSurname() != surname) {
			studentForUpdate.setStudentSurname(surname);
		}
		if(studentForUpdate.getMatriculationNr() != matriculationNr) {
			studentForUpdate.setMatriculationNr(matriculationNr);
		}
		if (studentForUpdate.getEmail() != email) {
			studentForUpdate.setEmail(email);
		}
		
		studentsRepo.save(studentForUpdate);
	}
	//------------------------------------------------------------------------------------
	
	//--------------DELETE----------------------------------------------------------------
	@Override 
	public void DeleteStudent(long id)throws Exception{
		Students person = retrieveById(id);
		studentsRepo.delete(person);
	}
	//--------------------------------------------------------------------------------------
	
	//===========================END OF CRUD==================================================================
	
	
	
	
}
