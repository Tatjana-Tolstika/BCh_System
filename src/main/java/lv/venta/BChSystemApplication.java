package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IStudentProgramCourseRepo;
import lv.venta.repo.IStudentProgramRepo;
import lv.venta.repo.IStudentsRepo;
import lv.venta.repo.IStudyCourseRepo;
import lv.venta.repo.IStudyProgramRepo;
import lv.venta.repo.ITestResultRepo;
import lv.venta.repo.ITestTaskRepo;

@SpringBootApplication
public class BChSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BChSystemApplication.class, args);
	}
	//=================================================================
	@Bean
	public CommandLineRunner testDB(ICourseTestRepo courseTestRepo, ILecturersRepo lecturersRepo, IStudentProgramCourseRepo studentProgCourseRepo, IStudentProgramRepo studentProgRepo,
									IStudentsRepo studentRepo, IStudyCourseRepo courseRepo, IStudyProgramRepo programRepo, ITestResultRepo resultRepo, ITestTaskRepo taskRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception{
				
				
				
			}
		};
	}

}
