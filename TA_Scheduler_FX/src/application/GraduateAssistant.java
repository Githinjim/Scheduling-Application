package application;

import java.util.ArrayList;
import java.util.Iterator;

public class GraduateAssistant {

	private String name;
	private String phoneNumber;
	private ArrayList<String> classDeallocationList;
	//private ArrayList<GraduateAvailableTimes> availableTimes;
	private ArrayList<String> listOfClassAssisting;
	private ArrayList<String> listOfClassGACouldAssist;
	
	private Calendar availability;
	
	/**
	 * Default constructor
	 */
	public GraduateAssistant()
	{
		name = null;
		phoneNumber = null;
		classDeallocationList = new ArrayList<String>();
		
		// Create ArrayList with five days of the week.
		//availableTimes = new ArrayList<GraduateAvailableTimes>(5);
		
		availability = new Calendar();
		
		listOfClassAssisting = new ArrayList<String>();
		listOfClassGACouldAssist = new ArrayList<String>();
	}
	
	/**
	 * Constructor that allows for the initialization of the name and phone number of the GA
	 * @param name
	 * @param phone
	 */
	public GraduateAssistant(String name, String phone)
	{
		this.name = name;
		phoneNumber = phone;
		classDeallocationList = new ArrayList<String>();

		// Create ArrayList with five days of the week.
		//availableTimes = new ArrayList<GraduateAvailableTimes>(5);
		
		availability = new Calendar();
		
		listOfClassAssisting = new ArrayList<String>();
		listOfClassGACouldAssist = new ArrayList<String>();
	}
	
	/**
	 * Method to add to the list of all possible class a GA could assist with
	 * 
	 * @param classNumber - the number of the class to add
	 */
	public void addPossibleGAClass(String classNumber){
		
		listOfClassGACouldAssist.add(classNumber);
	}//end method addPossibleGAClass
	
		
	/**
	 * This method is to add class number to the arrayList to keep track
	 * of all the classes the specific GA is assisting.
	 * 
	 * @param classNumber - the class number that the GA is responsible for
	 */
	public void addClassToCurrentList(String classNumber){
		
		listOfClassAssisting.add(classNumber);
	}//end method addClassToCurrentList
	
	/**
	 * This method will add to the list of class's that this grad
	 * student has been deallocated from.
	 * 
	 * @param dealocatedClass - class number that the student was dealocated.
	 */
	public void addDeallocatedClass(String dealocatedClass){
		
		classDeallocationList.add(dealocatedClass);
		
	}//end of method addDealocatedClass
	
	/**
	 * This method will return the name of the grad student
	 * 
	 * @return
	 */
	public String getName(){
		
		return name;
		
	}//end method getName
	
	/**
	 * This method will return the phoneNumber of the gradStudent
	 * 
	 * @return
	 */
	public String getPhoneNumber(){
		
		return phoneNumber;
		
	}//end method phoneNumber
	
	/**
	 * This method will remove a class from the current ArrayList that
	 * is keeping track of a specific GA
	 * 
	 * @param classNumberToRemove - the number of the class that is to be removed
	 */
	public void removeClassFromCurrentList(String classNumberToRemove){
		
		listOfClassAssisting.remove(classNumberToRemove);
	}//end method removeClassFromCurrentList
	
	public Iterator<String> returnIteratorOfAllClassForGA(){
		
		return listOfClassAssisting.iterator();
	}//end method returnIteratorOfAllClassForGA
	
	/**
	 * This method will set the name of the grad student
	 * 
	 * @param newName - name of the gradstudent
	 */
	public void setName(String newName){
		
		name = newName;
		
	}//end method setName
	
	/**
	 * This method will set the phoneNumber of the grad student
	 * 
	 * @param newNumber - phone number to set for the grad student
	 */
	public void setPhoneNumber(String newNumber){
		
		phoneNumber = newNumber;
		
	}//end method setPhoneNumber
	
	/**
	 * This method will an Iterator instance of the classDealocationList
	 * 
	 * @return - the Iterator instance of all the deallocated classes.
	 */
	public Iterator<String> iteratorOfDealocatedClasses(){
		
		return classDeallocationList.iterator();
		
	}//end IteratorOfDealocatedClasses
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param time
	 */
	public void setNotAvailableAt(int dayOfWeek, String time){
		availability.setBusy(dayOfWeek, time);
		//availableTimes.get(dayOfTheWeek).setNotAvailable(time);
		//availableTimes.setNotAvailable(time);
		
	}//end setNotAvailableAt
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param startTime - The starting time the GA is available.
	 * @param endTime - The ending time the GA is available. 
	 */
	public void setNotAvailableAt(int dayOfWeek, String startTime, String endTime){
		availability.setBusy(dayOfWeek, startTime, endTime);		
	}//end setNotAvailableAt
	
	/**
	 * 
	 * @param dayOfWeek - The day of the week
	 * @param time - Time the GA is available
	 */
	public void setAvailableAt(int dayOfWeek, String time){
		availability.setFree(dayOfWeek, time);
	}//end method setAvailableAt
	
	/**
	 * 
	 * @param dayOfWeek - The day of the week
	 * @param startTime - The starting time the GA is available.
	 * @param endTime - The ending time the GA is available. 
	 */
	public void setAvailableAt(int dayOfWeek, String startTime, String endTime){
		
		//availableTimes.get(dayOfTheWeek).setAvailable(time);
		availability.setFree(dayOfWeek, startTime, endTime);
		
	}//end method setAvailableAt
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param time
	 * @return
	 */
	public boolean isAvailable(int dayOfWeek, String time){
		//return availableTimes.get(dayOfTheWeek).getAvailableAt(time);
		return availability.getAvailable(dayOfWeek, time);
	}//end method isAvailable
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isAvailable(int dayOfWeek, String startTime, String endTime)
	{
		return availability.getAvailable(dayOfWeek, startTime, endTime);
	}
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isAvailable(ArrayList<Integer> daysOfWeek, String startTime, String endTime)
	{
		boolean returnVal = true;
		for (Integer day : daysOfWeek)
		{
			if (!availability.getAvailable(day, startTime, endTime))
			{
				returnVal = false;
				break;
			}
		}
		return returnVal;
	}
	
	/**
	 * This method will return an iterator of the ArrayList of all the possible
	 * class a certain GA could assist with.
	 * 
	 * @return an Iterator<String> of all the possible class this specific GA could assist with
	 */
	public Iterator<String> returnIteratorOfAllPossibleClasss(){
		
		return listOfClassGACouldAssist.iterator();
		
	}//end method returnIteratorOfAllPossibleClasss
	
}//end class GraduateAssistant
