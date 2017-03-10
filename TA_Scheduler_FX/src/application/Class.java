package application;

import java.util.ArrayList;

/**
 * 
 * @author sam juozapaitis 
 * 
 * The purpose of this class is to act as a data structure for the class(s)
 * that require a graduate assistant.
 *
 */

public class Class {

	private String classNumber; 	//the class number
	private String professor;		//the professors name that is teaching the class
	//private ArrayList<String> qualifications = new ArrayList<String>();
	private String startTime;
	private String endTime;
	private ArrayList<Integer> daysOfWeek;
	private ArrayList<GraduateAssistant> assignedGA;
	private ArrayList<GraduateAssistant> availableGAs;
	private int numberOfGAs;
	private int prepHours;
	private int uniqueIdentifier = -1;
	private boolean isClone = false;
	private int workHours = 0;

	/**
	 * Default Constructor
	 * 
	 */
	public Class()
	{
		classNumber = null;
		professor = null;
		startTime = "";
		endTime = "";
		daysOfWeek = new ArrayList<Integer>();
		assignedGA = new ArrayList<GraduateAssistant>();
		availableGAs = new ArrayList<GraduateAssistant>();
		numberOfGAs = 1;
		prepHours = 0;
		//Default constructor;
	}
	
	/**
	 * Constructor for creating a class with a number of GAs
	 * @param numberOfGAs	The number of graduates needed for the class
	 */
	public Class(int numberOfGAs){
		this.numberOfGAs = numberOfGAs;
		prepHours = 0;
	}
	
	/**
	 * used to set the unique identifier of the class
	 * 
	 * @param id	The unique identifier
	 */
	public void setUniqueIdentifier(int id){
		uniqueIdentifier = id;
	}//end method setUniqueIdentifier
	
	/**
	 * used to receive the unique identifier 
	 * 
	 * @return	the unique identifier
	 */
	public int getUniqueIdentifier(){
		return uniqueIdentifier;
	}
	
	/**
	 * Used to set the number of preperation hours for the given class
	 * 
	 * @param hours	The hours of prep for the class
	 */
	public void setPrepHours(int hours){
		prepHours = hours;
	}
	
	/**
	 * Method for adding a day of the week that the class occurs
	 * @param day The day of the week to add, 0 being monday and 4 being Friday
	 */
	public void addDayOfWeek(int day)
	{
		// Minor error checking
		if (day >= 0 && day <= 4)
		{
			daysOfWeek.add(day);
		}
	}
	
	/**
	 * Method for adding a day of the week that the class occurs
	 * @param day	The day of the week to add, monday - friday
	 */
	public void addDayOfWeek(String day)
	{
		day.toUpperCase();
		
		switch (day)
		{
		
		case "MONDAY":
			daysOfWeek.add(0);
		case "TUESDAY:":
			daysOfWeek.add(1);
		case "WEDNESDAY:":
			daysOfWeek.add(2);
		case "THURSDAY:":
			daysOfWeek.add(3);
		case "FRIDAY:":
			daysOfWeek.add(4);
		}
	}
	
	/**
	 * Used to add a GA to the list of possible assistant available
	 * 
	 * @param newGA - The graduate assistant to add
	 */
	public void addAvailableGA(GraduateAssistant newGA){
		
		availableGAs.add(newGA);
		
	}//end method addAvailableGA
	
	/**
	 * Sets the start time of the class
	 * 
	 * @param newTime	The start time of the class in string form, 6am - 8pm
	 */
	public void setStartTime(String newTime)
	{
		
		startTime = newTime;
	}//end method setStartTime
	
	/**
	 * Sets the end time of the class
	 * 
	 * @param newTime	The end time of the class, 6am - 8pm
	 */
	public void setEndTime(String newTime)
	{
		endTime = newTime;
	}//end method setEndTime
	
	/**
	 * Sets the days of the week the class occurs
	 * 
	 * @param newDays	the list of days the class occurs, 0 being monday and 4 being friday
	 */
	public void setDaysOfWeek(ArrayList<Integer> newDays)
	{
		daysOfWeek = newDays;
	}
	
	/**
	 * set a graduate assistant to assist with the class
	 * 
	 * @param ga	The graduate to assist with the class
	 * @return		false for is the assistant was not still available and true for if it was assign
	 */
	public boolean setAssignedGA(GraduateAssistant ga)
	{
		if(assignedGA.size() >= numberOfGAs)
		{
			return false;
		}
		else
		{
			assignedGA.add(ga);
			return true;
		}
	}
	
	/**
	 * This method will add the class number
	 * 
	 * @param newClassNumber - the classNumber
	 */
	public void setClassNumber(String newClassNumber){
		
		classNumber = newClassNumber;
		
	}//end method for setClassNumber
	
	/**
	 * This method will set the professor who is teaching the class
	 * 
	 * @param newProfessor - the professors name
	 */
	public void setProfessor(String newProfessor){
		
		professor = newProfessor;
		
	}//end method for setProffesor
	
