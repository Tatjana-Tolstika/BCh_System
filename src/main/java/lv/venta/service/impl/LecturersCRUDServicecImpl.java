package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Lecturers;
import lv.venta.repo.ILecturersRepo;
import lv.venta.service.ILecturersCRUDService;

@Service
public class LecturersCRUDServicecImpl implements ILecturersCRUDService{
	@Autowired
	private ILecturersRepo lecturersRepo;
	
	//========================CRUD=============================================
	//----------------CREATE-------------------------------------------------------------
		@Override
		public void createLecturer(String name, String surname, String degree ) throws Exception {
			if(name == null || surname == null || degree == null 
					|| !name.matches("^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+([\\s-][A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]++)*$")
					|| !surname.matches("[A-Z]{1}[a-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")){
				throw new Exception("Incorrect input parameters!");
			}
			
			if(lecturersRepo.existsByLecturerNameAndLecturerSurnameAndLecturerDegree(name,surname,degree)) {
				throw new Exception("Professor you want to create already exists!");
			}
			else {
				Lecturers newLecturer = new Lecturers(name, surname, degree);
				lecturersRepo.save(newLecturer);
			}
		}
	//------------------------------------------------------------------------------------
	//--------------RETRIEVE--------------------------------------------------------------
		@Override 
		public Lecturers retrieveLecturerById(long id) throws Exception{
			if(id < 0) {
				throw new Exception("Choose correct ID!");
			}
			Lecturers retrievedLecturer = lecturersRepo.findById(id)
					.orElseThrow(() -> new Exception("Lecturer with ID [ " + id + " ] doesn't exist!"));
			
			return retrievedLecturer;
		}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
		@Override 
		public void updateLecturerById(long id,String name, String surname, String degree) throws Exception{
			Lecturers lecturerForUpdate = retrieveLecturerById(id);
			if(name == null || surname == null ||!name.matches("^[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]+([\\s-][A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž]++)*$")
					|| !surname.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+") || degree == null ) {
				throw new Exception ("Incorrect input parameters!");
			}
			
			if(!lecturerForUpdate.getLecturerName().equals(name)) {
				lecturerForUpdate.setLecturerName(name);
			}
			if(!lecturerForUpdate.getLecturerSurname().equals(surname)) {
				lecturerForUpdate.setLecturerSurname(surname);
			}
			if(!lecturerForUpdate.getLecturerDegree().equals(degree)) {
				lecturerForUpdate.setLecturerDegree(degree);
			}
			
			lecturersRepo.save(lecturerForUpdate);
		}
	//------------------------------------------------------------------------------------
	//--------------DELETE----------------------------------------------------------------
		@Override 
		public void deleteLecturer(long id)throws Exception{
			Lecturers person = retrieveLecturerById(id);
			lecturersRepo.delete(person);
		}
	//--------------------------------------------------------------------------------------
		
		@Override 
		public ArrayList<Lecturers> selectAllLecturers() throws Exception{
			if(lecturersRepo.count() == 0) {
				throw new Exception("Lecturers list is empty!");
			}
			ArrayList<Lecturers> result = (ArrayList<Lecturers>) lecturersRepo.findAll();
			return result;
		}
	//=========================================================================
}
