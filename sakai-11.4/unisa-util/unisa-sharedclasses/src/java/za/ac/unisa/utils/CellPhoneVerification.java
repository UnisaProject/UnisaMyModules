package za.ac.unisa.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;

public class CellPhoneVerification {
	
	public boolean isCellNumber(String cellNumber){
		
		if (!cellNumber.substring(0,1).equalsIgnoreCase("+")){
			return false;
		}
		if (cellNumber.length()<12){
			return false;
		}
		try {
			long i = Long.parseLong(cellNumber.substring(1));
		} catch(NumberFormatException e){
			return false;
		}
		return true;	
	}
	
	public boolean isSaCellNumber(String cellNumber) {

		if (cellNumber.substring(0, 3).equalsIgnoreCase("+27")){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean validSaCellNumber(String cellNumber) {
	
		
//		if (cellNumber.substring(3,5).equalsIgnoreCase("82") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("83") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("84") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("81") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("60") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("71") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("72") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("73") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("74") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("76") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("78") ||
//				cellNumber.substring(3,5).equalsIgnoreCase("79")){
//			
//		}else{
//			if (cellNumber.substring(3, 4).equalsIgnoreCase("7") ||
//					cellNumber.substring(3, 4).equalsIgnoreCase("8")){
//				
//			}else{
//				return false;					
//			}
//		}
		
		//Johanet 20150219 - Remove hardcoding read cell ranges from gencod
		
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		dao = new StudentSystemGeneralDAO();
		Gencod gencod = new Gencod();
		gencod=dao.getGenCode("65", cellNumber.substring(3,5));
		if (gencod.getEngDescription()==null || gencod.getEngDescription().trim().equalsIgnoreCase("")){
			return false;
		}			
		
		if (cellNumber.length()!=12){
			return false;
		}
		return true;	
	}
	
	
	//Johanet 20150219 - Add new method get read cell ranges from gencod
	public List<Gencod> getSaCellPhoneRanges() {
	//Get SA cell phone ranges
			List<Gencod> cellRangeList = new ArrayList<Gencod>();
			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			dao = new StudentSystemGeneralDAO();
			cellRangeList =  dao.getGenCodes((short)65,0);					
			return cellRangeList;
	}		
}
