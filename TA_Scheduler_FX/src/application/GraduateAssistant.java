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
	 * @param name	name of the assistant
	 * @param phone	phone number of the assistant
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
	 * Used to add a qualification
	 * 
	 * @param newQualification	The new qualification
	 */
	public void addQualification(String newQualification){
		qualifiedClasses.add(newQualification);
	}
	
	/**
	 * Used to add to the total number of hours the assistant is currently assisting with.
	 * 
	 * @param hoursToAdd	The number of hours to add.
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
	 * User to get the hours currently assisting with
	 * 
	 * @return	The number of hours
	 */
	public int getHoursAssigned(){
		
		return hoursAssigned;
	}
	
	/**
	 * Used to get all the qualifications that the assistant has.
	 * 
	 * @return	A list of all the qualifications.
	 */
	public ArrayList<String> getQualifications(){
		return qualifiedClasses;
	}
		
	
	/**
	 * This method will return the name of the grad student
	 * 
	 * @return	name
	 */
	public String getName(){
		
		return name;
		
	}//end method getName
	
	/**
	 * This method will return the phoneNumber of the gradStudent
	 * 
	 * @return	The phone number
	 */
	public String getPhoneNumber(){
		
		return phoneNumber;
		
	}//end method phoneNumber
	
	/**
	 * Used to get a list of all the assigned classes.
	 * 
	 * @return	All the assigned classes
	 */
	public ArrayList<Class> getAssignedClasses()
	{
		return listOfClassAssisting;
	}
	
	/**
	 * Used to get all the classes that this assistant could assist based on its available
	 * times and qualifications from the initial set up.
	 * 
	 * @return	The potential classes the assistant could assist with.
	 */
	public ArrayList<Class> getPotentialClasses()
	{
		return listOfClassGACouldAssist;
	}
	
	/**
	 * Used to check if the assistant is available at a given time of a given day
	 * 
	 * @param dayOfWeek	Day of the week, 0 being Monday and 4 being Friday
	 * @param time		The time of the day to set free, 6am - 8pm
	 * @return
	 */
	public boolean isAvailable(int dayOfWeek, String time){
		//return availableTimes.get(dayOfTheWeek).getAvailableAt(time);
		return availability.getAvailable(dayOfWeek, time);
	}//end method isAvailable
	
	/**
	 * Used to get if the assistant available for a time period of a given day.
	 * 
	 * @param dayOfWeek	The day wanting to check.
	 * @param startTime	The time starting of the period to check if available, 6am - 8pm
	 * @param endTime	The time ending of the period to check if available, 6am - 8pm
	 * @return			True for is available, else false
	 */
	public boolean isAvailable(int dayOfWeek, String startTime, String endTime)
	{
		return availability.getAvailable(dayOfWeek, startTime, endTime);
	}
	
	/**
	 * Used to get if the assistant available for a time period for multiple days.
	 * 
	 * @param dayOfWeek	The days wanting to check.
	 * @param startTime	The time starting of the period to check if available, 6am - 8pm
	 * @param endTime	The time ending of the period to check if aailable, 6am - 8pm
	 * @return			True for if available, else false.
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
	 * Used to check if qualified for a given qualification.
	 * 
	 * @param qualification	The qualification to check
	 * @return				True for is qualified, else false.
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
	 * Used to get if the assistant is qualified for multiple qualifications.
	 * 
	 * @param 	qualificationList	The list of qualifications to check.
	 * @return	true for is qualified, else false.
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
	public void removeAssisting(Class weekly){
		
		listOfClassAssisting.remove(weekly);
		availability.setFree(weekly.getDaysOfWeek(), weekly.getStartTime(), weekly.getEndTime());
		hoursAssigned  -= weekly.getWorkTime();
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
	 * used to set the assistant not available for a given day for a time period
	 * 
	 * @param dayOfWeek	The day of the week, 0 being monday and 4 being Friday
	 * @param startTime Starting time for time period, 6am - 8pm
	 * @param endTime	The ending time for the tme period, 6am - 8pm
	 */
	public void setNotAvailableAt(int dayOfWeek, String startTime, String endTime){
		availability.setBusy(dayOfWeek, startTime, endTime);		
	}//end setNotAvailableAt
	
	/**
	 * Used to set the assistant available for a given day and time
	 * 
	 * @param dayOfWeek	The day of the week
	 * @param time		Time the GA is available
	 */
	public void setAvailableAt(int dayOfWeek, String time){
		availability.setFree(dayOfWeek, time);
	}//end method setAvailableAt
	
	/**
	 * Used to set the assistant available for a given day and a time period.
	 * 
	 * @param dayOfWeek	The day of the week
	 * @param startTime The starting time the GA is available.
	 * @param endTime	The ending time the GA is available. 
	 */
	public void setAvailableAt(int dayOfWeek, String startTime, String endTime){
		
		//availableTimes.get(dayOfTheWeek).setAvailable(time);
		availability.setFree(dayOfWeek, startTime, endTime);
		
	}//end method setAvailableAt
	
	/**
	 * Used to remove a possible class for the corresponding list.
	 * 
	 * @param weeklyClass	The class to remove.
	 */
	public void removePossibleClass(Class weeklyClass)
	{
		listOfClassGACouldAssist.remove(weeklyClass);
	}
	
	/**
	 * Used to reset the assistant, setting all their times to free, all assisting and possible assisting class to empty.
	 * 
	 */
	public void resetGA()
	{
		for (Class weekly : listOfClassAssisting)
		{
			availability.setFree(weekly.getDaysOfWeek(), weekly.getStartTime(), weekly.getEndTime());
		}
		
		listOfClassGACouldAssist.clear();
		listOfClassAssisting.clear();
		hoursAssigned = 0;
	}
	
	@Override
	/**
	 * Used to return the name of the assistant
	 * 
	 * @return	The name of the assistant
	 * 
	 */
	public String toString()
	{
		return name;
	}
	
	@Override
	/**
	 * Used to clone this assistant
	 * 
	 * @return	The cloned assistant
	 */
	public GraduateAssistant clone()
	{
		GraduateAssistant gaClone = new GraduateAssistant();
		gaClone.availability = this.availability;
		gaClone.hoursAssigned = this.hoursAssigned;
		gaClone.name = this.name;
		gaClone.phoneNumber = this.phoneNumber;
		gaClone.qualifiedClasses = (ArrayList<String>) this.qualifiedClasses.clone();
		
		for (Class taing : listOfClassAssisting)
		{
			gaClone.listOfClassAssisting.add(taing);
		}
		
		return gaClone;
	}
}//end class GraduateAssistant
