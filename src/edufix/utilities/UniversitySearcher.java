package edufix.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edufix.beans.SearchCriteria;
import edufix.beans.University;

public class UniversitySearcher {

		//Maximum number of records to be fetched
		private static final int MAX_HITS = 100;
		
		/** Logger for this class and subclasses */
		protected final Logger logger = Logger.getLogger(getClass());

		@SuppressWarnings("deprecation")
		final static Version luceneVersion = Version.LUCENE_CURRENT;
		
		MultiFieldQueryParser multiFieldQueryParser = null;
    	BooleanQuery booleanQuery = new BooleanQuery();
		
    	public List<String> getUniversityIdsOnCriteria(SearchCriteria searchCriteria) throws ParseException, IOException {
		
    		String[] fields = new String[] {"university_name","location","courses"};
    		multiFieldQueryParser = new MultiFieldQueryParser(luceneVersion, fields,new StandardAnalyzer(luceneVersion));
			booleanQuery.add(multiFieldQueryParser.parse(""), BooleanClause.Occur.MUST);
    		
			Directory directory = null;
			try
			{
				directory = FSDirectory.open(new File(IndexCronScheduler.INDEX_DIRECTORY));
			}
			catch(Exception exception)
			{
				throw new FileNotFoundException("Indexes not found at "+IndexCronScheduler.INDEX_DIRECTORY);
			}
			
	    	@SuppressWarnings("deprecation")
	    	IndexReader indexReader = IndexReader.open(directory);
	    	IndexSearcher indexSearcher = new IndexSearcher(indexReader);     
	    	TopDocs topDocs = null;
	    	
	    	/**
	    	 * The sorting is done on the FIELD_SCORE(document relevance) 
	    	 */
	    		topDocs = indexSearcher.search(booleanQuery, MAX_HITS, new Sort(SortField.FIELD_SCORE));
	    
	    	
	        ScoreDoc[] hits = topDocs.scoreDocs;
	       
	        System.out.println(hits.length + " Record(s) Found");
	       
	        List<String> universityIds = new ArrayList<String>();
	        if(hits.length == 0)
	        {
	        	System.out.println("No Records Found ");
	        }
	        else
	        {
	        	for (ScoreDoc scoreDoc : hits) 
		        {
		        	int docId = scoreDoc.doc;
		            Document d = indexSearcher.doc(docId);
		            
		            universityIds.add(d.get("university_id"));
				}
	        }
	        
	        indexReader.close();
			
    		return universityIds == null ? new ArrayList<String>():universityIds;
		}

}
