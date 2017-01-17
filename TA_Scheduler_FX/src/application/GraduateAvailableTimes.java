package application;

public class GraduateAvailableTimes {

	//boolean variable for if the GA is available at a given time
	private boolean isAvailableAtEightAM = true;
	private boolean isAvailableAtNineAM = true;
	private boolean isAvailableAtTenAM = true;
	private boolean isAvailableAtElevenAM = true;
	private boolean isAvailableAtTwelvePM = true;
	private boolean isAvailableAtOnePM = true;
	private boolean isAvailableAtTwoPM = true;
	private boolean isAvailableAtThreePM = true;
	private boolean isAvailableAtFourPM = true;
	private boolean isAvailableAtFivePM = true;
	private boolean isAvailableAtSixPM = true;
	private boolean isAvailableAtSevenPM = true;
	private boolean isAvailableAtEightPM = true;
	
	/**
	 * Method to determine if a given Graduate Assistant is available at a given time.
	 * 
	 * @param timeToTest - the time to test is a GA is available.  Should be in the form of
	 * 			8am, 9am, 10am, ..., 7pm, 8pm
	 * @return true for is they are available and false for not available.
	 */
	public boolean getAvailableAt(String timeToTest){
		switch (timeToTest){
		
		case "8am": return isAvailableAtEightAM;
		case "9am": return isAvailableAtNineAM;
		case "10am": return isAvailableAtTenAM;
		case "11am": return isAvailableAtElevenAM;
		case "12pm": return isAvailableAtTwelvePM;
		case "1pm": return isAvailableAtOnePM;
		case "2pm": return isAvailableAtTwoPM;
		case "3pm": return isAvailableAtThreePM;
		case "4pm": return isAvailableAtFourPM;
		case "5pm": return isAvailableAtFivePM;
		case "6pm": return isAvailableAtSixPM;
		case "7pm": return isAvailableAtSevenPM;
		case "8pm": return isAvailableAtEightPM;
		default:	return false;
		
		}//end switch
	}//end method getAvailableAt
	
	/**
	 * Method to set a given time to available
	 * 
	 * @param timeToSet - the time to set to available.  Given in the format
	 * 						8am, 9am, 10am, ..., 7pm, 8pm
	 */
	public void setAvailable(String timeToSet){
		
		switch(timeToSet){
		
			case "8am": isAvailableAtEightAM = true;
			case "9am": isAvailableAtNineAM = true;
			case "10am": isAvailableAtTenAM = true;
			case "11am": isAvailableAtElevenAM = true;
			case "12pm": isAvailableAtTwelvePM = true;
			case "1pm": isAvailableAtOnePM = true;
			case "2pm": isAvailableAtTwoPM = true;
			case "3pm": isAvailableAtThreePM = true;
			case "4pm": isAvailableAtFourPM = true;
			case "5pm": isAvailableAtFivePM = true;
			case "6pm": isAvailableAtSixPM = true;
			case "7pm": isAvailableAtSevenPM = true;
			case "8pm": isAvailableAtEightPM = true;
		
		}//end switch
		
	}//end of method setAvailable
	
	/**
	 * Method to set a given time to not available
	 * 
	 * @param timeToSetNotAvailable - the time to set not available.  Given in the format
	 * 									7am, 8am, ..., 7pm, 8pm
	 */
	public void setNotAvailable(String timeToSetNotAvailable){
		
		switch(timeToSetNotAvailable){
		
			case "8am": isAvailableAtEightAM = false;
			case "9am": isAvailableAtNineAM = false;
			case "10am": isAvailableAtTenAM = false;
			case "11am": isAvailableAtElevenAM = false;
			case "12pm": isAvailableAtTwelvePM = false;
			case "1pm": isAvailableAtOnePM = false;
			case "2pm": isAvailableAtTwoPM = false;
			case "3pm": isAvailableAtThreePM = false;
			case "4pm": isAvailableAtFourPM = false;
			case "5pm": isAvailableAtFivePM = false;
			case "6pm": isAvailableAtSixPM = false;
			case "7pm": isAvailableAtSevenPM = false;
			case "8pm": isAvailableAtEightPM = false;
		
		}//end switch
		
	}//end method setNotAvailable
	
}//end class GraduateAvailableTimes
