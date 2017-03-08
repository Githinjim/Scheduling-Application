package application;

import java.util.ArrayList;

public class Calendar {
	private boolean[][] calendar;
	
	/**
	 * Default constructor
	 */
	public Calendar()
	{
		calendar = new boolean[5][15]; // Everything is false by default. Available = True
	}
	
	/**
	 * get if the given time is available or not
	 * 
	 * @param day	an integer for the day value 0 being Monday and 4 being Friday
	 * @param hour	an integer for the hour, 0 being 6am and 14 being 8pm
	 * @return		true for is available and false for not
	 */
	public boolean getAvailable(int day, int hour)
	{
		boolean returnVal = true;
		
		try
		{
			returnVal = calendar[day][hour];
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Out of Bounds");
		}
		
		return returnVal;
	}
	
	/**
	 *  get if the given time is available or not
	 * 
	 * @param day 	an integer for the day value 0 being Monday and 4 being Friday
	 * @param hour	a string value for the hour.
	 * @return		true for is available and false for not
	 */
	public boolean getAvailable(int day, String hour)
	{
		boolean returnVal = true;
		int time = stringToHour(hour);
		try
		{
			returnVal = calendar[day][time];
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
		return returnVal;
	}
	
	/**
	 *  get if the given time is available or not
	 * 
	 * @param day	a string value for the day monday - friday
	 * @param hour	a string for the hour of the day
	 * @return		true for is available and false for is not
	 */
	public boolean getAvailable(String day, String hour)
	{
		boolean returnVal = true;
		
		int time = stringToHour(hour);
		int dayOfWeek = stringToDay(day);
		
		try
		{
			returnVal = calendar[dayOfWeek][time];
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
		return returnVal;
	}
	
	/**
	 *  get if the given time is available or not
	 * 
	 * @param day			integer value for the day, 0 being monday and 4 being friday
	 * @param startHour		a string value for the starting hour (example 6am)
	 * @param endHour		a string value for the ending hour (example 7am)
	 * @return				true for is available and false for not available
	 */
	public boolean getAvailable(int day, String startHour, String endHour)
	{
		boolean returnVal = true;
		int startTime = stringToHour(startHour);
		int endTime = stringToHour(endHour);

		for (int i = startTime; i < endTime; i++)
		{

			if (calendar[day][i] == false) // is busy at that time
			{
				returnVal = false;
				break;
			}

		}
		
		return returnVal;
	}
	
	/**
	 * used to set the desired hour of a day to busy
	 * 
	 * @param day	an integer value for the day, 0 being monday and 4 being friday
	 * @param hour	an integer value for the hour, 0 being 6am and 14 being 8pm
	 */
	public void setBusy(int day, int hour)
	{
		try
		{
			calendar[day][hour] = false;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * used to set the desired hour of a day to busy
	 * 
	 * @param day	an integer value for the day, 0 being monday and 4 being friday
	 * @param hour	a string value for the hour, 6am - 8pm
	 */
	public void setBusy(int day, String hour)
	{
		int time = stringToHour(hour);
		
		try
		{
			calendar[day][time] = false;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * used to set the desired hour of a day to busy
	 * 
	 * @param day	a string value for the day, monday - friday
	 * @param hour	a string value for the hour, 6am - 8pm
	 */
	public void setBusy(String day, String hour)
	{
		int time = stringToHour(hour);
		int dayOfWeek = stringToDay(day);
		try
		{
			calendar[dayOfWeek][time] = false;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * used to set the desired hours of a day to busy
	 * 
	 * @param day		the integer value for the day, 0 being monday and 4 being friday
	 * @param startHour	a string value for the starting hour of being busy. 6am - 8pm
	 * @param endHour	a string value for the ending hour of being busy. 6am - 8pm
	 */
	public void setBusy(int day, String startHour, String endHour)
	{
		int startTime = stringToHour(startHour);
		int endTime = stringToHour(endHour);
		
		for (int i = startTime; i < endTime; i++)
		{
			calendar[day][i] = false;
		}
	}
	
	/**
	 * used to set the desired hours of multiple days to busy
	 * 
	 * @param day		an array of integers for the days to set busy, 0 being monday and 4 being friday
	 * @param startHour	a string value for the starting hours of being busy, 6am - 8pm
	 * @param endHour	a string value for the ending hours of being
	 */
	public void setBusy(ArrayList<Integer> days, String startHour, String endHour)
	{
		int startTime = stringToHour(startHour);
		int endTime = stringToHour(endHour);
		
		for (int day : days)
		{
			for (int i = startTime; i < endTime; i++)
			{
				calendar[day][i] = false;
			}
		}
	}
	
	/**
	 * Used to free up a desired hour of a day
	 * 
	 * @param day	an integer value for the day, 0 for monday and 4 for friday
	 * @param hour	hour of the day, 6am - 8pm
	 */
	public void setFree(int day, int hour)
	{
		try
		{
			calendar[day][hour] = true;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * 
	 * @param day
	 * @param hour
	 */
	public void setFree(int day, String hour)
	{
		int time = stringToHour(hour);
		
		try
		{
			calendar[day][time] = true;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * 
	 * @param day
	 * @param hour
	 */
	public void setFree(String day, String hour)
	{
		int time = stringToHour(hour);
		int dayOfWeek = stringToDay(day);
		try
		{
			calendar[dayOfWeek][time] = true;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
	}
	
	/**
	 * 
	 * @param day
	 * @param startHour
	 * @param endHour
	 */
	public void setFree(int day, String startHour, String endHour)
	{
		int startTime = stringToHour(startHour);
		int endTime = stringToHour(endHour);
		
		for (int i = startTime; i < endTime; i++)
		{
			calendar[day][i] = true;
		}
	}
	
	public void setFree(ArrayList<Integer> days, String startHour, String endHour)
	{
		int startTime = stringToHour(startHour);
		int endTime = stringToHour(endHour);
		
		for (int day : days)
		{
			for (int i = startTime; i < endTime; i++)
			{
				calendar[day][i] = true;
			}
		}
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private int stringToDay (String day)
	{
		int dayOfWeek = -1;
		day = day.toUpperCase();
		
		switch (day)
		{
		case "MONDAY":
			dayOfWeek = 0;
			break;
		case "TUESDAY":
			dayOfWeek = 1;
			break;
		case "WEDNESDAY":
			dayOfWeek = 2;
			break;
		case "THURSDAY":
			dayOfWeek = 3;
			break;
		case "FRIDAY":
			dayOfWeek = 4;
			break;
		}
		
		return dayOfWeek;
	}
	
	private int stringToHour(String hour)
	{
		int time = -1;
		hour = hour.toUpperCase();
		
		switch (hour)
		{
		case "6AM":
			time = 0;
			break;
		case "7AM":
			time = 1;
			break;
		case "8AM":
			time = 2;
			break;
		case "9AM":
			time = 3;
			break;
		case "10AM":
			time = 4;
			break;
		case "11AM":
			time = 5;
			break;
		case "12PM":
			time = 6;
			break;
		case "1PM":
			time = 7;
			break;
		case "2PM":
			time = 8;
			break;
		case "3PM":
			time = 9;
			break;
		case "4PM":
			time = 10;
			break;
		case "5PM":
			time = 11;
			break;
		case "6PM":
			time = 12;
			break;
		case "7PM":
			time = 13;
			break;
		case "8PM":
			time = 14;
			break;
		case "9PM":
			time = 15;
			break;
		}
		
		return time;
	}
	
}
