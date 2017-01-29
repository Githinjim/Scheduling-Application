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
	public Algorithm(ArrayList<GraduateAssistant> newGAList, ArrayList<Class> newClassList)
	{
		students = new ArrayList<GraduateAssistant>();
		classes = new ArrayList<Class>();
		
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
	
	public void createInitialSolution(){
		
		// Sort all Classes by the number of available TAs
		Collections.sort(classes, new CustomComparator());
		
		for(int z = 0; z < classes.size(); z++){
			System.out.println(classes.get(z).getClassNumber() + " :: " + classes.get(z).getNumberOfAvailableGA());
		}
		
		//Loop through all classes with the "index" variable
		for(int index = 0; index < classes.size(); index++){
			
			if(classes.get(index).getNumberOfAvailableGA() == 0){
				
				//PROBLEM!!!
				
			}//end if number == 0
			else{
				
				//Loop though all possible GA's for a class
				
				//Here i changed index to indexOfGA
				boolean didAssignAGA = false;
				for(int indexOfGA = 0; indexOfGA < classes.get(index).getNumberOfAvailableGA(); indexOfGA++){
					didAssignAGA = false;
					//if(classes.get(index).getAvailableGA().get(indexOfGA) == null){}
					if(checkStillAvailable(classes.get(index), classes.get(index).getAvailableGA().get(indexOfGA))){
						
						//Declare some variables
						//A String for the name of the GA
						//an int for the index of the GA in the ArrayList 'students'
						
						String nameOfCurrentGA = classes.get(index).getAvailableGA().get(indexOfGA).getName();
						//System.out.println("Name of current GA is " + nameOfCurrentGA);
						//System.out.println("students is " + students);
						
						//int indexOfGAOcurrence = students.indexOf(nameOfCurrentGA);
						int indexOfGAOcurrence = -1;
						
						//loop to get the index ocurrence of the GA
						for(int i = 0; i < students.size(); i++){
							if(students.get(i).getName() == nameOfCurrentGA){
								indexOfGAOcurrence = i;
								break;
							}
						}
						
						
						//check if the GA doesn't go over 20 hours
						//1.get the name of the GA
						//2.Find the instance of the GA by name in the ArrayList of all GA
						//3.Assigm/check the hourse there
						//4.Assign the class to the instance of the GA there
						//(20 - students.get(indexOfGAOcurrence).getHoursAssigned()) <= classes.get(index).getTotalTime()
						if(20 >= students.get(indexOfGAOcurrence).getHoursAssigned() + classes.get(index).getTotalTime()){
							//add class to GA and GA to class
							classes.get(index).setAssignedGA(students.get(indexOfGAOcurrence));
							students.get(indexOfGAOcurrence).addClassToCurrentList(classes.get(index));
							didAssignAGA = true;
							//break;
							
						}//end if for time check 
						
					}//end if for check available
					
				}//end for loop for each GA in a certain class\
				
				//Here is where we start back tracking
				//1. DeAssign class to GA
				//2. DeAssign GA to class
				//3. Mark the specific GA for the class as visited
				//4. Decrement index of class's
				
				if(didAssignAGA == false){
				String nameOfGA = classes.get(index).deAssignGAFromClass().getName();
				index -= 1;
				for(int indexOfLoop = 0; indexOfLoop < students.size(); indexOfLoop++){
					if(students.get(indexOfLoop).getName() == nameOfGA){
						
						//dealocate the class from the TA
						students.get(indexOfLoop).addDeallocatedClass(classes.get(index));
						
						//Set students Calendar to free(free it up from the class that was dealocated)
						students.get(indexOfLoop).isAvailable(classes.get(index).getDaysOfWeek(), classes.get(index).getStartTime(), classes.get(index).getEndTime());               
						
					}//end if
				}//end loop for dealocation
				}
			}//end else for if number == 0
			
		}//end for loop for classes
		for(int j = 0; j < classes.size(); j++){
			for(int y = 0; y < classes.get(j).getAssignedGA().size(); y++){
				System.out.println("Class: " + classes.get(j).getClassNumber() + ".  Is taught by: " + classes.get(j).getAssignedGA().get(y).getName());
			}
		}
		for(int k = 0; k < students.size(); k++){
			System.out.println("student: " + students.get(k).getName() + ".  Is assisting: " );
			Iterator<Class> itr = students.get(k).returnIteratorOfAllClassForGA();
			while(itr.hasNext()){
				System.out.println(itr.next().getClassNumber());
			}
		}
		System.out.println();
	}//end method createInitialSolution (The one Sam created)
	
	/*
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
		//check if it has been dealocated
		if(!weeklyClass.isGADealocated(ga.getName())){
			
			//Here we need to check if the time is still available for the GA.  
			//I do not understand how the Calednar class works and need Matt
			//to explain that to me.
			
			return true;
		}
		return false;
	}//End method checkStillAvailable
	
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
	
}//end class Algorithm