	/**
	 * This method is used to set the qualifications of the class
	 * 
	 * @param newQualifications - the newQualifications
	 */
	public void setQualifications(ArrayList<String> newQualifications){
		
//		qualifications = newQualifications;
		
	}//end method for setQualifications
	
	/**
	 * This method is used to retrieve the classNumber
	 * 
	 * @return the class number (classNumber)
	 */
	public String getClassNumber()
	{
		
		return classNumber;
		
	}//end method getClassNumber
	
	/**
	 * This method is used to retrieve the name of the professors
	 * 
	 * @return - the name of the professors (professors)
	 */
	public String getProfessor(){
		
		return professor;
		
	}//end method getProffesor 
	
	/**
	 * This method is used to retrieve the qualifications for the class
	 * 
	 * @return - the qualifications of the class (qualifications)
	 */
//	public ArrayList<String> getQualifications(){
//		
//		return qualifications;
//		
//	}//end method getQualifications
	
	/**
	 * This method will return the size/number of entries for the
	 * number of available Graduate Assistants for the class.
	 * 
	 * @return - and int that is the number of the available Graduate Assistants for the class.
	 */
	public int getNumberOfAvailableGA(){
		
		return availableGAs.size();
		
	}//end method getNumberOfAvailableGA
	
	/**
	 * Used to get the start time of the class
	 * 
	 * @return the start time, 6am - 8pm
	 */
	public String getStartTime()
	{
		return startTime;
	}
	
	/**
	 * User to get the end time of the class
	 * 
	 * @return the end time, 6am - 8pm
	 */
	public String getEndTime()
	{
		return endTime;
	}
	
	/**
	 * used to get the number of preparation hours
	 * 
	 * @return an int value for how many prep hours are needed
	 */
	public int getPrepHours()
	{
		return prepHours;
	}

	/**
	 * used to get the total amount of hours the class is being held.
	 * This is based off of the startTime and endTime
	 * 
	 * @return	the number of hours the class is held.
	 */
	public int getWorkTime(){
		
		if (isClone == false)
		{
		ArrayList<String> timeList = new ArrayList<String>();
		timeList.add("6am");
		timeList.add("7am");
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
		timeList.add("9pm");
		
		int startIndex = timeList.indexOf(this.getStartTime().toLowerCase());
		int endIndex = timeList.indexOf(this.getEndTime().toLowerCase());
		workHours = ((endIndex - startIndex) * this.daysOfWeek.size()) + prepHours;
		}
		return workHours;
		
	} //end method getTotalTime
	
	/**
	 * Used to get the days of the week that the class is offered.
	 * 
	 * @return	The list of the days that the class is offered.
	 */
	public ArrayList<Integer> getDaysOfWeek()
	{
		return daysOfWeek;
	}
	
	/**
	 * used to get a list of all the available assistant that are available to teach this class,
	 * based off of the initial times available
	 * 
	 * @return	The list of the assistant available.
	 */
	public ArrayList<GraduateAssistant> getAvailableGA()
	{
		return availableGAs;
	}
	
	/**
	 * Used to get all the assistant that have been assigned.
	 * 
	 * @return	The list of the assistant that have been assigned this class.
	 */
	public ArrayList<GraduateAssistant> getAssignedGA()
	{
		return assignedGA;
	}
	
	/**
	 * Used to remove all assistants that were previously assigned
	 * 
	 */
	public void removeAssignedGA()
	{
		assignedGA.clear();
	}
	
	/**
	 * Used to remove a single assistant that has been assigned this class
	 * 
	 * @param ga	The assistant to move.
	 */
	public void removeAssignedGA(GraduateAssistant ga)
	{
		assignedGA.remove(ga);
	}
	
	/**
	 * Used to remove an assistant that was marked as available to the class
	 * 
	 * @param ga	The assistant to remove
	 */
	public void removeAvailableGA(GraduateAssistant ga)
	{
		availableGAs.remove(ga);
	}
	
	/**
	 * Used to reset the data structure instance to no assigned assistant and no 
	 * available assistants
	 * 
	 */
	public void resetClass()
	{
		assignedGA.clear();
		availableGAs.clear();
	}
	
	/**
	 * Used to return the class number
	 * 
	 * @return the class number
	 * 
	 */
	@Override
	public String toString()
	{
		return classNumber;
	}
	
	@Override
	/**
	 * Creates a clone of this instance of a class
	 * 
	 * @return the clone of the class
	 * 
	 */
	public Class clone()
	{
		Class classClone = new Class();
		
		classClone.classNumber = this.classNumber;
		classClone.professor = this.professor;
		classClone.startTime = this.startTime;
		classClone.endTime = this.endTime;
		classClone.daysOfWeek = this.daysOfWeek;

		
		for (GraduateAssistant assigned : assignedGA)
		{
			classClone.assignedGA.add(assigned);
		}
		
		classClone.numberOfGAs = this.numberOfGAs;
		classClone.prepHours = this.prepHours;
		classClone.uniqueIdentifier = this.uniqueIdentifier;
		classClone.isClone = true;
		return classClone;
	}
	
}//end Class
