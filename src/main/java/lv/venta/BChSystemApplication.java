package lv.venta;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.model.CourseTests;
import lv.venta.model.Lecturers;
import lv.venta.model.StudentProgram;
import lv.venta.model.StudentProgramCourse;
import lv.venta.model.Students;
import lv.venta.model.StudyCourses;
import lv.venta.model.StudyProgram;
import lv.venta.model.TestResult;
import lv.venta.model.TestTask;
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
									IStudentsRepo studentsRepo, IStudyCourseRepo courseRepo, IStudyProgramRepo programRepo, ITestResultRepo resultRepo, ITestTaskRepo taskRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception{
				
				Lecturers lect1 = new Lecturers("TestName", "TestSurname", "Mg.sc.Comp");
				Lecturers lect2 = new Lecturers("Karina", "Šķirmante", "Mg.sc.Comp");
				Lecturers lect3 = new Lecturers("Estere", "Vitola", "Mg.sc.Pead");
				Lecturers lect4 = new Lecturers("Kristaps", "Blumbergs", "Mg.sc");
				Lecturers lect5 = new Lecturers("Arturs", "Orbidans", "Mg.sc");
				lecturersRepo.saveAll(Arrays.asList(lect1, lect2, lect3, lect4, lect5));
				
				Students st1 = new Students("Tatjana", "Tolstika", "230000001", "s23tolstatj@venta.lv");
				Students st2 = new Students("Kristiana", "Felša", "230000002", "s23felskrist@venta.lv");
				Students st3 = new Students("Amanda","Rebuka", "23000003","s23rebuaman@venta.lv");
				Students st4 = new Students("Baiba", "Kvizikeviča", "23000004", "s23kvizibaib@venta.lv");
				Students st5 = new Students("Marta", "Dzelme", "23000005", "s23dzelmart@venta.lv");
				studentsRepo.saveAll(Arrays.asList(st1, st2, st3, st4, st5));
				
				StudyCourses course1 = new StudyCourses("Programmēšana tīmeklī JAVA", 6);
				StudyCourses course2 = new StudyCourses("Datu struktūras un algoritmi", 6);
				StudyCourses course3 = new StudyCourses("Objektorientēta programmēšana", 6);
				StudyCourses course4 = new StudyCourses("Programmēšna", 3);
				StudyCourses course5 = new StudyCourses("Vizuālas programmēšanas valodas", 3);
				courseRepo.saveAll(Arrays.asList(course1,course2,course3,course4, course5));
				
				StudyProgram prog1 = new StudyProgram("Datorzinātnes", "BA", "ITB");
				StudyProgram prog2 = new StudyProgram("Elektronikas inžinierija", "BA", "EIB");
				StudyProgram prog3 = new StudyProgram("Programmēšanas specialists", "AS", "IP");
				programRepo.saveAll(Arrays.asList(prog1,prog2,prog3));
				
				StudentProgram studProg1 = new StudentProgram(st1, prog1, 3);
				StudentProgram studProg2 = new StudentProgram( st2, prog1,3); 
				StudentProgram studProg3 = new StudentProgram(st3, prog1, 2);
				StudentProgram studProg4 = new StudentProgram(st4, prog3, 2);
				StudentProgram studProg5 = new StudentProgram(st5, prog2, 3);
				studentProgRepo.saveAll(Arrays.asList(studProg1, studProg2, studProg3, studProg4, studProg5));
				
				StudentProgramCourse spc1 = new StudentProgramCourse(st1, course1,8); 
				StudentProgramCourse spc2 = new StudentProgramCourse(st1, course3, 9); 
				StudentProgramCourse spc3 = new StudentProgramCourse(st2, course4, 8);
				StudentProgramCourse spc4 = new StudentProgramCourse(st3, course4, 9);
				StudentProgramCourse spc5 = new StudentProgramCourse(st3, course1, 10);
				StudentProgramCourse spc6 = new StudentProgramCourse(st5, course5, 10);
				StudentProgramCourse spc7 = new StudentProgramCourse(st4, course2, 7);
				StudentProgramCourse spc8 = new StudentProgramCourse(st5, course2,8); 
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
				
				TestTask task1 = new TestTask(test1,"Enum EnergyType, kurā glabājat informāciju par enerģijas tipiem - kā transportlīdzeklis uzņem\n"
						+ "enerģiju - benzīns, dīzelis, gāze, hibrīds, elektriskais, cits, nav_norādīts", 0.1);
				TestTask task2 = new TestTask(test1,"Klase Vehicle \n"
						+ "a. Mainīgie: int id, String title, String vehicleCode, float price, int quantity, EnergyType eType;\n"
						+ "b. Nodrošiniet unikāla id piešķiršanu;\n"
						+ "c. Get funkcijas visiem uzskaitītajiem mainīgajiem;\n"
						+ "d. Set funkcijas visiem uzkaitītajiem mainīgajiem (neaizmirstam pievienot pārbaudes set\n"
						+ "funkcijās, kur tas ir nepieciešams. Piemēram, pārbaudīt min un max robežas skaitliskām\n"
						+ "vērtībām, referenču vērtībām - vai nav null. Pēc saviem ieskatiem, varat izveidot regex masku\n"
						+ "String vērtībām (nav nepieciešams sarežģīt));\n"
						+ "e. setVehicleCode funkcijā saglabājiet vehicleCode mainīgā vērtību, ņemot vērā sekojošu\n"
						+ "informāciju un sekojošu formātu; <transporta id>_<transporta nosaukums jeb title>.\n"
						+ "Piemēram, 3_Motocikls, 14_Prāmis. Gan id, gan title ir šīs pašas klases mainīgie, tādēļ\n"
						+ "funkcijā nav nepieciešams padot parametrus.\n"
						+ "f. Noklusētais konstruktors;\n"
						+ "g. Konstruktors, kurā piešķir vērtības visiem uzskaitītajiem mainīgajiem;\n"
						+ "h. toString funkcija.", 2.0);
				TestTask task3 = new TestTask(test1,"Klase Class1_VarX - tiek mantota no Vehicle klases\n"
						+ "a. Mainīgie: varX_class1_1 un varX_class1_2;\n"
						+ "b. Get funkcijas;\n"
						+ "\n"
						+ "Programmēšana tīmeklī JAVA (2ITB)\n"
						+ "Objektorientētā programmēšana (3EIB)\n"
						+ "2022./2023. m.g.\n"
						+ "Lektore Karina Šķirmante\n"
						+ "\n"
						+ "c. Set funkcijas (ar pārbaudēm, piemērus skatīt 2.d. punktā);\n"
						+ "d. Noklusētais konstruktors;\n"
						+ "e. Konstruktors, kurā piešķir vērtības visiem klases un mātes klases mainīgajiem;\n"
						+ "f. toString funkcija.",1.2);
				TestTask task4 = new TestTask(test1,"Klase Class2_VarX - tiek mantota no Vehicle klases \n"
						+ "a. Mainīgais: varX_class2_1 un varX_class2_2;\n"
						+ "b. Get funkcijas;\n"
						+ "c. Set funkcijas (ar pārbaudēm, piemērus skatīt 2.d. punktā);\n"
						+ "d. Noklusētais konstruktors;\n"
						+ "e. Konstruktors, kurā piešķir vērtības visiem klases un mātes klases mainīgajiem;\n"
						+ "f. toString funkcija.", 1.2);
				TestTask task5 = new TestTask(test1,"Klase Purchase \n"
						+ "a. Mainīgie: String userCardNr, shoppingList - saraksts (kurā glabāsies transportlīdzekļi, ko\n"
						+ "lietotājs būs nopircis), LocalDateTime dateTime ;\n"
						+ "b. Noklusētais konstruktors;\n"
						+ "c. Konstruktors ar visiem parametriem;\n"
						+ "d. Get funkcijas;\n"
						+ "e. Set funkcijas (ar pārbaudēm - vai nav null);\n"
						+ "Turpmākās funkcijās būs jāizmantot 6.a punktā izveidotais transportlīdzekļu saraksts, jo tajā\n"
						+ "brīdī, kad vēlas ielikt transportlīdzekli savā pirkuma grozā, tas ir jāizņem no kopējā saraksta.\n"
						+ "Noteikti jāpārbauda, vai konkrētais transportlīdzeklis vispār ir atrodams veikalā un ja ir, vai\n"
						+ "to skaits ir pietiekošs.\n"
						+ "f. Funkcija addVehicleToShoppingListByVehicleCode(), kas pēc padotā transportlīdzekļa koda\n"
						+ "to sameklē veikalā (jeb 6.a.punktā izveidotajā Vehicle tipa sarakstā) un pievieno to pircēja\n"
						+ "pirkumam jeb shoppingList sarakstam. Tajā brīdī šis transportlīdzekļa skaits jāsamazina par 1\n"
						+ "veikalā (jeb 6.a.punktā izveidotajā Vehicle tipa sarakstā).\n"
						+ "g. Funkcija changeAmountOfVehicleInShoppingListByVehicleCode(), kas pēc padotā\n"
						+ "transportlīdzekļa koda to sameklē veikalā (jeb 6.a.punktā izveidotajā Vehicle tipa sarakstā) un\n"
						+ "nomaina konkrētā transportlīdzekļa skaitu savā pirkumā jeb shoppingList sarakstā. Tajā brīdī\n"
						+ "šis transportlīdzekļa skaits jāsamazina par padoto skaitli veikalā (jeb 6.a.punktā izveidotajā\n"
						+ "Vehicle tipa sarakstā).\n"
						+ "h. Funkcija removeVehicleFromShoppingListByVehicleCode(), kas pēc padotā transportlīdzekļa\n"
						+ "koda to sameklē veikalā (jeb 6.a.punktā izveidotajā Vehicle tipa sarakstā) un izdzēš no pircēja\n"
						+ "pirkuma jeb shoppingList saraksta. Tajā brīdī šis transportlīdzelis ir pieejams citiem\n"
						+ "pircējiem, līdz ar to tā skaits jāpalielina par 1 veikalā (jeb 6.a.punktā izveidotajā Vehicle tipa\n"
						+ "sarakstā).\n"
						+ "i. Funkcija showAllVehiclesInShoppingList() atgriež visus transportlīdzekļus, kas atrodas\n"
						+ "pircēja pirkumā jeb shoppingList sarakstā.\n"
						+ "j. Funkcija calculateShoppingListValue() aprēķina pirkuma kopējo vērtību, ņemot vērā pircēja\n"
						+ "pirkumu jeb shoppingList sarakstu.",2.0);
				TestTask task6 = new TestTask(test1, "Klase MainService\n"
						+ "a. Mainīgie (statiski): 1) Vehicle tipa saraksts, kurā glabājās visi transportlīdzekļi, kas ir veikalā\n"
						+ "pieejami 2) Purchase tipa saraksts, kurā glabājās visi veikalā veiktie pirkumi;\n"
						+ "b. main funkcijā izveidojiet vismaz divus Class1_VarX un divus Class2_VarX tipa objektus un\n"
						+ "tos ievietojat 6.a. punktā izveidotajā Vehicle sarakstā. Izveidojiet arī trīs Purchase tipa\n"
						+ "objektus un ievietojiet tos 6.a. punktā izveidotajā Purchase sarakstā.\n"
						+ "c. CRUD funkcionalitātes nodrošināšana darbam ar Class1_VarX objektiem:\n"
						+ "i. Izveidojiet funkciju getClass1_VarXById(), kas atgriež konkrētu Jūsu varianta\n"
						+ "2.punktā izveidotā klases tipa objektu no 6.a. punktā izveidotā Vehicle saraksta, ja\n"
						+ "zināms šī objekta id.\n"
						+ "ii. Izveidojiet funkciju getClass1_VarXByVehicleCode(), kas atgriež konkrētu Jūsu\n"
						+ "varianta 2.punktā izveidotā klases tipa objektu no 6.a. punktā izveidotā Vehicle\n"
						+ "saraksta, ja zināms šī objekta vehicleCode.\n"
						+ "iii. Izveidojiet funkciju createNewClass1_VarX(), kas ļauj pievienot jaunu Jūsu varianta\n"
						+ "2. punktā izveidotās klases tipa objektu 6.a. punkta izveidotajā Vehicle sarakstā.\n"
						+ "Neaizmirstam pārbaudīt, vai tāds transportlīdzeklis jau neeksistē 6.a. Vehicle tipa\n"
						+ "sarakstā. Ja eksistē, tad nepievienojam.\n"
						+ "iv. Izveidojiet funkciju getAllClass1_VarX(), kas atgriež visus Jūsu varianta 2.punktā\n"
						+ "izveidotās klases tipa objektus no 6.a. punktā izveidotā Vehicle saraksta.\n"
						+ "Neaizmirstam, ka šeit nepieciešams izfiltrēt tikai Class1_VarX tipa objektus no\n"
						+ "Vehicle tipa saraksta.\n"
						+ "v. Izveidojiet funkciju deleteClass1_VarXById(), kas dzēš konkrētu Jūsu varianta\n"
						+ "2.punktā izveidotā klases tipa objektu no 6.a. punktā izveidotā Vehicle saraksta, ja\n"
						+ "zināms šī objekta id.\n"
						+ "vi. Izveidojiet funkciju updateClass1_VarXById(), kas rediģē konkrētu Jūsu varianta\n"
						+ "2.punktā izveidotā klases tipa objektu 6.a. punktā izveidotā Vehicle sarakstā, ja\n"
						+ "zināms šī objekta id.\n"
						+ "\n"
						+ "d. Papildu funkcionalitātes nodrošināšana, veicot datu filtrāciju:\n"
						+ "i. Izveidot funkciju showAllPurchasesByUserCardNr(), kurā tiek izprintēti visi pirkumi\n"
						+ "(tai skaitā produktu sarakstu, katra nopirktā produkta skaitu, laiku un datumu kopējo\n"
						+ "summu) konkrētajam lietotājam.\n"
						+ "ii. Izveidojiet funkciju showVehiclesIfPriceLessThan10000(), kas izfiltrē un izprintē\n"
						+ "konsolē tikai tos transportlīdzekļus, kuru cena ir līdz 10 000 eur (neieskaitot). Šajā\n"
						+ "gadījumā jāizvada detalizētā informācija, neatkarīgi no transportlīdzekļa tipa.\n"
						+ "iii. Izveidojiet funkciju howManyVehiclesAreBought(), kas veic aprēķinu un izprintē\n"
						+ "konsolē, cik daudz transportlīdzekļu ir nopirkti visā veikala pastāvēšanas laikā.\n"
						+ "Atceramies, ka vienā pirkumā pircējs drīkst nopirkt vairākus transportlīdzekļus, jo\n"
						+ "katrā Purchase objektā glabājas saraksts ar Vehicle objektiem jeb nopirktajiem\n"
						+ "transportlīdzekļiem.\n"
						+ "iv. Izveidojiet funkciju howMuchIsTheIncome(), kas veic aprēķinu un izprintē konsolē,\n"
						+ "cik daudz ienākumus (kopējo summu) veikals ir ieguvis par transportlīdzekļu\n"
						+ "pārdošanu visā veikala pastāvēšanas laikā. Atceramies, ka vienā pirkumā pircējs\n"
						+ "drīkst nopirkt vairākus transportlīdzekļus. Šeit neņemam vērā nekādus nodokļus, tikai\n"
						+ "transportlīdzekļu cenu jeb price.\n"
						+ "\n"
						+ "Programmēšana tīmeklī JAVA (2ITB)\n"
						+ "Objektorientētā programmēšana (3EIB)\n"
						+ "2022./2023. m.g.\n"
						+ "Lektore Karina Šķirmante\n"
						+ "v. Izveidot generateVehicleInStore() funkciju, kurā aizpildīt 6.a. punktā izveidoto\n"
						+ "sarakstu ar vismaz vienu Class1_VarX tipa transportlīdzekli un vienu Class2_VarX\n"
						+ "tipa transportlīdzekli, kur objekta mainīgo vērtības tiek ģenerētas pēc nejaušības jeb\n"
						+ "Random principa. Ja ir nepieciešams, ieviest papildus masīvus.", 3.5);
				taskRepo.saveAll(Arrays.asList(task1,task2,task3,task4,task5,task6));
				
				TestResult result1 = new TestResult("Comment 1", 0.1, task1, spc1);
				TestResult result2 = new TestResult("Comment 2", 0, task2, spc1);
				TestResult result3 = new TestResult("Comment 3", 0, task3, spc1);
				TestResult result4 = new TestResult("Comment 4", 0.1, task4, spc1);
				TestResult result5 = new TestResult("Comment 5", 0.2, task5, spc1);
				TestResult result6 = new TestResult("Comment 6", 0, task6, spc1);
				resultRepo.saveAll(Arrays.asList(result1,result2,result3,result4,result5,result6));
				
				
			}
		};
	}

}
