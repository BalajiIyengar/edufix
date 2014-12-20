package edufix.utilities;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Runs the Scheduler every 5 mins
 * @author balaji i
 *
 */
@Component
public class IndexCronScheduler {

	/**
	 * Indexes the records after emptying the existing directory
	 * @author balaji i
	 */
	@Scheduled(cron="0 0/1 * * * ?")
	void indexRecords()
	{
		EmptyExistingDirectory.deleteFiles(UniversityIndexer.INDEX_DIRECTORY);

		UniversityIndexer indexer = new UniversityIndexer();
		indexer.indexUniversityDetails();
	}
	
}
