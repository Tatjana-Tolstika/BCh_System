package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.StudyProgram;

public interface IProgramCRUDService {

	public abstract void createProgram(String title, String degree, String abbreviation, int length) throws Exception;

	public abstract StudyProgram retrieveProgramById(long id) throws Exception;

	public abstract void updateProgramById(long id, String title, String degree, String abbreviation, int length) throws Exception;

	public abstract void deleteProgram(long id) throws Exception;

	public abstract ArrayList<StudyProgram> selectAllPrograms() throws Exception;



}
