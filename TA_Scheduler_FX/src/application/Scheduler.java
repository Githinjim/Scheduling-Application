package application;

import java.util.ArrayList;
import java.util.Comparator;

public class Scheduler {
	private ArrayList<GraduateAssistant> students;
	private ArrayList<Class> classes;
	
	/**
	 * Default constructor
	 */
	public Scheduler()
	{
		students = new ArrayList<GraduateAssistant>();
		classes = new ArrayList<Class>();
	}
	
	/**
	 * 
	 * @param GA
	 */
	public void addGradStudent(GraduateAssistant GA)
	{
		students.add(GA);
	}
	
	/**
	 * 
	 * @param newClass
	 */
	public void addClass(Class newClass)
	{
		classes.add(newClass);
	}
	
	/**
	 * Method that initializes 
	 */
	public void initializeGraph()
	{	
		// For each class
		for (Class weeklyClass : classes)
		{
			// For each Graduate Assistant
			for (GraduateAssistant ga : students)
			{
							
			}
		}
	}
	
	public void createInitialSolution()
	{
		// Sort all Classes by the number of available TAs
	}
	
	public void VNS()
	{
		
	}
	
	public void MILP()
	{
		
	}
}
