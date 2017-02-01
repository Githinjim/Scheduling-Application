import java.util.ArrayList;

import org.junit.Test;

import application.Algorithm;
import application.Class;
import application.GraduateAssistant;
import junit.framework.TestCase;

public class Test_Algorithm_BaseCase extends TestCase {

	Algorithm alg;
	ArrayList<GraduateAssistant> GAList;
	ArrayList<Class> classList;
	
	public void setUp()
	{
		GAList = new ArrayList<GraduateAssistant>();
		classList = new ArrayList<Class>();
		
		setupClasses();
		setupGAs();
	}
	
	@Test
	public void test()
	{					
		alg = new Algorithm(GAList, classList);
		alg.initializeGraph();
		alg.createInitialSolution();
		//fail("test");
	}
	
	private void setupClasses()
	{
		//456.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 456.001");
		classList.get(classList.size() - 1).setStartTime("10am");
		classList.get(classList.size() - 1).setEndTime("11am");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(1);
		
		//456.00
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 456.002");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(1);
		
		//Research
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Research");
		classList.get(classList.size() - 1).setStartTime("2pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		
		//446.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 446.001");
		classList.get(classList.size() - 1).setStartTime("12pm");
		classList.get(classList.size() - 1).setEndTime("1pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(1);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setStartTime("2pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setPrepHours(2);
		
		// 351.010
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 351.010");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).setPrepHours(2);

		//370.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 370.001");
		classList.get(classList.size() - 1).setStartTime("11am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//370.006
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 370.006");
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("11am");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//351.001 Test
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 351.001");
		classList.get(classList.size() - 1).setStartTime("1pm");
		classList.get(classList.size() - 1).setEndTime("2pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		// 450.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 450.001");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("9am");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("11am");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		
		//450.011 Test
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 450.011");
		classList.get(classList.size() - 1).setStartTime("10am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		
		// 455.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 455.001");
		classList.get(classList.size() - 1).setStartTime("1pm");
		classList.get(classList.size() - 1).setEndTime("2pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		
		// 455.002
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 455.002");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(4);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setStartTime("2pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		
		//564
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("IHP 564.001");
		classList.get(classList.size() - 1).setStartTime("4pm");
		classList.get(classList.size() - 1).setEndTime("8pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		
		// 350.003
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 350.003");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(4);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("3pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("3pm");
		classList.get(classList.size() - 1).setEndTime("5pm");
		classList.get(classList.size() - 1).addDayOfWeek(2);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setStartTime("5pm");
		classList.get(classList.size() - 1).setEndTime("7pm");
		classList.get(classList.size() - 1).addDayOfWeek(2);
		
		// Anatomy Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Anatomy Lab");
		classList.get(classList.size() - 1).setStartTime("6pm");
		classList.get(classList.size() - 1).setEndTime("8pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		
		//350.004
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 350.004");
		classList.get(classList.size() - 1).setStartTime("10am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//551.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("IHP 551.001");
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("11am");
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		
		//350.011
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 350.011");
		classList.get(classList.size() - 1).setStartTime("1pm");
		classList.get(classList.size() - 1).setEndTime("3pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//350.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 350.001");
		classList.get(classList.size() - 1).setStartTime("4pm");
		classList.get(classList.size() - 1).setEndTime("6pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//350.002
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 350.002");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
//		//351.011 
//		classList.add(new Class());
//		classList.get(classList.size() - 1).setClassNumber("EXSC 351.011");
//		classList.get(classList.size() - 1).setStartTime("1pm");
//		classList.get(classList.size() - 1).setEndTime("2pm");
//		classList.get(classList.size() - 1).addDayOfWeek(1);
//		classList.get(classList.size() - 1).setPrepHours(1);
		
		//351.003
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 351.003");
		classList.get(classList.size() - 1).setStartTime("1pm");
		classList.get(classList.size() - 1).setEndTime("3pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//450.002
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 450.002");
		classList.get(classList.size() - 1).setStartTime("2pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("3pm");
		classList.get(classList.size() - 1).setEndTime("4pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("5pm");
		classList.get(classList.size() - 1).setEndTime("6pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);

		//353.001
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 353.001");
		classList.get(classList.size() - 1).setStartTime("6pm");
		classList.get(classList.size() - 1).setEndTime("8pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).addDayOfWeek(1);

		//  351.012
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 351.012");
		classList.get(classList.size() - 1).setStartTime("8am");
		classList.get(classList.size() - 1).setEndTime("9am");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(2);;
		
		//351.002
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 351.002");
		classList.get(classList.size() - 1).setStartTime("10am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		
		//370.002
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 370.002");
		classList.get(classList.size() - 1).setStartTime("11am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		classList.get(classList.size() - 1).addDayOfWeek(1);
		classList.get(classList.size() - 1).addDayOfWeek(2);
		classList.get(classList.size() - 1).addDayOfWeek(4);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		//370.005
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("EXSC 370.005");
		classList.get(classList.size() - 1).setStartTime("11am");
		classList.get(classList.size() - 1).setEndTime("1pm");
		classList.get(classList.size() - 1).addDayOfWeek(3);
		classList.get(classList.size() - 1).setPrepHours(2);
		
		// Fit Lab
		classList.add(new Class());
		classList.get(classList.size() - 1).setClassNumber("Fit Lab");
		classList.get(classList.size() - 1).setStartTime("3pm");
		classList.get(classList.size() - 1).setEndTime("5pm");
		classList.get(classList.size() - 1).addDayOfWeek(2);
		
	}
	
	private void setupGAs()
	{
	
		// GRADUATE ASSISTANTS_-_-_-_-_-_-_-_-_-_-_-_-_-_
		// #0
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("0");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "9pm"); // All day Monday
		GAList.get(GAList.size() - 1).setAvailableAt(1, "8am", "12pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "2pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "9pm"); // All day Wednesday
		GAList.get(GAList.size() - 1).setAvailableAt(3, "8am", "12pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "2pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "9pm"); // All day Friday
		GAList.get(GAList.size() - 1).addQualification("EXSC 456");
		GAList.get(GAList.size() - 1).addQualification("EXSC 446");
		GAList.get(GAList.size() - 1).addQualification("EXSC 458");
		GAList.get(GAList.size() - 1).addQualification("EXSC 551");
		GAList.get(GAList.size() - 1).addQualification("Anatomy Lab");
		GAList.get(GAList.size() - 1).addQualification("Research");
		
		// #1
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("1");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "9am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "11am", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "9am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "11am", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "9am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "11am", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "9am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "11am", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "9am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "11am", "9pm");
		GAList.get(GAList.size() - 1).addQualification("EXSC 370");
		GAList.get(GAList.size() - 1).addQualification("EXSC 351");
		GAList.get(GAList.size() - 1).addQualification("EXSC 350");
		GAList.get(GAList.size() - 1).addQualification("EXSC 445");
		
		// #2
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("2");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "12pm");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "8am", "4pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "5pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "3pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "5pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "8am", "4pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "5pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "12pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "1pm", "9pm");
		GAList.get(GAList.size() - 1).addQualification("EXSC 450");
		GAList.get(GAList.size() - 1).addQualification("EXSC 455");
		GAList.get(GAList.size() - 1).addQualification("IHP 564");
		GAList.get(GAList.size() - 1).addQualification("Anatomy Lab");
		
		// #3
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("3");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "1pm");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "8am", "1pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "1pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "8am", "1pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "9pm");
		GAList.get(GAList.size() - 1).addQualification("EXSC 350");
		GAList.get(GAList.size() - 1).addQualification("Anatomy Lab");
		GAList.get(GAList.size() - 1).addQualification("Fit Lab");
		
		// #4
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("4");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "9am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "9am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "12pm", "9pm");
		GAList.get(GAList.size() - 1).addQualification("IHP 551");
		GAList.get(GAList.size() - 1).addQualification("EXSC 350");
		
		// #5
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("5");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "12pm", "4pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "9am");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "9am");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "11am", "9pm");
		GAList.get(GAList.size() - 1).addQualification("EXSC 350");
		GAList.get(GAList.size() - 1).addQualification("EXSC 351");
		GAList.get(GAList.size() - 1).addQualification("EXSC 450");
		GAList.get(GAList.size() - 1).addQualification("EXSC 353");
		GAList.get(GAList.size() - 1).addQualification("Fit Lab");
		
		// #6
		GAList.add(new GraduateAssistant());
		GAList.get(GAList.size() - 1).setName("6");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "8am", "10am");
		GAList.get(GAList.size() - 1).setAvailableAt(0, "12pm", "4pm");
		GAList.get(GAList.size() - 1).setAvailableAt(1, "8am", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "8am", "9am");
		GAList.get(GAList.size() - 1).setAvailableAt(2, "1pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "8am", "11am");
		GAList.get(GAList.size() - 1).setAvailableAt(3, "12pm", "9pm");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "8am", "9am");
		GAList.get(GAList.size() - 1).setAvailableAt(4, "11am", "9pm");
		GAList.get(GAList.size() - 1).addQualification("EXSC 351");
		GAList.get(GAList.size() - 1).addQualification("EXSC 370");
		GAList.get(GAList.size() - 1).addQualification("Fit Lab");
		
/*		GAList.add(new GraduateAssistant());
		GAList.get(0).setName("Nick Cage");
		GAList.get(0).setAvailableAt(0, "8am", "12pm");
		
		GAList.add(new GraduateAssistant());
		GAList.get(1).setName("Tom Hanks");
		GAList.get(1).setAvailableAt(1, "8am", "12pm");*/
	}
	
}
