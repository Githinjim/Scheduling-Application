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
	private String proffesor = null;		//the proffesors name that is teaching the class
	private String qualifications = null;	//qualifications of the class
	private String startTime = "";
	private String endTime = "";
	
	private ArrayList<String> listOfAvailableGraduateAssistants = new ArrayList<String>();	//ArrayList of all the available Graduate Assistants
	
	public void setStartTime(String newTime){
		
		startTime = newTime;
	}//end method setStartTime
	
	public void setEndTime(String newTime){
		
		endTime = newTime;
	}//end method setEndTime
	
	public String getStartTime(){
		return startTime;
	}
	
	public String getEndTime(){
		return endTime;
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
	 * This method will add the class number
	 * @param newClassNumber - the classNumber
	 */
	public void setClassNumber(String newClassNumber){
		
		classNumber = newClassNumber;
		
	}//end method for setClassNumber
	
	/**
	 * This method will set the proffesor who is teaching the class
	 * 
	 * @param newProffesor - the proffesors name
	 */
	public void setProffesor(String newProffesor){
		
		proffesor = newProffesor;
		
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
	public String getClassNumber(){
		
		return classNumber;
		
	}//end method getClassNumber
	
	/**
	 * This method is used to retrieve the name of the proffesor
	 * 
	 * @return - the name of the proffesor (proffesor)
	 */
	public String getProffesor(){
		
		return proffesor;
		
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

}//end Class
