package edufix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edufix.beans.University;
import edufix.service.IEdufixManager;
import edufix.utilities.URLMappings;

/**
 * Searches Universities based on Entered Parameters 
 * @author balaji i
 *
 */
@Controller
public class SearchController {

	@Autowired
	IEdufixManager eduFixManager;
	
	
	@RequestMapping(value = URLMappings.SEARCH_UNIVERSITIES)
	List<University> searchUniversities()
	{
		return null;
	}
	
}
