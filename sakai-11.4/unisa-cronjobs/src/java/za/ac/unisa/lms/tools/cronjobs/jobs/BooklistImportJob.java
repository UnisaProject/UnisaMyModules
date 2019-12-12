package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.BooklistImportDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;


public class BooklistImportJob extends SingleClusterInstanceJob implements
		StatefulJob, InterruptableJob, OutputGeneratingJob {
	
	
	/**
	 *
	 * @param path
	 * @param filename
	 */
	public void recommendedBooks()
	      throws Exception{
		InputStream is;
		File file = null;
		BooklistImportDAO db = new BooklistImportDAO();
			
	     try{
	    	String path = "/data/sakai/bibmare/in/";
	    	String errorPath = "/data/sakai/bibmare/out/";
	    	String backup="/data/sakai/bibmare/archive/";
	    	/*   String path = "C:\\data\\sakai\\resourceupload\\";
               String errorPath= "C:\\data\\sakai\\resourceupload\\";
               String backup="/data/sakai/bibmare/archive/";*/
               String importYear="";
	    	 
	    	    // settings for error file
		 		String errorFilename = "Recommended_errorfile.csv";
		 		String filename1 = "sixdigit_recommended_ErrorFile.csv";
		 	    File fileobject = new File(errorPath + errorFilename);
		 		FileWriter fw = new FileWriter(fileobject);
		 		fw.write("Recommended Books Import Error File");
		 	    File fileobject1= new File(errorPath + filename1);
		 	    FileWriter fw1 = new FileWriter(fileobject1);
		 	  try{  
	    	  String filename = "recommended.txt";
	    	  
	    	  //Vijay change: the code added to read the year field from file
	    	  File dir = new File(path);
		  	  String filenamefinal="";
	    	  for (File file2 : dir.listFiles()) {
	    	    if (file2.getName().toLowerCase().endsWith(("recommended.txt"))) {
	    	    	filenamefinal = file2.getName();
	    	    }
	    	  }
	    	  
	    	  if(filenamefinal.length()>0){
	    		  importYear=filenamefinal.substring(0,4);
	    	  }
	    	 
	    	  
	    	  String fullname = path+filenamefinal;
	    	  file = new File(fullname);
		 	  }catch(Exception e){
		 		  e.printStackTrace();
		 	  }
		 	 is = new FileInputStream(file);
	 		 file.length();
	    	   if(file.exists()==true){
	    	    // Get the object of DataInputStream
	    		   System.out.println("if(file.exists()==true"); 
	 		
	    	    DataInputStream in = new DataInputStream(is);
	    	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    	    String strLine;
	    	    
	    	    //Read File Line By Line
	    	    
	    	    String status="R";
	    	 
	    	    // delete the 2011 records.
	    	    db.deleteRecords(status,importYear);
	    	    int counter = 1;
	    	    while ((strLine = br.readLine()) != null)   {
	    	    	if (!strLine.equals("")) {
		    	    	//System.out.println("strLine: "+strLine+"==="+counter);
	    	    		if (counter>1) {
		    	    	  String[]strListtemp;
		    	    	  String[]strListtemp1;
		    	    	  String biblionum = "";
		    	          String bookNR="";
		    	    	  String title = "";
		    	    	  String author="";
		    	    	  String otherAuthor = "";
		    	    	  String isbnTemp = "";
		    	    	  String[] isbnTempList;
		    	    	  String edition = "";
		    	    	  String publisher = "";
		    	    	  String year = "";
		    	    	  String note_to_library = "";
		    	    	  String recommended="";
		    	    	 strLine = strLine.replaceAll("\"", "");
		    	         strListtemp = strLine.split("~");
		    	         
		    	         biblionum = strListtemp[0].replace('\"','\'');
		    	         bookNR = strListtemp[1].replace('\"','\'').trim();
		    	         if(bookNR==null||bookNR.equals("")){
		    	        	 bookNR="0";
		    	         }
		    	         isbnTemp = strListtemp[2].replace('\"','\''); 
		    	         author = strListtemp[3].replace('\"','\'');
		    	         otherAuthor = strListtemp[4].replace('\"','\'');
		    	         title = strListtemp[5].replace('\"','\'');
		    	         
		    	         isbnTempList = isbnTemp.split(";");
		    	         
		    	         edition = strListtemp[6].replace('\"','\'');
		    	         publisher =  strListtemp[7].replace('\"','\'');
		    	         year =  strListtemp[8].replace("^\\s+", "");
		    	         year = year.replace("\\s+$", "");
		    	         note_to_library = strListtemp[9].replace('\"','\'');
		    	    
		    	         
		    	         // delete the 2011 records.
		    	        
		    	    	
		    	    
		    	    	
		    	        
		    	    	boolean valid = true;
		    	    	boolean valid2 = true;
		    	    	for (int i=0; i< isbnTempList.length; i++) {
		    	    		if (isbnTempList[i].length() >= 50) {
		    	    			isbnTempList[i] = isbnTempList[i].substring(0,50);
		    	    			isbnTempList[i] = isbnTempList[i].replace("'","");
		    	    			valid=false;
		    	    		}
		    	    	}
		    	    	boolean valid1 = true;
		    	    	if ((year==null)||(year.equals(""))||(year.length()==0)) {
		    	    		valid1 = false;
		    	    		
		    	    	}
		    	    	
		    	    	if(year.length()>10){
		    	    		year = year.substring(0,10);
		    	    		fw.write("this is the bibliographicNumber "+biblionum+ "~");
		    	    		fw.write("year of publish is too long but was INSERTED: "+year+"\n");
		    	    		
		    	    		
		    	    	}
		    	    	
		    	    	if(edition.length()<=25){
		    	   
		    	    	}  else{
		    	    		edition=edition.substring(0,25);
	    	    			fw.write("this is the bibliographicNumber :"+biblionum+ "~");
	    	    			fw.write("this is the edition length is too long but was INSERTED :"+edition+"\n");
	    	    		}
		    	    	
		    	    	if(valid == false){
		    	    		fw.write("this is the bibliographicNumber :"+biblionum+ "~");
			    			fw.write("ISBN length is too long but was INSERTED"+"\n");
		    	    	}
	
			    	  			    	
		    	    	boolean valid4=true;
			    		boolean valid3 = isInteger(bookNR);
			    		
			    		if(biblionum.length()!=0){			    		
			    		if(valid3==true){
			    			//valid2  = db.checkBooknr(bookNR);
		    	     if(valid1==true){
		    	    	 boolean bibAndBooknrMatchwithDB  = db.checkBookNrAndBibNumMatch(biblionum,bookNR);
		                	if(bibAndBooknrMatchwithDB == true){		    	    	 		
		    	    	 		db.recommendedUpdate(biblionum, title,author, otherAuthor, isbnTempList, edition, publisher, year, note_to_library,bookNR);
		    	    	 	}else{
		    	    	     	// match not found
		                    	//check Bib number in DB
		                    	// for if biblionum not found in db, then update record with matching booknr
		    	    	 		boolean biblioNrExist = db.checkBibNrfromDB(biblionum);	
	                			if(biblioNrExist == true){
	                				// booknr at this stage may be 0 so no update will happen
	                				//if(bookNR.equals("0")||bookNR==null){
	                					// first try to get booknr from the db for this bibnr, if it is 0 then get new booknr
	                					String booknr=db.checkBookNrfromDB(biblionum);
	                					if(booknr.equals("0")){
	                						bookNR=db.getBooknr();
	                					}else{
	                						bookNR=booknr;
	                					}		               	    			
		               	    			
	                				//}
	                				db.recommendedUpdate(biblionum, title,author, otherAuthor, isbnTempList, edition, publisher, year, note_to_library,bookNR);
	                			}else{	                				                 	
	                					bookNR=db.getBooknr();
	                					db.recommendedInsert(biblionum, title, author, otherAuthor, isbnTempList, edition, publisher, year, note_to_library, bookNR);
	                			}
		    	    		}		
		    	       } else{
	    	    		fw.write("this is the bibliographicNumber: "+biblionum+ "~");
	    	    		fw.write("year of publish is null "+"\n");
		    	     }

		    	     //System.out.println(">>>>>>>>>>> BIBNR= "+biblionum+" bookNr= "+bookNR);
		            if((valid3==true)&&(valid1==true)){
	    	    	int Booknumber = Integer.parseInt(bookNR);
		    	     recommended = strListtemp[10].replace('\"','\'');
	    	         strListtemp1 =recommended.split(";");
	    	        int n= strListtemp1.length;
	    	        for(int i=0;i<n;i++){
		    	        
		    	            String record ="";
		    	            String[] tempList;
		    	            String year1="";
		    	            String course = "";
		    	            record = strListtemp1[i];
		    	        	tempList = record.split(" ");
		    	 
		    	        	if (tempList.length > 7) {
			    	        	year1 = tempList[7].replace(".","");
			    	 
			    	        	if(year1.equals(importYear)){
			    	        		course = tempList[4];
			    	        		if(course.length()==7){
			    	        			db.sevendigitInsert(course, year1,Booknumber);
			    	        			db.checkRecordExist(course, year1,Booknumber);
			    	        			db.insertRecommendedPBSAUD(course, year1);
			    	        		}
			    	        		if(course.length()==6){
			    	        			if(db.isCoursecodeValid1(course)==true){
			    	        				String courseCode = db.getCourseCode(course);
			    	        				db.sevendigitInsert(courseCode, year1,Booknumber);
			    	        				db.checkRecordExist(courseCode, year1,Booknumber);
			    	        				db.insertRecommendedPBSAUD(courseCode, year1);			    	        			
			    	        				}
			    	        			else{
			    	        				fw1.write("Recommended Books Import Error File      ");
			    	        				fw1.write("bibliographicNumber is  :"+biblionum+"  this is six digit course code "+course+"\n");
			    	        			}
			    	        		}
			    	        		
			    	        	}
		    	        	} else {
		    	        		// in error file ?
		    	        	}
	    	        }
			    		}
			    		}
	    	        else{
			        	  fw.write("Book Number is not an Integer:  "+bookNR+" and bibliographicNumber "+biblionum+ "\n");
			          }
			    		}else{
			        	  fw.write("bibliographicNumber is empty \n: "+bookNR);
		    	        
			          }
		    	    }
	    	    	   counter++;
	    	    	}
	    	    }
	    	  Runtime.getRuntime().exec("chmod 775 " + fileobject);
	    	  Runtime.getRuntime().exec("chmod 775 " + fileobject1);
	    	    //Close the input stream
	    	    in.close();
	    	    
	    	    // close error file
	    	    fw.flush();
	    		fw.close();
	    		 fw1.flush();
		    	 fw1.close();
	 	   }else{
	 		   System.out.println("there is no recommended file");
	 	   }
	 	    //String backup = "C:\\data\\sakai\\archived\\";
	 	    File dir = new File(backup);
	 	    boolean success = file.renameTo(new File(dir, file.getName()));
	 	    if (!success) {
	 	        System.out.println("file was successfully moved");
	 	    }
	    	    }catch (Exception e){
	    	    	//Catch exception if any
	    	    	 System.err.println("Error with try:Import file may not exists " + e.getMessage());
	    	    }
	    	    
	    	    System.out.println("BooklistImportJob end");
		
		
	}

	

	
	@Override
	public void executeLocked(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			recommendedBooks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}
	    public boolean isInteger( String input )  
	    {  
	       try  
	       {  
	          Integer.parseInt( input );  
	         return true;  
	       }  
	       catch(NumberFormatException e)  
	      {  
	         return false;  
	      }  
	   }  
	/*
	 create DB backup
	 
	    public void createBackup(String status) throws Exception {
			  
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String today = dateFormat.format(date);
		    String filename="crsbs1"+today+".csv";
	    	String pathname="C:\\data\\sakai\\resourceupload\\errorfiles\\";
			File fileobject = new File(pathname+filename);
			FileWriter fw = new FileWriter(fileobject);
			   ActionMessages messages = new ActionMessages();
			String seperatewith = "";
			BooklistImportDAO db = new BooklistImportDAO();
			BooklistForm booklistForm= new BooklistForm();
			
		    List list = db.backupOfCrsbs1(status);
		    booklistForm.setExportList(list);
		     seperatewith = "\t";
		     List siteInfo = (List)list;	

			 fw.write("COURSENR"+seperatewith+" BOOKNR "+seperatewith+" COURSE_LANGUAGE "+seperatewith+" BOOK_STATUS"+seperatewith+" PRIORITY "+seperatewith+" NOTES "+seperatewith+" YEAR "+seperatewith+"LAST_PNO"+seperatewith+" LAST_DATE "+seperatewith+" LAST_DATE "+seperatewith+" LAST_DATE "+seperatewith+" ESCALATION "+seperatewith+" CONFIRM "+seperatewith+" LAST_DATE "+seperatewith+" FROM_PERIOD "+seperatewith+"  TO_PERIOD"+seperatewith+"ACCEPTABLE_EDITION "+seperatewith+" \n");
				for(int i=0; i < siteInfo.size(); i++) {
			    fw.write(((Crsbs1Details)siteInfo.get(i)).getCoursenr()+seperatewith);		
				fw.write(((Crsbs1Details)siteInfo.get(i)).getBooknr()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getCourse_language()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getBookstatus()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getPrioroty()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getNotes()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getYear()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getLast_pno()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getLast_date()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getEscalation()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getConfirm()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getFrom_peroid()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getTo_period()+seperatewith);
				fw.write(((Crsbs1Details)siteInfo.get(i)).getAcceptable_edition()+seperatewith+"\n");
			
				
			
		
				}
			fw.flush();
			fw.close();
		}
	    public void createBackup1() throws Exception {
			  
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = new Date();
		    String today = dateFormat.format(date);
		    String filename="books1"+today+".csv";
	    	String pathname="C:\\data\\sakai\\resourceupload\\errorfiles\\";
			File fileobject = new File(pathname+filename);
			FileWriter fw = new FileWriter(fileobject);
			   ActionMessages messages = new ActionMessages();
			String seperatewith = "";
			BooklistImportDAO db = new BooklistImportDAO();
			BooklistForm booklistForm= new BooklistForm();
			
		    List list = db.backupOfBooks1();
		    booklistForm.setExportList1(list);
		     seperatewith = "\t";
		     List siteInfo = (List)list;	

			 fw.write(" BOOKNR"+seperatewith+"SUPPLIERNR"+seperatewith+"TITLE"+seperatewith+"AUTHOR"+seperatewith+"ISBN"+seperatewith+"EDITION"+seperatewith+"YEAR_OF_PUBLISH"+seperatewith+"TYPE_OF_BINDING"
                       +seperatewith+"PRICE"+seperatewith+"CURRENCY"+seperatewith+"DEWEY_NUMBER"+seperatewith+"NOTES"+seperatewith+"PUBLISHER"+seperatewith+"STOCK_LEVEL"+seperatewith+"BRN"+seperatewith+"CONFIRMED"+seperatewith+"FROM_PERIOD"+seperatewith+"TO_PERIOD"+seperatewith+
                       "IS_PUBLISHED"+seperatewith+"OTHER_AUTHORS"+seperatewith+"OTHER_AUTHORS"+seperatewith+"NOTE_TO_LIBRARY"+seperatewith+"AVAIL_AS_EBOOK"+seperatewith+"EBOOK_VOLUME"+seperatewith+"EBOOK_PAGES"+seperatewith+"URL"+seperatewith+
                       "MC_AVAIL"+seperatewith+"MC_FORMAT"+seperatewith+"BIBLIOGRAPHICNR"+seperatewith+"ISBN1"+seperatewith+"ISBN2"+seperatewith+"ISBN3"+seperatewith+"\n");
				for(int i=0; i < siteInfo.size(); i++) {
			    fw.write(((Books1Details)siteInfo.get(i)).getBooks1record()+"\n");		
			
			
		
				}
			fw.flush();
			fw.close();
		}
	    */
	    
}
