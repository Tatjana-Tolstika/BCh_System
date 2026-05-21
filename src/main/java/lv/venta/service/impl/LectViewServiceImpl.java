package lv.venta.service.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	
	private ILecturersRepo lecturersRepo;
	private IStudyCourseRepo coursesRepo;
	private ICourseTestRepo testRepo;
	private IMyUserRepo userRepo;
	private ITestTaskRepo taskRepo;
	private IStudentProgramCourseRepo spcRepo;
	private ITestResultRepo resultRepo;
	private CourseTestCRUDServiceImpl courseTestService;
	
	public LectViewServiceImpl (ILecturersRepo lecturersRepo, IStudyCourseRepo coursesRepo, ICourseTestRepo testRepo, IMyUserRepo userRepo, ITestTaskRepo taskRepo, IStudentProgramCourseRepo spcRepo, ITestResultRepo resultRepo, CourseTestCRUDServiceImpl courseTestService) 
		{this.lecturersRepo = lecturersRepo;
		this.coursesRepo = coursesRepo;
		this.testRepo = testRepo;
		this.userRepo = userRepo;
		this.taskRepo = taskRepo;
		this.spcRepo = spcRepo;
		this.resultRepo = resultRepo;
		this.courseTestService = courseTestService;}
	
	private static final String FILE_FUNC = "_files";
	private static final String JAVA_FUNC = ".java";
	
	
	@Override
	public List<StudyCourses> allCoursesForLecturer(long lectId) throws Exception{
		Lecturers lecturer = lecturersRepo.findById(lectId)
	        .orElseThrow(() -> new Exception("Lecturer not found!"));
	
	    List<StudyCourses> courses = coursesRepo.findByLecturers(lecturer);

	    return courses;
	}
	
	@Override
	public List<CourseTests> allTestsByCourse(long courseId) throws Exception{
		StudyCourses course = coursesRepo.findById(courseId)
				.orElseThrow(()-> new Exception("Course not found!"));
		List<CourseTests> tests = testRepo.findByCourse(course);

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
	    String folderName = testId + "_" + studentId + FILE_FUNC;
	    Path studentFolder = Paths.get("uploads").resolve(folderName).normalize();


	    // Find files and make them as texts
	    try (var stream = Files.walk(studentFolder)) {
	        return stream
	                .filter(Files::isRegularFile)
	                .map(path -> studentFolder.relativize(path).toString())
	                .toList();
	    }
	}
	
//-----------------STATS--------------------------------------------
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
	
	@Transactional
	@Override
	public List<Double> getAllMarksForTest(long testId) throws IOException {
		StudyCourses foundCourse;
		try {
			 foundCourse = courseTestService.selectCourseByTest(testId);
		} catch (Exception e) {
		    throw new IOException("Course not found for test: " + testId, e);
		}
	    CourseTests foundTest = testRepo.findById(testId)
	            .orElseThrow(() -> new IOException("Test not found!"));

	    if (foundTest.getStatus() == TestStatus.IN_PROCESS) {
	        return new ArrayList<>();
	    }

	    List<StudentProgramCourse> foundedStudents = spcRepo.findByCourse(foundCourse);
	    List<Double> results = new ArrayList<>();

	    for (StudentProgramCourse spc : foundedStudents) {
	        try {
				results.add(getStudentResult(testId,
				        spc.getStudentProgram().getStudent().getStudentId()));
			} catch (Exception e) {
				throw new IOException("Failed to get student result", e);
			}
	    }

	    return results;
	}
	//-------------------------------------------------------------------------------------
	//----------------------------Testst files---------------------------------------------
	@Override
	public void uploadZipTests(long testId, MultipartFile file) throws IOException {
		
		
	    if (file.isEmpty()) {
	        throw new IOException("The file is empty");
	    }

	    //making paths
	    String folderName = testId + FILE_FUNC;
	    Path uploadPath = Paths.get("tests_jUnit");
	    Path testFolder = uploadPath.resolve(folderName);

	    if (Files.exists(testFolder)) {
	        
	    	try (Stream<Path> stream = Files.walk(testFolder)) {
	            stream.sorted((a, b) -> b.compareTo(a))
	                  .forEach(path -> {
	                      try {
	                          Files.delete(path);
	                      } catch (IOException e) {
	                          throw new UncheckedIOException(
	                              "Cannot be deleted: " + path, e);
	                      }
	                  });
	        }
	    }
	    
	    Files.createDirectories(testFolder);

	    // Save ZIP
	    Path zipPath = testFolder.resolve("tests.zip");
	    Files.copy(file.getInputStream(), zipPath, StandardCopyOption.REPLACE_EXISTING);

	    // Open ZIP
	    unzip(zipPath, testFolder);
	    Files.delete(zipPath);
	  }
	
	
	private void unzip(Path zipFile, Path targetDir) throws IOException {
	    try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
	        ZipEntry entry;
	        while ((entry = zis.getNextEntry()) != null) {
	            
	            Path newPath = targetDir.resolve(entry.getName()).normalize();
	            
	            if (!newPath.startsWith(targetDir.normalize())) {
	            	throw new SecurityException(
	                        "Zip Slip attack detected: " + entry.getName());
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
	public String runTestsForStudent(long testId, long studentId) throws IOException, InterruptedException {

	    Path studentFiles = Paths.get("uploads/" + testId + "_" + studentId + FILE_FUNC);
	    Path testFiles = Paths.get("tests_jUnit/" + testId + FILE_FUNC);
	    Path junitJar = Paths.get("libs/junit-platform-console-standalone-1.11.4.jar");
	    Path outputDir = Files.createTempDirectory("junit_out_");

	    List<String> javaFiles = new ArrayList<>();


	    try (Stream<Path> studentStream = Files.walk(studentFiles);
	    	     Stream<Path> testStream = Files.walk(testFiles)) {

	    	    studentStream
	    	        .filter(p -> p.toString().endsWith(JAVA_FUNC))
	    	        .map(p -> p.toAbsolutePath().toString())
	    	        .forEach(javaFiles::add);

	    	    testStream
	    	        .filter(p -> p.toString().endsWith(JAVA_FUNC))
	    	        .map(p -> p.toAbsolutePath().toString())
	    	        .forEach(javaFiles::add);
	    	}
	    
	    checkDangerousCode(studentFiles);
	    
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

	private String runCommand(String cmd) throws InterruptedException, IOException {

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
	
	//Checking code safety
	private void checkDangerousCode(Path folder) throws IOException {
	    
	    List<String> forbidden = List.of(
	        "Runtime", "ProcessBuilder", "exec",
	        "shutdown", "Files.delete", "format"
	    );

	    List<Path> javaFiles = new ArrayList<>();
	    try (Stream<Path> stream = Files.walk(folder)) {
	        stream.filter(p -> p.toString().endsWith(JAVA_FUNC))
	              .forEach(javaFiles::add);
	    }
	    
	    for (Path file : javaFiles) {
	        String content = Files.readString(file);
	        for (String word : forbidden) {
	            if (content.contains(word)) {
	                throw new SecurityException("Dangerous place in code: " + word);
	            }
	        }
	    }
	}
}
