package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Students;

import lv.venta.repo.IStudentsRepo;
import lv.venta.service.IStudentsCRUDService;
@Service
public class StudentsCRUDServiceImpl implements IStudentsCRUDService {
	@Autowired
	private IStudentsRepo studentsRepo;

	
	//=======================CRUD====================================================================================
	
	//----------------CREATE-------------------------------------------------------------
	@Override
	public void createStudent(String name, String surname, String matriculationNr, String email ) throws Exception {
		if(name == null || surname == null || email == null || matriculationNr ==null ) {
			throw new Exception("Incorrect input parameters!");
		}
		
		if(studentsRepo.existsByStudentNameAndStudentSurnameAndMatriculationNrAndEmail(name,surname,matriculationNr, email)) {
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
			throw new Exception("Choose correct ID!");
		}
		
		return studentsRepo.findById(id)
				.orElseThrow(() -> new Exception("Student with ID [ " + id + " ] doesn't exist!"));
	}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
	@Override 
	public void updateStudentById(long id, String name, String surname, String email, String matriculationNr) throws Exception{
		Students studentForUpdate = retrieveById(id);
		if(name == null || surname == null || email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || matriculationNr == null || !matriculationNr.matches("[0-9]{4,10}")) {
			throw new Exception ("Incorrect input parameters!");
		}
		if(!studentForUpdate.getStudentName().equals(name)) {
			studentForUpdate.setStudentName(name);
		}
		if(!studentForUpdate.getStudentSurname().equals(surname)) {
			studentForUpdate.setStudentSurname(surname);
		}
		if(!studentForUpdate.getMatriculationNr().equals(matriculationNr)) {
			studentForUpdate.setMatriculationNr(matriculationNr);
		}
		if (!studentForUpdate.getEmail().equals(email)) {
			studentForUpdate.setEmail(email);
		}
		
		studentsRepo.save(studentForUpdate);
	}
	//------------------------------------------------------------------------------------
	
	//--------------DELETE----------------------------------------------------------------

	@Override
	public void deleteStudentById(long id) throws Exception {
	    Students student = retrieveById(id);
	    studentsRepo.delete(student);
	}

	//--------------------------------------------------------------------------------------
	
	//===========================END OF CRUD==================================================================
	
	@Override 
	public ArrayList<Students> selectAllStudents() throws Exception{
		if(studentsRepo.count() == 0) {
			throw new Exception("Students list is empty!");
		}
		ArrayList<Students> result = (ArrayList<Students>) studentsRepo.findAll();
		return result;
	}
	
	
	
	
}
