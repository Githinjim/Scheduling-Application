package application;

import java.util.ArrayList;
import java.util.Iterator;

public class GraduateAssistant {

	private String name;
	private String phoneNumber;
	private ArrayList<Class> listOfClassAssisting;
	private ArrayList<Class> listOfClassGACouldAssist;
	private Calendar availability;
	private int hoursAssigned = 0;
	private ArrayList<String> qualifiedClasses = new ArrayList<String>();
	
	/**
	 * Default constructor
	 */
	public GraduateAssistant()
	{
		name = null;
		phoneNumber = null;
		
		availability = new Calendar();
		
		listOfClassAssisting = new ArrayList<Class>();
		listOfClassGACouldAssist = new ArrayList<Class>();
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
		
		availability = new Calendar();
		
		listOfClassAssisting = new ArrayList<Class>();
		listOfClassGACouldAssist = new ArrayList<Class>();
	}
	
	/**
	 * 
	 * @param newQualification
	 */
	public void addQualification(String newQualification){
		qualifiedClasses.add(newQualification);
	}
	
	/**
	 * 
	 * @param hoursToAdd
	 */
	public void addToHours(int hoursToAdd){
		hoursAssigned += hoursToAdd;
	}
	
	/**
	 * Method to add to the list of all possible class a GA could assist with
	 * 
	 * @param classNumber - the number of the class to add
	 */
	public void addPossibleGAClass(Class classNumber){
		
		listOfClassGACouldAssist.add(classNumber);
	}//end method addPossibleGAClass
	
		
	/**
	 * This method is to add class number to the arrayList to keep track
	 * of all the classes the specific GA is assisting.
	 * 
	 * @param classNumber - the class number that the GA is responsible for
	 */
	public void addAssistingClass(Class weeklyClass){
		
		listOfClassAssisting.add(weeklyClass);
		availability.setBusy(weeklyClass.getDaysOfWeek(), weeklyClass.getStartTime(), weeklyClass.getEndTime());
		addToHours(weeklyClass.getWorkTime());
	}//end method addClassToCurrentList
	
	/**
	 * 
	 * @return
	 */
	public int getHoursAssigned(){
		
		return hoursAssigned;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getQualifications(){
		return qualifiedClasses;
	}
		
	
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
	 * 
	 * @return
	 */
	public ArrayList<Class> getAssignedClasses()
	{
		return listOfClassAssisting;
	}
	
	public ArrayList<Class> getPotentialClasses()
	{
		return listOfClassGACouldAssist;
	}
	
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
	 * 
	 * @param qualification
	 * @return
	 */
	public boolean isQualified(String qualification){
		qualification = qualification.split("[.]")[0];
		for(int i = 0; i < qualifiedClasses.size(); i++){
			if(qualifiedClasses.get(i).equals(qualification)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param qualificationList
	 * @return
	 */
	public boolean isQualified(ArrayList<String> qualificationList){
		boolean isQualified = false;
		if(qualificationList.size() == 0){
			return true;
		}
		for(int i = 0; i < qualificationList.size(); i++){
			isQualified = false;
			for(int j = 0; j < qualifiedClasses.size(); j++){
				if(qualificationList.get(i).equals(qualifiedClasses.get(j))){
					isQualified = true;
				}//end if
				
			}//end inner loop
			
		}//end outer loop
		return isQualified;
		
	}//end method checkIsQualified
	
	/**
	 * This method will remove a class from the current ArrayList that
	 * is keeping track of a specific GA
	 * 
	 * @param classNumberToRemove - the number of the class that is to be removed
	 */
	public void removeClassFromCurrentList(String classNumberToRemove){
		
		listOfClassAssisting.remove(classNumberToRemove);
	}//end method removeClassFromCurrentList
	
//	public Iterator<Class> returnIteratorOfAllClassForGA(){
//		
//		return listOfClassAssisting.iterator();
//	}//end method returnIteratorOfAllClassForGA
	
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
	
}//end class GraduateAssistant
