package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import  za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class SupervisorFileWriter{
	public void writeFile(List<SupervisorListRecord> records, String fileName,String countryCode){
		 //Create file 
		 try {
			 File file=new File(fileName);
			 FileOutputStream out = new FileOutputStream(file);
			 PrintStream ps = new PrintStream(out);
			 String headingLine = "Name~Cell_Number~Email~Contract_Start~Contact_End~"+
			                      "Students_Allowed~Student_Allocated~Provincer~District";				 
			 ps.print(headingLine);
			 ps.println();
			 for (int i=0; i<records.size(); i++){
				 SupervisorListRecord record = new SupervisorListRecord();
				 record = (SupervisorListRecord)records.get(i);
				 String line = getLine(record,countryCode); 			 
				 ps.print(line);
				 ps.println(); 		
			 }
			 ps.flush();
			 ps.close();
		 } catch (Exception e) {}		
	}
	
	private String getLine(SupervisorListRecord record,String countryCode){
		              String prov="";
		              String district="";
		              if(countryCode.equals(PlacementUtilities.getSaCode())){
		            	  prov=record.getProvince();
		            	  district=record.getDistrict();
		              }
		              String line = record.getName() + '~' + record.getCellNumber()+ '~' +record.getEmailAddress() + 
		                            '~' + record.getContractStart() + '~' + record.getContractEnd() + '~' +
		                            record.getStudentsAllowed() + '~' + record.getStudentsAllocated() + '~' + prov + 
		                            '~' + district;	
		              return line;
	}
	
}
