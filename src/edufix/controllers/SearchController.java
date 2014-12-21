package edufix.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edufix.beans.SearchCriteria;
import edufix.beans.University;
import edufix.service.IEdufixManager;
import edufix.utilities.CommonUtility;
import edufix.utilities.URLMappings;
import edufix.utilities.UniversitySearcher;

/**
 * Searches Universities based on Entered Parameters 
 * @author balaji i
 *
 */
@Controller
public class SearchController {

	@Autowired
	IEdufixManager eduFixManager;
	
	Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value = URLMappings.SEARCH_UNIVERSITIES)
	List<University> searchUniversities(@RequestBody SearchCriteria searchCriteria)
	{
		List<String> matchingUniversityIds = new ArrayList<String>();
		List<University> matchingUniversities = new ArrayList<University>();
		
		try {
			UniversitySearcher univSearcher = new UniversitySearcher();
			
			matchingUniversityIds = univSearcher.getUniversityIdsOnCriteria(searchCriteria);
			logger.info("Universities Searched Successfully");
			
			Integer resultCount = 0;
			if(null != matchingUniversities && ! matchingUniversities.isEmpty())
			{
				resultCount = matchingUniversities.size();
				String universityIds = CommonUtility.getCassandraInQueryString(matchingUniversityIds);
				
				matchingUniversities = eduFixManager.getUniversities(universityIds);
			}
			
			eduFixManager.storeSearchDetails(searchCriteria);
		}
		catch(Exception ex)
		{
			logger.error("Error In Fetching Universities "+ex.toString());
		}
		return matchingUniversities == null ? new ArrayList<University>():matchingUniversities;
	}
	
}
