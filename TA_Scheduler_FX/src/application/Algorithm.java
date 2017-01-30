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
		
		ArrayList<Class> unassignedClasses = new ArrayList<Class>();
		
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
				unassignedClasses.add(weeklyClass);
				//partialAssignment(weeklyClass);
			}
			else 
			{
				if(!assignGA(weeklyClass))
				{
					// Was unable to assign a class
					// Need to perform backtracking or partial assignment
					if (!backtrack(weeklyClass))
					{
						unassignedClasses.add(weeklyClass);
					}
					
				}
			}
		}
		
		// Now attempt to partially assign any assigned classes
//		for (int i = 0; i < unassignedClasses.size(); i++)
//		{
//			if (partialAssignment(unassignedClasses.get(i)))
//			{
//				unassignedClasses.remove(i);
//			}
//		}
		
		for (Class weeklyClass : unassignedClasses)
		{
			partialAssignment(weeklyClass);
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
	private boolean assignGA(Class weeklyClass)
	{	
		MersenneTwisterFast random_index = new MersenneTwisterFast();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		int index;
		
		// For the number of available GAs
		for (int i = 0; i < weeklyClass.getNumberOfAvailableGA(); i++)
		{	
			// Get a random GA
			do
			{
				index = random_index.nextInt(weeklyClass.getNumberOfAvailableGA());
			}
			while (visited.contains(index));
			visited.add(index);
			
			GraduateAssistant ga = weeklyClass.getAvailableGA().get(index);
			
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
	 * @return True if the class was assigned
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
				conflict.removeAssignedGA(ga);
				if (assignGA(conflict))
				{
					// Don't re-assign the same GA
					if (conflict.getAssignedGA().contains(ga))
					{
						assignGA(weeklyClass);
						return false;
					}
				}
				else {
					conflict.setAssignedGA(ga);
				}
			}
			
		}
		return true;
	}
	
	/**
	 * Assigns a GA to a class - where the GA is only available for part of the class times
	 * @param weeklyClass
	 * @return True if a GA was assigned
	 */
	private boolean partialAssignment(Class weeklyClass)
	{
		// Ignore qualifications		
		if (assignIgnoringQualifications(weeklyClass))
		{
			//return true;
		}
		
		// Attempt to assign a GA that is available all days of the week, but for only part of the class hours
				
		// Attempt to assign a GA that is available for the given times, but not all days of the week
		
		// Attempt to assign a GA that is available some days of the week, for some of the time
		
		return true;
	}
	
	/**
	 * 
	 * @param weeklyClass
	 * @return True if the classes was assigned
	 */
	private boolean assignIgnoringQualifications(Class weeklyClass)
	{
		boolean returnVal = false;
		
		MersenneTwisterFast random_index = new MersenneTwisterFast(System.nanoTime());
		ArrayList<Integer> visited = new ArrayList<Integer>();
		int index;
		
		// For the number of available GAs
		for (int i = 0; i < students.size(); i++)
		{	
			// Get a random GA
			do
			{
				index = random_index.nextInt(students.size());
			}
			while (visited.contains(index));
			visited.add(index);
			
			GraduateAssistant ga = students.get(index);
			
			
			
			// Assign the GA if they are still available
			if (checkStillAvailable(weeklyClass, ga) &&
				20 >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
			{
				weeklyClass.setAssignedGA(ga);
				ga.addAssistingClass(weeklyClass);
				returnVal = true; // A GA was assigned
				break;
			}
			else // Don't want that GA to be considered available
			{

				weeklyClass.removeAvailableGA(ga);
				ga.removePossibleClass(weeklyClass);
			}
			
		}
		return returnVal;
	}
	
	/**
	 * 
	 * @param days
	 * @param startTime
	 * @param endTime
	 * @param ga
	 * @return
	 */
	private boolean availablePartialHours(ArrayList<Integer> days, String startTime, String endTime, GraduateAssistant ga)
	{
		boolean returnVal = true;
		
		ArrayList<String> timeList = new ArrayList<String>();
		timeList.add("8am");
		timeList.add("9am");
		timeList.add("10am");
		timeList.add("11am");
		timeList.add("12pm");
		timeList.add("1pm");
		timeList.add("2pm");
		timeList.add("3pm");
		timeList.add("4pm");
		timeList.add("5pm");
		timeList.add("6pm");
		timeList.add("7pm");
		timeList.add("8pm");
		
		int startIndex = timeList.indexOf(startTime);
		int endIndex = timeList.indexOf(endTime);
		
		for (int i = startIndex + 1; i < endIndex; i++)
		{
			if (!ga.isAvailable(days, timeList.get(startIndex), endTime))
			{
				returnVal = false;
			}
		}

		return returnVal;
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
			return 1;
		}
	}

	
}//end class Algorithm
