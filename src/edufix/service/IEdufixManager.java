package edufix.service;

import java.util.List;

import edufix.beans.SearchCriteria;
import edufix.beans.University;

public interface IEdufixManager {

	void storeSearchDetails(SearchCriteria searchCriteria);

	List<University> getUniversities(String universityIds);

}
