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
			if(name == null || surname == null || degree == null || !name.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")|| !surname.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")) {
				throw new Exception("Incorrect input parameters!");
			}
			
			if(lecturersRepo.existsByLecturerNameAndLecturerSurnameAndLecturerDegree(name,surname,degree)) {
				throw new Exception("Student you want to create already exists!");
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
				throw new Exception("Choose correect ID!");
			}
			if(!lecturersRepo.existsById(id)) {
				throw new Exception("Lecturer with ID [ " + id + " ] doesn't exists!");
			}
			Lecturers retrievedLecturer = lecturersRepo.findById(id).get();
			
			return retrievedLecturer;
		}
	//------------------------------------------------------------------------------------
	//---------------UPDATE---------------------------------------------------------------
		@Override 
		public void updateLecturerById(long id, String name, String surname, String degree) throws Exception{
			Lecturers lecturerForUpdate = retrieveLecturerById(id);
			if(name == null || surname == null ||!name.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+")|| !surname.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž]+") || degree == null ) {
				throw new Exception ("Incorrect input parameters!");
			}
			if(lecturerForUpdate.getLecturerName() != name) {
				lecturerForUpdate.setLecturerName(name);
			}
			if(lecturerForUpdate.getLecturerSurname() != surname) {
				lecturerForUpdate.setLecturerSurname(surname);
			}
			if(lecturerForUpdate.getLecturerDegree() != degree) {
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
