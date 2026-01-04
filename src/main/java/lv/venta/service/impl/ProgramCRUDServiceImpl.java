package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.model.StudyProgram;
import lv.venta.repo.IStudyProgramRepo;
import lv.venta.service.IProgramCRUDService;

public class ProgramCRUDServiceImpl implements IProgramCRUDService{

	@Autowired
	private IStudyProgramRepo programRepo;
	
	//======================================CRUD=======================================================
		//-------------------------------CREATE-----------------------------------
		@Override
		public void createProgram(String title, String degree, String abbreviation) throws Exception{
			if(title == null || degree == null || abbreviation == null || !title.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž ]+")|| !abbreviation.matches("[A-Z]+")) {
				throw new Exception("Incorrect input parameters!");
			}
			
			if(programRepo.existsByProgramTitleAndProgramDegreeAndAbbreviation(title,degree, abbreviation)) {
				throw new Exception("Program you want to create already exists!");
			}
			else {
				StudyProgram newProgram = new StudyProgram(title,degree, abbreviation);
				programRepo.save(newProgram);
			}
		}
		//-----------------------------------------------------------------------
		//------------------------------DELETE---------------------------------
		@Override 
		public void deleteProgram(long id)throws Exception{
			StudyProgram program = retrieveProgramById(id);
			programRepo.delete(program);
		}
		//-----------------------------------------------------------------------
		//--------------RETRIEVE--------------------------------------------------------------
		@Override 
		public StudyProgram retrieveProgramById(long id) throws Exception{
			if(id < 0) {
				throw new Exception("Choose correct ID!");
			}
			if(!programRepo.existsById(id)) {
				throw new Exception("Program with ID [ " + id + " ] doesn't exists!");
			}
			StudyProgram retrievedProgram = programRepo.findById(id).get();
					
			return retrievedProgram;
		}
		//------------------------------------------------------------------------------------
		//--------------------------UPDATE---------------------------------------
		@Override 
		public void updateProgramById(long id, String title, String degree, String abbreviation) throws Exception{
			StudyProgram programForUpdate = retrieveProgramById(id);
			if(title == null || degree == null || abbreviation == null || !title.matches("[A-Za-zĀāČčĒēĢģĪīĶķĻļŅņŠšŪūŽž ]+")|| !abbreviation.matches("[A-Z]+")) {
				throw new Exception("Incorrect input parameters!");
			}
			if(programForUpdate.getProgramTitle() != title) {
				programForUpdate.setProgramTitle(title);
			}
			if(programForUpdate.getProgramDegree() != degree) {
				programForUpdate.setProgramDegree(degree);
			}
			if(programForUpdate.getAbbreviation() != abbreviation) {
				programForUpdate.setAbbreviation(abbreviation);
			}
			
			programRepo.save(programForUpdate);
		}
		//-----------------------------------------------------------------------
		
	//=================================================================================================
		
		@Override 
		public ArrayList<StudyProgram> selectAllPrograms() throws Exception{
			if(programRepo.count() == 0) {
				throw new Exception("Programs list is empty!");
			}
			ArrayList<StudyProgram> result = (ArrayList<StudyProgram>) programRepo.findAll();
			return result;
		}
}
