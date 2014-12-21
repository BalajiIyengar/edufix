package edufix.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.exceptions.NoHostAvailableException;

import edufix.beans.University;

/**
 * Runs the Scheduler every 5 mins
 * @author balaji i
 *
 */
@Component
public class IndexCronScheduler {
	
	@Autowired
	CassandraOperations cassandraOperations;
	
	public static final String UNIVERSITY_DETAILS = "select * from university_details";
	public static final String INDEX_DIRECTORY = "F:/projects/indexes";
	
	Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Indexes the records after emptying the existing directory
	 * @author balaji i
	 */
	@Scheduled(cron="* 2 * * * *")
	void indexRecords()
	{
		EmptyExistingDirectory.deleteFiles(INDEX_DIRECTORY);
		fetchUniversities();
	}
	
	void fetchUniversities()
	{
		List<University> universities = new ArrayList<University>();
		
		try {
			universities = cassandraOperations.select(UNIVERSITY_DETAILS,University.class);
			
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
			e.printStackTrace();
		}
	}


	@SuppressWarnings("deprecation")
	private void indexUniversityDetails(List<University> universities) throws IOException {
			StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT,CharArraySet.EMPTY_SET);  
		    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_CURRENT, standardAnalyzer);
		    IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File(INDEX_DIRECTORY)), indexWriterConfig);
		int counter = 0;   
		for (University university : universities) {
			 Document document = new Document();  
			 
			 document.add(new TextField("university_name",university.getUniversityName(),Field.Store.YES));
			 document.add(new TextField("location",university.getLocation(),Field.Store.YES));
			 document.add(new TextField("courses",university.getCourses().toString(),Field.Store.YES));
			 
			 indexWriter.addDocument(document);  
			 counter++;
		}
		
		System.out.println(counter+" Universities Indexed");
		indexWriter.commit();
		indexWriter.close();
	}
}
