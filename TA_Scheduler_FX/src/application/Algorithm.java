package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Algorithm {

	private ArrayList<GraduateAssistant> students;
	private ArrayList<Class> classes;
	
	/**
	 * Default constructor
	 */
	public Algorithm()
	{
		students = new ArrayList<GraduateAssistant>();
		classes = new ArrayList<Class>();
	}
	
	/**
	 * Constructor for creating a new Algorithm with already instantiated lists of GAs and Classes
	 */
	public Algorithm(ArrayList<GraduateAssistant> newGAList, ArrayList<Class> newClassList)
	{
		students = newGAList;
		classes = newClassList;
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
				if (ga.isAvailable(weeklyClass.getDaysOfWeek(), weeklyClass.getStartTime(), weeklyClass.getEndTime()) &&
						ga.isQualified(weeklyClass.getClassNumber()))
				{
					weeklyClass.addAvailableGA(ga);
					ga.addPossibleGAClass(weeklyClass);
				}
			}
		}
	}
	
	/**
	 * Creates an initial solution
	 */
	public void createInitialSolution()
	{
		// Sort all Classes by the number of available TAs
		Collections.sort(classes, new CompareClasses());
		
		// Verifying results
		System.out.println("-----AVAILABLE GAS-----");
		for (Class weeklyClass : classes)
		{
			System.out.println("Number available GAs for " + weeklyClass.getClassNumber() + ": " + weeklyClass.getNumberOfAvailableGA());
		}
		
		// For each class, start assigning GAs
		for (Class weeklyClass : classes)
		{
			if (weeklyClass.getNumberOfAvailableGA() == 0)
			{
				// Partial assignment
			}
			else 
			{
				if(!assignGA(weeklyClass))
				{
					backtrack(weeklyClass);
					// Backtracking or partial assignment
				}
			}
		}
		
		// Verifying output
		System.out.println("-----CLASSES-----");
		for (Class weeklyClass : classes)
		{
			System.out.print(weeklyClass.getClassNumber() + " is assisted by: ");
			for (GraduateAssistant ga : weeklyClass.getAssignedGA())
			{
				System.out.print("\t" + ga.getName());
			}
			System.out.println();
		}
		
		System.out.println("-----STUDENTS-----");
		for (GraduateAssistant ga : students)
		{
			System.out.print(ga.getName() + " is assisting for " + ga.getHoursAssigned() + ":");
			for (Class weeklyClass : ga.getAssignedClasses())
			{
				System.out.print("\t" + weeklyClass.getClassNumber());
			}
			System.out.println();
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
	
	/**
	 * Checks that the GA is still available to TA for the class
	 * @param weeklyClass
	 * @param ga
	 * @return True if the GA is available
	 */
	private boolean checkStillAvailable(Class weeklyClass, GraduateAssistant ga)
	{
		if (ga.isAvailable(weeklyClass.getDaysOfWeek(), weeklyClass.getStartTime(), weeklyClass.getEndTime()))
		{
			return true;
		}
		
		return false;
	}//End method checkStillAvailable
	
	/**
	 * Attempts to assign a GA to a class
	 * @param weeklyClass The class that needs a GA assigned
	 * @return True if a GA was assigned
	 */
	public boolean assignGA(Class weeklyClass)
	{		
		for (GraduateAssistant ga : weeklyClass.getAvailableGA())
		{
		
			// Assign the GA if they are still available
			if (checkStillAvailable(weeklyClass, ga) &&
				20 >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
			{
				weeklyClass.setAssignedGA(ga);
				ga.addAssistingClass(weeklyClass);;
				return true; // A GA was assigned
			}
		}
		
		return false; // A GA was not assigned.
	}

	/**
	 * 
	 * @return
	 */
	private boolean backtrack(Class weeklyClass)
	{
		for (GraduateAssistant ga : weeklyClass.getAvailableGA())
		{
			// Find conflicting class(es)
			ArrayList<Class> conflicting = new ArrayList<Class>();
			
			for (Class potentialConflict : ga.getPotentialClasses())
			{
				// Class conflicts if the start and end time are the same?
				// And the potential class is not the same as the weeklyClass
				if (!potentialConflict.getClassNumber().equals(weeklyClass.getClassNumber()) &&
					potentialConflict.getStartTime().equals(weeklyClass.getStartTime()) &&
					potentialConflict.getEndTime().equals(weeklyClass.getEndTime()))
				{
					conflicting.add(potentialConflict);
				}
			}
			
			// Attempt to re-assign a conflicting class
			for (Class conflict : conflicting)
			{
				//ArrayList<GraduateAssistant> temp = conflict.getAssignedGA();
				conflict.removeAssignedGA(ga);
				if (assignGA(conflict))
				{
					// Don't re-assign the same GA
					if (conflict.getAssignedGA().contains(ga))
					{
						System.out.println("Re-assigned: " + conflict.getClassNumber());
						assignGA(weeklyClass);
						return true;
					}
				}
				else {
					conflict.setAssignedGA(ga);
				}
			}
			
		}
		return false;
	}
	
	/**
	 * A custom Comparator class that allows for the comparison of two classes.
	 * Classes that have a smaller number of available GAs will be sorted first.
	 * @author Matthew
	 *
	 */
	private class CompareClasses implements Comparator<Class>
	{
		@Override
		public int compare(Class c1, Class c2)
		{
			if (c1.getNumberOfAvailableGA() < c2.getNumberOfAvailableGA())
			{
				return -1;
			}
			else if (c1.getNumberOfAvailableGA() == c2.getNumberOfAvailableGA())
			{
				if (c1.getWorkTime() < c2.getWorkTime())
				{
					return -1;
				}
			}
			return 0;
		}
	}
	
	/**
	 * 
	 * @author Matthew
	 *
	 */
	private class CompareGAS implements Comparator<GraduateAssistant>
	{
		@Override
		public int compare(GraduateAssistant g1, GraduateAssistant g2)
		{
			if (g1.getHoursAssigned() < g2.getHoursAssigned())
			{
				return -1;
			}
			return 0;
		}
	}
	
}//end class Algorithm
