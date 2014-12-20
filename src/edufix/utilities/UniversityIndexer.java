package edufix.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

import edufix.beans.University;

public class UniversityIndexer {

	@Autowired
	CassandraOperations cassandraOperations;

	public static final String UNIVERSITY_DETAILS = "select * from university";
	
	
	public static final String INDEX_DIRECTORY = "";
	
	Logger logger = Logger.getLogger(getClass());
	
	public void indexUniversityDetails() {
		
		List<University> universities = new ArrayList<University>();
		
		try {
			universities = cassandraOperations.query(UNIVERSITY_DETAILS,new UniversityDetailMapper());
			
			if(universities != null && ! universities.isEmpty())
				indexUniversityDetails(universities);
		}
		catch(NullPointerException npe){
			logger.error("NullPointerException in Indexing University Details");
		}
		catch(IllegalArgumentException argEx){
			logger.error("IllegalArgumentException in Indexing University Details");
		}
		catch(NoHostAvailableException noHostEx){
			logger.error("NoHostAvailableException in Indexing University Details");
		} catch (IOException e) {
			logger.error("IOException in Indexing University Details");
		}
	}

	private class UniversityDetailMapper implements RowMapper<University>{

		@Override
		public University mapRow(Row arg0, int arg1) throws DriverException {
			University universityDetails = new University();

			return universityDetails;
		}
		
	}

	private void indexUniversityDetails(List<University> universities) throws IOException {
		   StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT,CharArraySet.EMPTY_SET);  
		   IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_CURRENT, standardAnalyzer);
		   IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File(INDEX_DIRECTORY)), indexWriterConfig);
		   
		   for (University universityDetails : universities) {
			   Document document = new Document();  
			
			   
			   
			   indexWriter.addDocument(document);  
		   }
		   indexWriter.commit();
		   indexWriter.close();
	}
	
}
