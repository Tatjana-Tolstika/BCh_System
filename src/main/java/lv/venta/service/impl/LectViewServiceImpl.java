package lv.venta.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.MyUser;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.TestResult;
import lv.venta.model.TestStatus;
import lv.venta.model.TestTask;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IStudentProgramCourseRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.repo.ITestTaskRepo;
import lv.venta.service.ILectViewService;

@Service
public class LectViewServiceImpl implements ILectViewService{
	@Autowired
	private ILecturersRepo lecturersRepo;
	@Autowired 
	private IStudyCourseRepo coursesRepo;
	@Autowired
	private ICourseTestRepo testRepo;
	@Autowired
	private IMyUserRepo userRepo;
	@Autowired
	private ITestTaskRepo taskRepo;
	@Autowired
	private IStudentProgramCourseRepo spcRepo;
	@Autowired
	private ITestResultRepo resultRepo;
	
	@Autowired
	private CourseTestCRUDServiceImpl courseTestService;
	
	
	@Override
	public List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception{
		Lecturers lecturer = lecturersRepo.findById(lectId)
	        .orElseThrow(() -> new Exception("Lecturer not found!"));
	
	    List<StudyCourses> courses = coursesRepo.findByLecturers(lecturer);
	
//	    if(courses.isEmpty())
//	        throw new Exception("This lecturer has no courses");
	
	    return courses;
	}
	
	@Override
	public List<CourseTests> allTestsByCourse(long courseId) throws Exception{
		StudyCourses course = coursesRepo.findById(courseId)
				.orElseThrow(()-> new Exception("Course not found!"));
		List<CourseTests> tests = testRepo.findByCourse(course);
//		if(tests.isEmpty()) {
//			throw new Exception("This course has no tests!");
//		}
		return tests;
		
	}
	
	@Transactional
	@Override
	public List<Students> allStudentsOfTestResults(long testId) throws Exception{
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<Students> students = new ArrayList<>();
		List<TestTask> tasks = test.getTasksForTest();
		List<TestResult> results = new ArrayList<>();
		
		for(TestTask tt : tasks){
			results = tt.getTestResults();
			for(TestResult tr : results) {
				Students foundedStudent = tr.getStudentProgramCourse().getStudentProgram().getStudent();
				if(!students.contains(foundedStudent)) {
					students.add(foundedStudent);
				}
			}
		}
//		if (students.isEmpty()) {
//	        throw new Exception("No students have taken this test");
//	    }
		
		return students;
	}

	
	@Override
	public Lecturers getAuthorisedId() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = auth.getName();

	    MyUser user = userRepo.findByUsername(currentUsername);

	    if (user != null && user.getLecturer() != null) {
	        return user.getLecturer();
	    }
	    
