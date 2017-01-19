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
	 * Method that determines the possible GA and Class matching
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
	
	/**
	 * Method that creates the initial solution
	 */
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
				//Check that the GA is still available
				if (checkStillAvailable(weeklyClass, weeklyClass.getAvailableGA().get(0)))
				{
					weeklyClass.setAssignedGA(weeklyClass.getAvailableGA().get(0));
					weeklyClass.getAvailableGA().get(0).addClassToCurrentList(weeklyClass);
				}
				else
				{
					//Problem!
				}
			}
			else 
			{
				// How do we want to select the GA?
				
				// Start iterating over the list of available GAs
				for (int i = 0; i < weeklyClass.getNumberOfAvailableGA(); i++)
				{
					// If a GA is available, assign them?
					if (checkStillAvailable(weeklyClass, weeklyClass.getAvailableGA().get(i)))
					{
						weeklyClass.setAssignedGA(weeklyClass.getAvailableGA().get(i));
						weeklyClass.getAvailableGA().get(i).addClassToCurrentList(weeklyClass);
						break;
					}
				} // If we reach the end of the for loop and a GA has not been assigned,
				  // then we start our alternating path
			}
		}
	}
	
	/**
	 * Method for performing a Variable Neighborhood Search
	 */
	public void VNS()
	{
		
	}
	
	public void MILP()
	{
		
	}
	
	private boolean checkStillAvailable(Class weeklyClass, GraduateAssistant ga)
	{
		return true;
	}
	
	/**
	 * A custom Comparator class that allows for the comparison of two classes.
	 * Classes that have a smaller number of available GAs will be sorted first.
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
