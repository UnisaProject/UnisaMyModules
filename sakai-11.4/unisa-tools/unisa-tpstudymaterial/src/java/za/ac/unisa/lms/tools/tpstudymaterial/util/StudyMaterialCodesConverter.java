package za.ac.unisa.lms.tools.tpstudymaterial.util;

import java.util.HashMap;

public class StudyMaterialCodesConverter {

	public StudyMaterialCodesConverter() {
		      setCodesHashMap();
	}
	 private HashMap<String, String> codesHashMap = new HashMap<String, String>();
	 private void  setCodesHashMap(){
         codesHashMap = new HashMap<String, String>();
         codesHashMap.put("bb", "Business Calculations");
         codesHashMap.put("bl", "Booklet");
         codesHashMap.put("hl", "H Law Documents");
         codesHashMap.put("lb", "Logbook");
         codesHashMap.put("ma", "Florida Manual");
         codesHashMap.put("mg", "Mentor Guide");
         codesHashMap.put("mo", "Module");
         codesHashMap.put("qb", "Question Bank");
         codesHashMap.put("re", "Reader");
         codesHashMap.put("sg", "Study Guide");
         codesHashMap.put("sw", "Study Guide (MS Word)");
         codesHashMap.put("tl", "Tutorial Letter");
         codesHashMap.put("tw", "Tutorial Letter (MS Word)");
         codesHashMap.put("tp", "Tutorial Letter");
         codesHashMap.put("wb", "Workbook");
         codesHashMap.put("a", "Afr");
         codesHashMap.put("e", "Eng");
         codesHashMap.put("b", "Both");
         codesHashMap.put("qp", "Examination Question Paper");
         codesHashMap.put("dv","DV");
         codesHashMap.put("dv","DV");
         codesHashMap.put("cm","CM");
  }
	 public String convertCode(String studyMaterialCode){
		              if(studyMaterialCode.equalsIgnoreCase("GD")){
		            	  studyMaterialCode="sg";
		              }
		               if((studyMaterialCode!=null)&&codesHashMap.containsKey(studyMaterialCode.toLowerCase())){
		            	  return codesHashMap.get(studyMaterialCode.toLowerCase());
		              }else{
		            	  return "Tutorial Letter";
		              }
    }
}
