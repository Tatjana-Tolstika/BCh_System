package lv.venta.service;


import java.util.ArrayList;

import lv.venta.model.Lecturers;

public interface ILecturersCRUDService {

	public abstract void createLecturer(String name, String surname, String degree) throws Exception;

	public abstract Lecturers retrieveLecturerById(long id) throws Exception;

	public abstract void updateLecturerById(long id, String name, String surname, String degree) throws Exception;

	public abstract void deleteLecturer(long id) throws Exception;

	public abstract ArrayList<Lecturers> selectAllLecturers() throws Exception;

}
