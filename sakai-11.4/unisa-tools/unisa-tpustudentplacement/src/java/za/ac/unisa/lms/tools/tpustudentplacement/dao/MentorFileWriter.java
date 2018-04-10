package za.ac.unisa.lms.tools.tpustudentplacement.dao;

	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.PrintStream;
	import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorModel;
import  za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

	public class MentorFileWriter {
		public void writeFile(List<MentorModel> records, String fileName,String countryCode){
			 //Create file 
			 try {
				 File file=new File(fileName);
				 FileOutputStream out = new FileOutputStream(file);
				 PrintStream ps = new PrintStream(out);
				 String headingLine = "Name~Cell_Number~Email~Landline~Province~District";				 
				 ps.print(headingLine);
				 ps.println();
				 for (int i=0; i<records.size(); i++){
					 MentorModel record = new MentorModel();
					 record = (MentorModel)records.get(i);
					 String line = getLine(record,countryCode); 			 
					 ps.print(line);
					 ps.println(); 		
				 }
				 ps.flush();
				 ps.close();
			 } catch (Exception e) {}		
		}
		
		private String getLine(MentorModel record,String countryCode){
			              String prov="";
			              String district="";
			              if(countryCode.equals(PlacementUtilities.getSaCode())){
			            	  prov=record.getProvinceName();
			            	  district=record.getDistrictName();
			              }
			              String line = record.getName()+ '~' + record.getCellNumber()+ '~' +record.getEmailAddress() + 
			                            '~' + record.getPhoneNumber() + '~' + prov + '~' + district;	
			              return line;
		}
		}
