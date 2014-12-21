package edufix.utilities;

import java.io.File;
import java.io.IOException;

/**
 * Deletes all the Existing Files in the Mentioned Directory
 * @author balaji i
 *
 */
public class EmptyExistingDirectory {


    public static void deleteFiles(String SRC_FOLDER)
    {	
    	File directory = new File(SRC_FOLDER);
 
    	//make sure directory exists
    	if(!directory.exists()){
 
           System.out.println("Directory does not exist.");
 
        }else{
 
           try{
               delete(directory);
 
           }catch(IOException e){
               e.printStackTrace();
               System.exit(0);
           }
        }
 
    	System.out.println("Deletion Successfully Completed");
    }
 
    public static void delete(File file)
    	throws IOException{
 
    	if(file.isDirectory()){
 
    		//directory is empty, then delete it
    		if(file.list().length==0){
 
    		   file.delete();
    		   System.out.println(file.getAbsolutePath()+" Directory deleted" );
 
    		}else{
 
    		   //list all the directory contents
        	   String files[] = file.list();
 
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
 
        	      //recursive delete
        	     delete(fileDelete);
        	   }
 
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println(file.getAbsolutePath()+" Directory deleted ");
        	   }
    		}
 
    	}else{
    		//if file, then delete it
    		file.delete();
    		System.out.println(file.getAbsolutePath()+" File deleted" );
    	}
    }

}
