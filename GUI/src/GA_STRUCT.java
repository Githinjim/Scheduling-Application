import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class GA_STRUCT {

	// Field Declarations
	String 	GA_Name,
			GA_Phone,
			GA_ID;
	
	HashMap<Date, CLASS_STRUCT> availability;
	
	String pattern = "dd 'from' hh 'to' hh";
	SimpleDateFormat date_format = new SimpleDateFormat(pattern);
	
	// Some sort of structure like this might allow for some comparisons.
	/*
	 * For example: 
	 * if (GA_STRUCT.availability.containsKey(class_date)
	 * 		if (GA_STRUCT.availability.get(class_date) == null)
	 * 			ASSIGN_GA_AS_TA(class_date);	 * 
	 */
	
}
