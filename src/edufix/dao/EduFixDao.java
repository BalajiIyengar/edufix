package edufix.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import edufix.beans.SearchCriteria;
import edufix.beans.University;

public class EduFixDao implements IEduFixDao {

	@Autowired
	CassandraOperations cassandraOperations;

	static final String USER_SEARCH_TABLE = "user_searches";
	
	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void storeSearchDetails(SearchCriteria searchCriteria) {
		Insert storeSearchDetails = QueryBuilder.insertInto(USER_SEARCH_TABLE).values(new String[]{}, new Object[]{});
		
		cassandraOperations.execute(storeSearchDetails);
	}

	@Override
	public List<University> getUniversities(String universityIds) {
		String selectUniversityDetails = "select * from university_details where university_id in ("+universityIds+")";
		List<University> universities = new ArrayList<University>();
		
		Select select = QueryBuilder.select().from("university_details");
		select.where(QueryBuilder.in("university_id", universityIds));
		
		try {
			universities = cassandraOperations.select(select, University.class);
		}
		catch(NullPointerException npe){
			logger.error("NullPointerException in Indexing University Details");
		}
		catch(IllegalArgumentException argEx){
			logger.error("IllegalArgumentException in Indexing University Details");
		}
		catch(NoHostAvailableException noHostEx){
			logger.error("NoHostAvailableException in Indexing University Details");
		} 
		return universities == null ? new ArrayList<University>() : universities;
	}
}
