package application;

import java.util.ArrayList;
import java.util.Iterator;


public class GraduateAssistant {

	private String name = null;
	private String phoneNumber = null;
	private ArrayList<String> classDealocationList = new ArrayList<String>();
	private ArrayList<GraduateAvailableTimes> availableTimes = new ArrayList<GraduateAvailableTimes>(5);
	private ArrayList<String> listOfClassAssisting = new ArrayList<String>();
	private ArrayList<String> listOfClassGACouldAssist = new ArrayList<String>();
	
	public GraduateAssistant(String name, String phone)
	{
		this.name = name;
		phoneNumber = phone;
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
	 * This method will return an iterator of the ArrayList of all the possible
	 * class a certain GA could assist with.
	 * 
	 * @return an Iterator<String> of all the possible class this specific GA could assist with
	 */
	public Iterator<String> returnIteratorOfAllPossibleClasss(){
		
		return listOfClassGACouldAssist.iterator();
		
	}//end method returnIteratorOfAllPossibleClasss
	
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
	 * This method will add to the list of class's that this grad
	 * student has been dealocated from.
	 * 
	 * @param dealocatedClass - class number that the student was dealocated.
	 */
	public void addDealocatedClass(String dealocatedClass){
		
		classDealocationList.add(dealocatedClass);
		
	}//end of method addDealocatedClass
	
	/**
	 * This method will an Iterator instance of the classDealocationList
	 * 
	 * @return - the Iterator instance of all the dealocated classes.
	 */
	public Iterator<String> iteratorOfDealocatedClasses(){
		
		return classDealocationList.iterator();
		
	}//end IteratorOfDealocatedClasses
	
	public void setNotAvailableAt(int dayOfTheWeek, String time){
		availableTimes.get(dayOfTheWeek).setNotAvailable(time);
		//availableTimes.setNotAvailable(time);
		
	}//end setNotAvailableAt
	
	public void setAvailableAt(int dayOfTheWeek, String time){
		
		availableTimes.get(dayOfTheWeek).setAvailable(time);
		
	}//end method setAvailableAt
	
	public boolean isAvailable(int dayOfTheWeek, String time){
		
		return availableTimes.get(dayOfTheWeek).getAvailableAt(time);
	}//end method isAvailable
	
}//end class GraduateAssistant
