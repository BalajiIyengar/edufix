package edufix.utilities;

import java.util.List;

public class CommonUtility {

	/**
	 * Converts List of String to a String to be Used in IN Clause of Cassandra 
	 * @param listStr
	 * @return String outputString
	 */
	public static String getCassandraInQueryString(List<String> listStr)
	{
		StringBuilder listSB = new StringBuilder();
		String outputString = "";
		
		for (String emailId : listStr) {
			listSB = listSB.append("'").append(emailId).append("',");
		}
		
		if(listSB.length() > 0)
			outputString = listSB.substring(0,listSB.lastIndexOf(","));
		
		return outputString;
	}
}
