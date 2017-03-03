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
	 * @param numberOfGAs
	 */
	public Class(int numberOfGAs){
		this.numberOfGAs = numberOfGAs;
		prepHours = 0;
	}
	
	public void setUniqueIdentifier(int id){
		uniqueIdentifier = id;
	}//end method setUniqueIdentifier
	
	public int getUniqueIdentifier(){
		return uniqueIdentifier;
	}
	
	public void setPrepHours(int hours){
		prepHours = hours;
	}
	
	/**
	 * Method for adding a day of the week that the class occurs
	 * @param day
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
	 * @param day
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
	 * This method will add the a GA (name of the GA to the ArrayList)
	 * 
	 * @param newGA - the name of the GA that is to be added
	 */
	public void addAvailableGA(GraduateAssistant newGA){
		
		availableGAs.add(newGA);
		
	}//end method addAvailableGA
	
	/**
	 * 
	 * @param newTime
	 */
	public void setStartTime(String newTime)
	{
		
		startTime = newTime;
	}//end method setStartTime
	
	/**
	 * 
	 * @param newTime
	 */
	public void setEndTime(String newTime)
	{
		endTime = newTime;
	}//end method setEndTime
	
	/**
	 * 
	 * @param newDays
	 */
	public void setDaysOfWeek(ArrayList<Integer> newDays)
	{
		daysOfWeek = newDays;
	}
	
	/**
	 * 
	 * @param ga
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
	 * @param newQualifications - the newQualification
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
	 * 
	 * @return
	 */
	public String getStartTime()
	{
		return startTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEndTime()
	{
		return endTime;
	}
	
	public int getPrepHours()
	{
		return prepHours;
	}

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
	
	public ArrayList<Integer> getDaysOfWeek()
	{
		return daysOfWeek;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<GraduateAssistant> getAvailableGA()
	{
		return availableGAs;
	}
	
	public ArrayList<GraduateAssistant> getAssignedGA()
	{
		return assignedGA;
	}
	
	/**
	 * 
	 * @param ga
	 */
	public void removeAssignedGA()
	{
		assignedGA.clear();
	}
	
	/**
	 * 
	 * @param ga
	 */
	public void removeAssignedGA(GraduateAssistant ga)
	{
		assignedGA.remove(ga);
	}
	
	public void removeAvailableGA(GraduateAssistant ga)
	{
		availableGAs.remove(ga);
	}
	
	public void resetClass()
	{
		assignedGA.clear();
		availableGAs.clear();
	}
	
	@Override
	public String toString()
	{
		return classNumber;
	}
	
	@Override
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
