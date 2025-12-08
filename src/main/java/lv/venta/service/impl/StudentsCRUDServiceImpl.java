package lv.venta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.model.Students;
import lv.venta.repo.IStudentsRepo;
import lv.venta.service.IStudentsCRUDService;

public class StudentsCRUDServiceImpl implements IStudentsCRUDService {
	@Autowired
	private IStudentsRepo studentsRepo;
	
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
}
