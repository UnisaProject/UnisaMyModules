package org.sakaiproject.studymaterial.utils;

public class StudyMaterialDataFilteringUtil {

	public static boolean isDocumentTypeValid(String docType) {
		String[] validDocTypes = { "BB", "BL", "GD", "HL", "LB", "MA", "MO",
				"MG", "QB", "RE", "TL", "WB" };
		boolean docTypeMatch = false;
		for (int x = 0; x < validDocTypes.length; x++) {
			if (docType.trim().equalsIgnoreCase(validDocTypes[x])) {
				docTypeMatch = true;
				break;
			}
		}
		return docTypeMatch;
	}

	public static boolean isAvailableDateValid(String dateAvailable) {
		DateUtil dateutil = new DateUtil();
		return dateutil.isDateBeforeSysDate(dateAvailable);
	}

	public static boolean isFileSizeValid(String filesize) {
		//if (filesize.equals("")) {
		//	return false;
		//}
		return true;
	}

	public static boolean isStudyMaterialValid(String docType, String filesize,	String dateAvailable) {
		//Vijay change: Not to show the MO type material
		/*if(docType.equalsIgnoreCase("MO")){
			return false;
		} else if (isDocumentTypeValid(docType) && isAvailableDateValid(dateAvailable) && isFileSizeValid(filesize)) {
			return true;
		}*/
		
		if (isDocumentTypeValid(docType) && isAvailableDateValid(dateAvailable)
				&& isFileSizeValid(filesize)) {
			return true;
		}
		
		return false;
	}
}
