package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Algorithm {

	private ArrayList<GraduateAssistant> students;
	private ArrayList<Class> classes;
	private int MAX_HOURS = 20;
	
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
	
	public Algorithm(ArrayList<GraduateAssistant> newGAList, ArrayList<Class> newClassList,int MAX_HOURS)
	{
		this.MAX_HOURS = MAX_HOURS;
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
	public ArrayList<Class> createInitialSolution()
	{
		
		ArrayList<Class> unassignedClasses = new ArrayList<Class>();
		
		// Sort all Classes by the number of available TAs
		Collections.sort(classes, new CompareClasses());
		
		// For each class, start assigning GAs
		for (Class weeklyClass : classes)
		{
			if (weeklyClass.getNumberOfAvailableGA() == 0)
			{
				// Partial assignment
				unassignedClasses.add(weeklyClass);
			}
			else 
			{
				if(!assignGA(weeklyClass))
				{
					// Was unable to assign a class
					// Need to perform backtracking or partial assignment
					if (!alternatingPath(weeklyClass))
					{
						unassignedClasses.add(weeklyClass);
					}
				}
			}
		}	
		
		return unassignedClasses;
	}
	
	/**
	 * Resets all assignments
	 */
	public void reset()
	{
		for (GraduateAssistant ga : students)
		{
			ga.resetGA();
		}
		
		for (Class weekly : classes)
		{
			weekly.resetClass();
		}
	}
	
	/**
	 * 
	 * @return Total number of hours worked by GAs
	 */
	public int getStudentHours()
	{
		int hours = 0;
		
		for (GraduateAssistant ga : students)
		{
			hours += ga.getHoursAssigned();
		}
		
		return hours;
	}
	
	/**
	 * 
	 * @return Total number of class hours that require a TA
	 */
	public int getClassHours()
	{
		int hours = 0;
		
		for (Class weekly : classes)
		{
			hours += weekly.getWorkTime();
		}
		
		return hours;
	}
	
	/**
	 * Assigns a GA to a class - where the GA is only available for part of the class times
	 * @param weeklyClass
	 * @return True if a GA was assigned
	 */
	public boolean partialAssignment(Class weeklyClass)
	{
		// Ignore qualifications		
		if (assignIgnoringQualifications(weeklyClass))
		{
			return true;
		}
		// Attempt to assign a GA that is available all days of the week, but for only part of the class hours
		else if (assignPartialHours(weeklyClass))
		{
			return true;
		}
		else if(assignIgnoringQualificationsAndTimes(weeklyClass))
		{
			return true;
		}
		
		return false;
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
				MAX_HOURS >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
			{
				weeklyClass.setAssignedGA(ga);
				ga.addAssistingClass(weeklyClass);;
				return true; // A GA was assigned
			}
			
		}

		return false; // A GA was not assigned.
	}

	/**
	 * In the case of a class having no available GAs, this method checks for any conflicting classes and attempts
	 * to re-assign the conflicting classes, freeing up a GA for assignment
	 * @return True if the class was assigned
	 */
	private boolean alternatingPath(Class weeklyClass)
	{
		for (GraduateAssistant ga : weeklyClass.getAvailableGA())
		{
			// Find conflicting class(es)
			ArrayList<Class> conflicting = new ArrayList<Class>();
			for (Class potentialConflict : ga.getAssignedClasses())
			{
				if (!potentialConflict.getClassNumber().equals(weeklyClass.getClassNumber()) && // Don't want it to be the same class
					potentialConflict.getAssignedGA().size() >= 1 &&							// Conflicting class has to have a GA assigned
					hourConflict(potentialConflict, weeklyClass) &&								// Conflicting times
					dayConflict(potentialConflict, weeklyClass))									
				{
					conflicting.add(potentialConflict);
				}
			}
			
			// Attempt to re-assign a conflicting class
			for (Class conflict : conflicting)
			{
				conflict.removeAssignedGA(ga);
				conflict.removeAvailableGA(ga);
				
				if (assignGA(conflict))
				{
						
					// Don't re-assign the same GA
					if (!conflict.getAssignedGA().contains(ga))
					{
						weeklyClass.setAssignedGA(ga);
						return true;
					}
				}
//				else if (alternatingPath(conflict))
//				{
//					return true;
//				}
				else
				{
					conflict.setAssignedGA(ga);
				}
				// Ensure that the GA is still available 
				conflict.addAvailableGA(ga);
			}
			
		}
		return false;
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
				MAX_HOURS >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
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
	private boolean assignPartialHours(Class weeklyClass)
	{
		boolean returnVal = false;
		int maxAvailableHours = 0, numAvailableHours = 0;
		GraduateAssistant mostAvailableGA = null;
		
		int startTime = stringToHour(weeklyClass.getStartTime());
		int endTime = stringToHour(weeklyClass.getEndTime());
		
		// Determine which GA has the most time they would be able to spend on the class
		// This also relaxes the qualifications
		for (GraduateAssistant ga : students)
		{
			numAvailableHours = 0;
			if (ga.isQualified(weeklyClass.getClassNumber()) &&
				MAX_HOURS >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
			{
				// For each day
				for (int day : weeklyClass.getDaysOfWeek())
				{
					// Determine the amount of available time
					for (int i = startTime; i < endTime; i++)
					{
						if (ga.isAvailable(day, hourToString(i)))
						{
							numAvailableHours++;
						}
					}
				}
			}
			
			if (numAvailableHours > maxAvailableHours)
			{
				mostAvailableGA = ga;
			}
		}
		
		if (mostAvailableGA != null)
		{
			mostAvailableGA.addAssistingClass(weeklyClass);
			weeklyClass.setAssignedGA(mostAvailableGA);
			returnVal = true;
		}
		return returnVal;
	}
	
	/**
	 * 
	 * @param weeklyClass
	 * @return
	 */
	private boolean assignIgnoringQualificationsAndTimes(Class weeklyClass)
	{
		boolean returnVal = false;
		int maxAvailableHours = 0, numAvailableHours = 0;
		GraduateAssistant mostAvailableGA = null;
		
		int startTime = stringToHour(weeklyClass.getStartTime());
		int endTime = stringToHour(weeklyClass.getEndTime());
		
		// Determine which GA has the most time they would be able to spend on the class
		// This also relaxes the qualifications
		for (GraduateAssistant ga : students)
		{
			numAvailableHours = 0;
			if (MAX_HOURS >= ga.getHoursAssigned() + weeklyClass.getWorkTime())
			{
				// For each day
				for (int day : weeklyClass.getDaysOfWeek())
				{
					// Determine the amount of available time
					for (int i = startTime; i < endTime; i++)
					{
						if (ga.isAvailable(day, hourToString(i)))
						{
							numAvailableHours++;
						}
					}
				}
			}
			
			if (numAvailableHours > maxAvailableHours)
			{
				mostAvailableGA = ga;
			}
		}
		
		if (mostAvailableGA != null)
		{
			mostAvailableGA.addAssistingClass(weeklyClass);
			weeklyClass.setAssignedGA(mostAvailableGA);
			returnVal = true;
		}
		
		return returnVal;
	}
	
	/**
	 * 
	 * @param c1
	 * @param c2
	 * @return True if Class 1 has any hours that overlap with Class 2
	 */
	private boolean hourConflict(Class c1, Class c2)
	{
		boolean returnVal = false;
		
		int c1_start = stringToHour(c1.getStartTime());
		int c1_end = stringToHour(c1.getEndTime());
		int c2_start = stringToHour(c2.getStartTime());
		int c2_end = stringToHour(c2.getEndTime());
		
		// See if there are conflicting hours
		for (int i = c1_start; i < c1_end; i++)
		{
			if (i >= c2_start && i < c2_end)
			{
				returnVal = true;
				break;
			}
		}
		
		return returnVal;
	}
	
	/**
	 * Determines if two classes have overlapping days
	 * @param c1
	 * @param c2
	 * @return
	 */
	private boolean dayConflict(Class c1, Class c2)
	{
		boolean returnVal = false;
		
		ArrayList<Integer> days_c1 = c1.getDaysOfWeek();
		ArrayList<Integer> days_c2 = c2.getDaysOfWeek();
		
		for (int day : days_c1)
		{
			if (days_c2.contains(day))
			{
				returnVal = true;
				break;
			}
		}
		
		return returnVal;
	}
	
	/**
	 * Converts a Time string to its integer representation
	 * @param hour
	 * @return the integer representation of the string
	 */
	private int stringToHour(String hour)
	{
		int time = -1;
		hour = hour.toUpperCase();
		
		switch (hour)
		{
		case "8AM":
			time = 0;
			break;
		case "9AM":
			time = 1;
			break;
		case "10AM":
			time = 2;
			break;
		case "11AM":
			time = 3;
			break;
		case "12PM":
			time = 4;
			break;
		case "1PM":
			time = 5;
			break;
		case "2PM":
			time = 6;
			break;
		case "3PM":
			time = 7;
			break;
		case "4PM":
			time = 8;
			break;
		case "5PM":
			time = 9;
			break;
		case "6PM":
			time = 10;
			break;
		case "7PM":
			time = 11;
			break;
		case "8PM":
			time = 12;
			break;
		case "9PM":
			time = 13;
			break;
		}
		
		return time;
	}
	
	/**
	 * Converts an integer representation to it's equivalent String
	 * @param hour
	 * @return
	 */
	private String hourToString(int hour)
	{
		String time = "";
		
		switch(hour)
		{
		case 0:
			time = "8AM";
			break;
		case 1:
			time = "9AM";
			break;
		case 2:
			time = "10AM";
			break;
		case 3:
			time = "11AM";
			break;
		case 4:
			time = "12PM";
			break;
		case 5:
			time = "1PM";
			break;
		case 6:
			time = "2PM";
			break;
		case 7:
			time = "3PM";
			break;
		case 8:
			time = "4PM";
			break;
		case 9:
			time = "5PM";
			break;
		case 10:
			time = "6PM";
			break;
		case 11:
			time = "7PM";
			break;
		case 12:
			time = "8PM";
			break;
		case 13:
			time = "9PM";
			break;
		}
		
		return time;
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
