package lv.venta;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.MyAuthority;
import lv.venta.model.MyUser;
import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.StudyProgram;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
import lv.venta.repo.ICourseTestRepo;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IMyAuthorityRepo;
import lv.venta.repo.IMyUserRepo;
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
									IStudentsRepo studentsRepo, IStudyCourseRepo courseRepo, IStudyProgramRepo programRepo, ITestResultRepo resultRepo, ITestTaskRepo taskRepo,
									IMyAuthorityRepo roleRepo, IMyUserRepo userRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception{
				
				//Sistēmai
				MyAuthority admin = new MyAuthority("ADMIN");
				MyAuthority lect = new MyAuthority("LECT");
				roleRepo.saveAll(Arrays.asList(admin,lect));
				
				PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
				//====================================================================================
				
				
				Lecturers lect1 = new Lecturers("TestUser","Test Name", "Testsurname", "Mg.sc.Comp");
				Lecturers lect2 = new Lecturers("KarinaSkirmante","Karina", "Šķirmante", "Mg.sc.Comp");
				Lecturers lect3 = new Lecturers("EstereVitola","Estere", "Vitola", "Mg.sc.Pead");
				Lecturers lect4 = new Lecturers("KristapsBlumbergs", "Kristaps", "Blumbergs", "Mg.sc");
				Lecturers lect5 = new Lecturers("ArtursOrbidans","Arturs", "Orbidans", "Mg.sc");
				lecturersRepo.saveAll(Arrays.asList(lect1, lect2, lect3, lect4, lect5));
				
				//USERS FOR LECTURERS==========================================================
				MyUser lectUser1 = new MyUser("KarinaSkirmante",encoder.encode("12345"),lect,lect2);
				MyUser lectUser2 = new MyUser("EstereVitola",encoder.encode("12345"), lect,lect3);
				MyUser lectUser3 = new MyUser("TestUser",encoder.encode("test"), lect,lect1);
				MyUser lectUser4 = new MyUser("KristapsBlumbergs",encoder.encode("12345"), lect,lect4);
				MyUser lectUser5 = new MyUser("ArtursOrbidans",encoder.encode("12345"), lect,lect5);

				userRepo.saveAll(Arrays.asList(lectUser1, lectUser2, lectUser3, lectUser4, lectUser5));
				//=============================================================================
				
				Students st1 = new Students("Tatjana", "Tolstika", "230000001", "s23tolstatj@venta.lv");
				Students st2 = new Students("Kristiana", "Felša", "230000002", "s23felskrist@venta.lv");
				Students st3 = new Students("Amanda","Rebuka", "23000003","s23rebuaman@venta.lv");
				Students st4 = new Students("Baiba", "Kvizikeviča", "23000004", "s23kvizibaib@venta.lv");
				Students st5 = new Students("Marta", "Dzelme", "23000005", "s23dzelmart@venta.lv");
				Students st6 = new Students("Anatolijs", "Berziņš", "23000005", "s22berzanat@venta.lv");
				studentsRepo.saveAll(Arrays.asList(st1, st2, st3, st4, st5, st6));
				
				StudyCourses course1 = new StudyCourses("Programmēšana tīmeklī JAVA", 6);
				StudyCourses course2 = new StudyCourses("Datu struktūras un algoritmi", 6);
				StudyCourses course3 = new StudyCourses("Objektorientēta programmēšana", 6);
				StudyCourses course4 = new StudyCourses("Programmēšna", 3);
				StudyCourses course5 = new StudyCourses("Vizuālas programmēšanas valodas", 3);
				courseRepo.saveAll(Arrays.asList(course1,course2,course3,course4, course5));
				
				//-------------------------------
				lect2.getCourses().add(course1);
				lect2.getCourses().add(course2);
				lect3.getCourses().add(course4);
				lect3.getCourses().add(course3);
				lecturersRepo.saveAll(Arrays.asList(lect2, lect3));
				//-------------------------------
				
				StudyProgram prog1 = new StudyProgram("Datorzinātnes", "BA", "ITB", 3);
				StudyProgram prog2 = new StudyProgram("Elektronikas inžinierija", "BA", "EIB", 4);
				StudyProgram prog3 = new StudyProgram("Programmēšanas specialists", "AS", "IP", 2);
				programRepo.saveAll(Arrays.asList(prog1,prog2,prog3));
				
				StudentProgram studProg1 = new StudentProgram(st1, prog1, 3);
				StudentProgram studProg2 = new StudentProgram( st2, prog1,3); 
				StudentProgram studProg3 = new StudentProgram(st3, prog1, 2);
				StudentProgram studProg4 = new StudentProgram(st4, prog3, 2);
				StudentProgram studProg5 = new StudentProgram(st5, prog2, 3);
				studentProgRepo.saveAll(Arrays.asList(studProg1, studProg2, studProg3, studProg4, studProg5));
				
				StudentProgramCourse spc1 = new StudentProgramCourse(studProg1, course1,8); 
				StudentProgramCourse spc2 = new StudentProgramCourse(studProg1, course3, 9); 
				StudentProgramCourse spc3 = new StudentProgramCourse(studProg2, course4, 8);
				StudentProgramCourse spc4 = new StudentProgramCourse(studProg3, course4, 9);
				StudentProgramCourse spc5 = new StudentProgramCourse(studProg3, course1, 10);
				StudentProgramCourse spc6 = new StudentProgramCourse(studProg5, course5, 10);
				StudentProgramCourse spc7 = new StudentProgramCourse(studProg4, course2, 7);
				StudentProgramCourse spc8 = new StudentProgramCourse(studProg5, course2,8); 
				studentProgCourseRepo.saveAll(Arrays.asList(spc1,spc2,spc3,spc4,spc5,spc6,spc7,spc8));
				
				//------------------About Tests-----------------------------------------------------------------------------
				
				CourseTests test1 = new CourseTests("JAVA_KD1", "Izveidot informācijas sistēmu “Mans transportlīdzekļu e-veikals”, kurā ir iespējams nodrošināt CRUD\n"
						+ "darbības ar precēm, preču pirkšanu, filtrāciju un statistikas attēlošanu.\n"
						+ "Katram studentam tiks piešķirta atšķirīgu transportlīdzekļu kopa, kura jārealizē sistēmā. Variantus skatīt\n"
						+ "tabulā (dokumenta pielikumā Nr.2). Ar dzeltenu krāsu iezīmētajās vietās ir jāievieto Jūsu varianta klase vai\n"
						+ "mainīgais.\n"
						+ "Pēc saviem ieskatiem varat papildināt aprakstā minētās klases, lai nezaudētu sistēmas funkcionalitāti. Jūs\n"
						+ "drīkstat lietot masīvus vai citas piemērotas datu struktūras tajos gadījumos, kad aprakstā minēts saraksts jeb\n"
						+ "ArrayList.\n"
						+ "Par katras klases realizāciju norādīts maksimālais punktu skaits pie ideālas klases realizācijas (norādīts kā\n"
						+ "parametrs: max). Par katru neizpildīto vai nepareizi izpildīto punktu un par katru nerealizēto vai nepareizi\n"
						+ "realizēto pārbaudi, tiek noņemts punktu skaits no maksimālā (norādīts kā parametrs: neizpilde).\n"
						+ "Iespējamā UML klašu diagramma punktu 1.-6. izpildei atrodama dokumenta pielikumā Nr.1.",10, course2);
				
				CourseTests test2 = new CourseTests("JAVA_KD2", "Apraksts",10, course1);
				courseTestRepo.saveAll(Arrays.asList(test1, test2));
				
				TestTask task1 = new TestTask(test1, "Enum EnergyType, kurā glabājat informāciju par enerģijas tipiem - kā transportlīdzeklis uzņem\r\n"
						+ "enerģiju - benzīns, dīzelis, gāze, hibrīds, elektriskais, cits, nav_norādīts",0.1, "");
				TestTask task2 = new TestTask(test1, "Klase Vehicle, Mainīgie: int id, String title, String vehicleCode, float price, int quantity, EnergyType eType", 0.25, "");
				TestTask task3 = new TestTask(test1, "Klase Vehicle, Nodrošiniet unikāla id piešķiršanu", 0.25 , "");
				TestTask task4 = new TestTask(test1, "Klase Vehicle, Get funkcijas visiem uzskaitītajiem mainīgajiem",0.25 , "");
				TestTask task5 = new TestTask(test1, "Klase Vehicle, Set funkcijas visiem uzkaitītajiem mainīgajiem",0.3 , "(neaizmirstam pievienot pārbaudes set\r\n"
						+ "funkcijās, kur tas ir nepieciešams. Piemēram, pārbaudīt min un max robežas skaitliskām\r\n"
						+ "vērtībām, referenču vērtībām - vai nav null. Pēc saviem ieskatiem, varat izveidot regex masku String vērtībām)");
				TestTask task6 = new TestTask(test1, "Klase Vehicle, setVehicleCode funkcijā saglabājiet vehicleCode mainīgā vērtību, ņemot vērā sekojošu\r\n"
						+ "informāciju un sekojošu formātu; <transporta id>_<transporta nosaukums jeb title>.", 0.2 , "Piemēram, 3_Motocikls, 14_Prāmis. Gan id, gan title ir šīs pašas klases mainīgie, tādēļ\r\n"
								+ "funkcijā nav nepieciešams padot parametrus.");
				TestTask task7 = new TestTask(test1, "Klase Vehicle, Noklusētais konstruktors", 0.25 , "");
				TestTask task8 = new TestTask(test1, "Klase Vehicle, Konstruktors, kurā piešķir vērtības visiem uzskaitītajiem mainīgajiem;", 0.25 , "");
				TestTask task9 = new TestTask(test1, "Klase Vehicle, toString funkcija.", 0.25 , "");
				TestTask task10 = new TestTask(test1, "Klase Class1_VarX, Mainīgie: varX_class1_1 un varX_class1_2", 0.2 , "tiek mantota no Vehicle klases");
				TestTask task11 = new TestTask(test1, "Klase Class1_VarX, Get funkcijas", 0.2 , "");
				TestTask task12 = new TestTask(test1, "Klase Class1_VarX, Set funkcijas", 0.2 , "(ar pārbaudēm, piemērus skatīt 2.d. punktā)");
				TestTask task13 = new TestTask(test1, "Klase Class1_VarX, Noklusētais konstruktors;", 0.2 , "");
				TestTask task14 = new TestTask(test1, "Klase Class1_VarX, Konstruktors, kurā piešķir vērtības visiem klases un mātes klases mainīgajiem", 0.2 , "");
				TestTask task15 = new TestTask(test1, "Klase Class1_VarX, toString funkcija", 0.2 , "");
				TestTask task16 = new TestTask(test1, "Klase Class2_VarX, Mainīgais: varX_class2_1 un varX_class2_2;", 0.2 , "");
				TestTask task17 = new TestTask(test1, "Klase Class2_VarX, Get funkcijas", 0.2 , "");
				TestTask task18 = new TestTask(test1, "Klase Class2_VarX, Set funkcijas", 0.2 , "");
				TestTask task19 = new TestTask(test1, "Klase Class2_VarX, Noklusētais konstruktors;", 0.2 , "");
				TestTask task20 = new TestTask(test1, "Klase Class2_VarX, Konstruktors, kurā piešķir vērtības visiem klases un mātes klases mainīgajiem", 0.2 , "");
				TestTask task21 = new TestTask(test1, "Klase Class2_VarX, toString funkcija", 0.2 , ""); 
				TestTask task22 = new TestTask(test1, "Klase Purchase, Mainīgie: String userCardNr, shoppingList - saraksts, LocalDateTime dateTime", 0.1 , "(shoppingList - srakasts kurā glabāsies transportlīdzekļi, ko lietotājs būs nopircis)");
				TestTask task23 = new TestTask(test1, "Klase Purchase, Noklusētais konstruktors", 0.1 , "");
				TestTask task24 = new TestTask(test1, "Klase Purchase, Konstruktors ar visiem parametriem", 0.1 , "");
				TestTask task25 = new TestTask(test1, "Klase Purchase, Get funkcijas", 0.1 , "");
				TestTask task26 = new TestTask(test1, "Klase Purchase, Set funkcijas", 0.1 , "(ar pārbaudēm - vai nav null)");
				TestTask task27 = new TestTask(test1, "Klase Purchase, toString funkcija", 0.2 , "");
				taskRepo.saveAll(Arrays.asList(task1,task2,task3,task4,task5,task6,task7, task8,task9, task10, task11, task12, task13, task14, task15, task16, task17, task18, task19, task20,task21, task22, task23, task24, task25, task26, task27));
				
				
				TestResult result1 = new TestResult("Comment 1", 0.1, task1, spc7);
				TestResult result2 = new TestResult("Comment 2", 0, task2, spc7);
				TestResult result3 = new TestResult("Comment 3", 0, task3, spc7);
				TestResult result4 = new TestResult("Comment 4", 0.1, task4, spc7);
				TestResult result5 = new TestResult("Comment 5", 0.2, task5, spc7);
				TestResult result6 = new TestResult("Comment 6", 0, task6, spc7);
				resultRepo.saveAll(Arrays.asList(result1,result2,result3,result4,result5,result6));
				
				
			}
		};
	}

}
