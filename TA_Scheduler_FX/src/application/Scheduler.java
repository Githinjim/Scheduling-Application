package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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
				// Check if the GA is available for the class times
				if (ga.isAvailable(weeklyClass.getDaysOfWeek(), weeklyClass.getStartTime(), weeklyClass.getEndTime()))
				{
					weeklyClass.addAvailableGA(ga);
					ga.addPossibleGAClass(weeklyClass);
				}
			}
		}
	}
	
	public void createInitialSolution()
	{
		// Sort all Classes by the number of available TAs
		Collections.sort(classes, new CustomComparator());
		
		// For each class, start assigning GAs
		for (Class weeklyClass : classes)
		{
			if (weeklyClass.getNumberOfAvailableGA() == 0)
			{
				// Problem!!
			}
			else if (weeklyClass.getNumberOfAvailableGA() == 1)
			{
				weeklyClass.setAssignedGA(weeklyClass.getAvailableGA().get(0));
			}
			else 
			{
				// How do we want to select the GA?
			}
		}
	}
	
	public void VNS()
	{
		
	}
	
	public void MILP()
	{
		
	}
	
	/**
	 * 
	 * @author Matthew
	 *
	 */
	private class CustomComparator implements Comparator<Class>
	{
		@Override
		public int compare(Class c1, Class c2)
		{
			if (c1.getNumberOfAvailableGA() < c2.getNumberOfAvailableGA())
			{
				return -1;
			}
			else if (c1.getNumberOfAvailableGA() > c2.getNumberOfAvailableGA())
			{
				return 1;
			}
			return 0;
		}
	}
}
