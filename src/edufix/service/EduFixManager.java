package edufix.service;

import edufix.beans.SearchCriteria;
import edufix.dao.IEduFixDao;

public class EduFixManager implements IEdufixManager {

	IEduFixDao eduFixDao;

	@Override
	public void storeSearchDetails(SearchCriteria searchCriteria) {
		eduFixDao.storeSearchDetails(searchCriteria);
	}

	
	
	public IEduFixDao getEduFixDao() {
		return eduFixDao;
	}

	public void setEduFixDao(IEduFixDao eduFixDao) {
		this.eduFixDao = eduFixDao;
	}

	
}
