package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Algorithm {

	private ArrayList<GraduateAssistant> students;
	private ArrayList<Class> classes;

	/**
	 * Default constructor
	 */
	public Algorithm() {
		students = new ArrayList<GraduateAssistant>();
		classes = new ArrayList<Class>();
	}

	/**
	 * Constructor that takes already developed lists of Grad. Students and
	 * Classes
	 */
	public Algorithm(ArrayList<GraduateAssistant> newGAList, ArrayList<Class> newClassList) {
		students = newGAList;
		classes = newClassList;
	}

	/**
	 * 
	 * @param GA
	 */
	public void addGradStudent(GraduateAssistant GA) {
		students.add(GA);
	}

	/**
	 * 
	 * @param newClass
	 */
	public void addClass(Class newClass) {
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
				// If yes, then add the GA as a possible TA
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

		// Testing correctness
		for (int z = 0; z < classes.size(); z++) {
			System.out.println(classes.get(z).getClassNumber() + " :: " + classes.get(z).getNumberOfAvailableGA());
		}

		// Loop through all classes
		for (int index = 0; index < classes.size(); index++) {

			if (classes.get(index).getNumberOfAvailableGA() == 0) {

				// PROBLEM!!!

			} // end if number == 0
			else 
			{

				// Loop though all possible GA's for a class

				// Assign a GA from the list of potentially available GAs
				boolean didAssignAGA = false;
				for (int indexOfGA = 0; indexOfGA < classes.get(index).getNumberOfAvailableGA(); indexOfGA++) 
				{
					didAssignAGA = false;
					if (checkStillAvailable(classes.get(index), classes.get(index).getAvailableGA().get(indexOfGA))) 
					{					
						if (20 >= classes.get(index).getAvailableGA().get(indexOfGA).getHoursAssigned()	+ 
								  classes.get(index).getTotalTime()) 
						{
							// add class to GA and GA to class
							classes.get(index).setAssignedGA(classes.get(index).getAvailableGA().get(indexOfGA));
							classes.get(index).getAvailableGA().get(indexOfGA).addClassToCurrentList(classes.get(index));
							didAssignAGA = true;
							break;

						} // end if for time check

					} // end if for check available

				} // end for loop for each GA in a certain class\

				// Here is where we start back tracking
				// 1. DeAssign class to GA
				// 2. DeAssign GA to class
				// 3. Mark the specific GA for the class as visited
				// 4. Decrement index of class's
				if (didAssignAGA == false) 
				{
					String nameOfGA = classes.get(index).deAssignGAFromClass().getName();
					index -= 1;

					for (int indexOfLoop = 0; indexOfLoop < students.size(); indexOfLoop++) 
					{
						if (students.get(indexOfLoop).getName() == nameOfGA) 
						{

							// deallocate the class from the TA
							students.get(indexOfLoop).addDeallocatedClass(classes.get(index));

							// Set students Calendar to free(free it up from the
							// class that was deallocated)
							students.get(indexOfLoop).isAvailable(classes.get(index).getDaysOfWeek(),
									classes.get(index).getStartTime(), classes.get(index).getEndTime());

						} // end if
					} // end loop for deallocation
				}
			} // end else for if number == 0

		} // end for loop for classes

		// Testing correctness
		for (int j = 0; j < classes.size(); j++) 
		{
			System.out.println("Class: " + classes.get(j).getClassNumber() + " is taught by: "
					+ classes.get(j).getAssignedGA().getName());
		}

		for (int k = 0; k < students.size(); k++) 
		{
			System.out.print("student: " + students.get(k).getName() + " is assisting: ");
			Iterator<Class> itr = students.get(k).returnIteratorOfAllClassForGA();
			while (itr.hasNext()) {
				System.out.print(itr.next().getClassNumber() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}// end method createInitialSolution (The one Sam created)


	/**
	 * Method for performing a Variable Neighborhood search
	 */
	public void VNS() {

	}

	public void MILP() {

	}

	private boolean checkStillAvailable(Class weeklyClass, GraduateAssistant ga) {
		// check if it has been dealocated
		if (!weeklyClass.isGADealocated(ga.getName())) {

			// Here we need to check if the time is still available for the GA.
			// I do not understand how the Calednar class works and need Matt
			// to explain that to me.

			return true;
		}
		return false;
	}// End method checkStillAvailable

	/**
	 * A custom Comparator class that allows for the comparison of two classes.
	 * Classes that have a smaller number of available GAs will be sorted first.
	 * 
	 * @author Matthew
	 *
	 */
	private class CustomComparator implements Comparator<Class> {
		@Override
		public int compare(Class c1, Class c2) {
			if (c1.getNumberOfAvailableGA() < c2.getNumberOfAvailableGA()) {
				return -1;
			} else if (c1.getNumberOfAvailableGA() > c2.getNumberOfAvailableGA()) {
				return 1;
			}
			return 0;
		}
	}

}// end class Algorithm
