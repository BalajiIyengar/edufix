package edufix.dao;

import java.util.List;

import edufix.beans.SearchCriteria;
import edufix.beans.University;

public interface IEduFixDao {

	void storeSearchDetails(SearchCriteria searchCriteria);

	List<University> getUniversities(String universityIds);
}
