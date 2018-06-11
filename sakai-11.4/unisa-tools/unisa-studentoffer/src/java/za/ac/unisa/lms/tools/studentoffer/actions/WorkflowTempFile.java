package za.ac.unisa.lms.tools.studentoffer.actions;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;


/**
 * WorkflowFile is used to write strings of text into a file created in
 * a specified directory. (Use path + /tmp for new applications (2013)
 *
 * The add method builds up a StringBuffer that is written to the file in the close method.
 *
 * The file will be written to a location specified by the workflowfile.path property in sakai.properties
 *
 * Example:
 * WorkflowFile file = new WorkflowFile("myFileName");
 *		file.add("Text line :\r\n\r\n");
 *   	file.add("Another text line :\t and more information \r\n\r\n");
 *		file.close();
 *
 * @author      Elmarie Penzhorn
 * @Update		Edmund Wilschewski (2013)
 */


public class WorkflowTempFile {

	private StringBuffer fileContent;
	private String fileName;
	private String filePath;

	public static Log log = LogFactory.getLog(StudentOfferAction.class);

	/**
	* Class constructor.
	*
	* @param fileName	        the name of the file, .wfl will be added to the name
	* @param type	            the type of file to be created
	*/

	public WorkflowTempFile(String fileName, String fileType) {
		fileContent = new StringBuffer();
		 log = LogFactory.getLog(this.getClass());
		 this.fileName = fileName + ".wfl";
		  // determine filepath according to fileType
		if(fileType == null || "".equals(fileType)){
			// normal workflow path
		  filePath = ServerConfigurationService.getString("workflow.path");
		}else if ("A".equalsIgnoreCase(fileType)){
			// application workflow path
		  filePath = ServerConfigurationService.getString("application.path");
		}else if ("M".equalsIgnoreCase(fileType)){
			// MD application workflow path
			  filePath = ServerConfigurationService.getString("mdapply.path");
		}
	
		//log.debug(filePath+"/"+fileName+": instantiated.");
	}

	/**
	 * This method adds a string to the file buffer
	 *
	 * @param fileContent       the string to be written to the file
	*/

	public void add(String fileContent) {
		// Add file content to a string buffer
		this.fileContent.append(fileContent);
		//log.debug(filePath+"/tmp/"+fileName+": added - "+fileContent.replaceAll("[\r\n]", ""));
	}

	/**
	 * This method opens the file, writes the content to the file and closes it for a specific student folder.
	 *
	*/
	@SuppressWarnings("unused")
	public void close(String studentNr) {

		FileWriter workflowFile = null;

		String stuGroupDir = studentNr.substring(0, 1);
    	String stuDir = studentNr;
    	String finalWFLFilePath = filePath; //Only used for delete (/data/sakai/applications)
    	String tmpWFLFilePath = filePath + "/tmp/" + stuGroupDir + "/" + stuDir;
    	
		String finalWFLFile = finalWFLFilePath +"/"+ fileName;
		String tmpWFLFile = tmpWFLFilePath +"/"+ fileName;
		
		// Create workflow file in workflow directory
		try {
			//2014 Edmund
			//Create new folder with Student number as too many files in one folder (in excess of 20 000) slows down the system
			//Create thus one subfolder for the first character in the number (ie for 12345678, create "1")
			//Then create the actual student number folder under that folder (ie 12345678)
			File newDir = new File(tmpWFLFilePath);
			if (!newDir.exists()) {
				if (newDir.mkdirs()) {
					//log.debug("WorkFlowTempFile - Directory is created: " + tmpWFLFilePath);
				} else {
					log.info("WorkFlowTempFile - Failed to create directory: " + tmpWFLFilePath);
				}
			}
			//2014 Edmund
			//First check if file already exists and delete it if it does as otherwise it won't create a new file
			//This caused a problem if a file could not be moved as the same file exits in the destination folder
			//Should only apply for returning students as new student's files are created by Ina's proxy.

			boolean remTmpWFLFile = removeOldFile(tmpWFLFile, "FutureUse");
			/**2014 Edmund
			 * for Future use if you wish to rename files instead of deleting, 
			 * we however only worry about the latest version of the file
			 * at this stage, so we delete the existing uniflow file.
			 */
			/**
			if (!remTmpWFLFile){
				//log.info(tmpWFLFile+": file did not exist or has been deleted, continue to write file");
			}else{
				//Could do rename here if you wanted
			}
			**/
			//Added removal of uniflow file from /data/sakai/applications as well as the file exists during testing
			//should not exist under normal circumstances, but could be if it is decided to allow more than one
			//application or change of qualification per academic year in the future.
			boolean remDestWFL = removeOldFile(finalWFLFile, "FutureUse");
			
			workflowFile = new FileWriter(tmpWFLFile);
			workflowFile.write(fileContent.toString());
			workflowFile.close();
			//log.debug(tmpWFLFile+": file written to filesystem");
		} catch (Exception e) {
			log.error("WorkflowFile : Error creating file " + tmpWFLFile +": exception "+e+" "+e.getMessage());
		}
	}
	
	/**2014 Edmund
	 * Add method to delete the workflow file if it already exists. 
	 * This is only specific to returning students, who could go back to change a qualification
	 * as the workflow file is created immediately, not like with a new student, where the proxy
	 * created the workflow file and the student cannot return to change their qualification
	 * Remember that once a student does have a STUAPQ entry, then no student, returning or new can re-apply
	 * So they should never get here, this is thus only while they are still busy processing their first application 
	 * for a specific academic year.
	 * @param oldPathFile
	 * @return
	 */
	public boolean removeOldFile(String oldPathFile, String newFileName) {
	    java.io.File oldFileName = new java.io.File(oldPathFile);
	    if (!oldFileName.isFile()) {
	    	return false;
	    }
	    boolean deleteOldFileSucceeded = oldFileName.delete();
	    if (!deleteOldFileSucceeded) {
	    	return false;
	    }
	    //Add Rename if you do not wish to delete
	    //newFileName.renameTo(oldFileName);
	    //Return true if file has been renamed/moved
	    //return newFileName.exists();
	    
	    //remove this return if you wish to rename file as it is done above
	    return true;
	}
	
}
