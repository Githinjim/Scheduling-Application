import java.sql.Time;
import java.time.DayOfWeek;
import java.util.HashMap;

import com.google.common.collect.ListMultimap;


public class GA_STRUCT {

	// Field Declarations
	String 	GA_Name,
			GA_Phone,
			GA_ID;
	
	ListMultimap<DayOfWeek, HashMap<Time, CLASS_STRUCT>> availability;
	//HashMap<DayOfWeek, HashMap<Hour, CLASS_STRUCT>> availability;
	
	// Some sort of structure like this might allow for some comparisons.
	/*
	 * For example: 
	 * if (GA_STRUCT.availability.containsKey(class_date) // Checking that the GA is does not have any other obligations at this time
	 * 		if (GA_STRUCT.availability.get(class_date) == null) // Checking that the GA hasn't already been assigned a class for this time
	 * 			ASSIGN_GA_AS_TA(class_date);	 * 
	 */
	
}
