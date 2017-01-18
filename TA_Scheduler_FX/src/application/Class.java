package application;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author sam juozapaitis 
 * 
 * The purpose of this class is to act as a data structure for the class(s)
 * that require a graduate assistant.
 *
 */

public class Class {

	private String classNumber = null; 	//the class number
	private String professor = null;		//the professors name that is teaching the class
	private String qualifications = null;	//qualifications of the class
	
	private String startTime = "";
	private String endTime = "";
	private ArrayList<Integer> daysOfWeek = new ArrayList<Integer>();
		
	private ArrayList<String> listOfAvailableGraduateAssistants = new ArrayList<String>();	//ArrayList of all the available Graduate Assistants
	
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
	public void addAvailableGA(String newGA){
		
		listOfAvailableGraduateAssistants.add(newGA);
		
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
	public void setQualifications(String newQualifications){
		
		qualifications = newQualifications;
		
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
	public String getQualifications(){
		
		return qualifications;
		
	}//end method getQualifications
	
	/**
	 * This method will return an instance of an Iterator that contains all the 
	 * the names of the available Graduate Assistants available for this class.
	 * 
	 * @return - an Iterator of the ArrayList listOfAvailableGraduateAssistants
	 */
	public Iterator getIterationOfAvailableGA(){
		
		return listOfAvailableGraduateAssistants.iterator();
		
	}//end getIterationOfAvailableGA
	
	/**
	 * This method will return the size/number of entries for the
	 * number of available Graduate Assistants for the class.
	 * 
	 * @return - and int that is the number of the available Graduate Assistants for the class.
	 */
	public int getNumberOfAvailableGA(){
		
		return listOfAvailableGraduateAssistants.size();
		
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

	public ArrayList<Integer> getDaysOfWeek()
	{
		return daysOfWeek;
	}

}//end Class
