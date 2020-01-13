
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

public class BooklistEreservesImportJob extends SingleClusterInstanceJob implements
            StatefulJob, InterruptableJob, OutputGeneratingJob {

      
      public void ereservesBooks()  throws Exception{
            
          InputStream is;
            File file = null;
            BooklistImportDAO db = new BooklistImportDAO();
           try{
               String path = "/data/sakai/bibmare/in/";
                  String errorpath = "/data/sakai/bibmare/out/";
                  String backup="/data/sakai/bibmare/archive/";
               
                /* String path = "C:\\data\\sakai\\resourceupload\\";
                  String errorpath= "C:\\data\\sakai\\resourceupload\\";
                  String backup="/data/sakai/bibmare/archive/";*/
            // settings for error file
                 String errorFilename = "EReserves_ErrorFile.csv";
                 String filename1 = "6digit_ereserves_ErrorFile.csv";
               File fileobject = new File(errorpath + errorFilename);
                 FileWriter fw = new FileWriter(fileobject);
                 fw.write("Ereserves Books Import Error File");
               File fileobject1= new File(errorpath + filename1);
               FileWriter fw1 = new FileWriter(fileobject1);
               String importYear="";
               try{
                 String filename = "ereserves.txt";
                    
           	  File dir = new File(path);
		  	  String filenamefinal="";
	    	  for (File file2 : dir.listFiles()) {
	    	    if (file2.getName().toLowerCase().endsWith(("ereserves.txt"))) {
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
               } // end try
                 is = new FileInputStream(file);
                 if(file.exists()==true){
                       System.out.println("if(file.exists()==true");
                // Get the object of DataInputStream
                DataInputStream in = new DataInputStream(is);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                //Read File Line By Line
                String status="E";
                //backup
            
                db.deleteRecords(status,importYear);
                int counter = 1;
                while ((strLine = br.readLine()) != null)   {
                  if(counter>1){
                     String[]strListtemp;
                   String[] strListtemp1;
                    String biblionum = "";
                    String bookNR = "";
                    String title = "";
                    String author = "";
                    String otherAuthor = "";
                    String publisher = "";
                    String ebook_pages="";
                    String ebook_volume ="";
                    String year_publishtemp = "";
                    String year_publish = "";
                    String notes="";
                    String url="";
                    String ereserves = "";
                    String eReserveType="";
                
 

                      //String title1 = title.replace("'","");
                      //strLine.replace("\"","");
                     strListtemp = strLine.split("~");
                     biblionum = strListtemp[0].replaceAll("\\\"","");
                     bookNR = strListtemp[1].replaceAll("\\\"","").trim();
                     if(bookNR==null||bookNR.equals("")){
                         bookNR="0";
                     }
                     title = strListtemp[2].replaceAll("\\\"","");
                     author = strListtemp[3].replaceAll("\\\"","");
                     otherAuthor = strListtemp[4].replaceAll("\\\"","");
                     publisher = strListtemp[5].replaceAll("\\\"","");
                     ebook_pages = strListtemp[6].replaceAll("\\\"","");
                     ebook_volume = strListtemp[7].replaceAll("\\\"","");
                     year_publishtemp = strListtemp[8].replaceAll("\\\"","");
                     if(year_publishtemp.length()>10){
                   
                               fw.write("bibliographicNumber is  :"+biblionum+" ~ "+"and the year is   :"+year_publishtemp+"\n");
                     }
                     if((year_publishtemp.length()==0)||year_publishtemp.equals("")){
                         year_publish = "0000";
                     }else{
                         year_publish = year_publishtemp;
                     }
                       notes = strListtemp[9].replaceAll("\\\"","");
                      // url=strListtemp[11].replaceAll("\\\"","");
                       
                       
                     //Library impost file uses several types of ereserves, but on myunisa only 3
                       
                       eReserveType = strListtemp[13].replaceAll("\\\"","");
                       //System.out.println("eReserveType "+eReserveType);
                       
                       if(eReserveType.equalsIgnoreCase("pr") || eReserveType.equalsIgnoreCase("in") ){
                                 eReserveType="J";
                       }else if(eReserveType.equalsIgnoreCase("lr") || eReserveType.equalsIgnoreCase("ac")){
                                 eReserveType="L";
                       }else if(eReserveType.equalsIgnoreCase("ch") || eReserveType.equalsIgnoreCase("to") || eReserveType.equalsIgnoreCase("eb") || eReserveType.equalsIgnoreCase("gp")){
                                 eReserveType="B";
                       }else{
                                 eReserveType = "";
                       }
                       
                       //System.out.println("eReserveType after change"+eReserveType);
                       
                       //check the bib number in the database-also check the booknr for that bib number If the booknr(DB) matches
                       //with that book nr update the record else if book nr(db) not the same as booknr in import, create new record
                      boolean valid=isInteger(bookNR);
                      
                      //eReserveType is type of eReserve used from 2016 booklist. Here it is hardcoded to type "J" for 2015 import
                     // String eReserveType="J";
                      //check booknr for the bib number and update the books1 with bibnr and booknr
                      if(biblionum.length()!=0){
                        int count = 0;
                            if(valid==true){
                              boolean bibAndBooknrMatchwithDB  = db.checkBookNrAndBibNumMatch(biblionum,bookNR);
                              if(bibAndBooknrMatchwithDB == true){
                                    // match found
                                  db.ereservesUpdate(biblionum, title, author, otherAuthor, publisher, ebook_pages, ebook_volume , year_publish, notes, url,bookNR,eReserveType);
                                } else {
                                    // match not found
                                    //check Bib number in DB
                                    // for if biblionum not found in db, then update record with matching booknr
                                    boolean biblioNrExist = db.checkBibNrfromDB(biblionum);
                                    
                                    if(biblioNrExist == true){
                                          String booknr=db.checkBookNrfromDB(biblionum);
                                          // booknr at this stage may be 0 so no update will happen
                                    //if(bookNR.equals("0")||bookNR==null){
                                          // first try to get booknr from the db for this bibnr, if it is 0 then get new booknr
                                          
                                          if(booknr.equals("0")){
                                                bookNR=db.getBooknr();
                                          }else{
                                                bookNR=booknr;
                                          }                                                                                         
                                    //}
                                          db.ereservesUpdate(biblionum, title, author, otherAuthor, publisher, ebook_pages, ebook_volume , year_publish, notes, url,bookNR,eReserveType);     
                                    } else {                            
                                          bookNR=db.getBooknr();
                                          db.ereservesInsert(biblionum, title, author,otherAuthor, publisher, ebook_pages, ebook_volume ,year_publish,notes,url,bookNR,eReserveType);
                                    }
                              } // end if(bibAndBooknrMatchwithDB == true){
                        
                              
//                            if(valid==true){
                              int Booknumber = Integer.parseInt(bookNR);
                              ereserves = strListtemp[12];
                               strListtemp1 =ereserves.split(";");
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
                                                            db.sevendigitInsert1(course, year1,Booknumber);
                                                            db.checkRecordExist1(course, year1,Booknumber);
                                                            db.insertRecommendedPBSAUD1(course, year1);
                                                      }
                                                      if(course.length()==6){
                                                            if(db.isCoursecodeValid1(course)==true){
                                                                  String courseCode = db.getCourseCode(course);
                                                                  db.sevendigitInsert1(courseCode, year1,Booknumber);
                                                                  db.checkRecordExist1(courseCode, year1,Booknumber);
                                                                  db.insertRecommendedPBSAUD1(courseCode, year1);
                                                            }else{
                                                                  fw1.write("EReserves Books Import Error File");
                                                                  fw1.write("bibliographicNumber is  :"+biblionum+"    this is six digit course code  "+course+"\n");
                                                           }
                                                      }
                                                      count++;
                                                }
                                         } // end if (tempList.length > 7) {
                                    } // end for(int i=0;i<n;i++){
                              } // if(valid==true){
                    
                                          if(count==0){
                                                fw.write(" EReserves  error file  ");
                                                fw.write("bibliographicNumber is  :"+biblionum+ "~"+"  no current year found for course info \n");
                                          }
                        }else{
                        fw.write("bibliographicNumber is empty \n");
                  } // end if(biblionum.length()!=0){
                  } // if(counter>1){
                counter++;
             } // while
                  Runtime.getRuntime().exec("chmod 775 " + fileobject);
                    Runtime.getRuntime().exec("chmod 775 " + fileobject1);
                //Close the input stream
                in.close();
                fw.flush();
                  fw.close();
                  fw1.flush();
                  fw1.close();
               }else{
                     System.out.println("if(file.exists()== false");
               }  // if(file.exists()==true){
                //String backup = "C:\\data\\sakai\\archived\\";
                     File dir = new File(backup);
                     boolean success = file.renameTo(new File(dir, file.getName()));
                           if (success) {
                               System.out.println("file was successfully moved");
                           }
                }catch (Exception e){
                  //Catch exception if any
                  System.err.println("Error with try:Import file may not exists " + e.getMessage());
                }
           System.out.println("BooklistEreservesImportJob end");

      } // end public void ereservesBooks()


      @Override
      public void executeLocked(JobExecutionContext arg0)
                  throws JobExecutionException {
            // TODO Auto-generated method stub
            try {
                  ereservesBooks();
            } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            
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
      public String getOutput() {
            // TODO Auto-generated method stub
            return null;
      }
} // end public class
