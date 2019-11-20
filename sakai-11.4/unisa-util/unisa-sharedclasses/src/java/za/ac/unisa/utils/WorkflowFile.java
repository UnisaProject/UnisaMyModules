package za.ac.unisa.utils;

import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ServerConfigurationService;


/**
 * WorkflowFile is used to write strings of text into a file created in
 * a specified directory.
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
 * 
 * Updated Edmund 2017 - Added folders for SBL, Honours and PostGraduate Applications.
 * 
 */


public class WorkflowFile {

	private StringBuffer fileContent;
	private String fileName;
	private String filePath;
	private Log log;

	/**
	* Class constructor.
	*
	* @param fileName	        the name of the file, .wfl will be added to the name
	*/

	public WorkflowFile(String fileName) {
		fileContent = new StringBuffer();
		log = LogFactory.getLog(this.getClass());
		this.fileName = fileName + ".wfl";
		filePath = ServerConfigurationService.getString("workflow.path");
		log.debug(filePath+"/"+fileName+": instantiated.");
	}
	
	/**
	* Class constructor.
	*
	* @param fileName	        the name of the file, .wfl will be added to the name
	* @param type	            the type of file to be created
	*/

	public WorkflowFile(String fileName, String fileType) {
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
		}else if ("SBL".equalsIgnoreCase(fileType)){
			// MD application workflow path
			  filePath = ServerConfigurationService.getString("sblapply.path");
		}else if ("HON".equalsIgnoreCase(fileType)){
			// MD application workflow path
			  filePath = ServerConfigurationService.getString("honapply.path");
		}else if ("PG".equalsIgnoreCase(fileType)){
			// MD application workflow path
			  filePath = ServerConfigurationService.getString("pgapply.path");
		}
	
		log.debug(filePath+"/"+fileName+": instantiated.");
	}

	/**
	 * This method adds a string to the file buffer
	 *
	 * @param fileContent       the string to be written to the file
	*/

	public void add(String fileContent) {
		// Add file content to a string buffer
		this.fileContent.append(fileContent);
		log.debug(filePath+"/"+fileName+": added - "+fileContent.replaceAll("[\r\n]", ""));
	}

	/**
	 * This method opens the file, writes the content to the file and closes it.
	 *
	*/
	public void close()throws Exception{

		FileWriter workflowFile = null;

		// Create workflow file in workflow directory
		try {
			workflowFile = new FileWriter(filePath +"/"+ fileName);
			workflowFile.write(fileContent.toString());
			workflowFile.close();
			log.info(filePath+"/"+fileName+": file written to filesystem");
		} catch (Exception e) {
			log.error(filePath+"/"+fileName+": exception "+e+" "+e.getMessage());
			throw new Exception("WorkflowFile : Error creating file "+ filePath + "/" + fileName +": "+ e.getMessage(), e);
		}

	}

}
