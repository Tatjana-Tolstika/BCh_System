package lv.venta.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lv.venta.model.CourseTests;
import lv.venta.model.MyUser;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.model.TestStatus;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.service.IStudentViewService;
@Service
public class StudentViewServiceImpl implements IStudentViewService{

	@Autowired
	private IStudyCourseRepo courseRepo;
	@Autowired
	private IMyUserRepo userRepo;
	@Autowired
	private ITestResultRepo resultsRepo;
	@Autowired
	private ICourseTestRepo testRepo;
	
	@Override
	public List<StudyCourses> coursesForStudent(long studentId) throws Exception{
		List<StudyCourses> courses = courseRepo.findByStudentProgramCourseStudentProgramStudentStudentId(studentId);
		return courses;
	}
	
	@Override
	public Students getAuthorisedId() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = auth.getName();

	    MyUser user = userRepo.findByUsername(currentUsername);

	    if (user != null && user.getStudent() != null) {
	        return user.getStudent();
	    }
	    
	    return null;
	}
	
	
	@Override
	public List<TestResult> resultsForStudent(long studentId, long testId) throws Exception{
		List<TestResult> results = resultsRepo.findByTaskTestTestIdAndStudentProgramCourseStudentProgramStudentStudentId(testId, studentId);
		return results;
	}
	
	@Override
	public List<CourseTests> allTestsByCourseAndStatus(long courseId) throws Exception{
		StudyCourses course = courseRepo.findById(courseId)
				.orElseThrow(()-> new Exception("Course not found!"));
		List<CourseTests> tests = testRepo.findByCourseAndStatus(course, TestStatus.PUBLISHED);
//		if(tests.isEmpty()) {
//			throw new Exception("This course has no tests!");
//		}
		return tests;
		
	}
	
	//------------------------FILES_UPLOADING------------------------------
	
	private void unzip(Path zipFile, Path targetDir) throws Exception {
	    try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
	        ZipEntry entry;
	        while ((entry = zis.getNextEntry()) != null) {
	            
	            Path newPath = targetDir.resolve(entry.getName()).normalize();
	            
	            if (!newPath.startsWith(targetDir)) {
	                throw new Exception("Drošības kļūda: ZIP fails mēģina rakstīt ārpus mērķa mapes!");
	            }

	            if (entry.isDirectory()) {
	                Files.createDirectories(newPath);
	            } else {
	                
	                if (newPath.getParent() != null) {
	                    Files.createDirectories(newPath.getParent());
	                }
	                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
	            }
	            zis.closeEntry();
	        }
	    }
	}
	
	@Override
	public void uploadZip(long testId, MultipartFile file, String username) throws Exception {
		
		CourseTests test = testRepo.findById(testId)
		        .orElseThrow(() -> new Exception("Test not found"));

		if (test.getDeadline() != null && LocalDateTime.now().isAfter(test.getDeadline())) {
		    throw new Exception("Deadline is over!");
		}
		
	    if (file.isEmpty()) {
	        throw new Exception("The file is empty");
	    }

	    
	    MyUser user = userRepo.findByUsername(username);
	    if (user == null || user.getStudent() == null) {
	        throw new Exception("Student is not founded");
	    }
	    //sagatavojam ceļu
	    long studentId = user.getStudent().getStudentId();
	    String folderName = testId + "_" + studentId + "_files";
	    Path uploadPath = Paths.get("uploads");
	    Path studentFolder = uploadPath.resolve(folderName);

	    // tirišanas Ja mape jau eksistē, izdzēšam visu tās saturu
	    if (Files.exists(studentFolder)) {
	        // Šī rinda iziet cauri visiem failiem mapē un tos izdzēš
	        Files.walk(studentFolder)
	             .sorted((a, b) -> b.compareTo(a)) // vispirms dzēšam failus, tad mapes
	             .forEach(path -> {
	                 try {
	                     Files.delete(path);
	                 } catch (IOException e) {
	                     System.err.println("Cannot be deleted: " + path);
	                 }
	             });
	    }

	    // Izveidojam tukšu mapi no jauna
	    Files.createDirectories(studentFolder);

	    // Saglabājam ZIP
	    Path zipPath = studentFolder.resolve("submission.zip");
	    Files.copy(file.getInputStream(), zipPath, StandardCopyOption.REPLACE_EXISTING);

	    unzip(zipPath, studentFolder);
	    Files.delete(zipPath);
	}

	//----------------------------------------------------------------------------------------------
	

}
