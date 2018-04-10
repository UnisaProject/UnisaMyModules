package za.ac.unisa.lms.tools.filemgrutil.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;



public class UtilDetailsForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables
	private String subjectCode;
	private String task;
	private String webId;
	private ArrayList wedids;
	private String id;
	
	private String file_name;
	private String source;
	private String dest;
	
	
	

	// --------------------------------------------------------- Methods
	public void setSubjectCode(String subjectCode)
	{
		this.subjectCode = subjectCode;
	}
	
	public String getSubjectCode()
	{	
		return subjectCode;
	}
	public void setTask(String task)
	{
		this.task = task;
	}
	public String getTask()
	{
		return task;
	}
	public void setWebId(String webId)
	{
		this.webId=webId;
	}
	public String getWebId()
	{
		return webId;
	}

	public ArrayList getWedids() {
		return wedids;
	}

	public void setWedids(ArrayList wedids) {
		this.wedids = wedids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	




	
}