package za.ac.unisa.utils;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.processDrivenSms.ProcessDrivenSMSDao;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRecipient;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRecipientResult;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRequest;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRequestResult;
import za.ac.unisa.utils.CellPhoneVerification;

public class ProcessDrivenSMS {
	
public SmsRequestResult validateSMSRequest(SmsRequest smsRequest) throws Exception{
		
		SmsRequestResult result = new SmsRequestResult();
		List<SmsRecipientResult> listSmsOut = new ArrayList<SmsRecipientResult>();
		
		ProcessDrivenSMSDao dao = new ProcessDrivenSMSDao();
		result.setSmsSendCount(0);
		result.setErrMsg("");
		result.setBatchNr(0);
		//Validate input		
		if (smsRequest.getReasonCodeGC27()==null || smsRequest.getReasonCodeGC27().equalsIgnoreCase("")){
			result.setErrMsg("Please indicate the reason for the sms, reasonCodeGC27 is required.");
			return result;
		}else{
			Gencod gencod = new Gencod();
			StudentSystemGeneralDAO generalDao = new StudentSystemGeneralDAO();
			gencod = generalDao.getGenCode("27", smsRequest.getReasonCodeGC27());
			if (gencod==null || gencod.getEngDescription()==null || gencod.getEngDescription().equalsIgnoreCase("")){
				result.setErrMsg("Invalid sms reason code(reasonCodeGC27).");
				return result;
			}
		}
		if (smsRequest.getPersNo()==0){
			result.setErrMsg("Personnel No (persNo) is required.");
			return result;
		}
		if (smsRequest.getRcCode()==null || smsRequest.getRcCode().equalsIgnoreCase("")){
			result.setErrMsg("Rc-code is (rcCode) required.");
			return result;
		}else{
			Person person = new Person();
            PersonnelDAO personnelDao = new PersonnelDAO();
            person = personnelDao.getPerson(smsRequest.getPersNo());
            if (person == null || person.getPersonnelNumber()==null){
            	result.setErrMsg("Invalid persno, no staff record for this personnel number.");
				return result;
            }
           result.setUserDeptCode(Integer.parseInt(person.getDepartmentCode()));
		}		
		if (smsRequest.getMessage()!=null && !smsRequest.getMessage().equalsIgnoreCase("")){
			if (smsRequest.getMessage().length() > 160){
				result.setErrMsg("Sms message may not be longer than 160 characters.");
			}
		}
		
		CellPhoneVerification verifyCell = new CellPhoneVerification();
		List<Gencod> saCellRanges = new ArrayList<Gencod>();
		saCellRanges = verifyCell.getSaCellPhoneRanges(); 
		//Validate Student number and get student cellphone number		
		for (int i=0; i < smsRequest.getListRecipient().size(); i++){
			String cellNumber="";
			String errMsg="";
			SmsRecipient recipientIn = new SmsRecipient();
			SmsRecipientResult recipientOut = new SmsRecipientResult();
			recipientIn = (SmsRecipient) smsRequest.getListRecipient().get(i);
			recipientOut.setStudentNr(recipientIn.getStudentNr());
			recipientOut.setMessage(recipientIn.getMessage());
			if (dao.isStudentNr(recipientIn.getStudentNr())){				
		    	 cellNumber = dao.getCellNumber(recipientIn.getStudentNr());
		    	 cellNumber = cellNumber.trim();
		    	 recipientOut.setCellNr(cellNumber);
		    }else {
		      errMsg = "Invalid student number.";
		    }
			if (errMsg.equalsIgnoreCase("")){
				if (cellNumber==null || cellNumber.trim().equalsIgnoreCase("")){	
					errMsg = "No cell phone number.";
	    		}else{	   					    					
	   				if (!verifyCell.isCellNumber(cellNumber)){
	   					errMsg =  "Invalid cell number.";
	   				}else{
	   					if (verifyCell.isSaCellNumber(cellNumber)){
		   					boolean found = false;
							for (int j=0; j < saCellRanges.size(); j++){
								if (cellNumber.substring(3,5).equalsIgnoreCase(((Gencod)saCellRanges.get(j)).getCode())){
									found =true;
									j = saCellRanges.size();
								}						
							}
							if (!found){
								errMsg =  "Invalid cell number.";							
							}
	   					}	
//	   					if (verifyCell.isSaCellNumber(cellNumber)){
//	   						if (!verifyCell.validSaCellNumber(cellNumber)){
//	   							errMsg =  "Invalid cell number.";
//	   						}
//	   					}
	   				}			
	    		}	
			}
			if (errMsg.equalsIgnoreCase("") && recipientIn.getMessage().length()> 160){
				errMsg = "Student sms message is longer than 160 characters.";
			}			
			if (errMsg.equalsIgnoreCase("")){
				result.setSmsSendCount(result.getSmsSendCount() + 1);
			}else{
				recipientOut.setErrMsg(errMsg);
			}
			listSmsOut.add(recipientOut);			
		}
		
		result.setListRecipient(listSmsOut);
		return result;	
	}

	public SmsRequestResult sendSMSRequest(SmsRequest smsRequest) throws Exception{
		
		SmsRequestResult result = new SmsRequestResult();
		result = validateSMSRequest(smsRequest);
		
		ProcessDrivenSMSDao dao = new ProcessDrivenSMSDao();
		
		if (result.getSmsSendCount()>0){
			result = dao.createSmsRequestRecords(smsRequest,result);
		}
		
		return result;	
	}

}
