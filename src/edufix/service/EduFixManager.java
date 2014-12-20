package edufix.service;

import edufix.dao.IEduFixDao;

public class EduFixManager implements IEdufixManager {

	IEduFixDao eduFixDao;

	public IEduFixDao getEduFixDao() {
		return eduFixDao;
	}

	public void setEduFixDao(IEduFixDao eduFixDao) {
		this.eduFixDao = eduFixDao;
	}
	
}
