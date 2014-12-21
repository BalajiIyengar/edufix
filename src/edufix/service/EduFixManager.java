package edufix.service;

import java.util.List;

import edufix.beans.SearchCriteria;
import edufix.beans.University;
import edufix.dao.IEduFixDao;

public class EduFixManager implements IEdufixManager {

	IEduFixDao eduFixDao;

	@Override
	public void storeSearchDetails(SearchCriteria searchCriteria) {
		eduFixDao.storeSearchDetails(searchCriteria);
	}

	@Override
	public List<University> getUniversities(String universityIds) {
		return eduFixDao.getUniversities(universityIds);
	}
	
	
	public IEduFixDao getEduFixDao() {
		return eduFixDao;
	}

	public void setEduFixDao(IEduFixDao eduFixDao) {
		this.eduFixDao = eduFixDao;
	}
}
