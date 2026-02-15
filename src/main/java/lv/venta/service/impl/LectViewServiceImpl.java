package lv.venta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Lecturers;
import lv.venta.model.StudyCourses;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.service.ILectViewService;

@Service
public class LectViewServiceImpl implements ILectViewService{
	@Autowired
	private ILecturersRepo lecturersRepo;
	@Autowired 
	private IStudyCourseRepo coursesRepo;
	
	@Override
	public List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception{
		Lecturers lecturer = lecturersRepo.findById(lectId)
	        .orElseThrow(() -> new Exception("Lecturer not found"));
	
	    List<StudyCourses> courses = coursesRepo.findByLecturers(lecturer);
	
	    if(courses.isEmpty())
	        throw new Exception("This lecturer has no courses");
	
	    return courses;
	}
	

}
