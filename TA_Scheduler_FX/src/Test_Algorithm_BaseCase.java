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
		
		// GRADUATE ASSISTANTS_-_-_-_-_-_-_-_-_-_-_-_-_-_
		GAList.add(new GraduateAssistant());
		GAList.get(0).setName("Nick Cage");
		GAList.get(0).setAvailableAt(0, "8am", "12pm");
		
		GAList.add(new GraduateAssistant());
		GAList.get(1).setName("Tom Hanks");
		GAList.get(1).setAvailableAt(1, "8am", "12pm");
		

		//CLASSES_-_-_-_-_-_-_-_-_-_-_-_-_-_
		classList.add(new Class());
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).setClassNumber("cool class 201");
		classList.get(classList.size() - 1).addDayOfWeek(0);
		//classList.get(classList.size() - 1).addDayOfWeek(1);
		
		
		classList.add(new Class());
		classList.get(classList.size() - 1).setStartTime("9am");
		classList.get(classList.size() - 1).setEndTime("10am");
		classList.get(classList.size() - 1).setClassNumber("ENG 345");
		classList.get(classList.size() - 1).addDayOfWeek(1);
		
		classList.add(new Class());
		classList.get(classList.size() - 1).setStartTime("11am");
		classList.get(classList.size() - 1).setEndTime("12pm");
		classList.get(classList.size() - 1).setClassNumber("CS 501");
		classList.get(classList.size() - 1).addDayOfWeek(0);

	}
	
	@Test
	public void test()
	{		
		System.out.println("Total hours of the class is: " + classList.get(0).getTotalTime());
		System.out.println("Total hours of the class is: " + classList.get(1).getTotalTime());
		System.out.println("Total hours of the class is: " + classList.get(2).getTotalTime());
		
		alg = new Algorithm(GAList, classList);
		alg.initializeGraph();
		alg.createInitialSolution();
		//fail("test");
	}
	
}
