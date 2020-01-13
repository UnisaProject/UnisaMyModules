package za.ac.unisa.lms.tools.smsbatch.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.smsbatch.dao.GeneralItem;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsBatchDAO;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsFileDAO;
import za.ac.unisa.lms.tools.smsbatch.forms.SmsBatchForm;

public class SmsFileActionBackup extends LookupDispatchAction {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods
	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.continue", "nextStep");
		map.put("button.send", "send");
		map.put("nextStep", "nextStep");
		map.put("step1", "step1");
		map.put("button.upload", "upload");
		map.put("testMultiAdd", "testMultiAdd");

		return map;
	}
	
	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Check user
		SmsBatchForm smsForm = (SmsBatchForm) form;
		Cookie[] mySiteCookies = request.getCookies();
		smsForm.setNovellUserCode("");
		if (mySiteCookies != null) {
			for (int i = 0; i < mySiteCookies.length; i++) {
				Cookie co = mySiteCookies[i];
				if ("novelluser".equalsIgnoreCase(co.getName().toString()
						.trim())) {
					smsForm.setNovellUserCode(co.getValue());
				}
			}
			if ("".equals(smsForm.getNovellUserCode())) {
				/*
				 * NB NB NB NB NB NB NB NB set user code for local dev only
				 */
				//smsForm.setNovellUserCode("DLAMIW");
				smsForm.setNovellUserCode("PRETOJ");
				//smsForm.setNovellUserCode("PENZHE");
				//return mapping.findForward("userunknown");
			}
		} else {
			return mapping.findForward("userunknown");
		}

		ActionMessages messages = new ActionMessages();

		/** Do validation */
		if ("-1".equals(smsForm.getRcCode()) || "".equals(smsForm.getRcCode())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Select a Responsibility code from the list."));
			addErrors(request, messages);
			return mapping.findForward("firstpage");
		}

		// Set default values
		smsForm.setReasonGc27("2 : Learner Support");
		setLists(smsForm, request);

		return mapping.findForward("step1forward");
	}
	
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SmsBatchForm smsForm = (SmsBatchForm) form;
		ActionMessages messages = new ActionMessages();
		
		String readFile = "";
		String writeFile = "";
		FileInputStream buffIn = null;
		FileOutputStream buffout = null;
		String fileDir;
		String inputFileName = "";
				
		inputFileName = request.getAttribute("theFile").toString();		
		
		String uploadPath = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
		String[] input = inputFileName.split(",");
		String[] parseInput; 
		String extensions = "txt";
		String[] extList = extensions.split(",");
		for (int i=0; i < input.length; i++){
			parseInput = input[i].split("=");
			if (parseInput.length > 1){
				if (i == 0){		
					String tmp = parseInput[1];					
					if (parseInput[1].lastIndexOf("\\") > 0 ){
						tmp = parseInput[1].substring(parseInput[1].lastIndexOf("\\")+1);
					}
					writeFile = tmp;
				} else if (i == 1){
					readFile = parseInput[1];
				}
			}
		}
		
		String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
		String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
		String newFileName = smsForm.getNovellUserCode() +"_"+ smsForm.getFileContentType() + "_"+ time +"." + fileExtension;
		writeFile = newFileName;
		
		request.setAttribute("inputFileName", inputFileName);
		FileItem item = (FileItem)request.getAttribute("theFile");	
		boolean inMemory = item.isInMemory();
		long filesize = item.getSize();
		String tempstr = item.getString();
		byte[] array = item.get();
		
		if (!writeFile.equalsIgnoreCase("") && writeFile != null){
			boolean notAllowed = true;
			File testSize = new File(readFile);
			for(int i=0; i < extList.length; i++){
				if (writeFile.substring(writeFile.lastIndexOf(".")+1).equalsIgnoreCase(extList[i])){
					notAllowed = false;
				}
			}
			// check file type
			if (notAllowed){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You can only upload txt file types."));
				addErrors(request, messages);
				// Set default values
				smsForm.setReasonGc27("2 : Learner Support");
				setLists(smsForm, request);
				return mapping.findForward("step1forward");
			}
			// check file size,  not greater than 2 MB
			if (testSize.length() > 2097152){ // measured in BYTES!
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You cannot upload files greater than 2 MB (2048 KB)"));
				addErrors(request, messages);
				// Set default values
				smsForm.setReasonGc27("2 : Learner Support");
				setLists(smsForm, request);
				return mapping.findForward("step1forward");
			} else {
				try {
					buffIn = new FileInputStream(readFile);
					// write file to temp dir: /data/sakai/wfl/tmp
					// specified in web.xml
				
					fileDir = uploadPath +"/";
					File dir = new File(fileDir);
					if (!dir.exists()){
						dir.mkdirs();
					}
					buffout = new FileOutputStream(fileDir+writeFile);
					boolean eof = false;
					while(!eof){
						int line = buffIn.read();
						if (line == -1){
							eof  = true;
						} else {
							buffout.write(line);
						}
					}
					buffIn.close();
					buffout.close();
					
				} catch (FileNotFoundException e) {
					//e.printStackTrace();			
					
					fileDir = uploadPath +"/";
					File dir = new File(fileDir);
					if (!dir.exists()){
						dir.mkdirs();
					}
					buffout = new FileOutputStream(fileDir+writeFile);
					buffout.write(array);
					buffout.close();
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","temp file not found"));
					addErrors(request, messages);
					// Set default values
					smsForm.setReasonGc27("2 : Learner Support");
					setLists(smsForm, request);
					return mapping.findForward("step1forward");
				} catch (NullPointerException ne) {						
					ne.printStackTrace();						
				} catch (IOException ioe){
					ioe.printStackTrace();
				} finally {
					try{
						if (buffIn != null){
							buffIn.close();
							buffout.close();
						}
					} catch(IOException ie){
						ie.printStackTrace();
					}
				}
			}
		
		}		
		
		SmsFileDAO dao = new SmsFileDAO();
		
		double smsCost=0;
		smsCost = dao.getSmsCost();
		smsForm.setCostPerSms(smsCost);		

		return mapping.findForward("step2forward");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		String prevPage = "";

		// Clear all fields
		smsBatchForm.setCanSendSms(false);
		smsBatchForm.setSmsWasSend(false);
		smsBatchForm.setRegCriteriaType("");
		smsBatchForm.setGeoCriteriaType("");
		smsBatchForm.setRegList(new ArrayList());
		smsBatchForm.setMessageCount(0);
		smsBatchForm.setControllCellNr("");
		smsBatchForm.setDataFileName("");
		smsBatchForm.setDeptCode(Short.parseShort("0"));
		smsBatchForm.setGeoSelection(new String[15]);
		smsBatchForm.setMessage("");
		smsBatchForm.setSearchCode("");
		smsBatchForm.setSearchDescription("");
		smsBatchForm.setSelectionString("");
		smsBatchForm.setRegistrationPeriod("");
		smsBatchForm.setSelectedItems("");
		smsBatchForm.setReasonGc27("2 : Learner Support");
		smsBatchForm.setFileContentType("");
		prevPage = "firstpage";

		return mapping.findForward(prevPage);
	}
	
	private void setLists(SmsBatchForm smsForm, HttpServletRequest request)
	throws Exception {
		/** Set reason code list */
		SmsBatchDAO dao = new SmsBatchDAO();
		GeneralItem genItem = new GeneralItem();
		List tempReasonList = new ArrayList();
		List reasonList = new ArrayList();
		tempReasonList = dao.getGeneralCodesList(request,"27", 1);
		for (int i=0; i< tempReasonList.size();i++){
			genItem = getItem(((LabelValueBean)(tempReasonList.get(i))).getValue());
			if (genItem.getCode().equals("2") || genItem.getCode().equals("9999")) {
				reasonList.add(tempReasonList.get(i));
			}
		}
		request.setAttribute("reasonList", reasonList);
	}
	
	private GeneralItem getItem(String inputStr) {
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf(":");
		item.setDesc(inputStr.substring(pos + 2, inputStr.length()));
		item.setCode(inputStr.substring(0, pos - 1));
		return item;
	}
	
	//String readFile = "";
	//String writeFile = "";
	//FileInputStream buffIn = null;
	//FileOutputStream buffout = null;
	//String fileDir;
	//String inputFileName = "";
			
	//String uploadPath = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
	//request.setAttribute("inputFileName", inputFileName);
	//FileItem item = (FileItem)request.getAttribute("theFile");
	
	/*String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
	String newFileName = smsForm.getNovellUserCode() +"_"+ smsForm.getFileContentType() + "_"+ time +".txt";
	writeFile = newFileName;
	
	fileDir = uploadPath +"/";
	File dir = new File(fileDir);
	if (!dir.exists()){
		dir.mkdirs();
	}
				
	buffout = new FileOutputStream(fileDir+writeFile);
	buffout.write(array);
	buffout.close();
	
	SmsFileDAO dao = new SmsFileDAO();
	String strCellNumbers="";
	int smsCount = 0;		
	int recordNr = 0;
	int invalidRecordNr = 0;
	//read file to determine number of numbers
	 try{
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(fileDir+writeFile);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    //Read File Line By Line
		    String cellNumber="";			   
		    while ((strLine = br.readLine()) != null)   {
		      // Validate student Number and get cell phone number
		      // Validate cell phone Number
		     String errMsg = "";	
		     if (isInteger(strLine.trim())){
		    	  boolean validStudent = false;
		    	  validStudent = dao.isStudentNr(Integer.parseInt(strLine.trim()));
		    	  if (validStudent){
		    		 cellNumber = dao.getCellNumber(Integer.parseInt(strLine.trim()));
		    		 cellNumber = cellNumber.trim();
		    		 if (!cellNumber.equalsIgnoreCase("")){
		    			  if (cellNumber.substring(0, 1).equalsIgnoreCase("+")){
		    				  smsCount = smsCount + 1;
		    				  if (strCellNumbers.equalsIgnoreCase("")){
		    					  strCellNumbers = cellNumber.trim();
		    				  }else{
		    					  strCellNumbers = strCellNumbers + "~"  + cellNumber.trim();
		    				  }					    		  
		    			  }else{
		    				  errMsg = "Invalid cell phone number";
		    			  }				    		  
			    	 }else{
			    		 errMsg = "No cell phone number";				    		
			    	 }
		    	  }else {
		    		  errMsg = "Invalid student number";
		    	  }
		      }else{
		    	  errMsg = "Student number not numeric";
		      }
		      recordNr = recordNr + 1;
		      if (!errMsg.equalsIgnoreCase("")){
		    	  invalidRecordNr = invalidRecordNr + 1;
		      }
		    }
		    //Close the input stream
		    in.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	*/
}
