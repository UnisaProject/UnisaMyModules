package za.ac.unisa.lms.tools.tpstudymaterial.util;

import java.io.File;

public class StudyMaterialLocation {
	
     public static String getFilepath(String module,String documentType, String fileName) throws Exception {
		
    	  String filefullPath = "";
          boolean collectionExistFlag = isCollectionStudyMaterial(documentType,module,fileName);
		
          if (collectionExistFlag) {
			  filefullPath = getUploadDirPathForCollection(documentType,module);
			  filefullPath = filefullPath+fileName;
		   } else {
			  filefullPath = getUploadDirPathForSingle(documentType,module);
			  filefullPath = filefullPath+fileName;
			 }
		
		return filefullPath;
	}
	
    public static String getUploadDirPathForCollection(String type,String module){ 
        return ServerConfig.studyMaterialPath+"collect/"+module+"/"+type+"/";
    }
    
    public static String getUploadDirPathForSingle(String type,String module){
        return  ServerConfig.studyMaterialPath+module+"/"+type+"/";
    }
	
    public static boolean isCollectionStudyMaterial(String type,String module,String inputFilename) {

		String collection = getUploadDirPathForCollection(type,module);
		File file = new File(collection+inputFilename);
		return file.exists()?Boolean.TRUE:Boolean.FALSE;
	}
    
	public static String getStudyMaterialTypeDirectoryName(String annType) {

		String dirType = null;

		if ((annType.equalsIgnoreCase("TL"))
				|| (annType.equalsIgnoreCase("TW"))
				|| (annType.equalsIgnoreCase("TP"))) {
			dirType = "tl";
		} else if ((annType.equalsIgnoreCase("GD"))
				|| (annType.equalsIgnoreCase("SW"))
				|| (annType.equalsIgnoreCase("SP"))) {
			dirType = "sg";
		} else if (annType.equalsIgnoreCase("MA")) {
			dirType = "ma";
		} else if (annType.equalsIgnoreCase("WB")) {
			dirType = "wb";
		} else if (annType.equalsIgnoreCase("QB")) {
			dirType = "qb";
		} else if (annType.equalsIgnoreCase("MO")) {
			dirType = "mo";
		} else if (annType.equalsIgnoreCase("LB")) {
			dirType = "lb";
		} else if (annType.equalsIgnoreCase("BL")) {
			dirType = "bl";
		} else if (annType.equalsIgnoreCase("BB")) {
			dirType = "bb";
		} else if (annType.equalsIgnoreCase("HL")) {
			dirType = "hl";
		} else if (annType.equalsIgnoreCase("RE")) {
			dirType = "re";
		} else if (annType.equalsIgnoreCase("MG")) {
			dirType = "mg";
		}

		return dirType;
	}

}


