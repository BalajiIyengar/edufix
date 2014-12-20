package edufix.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import edufix.beans.SearchCriteria;

public class EduFixDao implements IEduFixDao {

	@Autowired
	CassandraOperations cassandraOperations;

	static final String USER_SEARCH_TABLE = "user_searches";
	
	@Override
	public void storeSearchDetails(SearchCriteria searchCriteria) {
		Insert storeSearchDetails = QueryBuilder.insertInto(USER_SEARCH_TABLE).values(new String[]{}, new Object[]{});
		
		cassandraOperations.execute(storeSearchDetails);
	}
}