	    return null;
	}
	
	@Transactional
	@Override
	public double testPointsCounter(long testId) throws Exception{
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<TestTask> tasks = test.getTasksForTest();
		double counter = 0;
		for(TestTask t : tasks) {
			counter += t.getMaxPoints();
		}
		return counter;
	}
	
	@Transactional
	@Override
	public double getStudentResult(long testId, long studentId) throws Exception{
		double counter = 0;
		double studentMinus =0;
		CourseTests test = testRepo.findById(testId)
				.orElseThrow(()-> new Exception("Test not found!"));
		List<TestTask> tasks = test.getTasksForTest();
		for(TestTask t: tasks) {
			List<TestResult> results = t.getTestResults();
			for(TestResult r : results) {
				if(r.getStudentProgramCourse().getStudentProgram().getStudent().getStudentId() == studentId) {
					studentMinus += r.getMinus();
				}
			}
		}
		counter = 10 - studentMinus;
		return counter;
	}
	
	@Transactional
	@Override
	public String controlTestVisibility(long testId, String testDeadline) throws Exception {
	    CourseTests test = testRepo.findById(testId)
	            .orElseThrow(() -> new Exception("Test not found!"));

	    if (test.getStatus() == TestStatus.IN_PROCESS) {

	    	//deadline check-------------------------------
	    	if (testDeadline == null || testDeadline.isEmpty()) {
	            return "Please select deadline!";
	        }

	        LocalDateTime deadline = LocalDateTime.parse(testDeadline);

	        if (deadline.isBefore(LocalDateTime.now())) {
	            return "Deadline must be in the future!";
	        }
	        //------------------------------------------------
	    	
	    	
	        List<TestTask> tasks = taskRepo.findByTest(test);

	        double pointCounter = 0;
	        for (TestTask t : tasks) {
	            pointCounter += t.getMaxPoints();
	        }

	        if (pointCounter != 10.0) {
	        	 return "Test cannot be published. Total points must be exactly 10.";
	        }

	        StudyCourses course = test.getCourse();
	        List<StudentProgramCourse> studentCourses = spcRepo.findByCourse(course);

	        for (StudentProgramCourse spc : studentCourses) {
	            for (TestTask task : tasks) {
	                boolean exists = resultRepo.existsByTaskAndStudentProgramCourse(task, spc);

	                if (!exists) {
	                    TestResult newResult = new TestResult();
	                    newResult.setStudentProgramCourse(spc);
	                    newResult.setTask(task);
	                    newResult.setMinus(task.getMaxPoints());
	                    newResult.setComments("");
	                    resultRepo.save(newResult);
	                }
	            }
	        }

	        test.setStatus(TestStatus.PUBLISHED);
	        test.setDeadline(deadline);
	        testRepo.save(test);
	        
	    }
	    else if (test.getStatus() == TestStatus.PUBLISHED) {
	        test.setStatus(TestStatus.IN_PROCESS);
	        testRepo.save(test);
	        
	    }
	    return null;
	}
	
	//Update testResult can be taken from CRUD service
	//Adding new tests to the course can be taken from courseTestCRUD service

	@Override
	public List<String> getStudentFiles(long testId, long studentId) throws Exception {
	    String folderName = testId + "_" + studentId + "_files";
	    Path studentFolder = Paths.get("uploads").resolve(folderName).normalize();

//	    if (!Files.exists(studentFolder)) {
//	        throw new Exception("Student have not submitted any files for this test");
//	    }

	    // Find files and make them as texts
	    try (var stream = Files.walk(studentFolder)) {
	        return stream
	                .filter(Files::isRegularFile)
	                .map(path -> studentFolder.relativize(path).toString())
	                .toList();
	    }
	}
	
	@Transactional
	@Override 
	public double getAverageMark(long testId) throws Exception{
		StudyCourses foundCourse = courseTestService.selectCourseByTest(testId);
		CourseTests foundTest = testRepo.findById(testId)
				.orElseThrow(() -> new Exception("Test not found!"));
		if(foundTest.getStatus() == TestStatus.IN_PROCESS) {
			return 0;
		}
		else {
			List<StudentProgramCourse> foundedStudents = spcRepo.findByCourse(foundCourse);
			List<Double> results = new ArrayList<>();
			int counter = 0;
			double sum = 0;
			
			for(StudentProgramCourse spc : foundedStudents) {
				results.add(getStudentResult(testId, spc.getStudentProgram().getStudent().getStudentId() ));
				sum += getStudentResult(testId, spc.getStudentProgram().getStudent().getStudentId() );
				counter++;
			}
			
			return sum / counter;
		}
		
	}
	//----------------------------Testst files---------------------------------------------
	@Override
	public void uploadZipTests(long testId, MultipartFile file) throws Exception {
		
		CourseTests test = testRepo.findById(testId)
		        .orElseThrow(() -> new Exception("Test not found"));

		
	    if (file.isEmpty()) {
	        throw new Exception("The file is empty");
	    }

	    //making paths
	    String folderName = testId + "_files";
	    Path uploadPath = Paths.get("tests_jUnit");
	    Path testFolder = uploadPath.resolve(folderName);

	    //????????????DOES IT NEEDED????????????????
	    // if the folder already exists, delete everything that is inside 
	    if (Files.exists(testFolder)) {
	        
	        Files.walk(testFolder)
	             .sorted((a, b) -> b.compareTo(a)) 
	             .forEach(path -> {
	                 try {
	                     Files.delete(path);
	                 } catch (IOException e) {
	                     System.err.println("Cannot be deleted: " + path);
	                 }
	             });
	    }
	    
	    Files.createDirectories(testFolder);

	    // Save ZIP
	    Path zipPath = testFolder.resolve("tests.zip");
	    Files.copy(file.getInputStream(), zipPath, StandardCopyOption.REPLACE_EXISTING);

	    // Open ZIP
	    unzip(zipPath, testFolder);
	    Files.delete(zipPath);
	  }
	
	
	private void unzip(Path zipFile, Path targetDir) throws Exception {
	    try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
	        ZipEntry entry;
	        while ((entry = zis.getNextEntry()) != null) {
	            
	            Path newPath = targetDir.resolve(entry.getName()).normalize();
	            
	            if (!newPath.startsWith(targetDir)) {
	                throw new Exception("Security error!");
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
	
	//---------------for jUnit tests-----------------------------------------------------------


	@Override
	public String runTestsForStudent(long testId, long studentId) throws Exception {

	    Path studentFiles = Paths.get("uploads/" + testId + "_" + studentId + "_files");
	    Path testFiles = Paths.get("tests_jUnit/" + testId + "_files");
	    Path junitJar = Paths.get("libs/junit-platform-console-standalone-1.11.4.jar");
	    Path outputDir = Files.createTempDirectory("junit_out_");

	    
	    List<String> javaFiles = new ArrayList<>();
	    Files.walk(studentFiles)
	         .filter(p -> p.toString().endsWith(".java"))
	         .map(Path::toString)
	         .forEach(javaFiles::add);
	    Files.walk(testFiles)
	         .filter(p -> p.toString().endsWith(".java"))
	         .map(Path::toString)
	         .forEach(javaFiles::add);

	    // Compilatiopn
	    String compileCmd = "javac -cp " + junitJar.toAbsolutePath()
	            + " -d " + outputDir.toAbsolutePath()
	            + " " + String.join(" ", javaFiles);

	    String compileResult = runCommand(compileCmd);
	    if (!compileResult.isBlank()) {
	        return "Kompilācijas kļūda: " + compileResult;
	    }

	    // Tests execution
	    String testCmd = "java -jar " + junitJar.toAbsolutePath()
	            + " execute --scan-classpath=" + outputDir.toAbsolutePath()
	            + " --disable-ansi-colors";

	    return runCommand(testCmd);
	}

	private String runCommand(String cmd) throws Exception {

	    ProcessBuilder pb;

	    if (System.getProperty("os.name").toLowerCase().contains("win")) { //Check operatingsystem
	        pb = new ProcessBuilder("cmd.exe", "/c", cmd);
	    } else {
	        pb = new ProcessBuilder("bash", "-c", cmd);
	    }

	    pb.redirectErrorStream(true);
	    Process process = pb.start();
	    process.waitFor(30, TimeUnit.SECONDS);

	    return new String(process.getInputStream().readAllBytes());
	}
}
