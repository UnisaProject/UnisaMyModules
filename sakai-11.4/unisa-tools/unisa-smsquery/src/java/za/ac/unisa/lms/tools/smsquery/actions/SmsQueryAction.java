package za.ac.unisa.lms.tools.smsquery.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ListIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.tools.smsquery.SmsQueryDAO.*;
import za.ac.unisa.lms.tools.smsquery.forms.*;
import za.ac.unisa.lms.tools.smsquery.utils.*;

public class SmsQueryAction extends LookupDispatchAction{
			@Override
	protected Map getKeyMethodMap(){
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("button.display","displayList");
		map.put("button.first", "displayFirstHundred");
		map.put("button.next", "displayNext");
		map.put("button.previous", "displayPrevious");
		map.put("inputSmsBatchSearch","inputSmsBatchSearch");
		map.put("getLogEntryDetail", "getLogEntryDetail");
		map.put("button.cancel", "cancel");
		return map;
	}
		
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    SmsQueryForm smsForm = (SmsQueryForm)form;
		    resetVars(form);
		    smsForm.setCurrentPage("startPage");	
		    return mapping.findForward("startPage");
	}
	public String startPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    resetVars(form);
		    SmsQueryForm smsForm = (SmsQueryForm)form;
		    smsForm.setCurrentPage("startPage");	
		    return "startPage";
	}
	public void resetVars(ActionForm form){
				  SmsQueryForm smsForm = (SmsQueryForm) form;
				  smsForm.setSearchBatchNumber("");
 		          smsForm.setSearchCellNumber("");
		          smsForm.setSearchStudentNumber("");
		          smsForm.setStudentNumber("");
		          smsForm.setSearchPersonnelNumber("");
		          smsForm.setSearchResponsibilityCode("");
		          smsForm.setSearchFromDate("");
		          smsForm.setSearchToDate("");
		          smsForm.setSentSms("");
		          smsForm.setView("All items");
		          smsForm.setSearchResponsibilityCode("");
		          smsForm.setSearchPersonnelNumber("");
		          smsForm.setMessage("");
		          smsForm.setSearchPersonnelNumber("");
		          smsForm.setNextStartPos(0);
		          smsForm.setCurrentPage("startPage");
	}
	public ActionForward getLogEntryDetail(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			SmsQueryForm smsForm = (SmsQueryForm) form;
			MtnSmsStatusDAO   dao=new MtnSmsStatusDAO();
			SmsBatchDAO batchdao=new SmsBatchDAO(); 
			String selectedBatchNumber=request.getParameter("batchNr");
		    String selectedSeqNumber=request.getParameter("sequenceNr");
		    String message=dao.getMessage(selectedBatchNumber,selectedSeqNumber);
		    PersonnelClass person=dao.getSenderDetails(selectedBatchNumber,selectedSeqNumber);
		    String title=person.getTitle();
		    String initials=person.getInitial();
		    String surname=person.getSurname();
		    if(title==null)
		    	 title="";
		    if(initials==null)
		    	 initials="";
		    if(surname==null)
		    	surname="";
		    String name=title+" "+initials+" "+surname;
		    smsForm.setSenderName(name);
		    smsForm.setSenderEmail(person.getEmail());
		    smsForm.setSenderPhoneNumber(person.getTelNumber());
			smsForm.setBatchNumber(selectedBatchNumber);
			int sysgc26= batchdao.getFROM_SYSTEM_GC26_field(selectedBatchNumber,message);
			int reasongc27=batchdao.getREASON_GC27_field(selectedBatchNumber,message);
			if((sysgc26==3)&&(reasongc27==11)){
				    smsForm.setMessage("Message blocked.  MyLife password sms.");
	        }else{
		            smsForm.setMessage(message);
	        }
			smsForm.setSmsStatus(person.getMessageStatus());
		    smsForm.setSmsStatusDesc(person.getDescription());		
		
		return mapping.findForward("displayLogEntryDetail");
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				SmsQueryForm smsForm = (SmsQueryForm) form;	
				    if(smsForm.getCurrentPage().equalsIgnoreCase("logEntryDetail")){
				    	      smsForm.setCurrentPage(smsForm.getPrevPage());
			        	      initializeForNextPage(smsForm.getCurrentPage(),smsForm);
			        	      String  nextPage=determineNextPageForCancel(mapping,form,request,response);
			        	      return mapping.findForward(nextPage);
			      	}else {
			      		     smsForm.setCurrentPage("startPage");
			      		     smsForm.setDisplayList(new ArrayList());
			      		     smsForm.setBatchesList(new ArrayList());
			      		     resetVars(form);
			      		     return mapping.findForward("startPage");
			      	}
	 }
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
	private void initializeForNextPage(String  prevPage,SmsQueryForm smsForm){
                		//it is called after the cancel btn has been pressed and user is in the logEntryDetail page
		                if (smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectePage")){
		                	   smsForm.setSearchBatchNumber("");
		                	   smsForm.setSearchStudentNumber("");
                        }else if (smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectePage")){
                        	       smsForm.setSearchBatchNumber("");
                        	       smsForm.setSearchCellNumber("");
                        }else if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectePage")){
                        	     smsForm.setSearchStudentNumber("");
                        	     smsForm.setSearchCellNumber("");
                        }
		                modifyDatesToYYYYMMDD(smsForm);
    }
	//display 1-100 btn
	public String displayBatchListOneToHundred(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getFirstHundredItems(mapping,form,"batchListPage"); 
	}
	public String displayStuNumOneToHundred(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getFirstHundredItems(mapping,form,"stuNumSelectedPage"); 
	}
	public String displayCellNumOneToHundred(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			    return getFirstHundredItems(mapping,form,"cellNumSelectedPage"); 
	}
	public String displayBatchNumOneToHundred(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			return getFirstHundredItems(mapping,form,"batchNumSelectedPage"); 
	}
	//next   btn
	public String batchListPageNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getNextItems(mapping,form,"batchListPage"); 
	}
	public String stuNumSelectedPageNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getNextItems(mapping,form,"stuNumSelectedPage"); 
	}
	public String cellNumSelectedPageNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			    return getNextItems(mapping,form,"cellNumSelectedPage"); 
	}
	public String batchNumSelectedPageNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			return getNextItems(mapping,form,"batchNumSelectedPage"); 
	}
	
	//prev btn
	
	public String batchListPagePrev(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getPrevItems(mapping,form,"batchListPage");
	}
	
	public String stuNumSelectedPagePrev(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getPrevItems(mapping,form,"stuNumSelectedPage");
	}
	public String cellNumSelectedPagePrev(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				return getPrevItems(mapping,form,"cellNumSelectedPage");
	}
	
	public String batchNumSelectedPagePrev(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			 return getPrevItems(mapping,form,"batchNumSelectedPage");
	}
	
	//displaying existing display list
	public String stuNumSelectedPageForCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			           SmsQueryForm smsForm = (SmsQueryForm) form;
			           ActionMessages messages = new ActionMessages();
			           return displayExistingDisplayList(mapping,form,"stuNumSelectedPage");
	}
	public String batchNumSelectedPageForCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			           SmsQueryForm smsForm = (SmsQueryForm) form;
			           ActionMessages messages = new ActionMessages();
			           return displayExistingDisplayList(mapping,form,"batchNumSelectedPage");
	}
	public String cellNumSelectedPageForCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			           SmsQueryForm smsForm = (SmsQueryForm) form;
			           ActionMessages messages = new ActionMessages();
			           return displayExistingDisplayList(mapping,form,"cellNumSelectedPage");
	}
	public String batchListPageForCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			           SmsQueryForm smsForm = (SmsQueryForm) form;
			           ActionMessages messages = new ActionMessages();
			           return displayExistingDisplayList(mapping,form,"batchListSelectedPage");
	}
	public String stuNumSelectedPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			           SmsQueryForm smsForm = (SmsQueryForm) form;
			           ActionMessages messages = new ActionMessages();
			           smsForm.setSearchCellNumber("");
			           if(!smsForm.isStuNumFirstEntry()){
	                          boolean validInput=validateInput(mapping,form,request,response,messages);
	                          if(!validInput){
	                        	  addErrors(request,messages);
	                        	  return "stuNumSelectedPage";	
	                          }
	                   }else{
		                 	setDefaultDates(smsForm);
		                 	smsForm.setStuNumFirstEntry(false);
		               }
			           SmsDAO dao=new SmsDAO(); 
			           String resCode=smsForm.getSearchResponsibilityCode();
			           String perno=smsForm.getSearchPersonnelNumber();
			           String fromDate=smsForm.getSearchFromDate();
	                   String toDate=smsForm.getSearchToDate();
	                   String stuNum=smsForm.getSearchStudentNumber();
	                   String view=smsForm.getView();
	                   String filterStr=smsForm.getFilterMessage();
	                   ArrayList<Sms> dataList=dao.getSmsWithStuNum(stuNum,view,resCode,perno,fromDate,toDate,filterStr);
	                   smsForm.setBatchesList(dataList);
	                   if(!dataList.isEmpty()){
	  	        	     Sms  firstObj=(Sms)dataList.get(0);
	  	        	     smsForm.setSearchCellNumber(firstObj.getCellNumber());
	  	           }else{
	  	        	   smsForm.setNextStartPos(0);
	  	           }
                   return getFirstHundredItems(mapping,form,"stuNumSelectedPage");
	}
	public String cellNumSelectedPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			   SmsQueryForm smsForm = (SmsQueryForm) form;
			   ActionMessages messages = new ActionMessages();
			   smsForm.setSearchStudentNumber("");
	           if(!smsForm.isCellNumFirstEntry()){
                      boolean validInput=validateInput(mapping,form,request,response,messages);
                      if(!validInput){
                    	  addErrors(request,messages);
                    	  return "cellNumSelectedPage";	
                      }
               }else{
                    	setDefaultDates(smsForm);
                    	smsForm.setCellNumFirstEntry(false);
               }
	           SmsDAO dao=new SmsDAO(); 
			   String resCode=smsForm.getSearchResponsibilityCode();
			   String perno=smsForm.getSearchPersonnelNumber();
			   String fromDate=smsForm.getSearchFromDate();
	           String toDate=smsForm.getSearchToDate();
	           String cellNum=smsForm.getSearchCellNumber();
	           String view=smsForm.getView();
	           String filterStr=smsForm.getFilterMessage();
	           ArrayList batchList=dao.getSmsWithCellNum(cellNum,view,resCode,perno,fromDate,toDate,filterStr);
	           smsForm.setBatchesList(batchList);
	           if(!batchList.isEmpty()){
	        	     Sms  firstObj=(Sms)batchList.get(0);
	        	     smsForm.setSearchStudentNumber(firstObj.getRefNumber());
	           }else{
	        	   smsForm.setNextStartPos(0);
	           }
	           smsForm.setBatchesList(batchList);
	           return getFirstHundredItems(mapping,form,"cellNumSelectedPage");
	}
	public String batchNumSelectedPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			 SmsQueryForm smsForm = (SmsQueryForm) form;
			 ActionMessages messages = new ActionMessages();
			    if(!smsForm.isBatchNumFirstEntry()){
	        	    boolean validInput=validateInput(mapping,form,request,response,messages);
                    if(!validInput){
                  	  addErrors(request,messages);
                  	  return "batchNumSelectedPage";
                    }
                }else{
                	setDefaultDates(smsForm);
                	smsForm.setBatchNumFirstEntry(false);
                }
	            SmsDAO dao=new SmsDAO(); 
			    String resCode=smsForm.getSearchResponsibilityCode();
			    String perno=smsForm.getSearchPersonnelNumber();
			    String batchNum=smsForm.getSearchBatchNumber();
	            String stuNum=smsForm.getStudentNumber();
	            String view=smsForm.getView();
	            String filterStr=smsForm.getFilterMessage();
	            ArrayList batchList=dao.getSmsForBatchNum(view,resCode,perno,stuNum,batchNum);
	            if(batchList==null){
	            	batchList=new ArrayList();
	            }
	            smsForm.setBatchesList(batchList);
	            if(!batchList.isEmpty()){
	        	     Sms  firstObj=(Sms)batchList.get(0);
	        	     smsForm.setDateSent(firstObj.getSentOn());
	        	     smsForm.setMessage(firstObj.getMessage());
	            }else{
		        	   smsForm.setNextStartPos(0);
		         }
	            return getFirstHundredItems(mapping,form,"batchNumSelectedPage");
	}
	public String batchListPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ActionMessages messages = new ActionMessages();
			   SmsQueryForm smsForm = (SmsQueryForm) form;
			   if(!smsForm.isBatchListFirstEntry()){
			            boolean batchInputValid= validateBatchInput(mapping,form,request,response,messages,smsForm);
			            if(!batchInputValid){
			               	   addErrors(request,messages);
			               	   return "batchListPage";
			            }
			   }
			   SmsBatchDAO dao=new SmsBatchDAO(); 
			   String resCode=smsForm.getSearchResponsibilityCode();
			   String perno=smsForm.getSearchPersonnelNumber();
			   String fromDate=smsForm.getSearchFromDate();
	           String toDate=smsForm.getSearchToDate();
	           ArrayList dataList=dao.getBatches(resCode,perno,fromDate,toDate);
	           smsForm.setBatchesList(dataList);
	           if(dataList.isEmpty()){
	        	   smsForm.setNextStartPos(0);
	           }
	           return getFirstHundredItems(mapping,form,"batchListPage");
	}
	public String getFirstHundredItems(
	          ActionMapping mapping,
	          ActionForm form,
	          String toPage) throws Exception {
	              SmsQueryForm smsForm = (SmsQueryForm) form;
	              ArrayList dataList=smsForm.getBatchesList();
	              if(!dataList.isEmpty()){
	            	     smsForm.setNextStartPos(0);
	            	     int nxtStartPosBefore=smsForm.getNextStartPos();
	            		 ArrayList displayList=getNextFirstHundrdItems(dataList,smsForm);
                         int nxtStartPos=smsForm.getNextStartPos();
                         int listSize=dataList.size();
	                     if(nxtStartPos>=listSize){
		        	            smsForm.setPageUpFlag("N");
		                 }else{
		        	            smsForm.setPageUpFlag("Y");
		                 }
	                     System.out.println("  "+nxtStartPosBefore);
	                     System.out.println(""+nxtStartPos);
	                     smsForm.setPageDownFlag("N");	    
                         smsForm.setDisplayList(displayList);
                 }else{
              	   smsForm.setDisplayList(new ArrayList());   
                 }
	              smsForm.setCurrentPage(toPage);
	              return toPage;
    }
	public String displayExistingDisplayList(
	          ActionMapping mapping,
	          ActionForm form,
	          String toPage) throws Exception {
	              SmsQueryForm smsForm = (SmsQueryForm) form;
	              smsForm.setCurrentPage(toPage);
	              return toPage;
   }
	public String getNextItems(
			          ActionMapping mapping,
			          ActionForm form,
			          String toPage) throws Exception {
			              SmsQueryForm smsForm = (SmsQueryForm) form;
			              ArrayList dataList=smsForm.getBatchesList();
			              if(!dataList.isEmpty()){
			            		 int nxtStartPos=smsForm.getNextStartPos();
			            		 if(nxtStartPos<100){
			            			 smsForm.setNextStartPos(100);
		                         }
	                             ArrayList displayList=getNextHundrdItems(dataList,smsForm);
	                             int nxtStartPosAfter=smsForm.getNextStartPos();
	                             int listSize=dataList.size();
			                     if(nxtStartPosAfter>=listSize){
				        	            smsForm.setPageUpFlag("N");
				                 }else{
				        	            smsForm.setPageUpFlag("Y");
				                 }
			                     System.out.println("  "+nxtStartPos);
			                     System.out.println("  "+nxtStartPosAfter);
			                     if(nxtStartPos>100){
			                    	     smsForm.setPageDownFlag("Y");
			                     }else{
			                    	     smsForm.setPageDownFlag("N");	    
		                         }
			                     smsForm.setDisplayList(displayList);
	                       }else{
	                    	   smsForm.setDisplayList(new ArrayList());   
	                       }
			              smsForm.setCurrentPage(toPage);
			              return toPage;
	}
	public String getPrevItems(
                     ActionMapping mapping,
                     ActionForm form,
                     String toPage) throws Exception {
                       SmsQueryForm smsForm = (SmsQueryForm) form;
                       ArrayList dataList=smsForm.getBatchesList();
                       if(!dataList.isEmpty()){
                    	     int nxtStartPosBefore=smsForm.getNextStartPos();
                             ArrayList displayList=getPrevHundrdItems(dataList,smsForm);
                             int nxtStartPos=smsForm.getNextStartPos();
                             int listSize=dataList.size();
                             if(nxtStartPos>=listSize){
			        	            smsForm.setPageUpFlag("N");
			                 }else{
			        	            smsForm.setPageUpFlag("Y");
			                 }
		                     System.out.println(" "+nxtStartPosBefore);
		                     System.out.println(" "+nxtStartPos);
		                     if(nxtStartPos>99){
		                    	     smsForm.setPageDownFlag("Y");
		                     }else{
		                    	     smsForm.setPageDownFlag("N");	    
	                         }
		                     smsForm.setDisplayList(displayList);
                       }else{
                    	   smsForm.setDisplayList(new ArrayList());   
                       }
                       smsForm.setCurrentPage(toPage);
	                   return toPage;
    }
	private ArrayList  getNextFirstHundrdItems(ArrayList list,SmsQueryForm smsForm){
                              ArrayList subList=new ArrayList();
                              int newStartPos=getHundrdItems(list,subList,"Up",100,0);
                              smsForm.setNextStartPos(newStartPos);
                              return  subList;
    }
	private ArrayList  getNextHundrdItems(ArrayList list,SmsQueryForm smsForm){
		                    //Pre Condition :startPos>=100
		                    int startPos=smsForm.getNextStartPos();
                            ArrayList subList=new ArrayList();
                            int newStartPos=getHundrdItems(list,subList,"Up",100,startPos);
		                    smsForm.setNextStartPos(newStartPos);
		                    return  subList;
	}
	private ArrayList  getPrevHundrdItems(ArrayList list,SmsQueryForm smsForm){
		                     //Pre Condition :startPos>99
		                     int startPos=smsForm.getNextStartPos();
	                         int size=list.size();
	                         int limit=100;
	                         if(startPos>=size){
	                        	 limit=size%100;
	                         }
	                         ArrayList subList=new ArrayList();
		                     int newStartPos=getHundrdItems(list,subList,"Down",limit,startPos);
		                     smsForm.setNextStartPos(newStartPos);
		                     return  subList;
    }
	private int  getHundrdItems(ArrayList list,ArrayList subList, String direction,int limit,int startPos){
		      //direction is either up from the nxtStartPos or down from the nxtStartPos of the list
                   arrayListUtil arrlu=new arrayListUtil();
                   if( direction.equals("Up")){
                	    return arrlu.getNextItems(list,subList,limit,startPos);
                   }else{
                	    return arrlu.getPrevItems(list,subList,limit,startPos);
                   }
                   
    }
	private void modifyDates(SmsQueryForm smsForm){
           		   //expects date of this form yyyymmdd
		            String fromDate=smsForm.getSearchFromDate();
		            String toDate=smsForm.getSearchToDate();
		            if(!fromDate.equals("")&&!toDate.equals("")){
			              fromDate=fromDate.substring(0,4)+"-"+fromDate.substring(4,6)+"-"+fromDate.substring(6);
			              toDate=toDate.substring(0,4)+"-"+toDate.substring(4,6)+"-"+toDate.substring(6);
		            }else{
		            	  dateUtil dateutil=new dateUtil();
		            	  fromDate=dateutil.getPrevDate();
		            	  toDate=dateutil.todayDateStr();
		            }
		            smsForm.setSearchFromDate(fromDate);
		            smsForm.setSearchToDate(toDate);
	}
	private void modifyDatesToYYYYMMDD(SmsQueryForm smsForm){
		         //expects date of this form yyyy-mm-dd
                 String fromDate=smsForm.getSearchFromDate();
                 String toDate=smsForm.getSearchToDate();
                 fromDate=fromDate.substring(0,4)+fromDate.substring(5,7)+fromDate.substring(8);
                 toDate=toDate.substring(0,4)+toDate.substring(5,7)+toDate.substring(8);
                 smsForm.setSearchFromDate(fromDate);
                 smsForm.setSearchToDate(toDate);
    }
	private void setDefaultDates(SmsQueryForm smsForm){
			           String fromDate="";
                       String toDate="";
                	   dateUtil dateutil=new dateUtil();
                 	   fromDate=dateutil.getPrevDate();
        	           toDate=dateutil.todayDateStr();
                       smsForm.setSearchFromDate(fromDate);
                       smsForm.setSearchToDate(toDate);
    }
	public ActionForward inputSmsBatchSearch(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		         dateUtil dateutil=new dateUtil();
			     SmsQueryForm smsForm = (SmsQueryForm) form;
		         smsForm.setSearchFromDate(dateutil.getPrevDate());
     		     smsForm.setSearchToDate(dateutil.dateOnly());
     		     smsForm.setBatchListFirstEntry(false);
     		     smsForm.setBatchesList(new ArrayList());
     		     smsForm.setCurrentPage("batchListPage");
     		     smsForm.setSearchBatchNumber("");
     		     smsForm.setSearchCellNumber("");
     		     smsForm.setSearchStudentNumber("");
		return mapping.findForward("batchListPage");
	}
	private void setToPage(SmsQueryForm smsForm){
		             
		             if(!smsForm.getSearchStudentNumber().equals("")){
		        	      smsForm.setCurrentPage("stuNumSelectedPage");
		        	 }else if(!smsForm.getSearchCellNumber().equals("")){
		        	     smsForm.setCurrentPage("cellNumSelectedPage");
		             }else if(!smsForm.getSearchBatchNumber().equals("")){
		        	    smsForm.setCurrentPage("batchNumSelectedPage");
		             }else if(!smsForm.getCurrentPage().equals("batchListPage")){
		            	 smsForm.setCurrentPage("batchListPage"); 
		             }
		             
	}
	public ActionForward displayList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		        SmsQueryForm smsForm = (SmsQueryForm) form;
		        smsForm.setNextStartPos(0);
		    	String nextPage=determineNextPage(mapping,form,request,response);
		    	return mapping.findForward(nextPage);	
	}
	
	private String determineNextPageForCancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		         SmsQueryForm smsForm = (SmsQueryForm) form;
		         String nextPage="";
	             setToPage(smsForm);
	             if(smsForm.getCurrentPage().equalsIgnoreCase("batchListPage")){
		               nextPage =batchListPageForCancel(mapping, form, request, response);
	              }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectedPage")){
		                nextPage =stuNumSelectedPageForCancel(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectedPage")){
		                 nextPage =cellNumSelectedPageForCancel(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectedPage")){
		                nextPage =batchNumSelectedPageForCancel(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("startPage")){
		                nextPage =startPage(mapping, form, request, response);
	             }
	             return nextPage;
	}
	private String determineNextPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		         SmsQueryForm smsForm = (SmsQueryForm) form;
		         String nextPage="";
	             setToPage(smsForm);
	             if(smsForm.getCurrentPage().equalsIgnoreCase("batchListPage")){
		               nextPage =batchListPage(mapping, form, request, response);
	              }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectedPage")){
		                nextPage =stuNumSelectedPage(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectedPage")){
		                 nextPage =cellNumSelectedPage(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectedPage")){
		                nextPage =batchNumSelectedPage(mapping, form, request, response);
	             }
	             if(smsForm.getCurrentPage().equalsIgnoreCase("startPage")){
		                nextPage =startPage(mapping, form, request, response);
	             }
	             return nextPage;
	}
	public ActionForward displayNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			SmsQueryForm smsForm = (SmsQueryForm) form;
			String nextPage="startPage";
		    if(smsForm.getCurrentPage().equalsIgnoreCase("batchListPage")){
			       nextPage =batchListPageNext(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectedPage")){
			       nextPage =stuNumSelectedPageNext(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectedPage")){
			       nextPage =cellNumSelectedPageNext(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectedPage")){
  			       nextPage =batchNumSelectedPageNext(mapping, form, request, response);
		    }
		return mapping.findForward(nextPage);
	}
	public ActionForward displayFirstHundred(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    	SmsQueryForm smsForm = (SmsQueryForm) form;
		 		String nextPage="";
	        	smsForm.setNextStartPos(0);
	        	if(smsForm.getCurrentPage().equalsIgnoreCase("batchListPage")){
			         nextPage =batchListPageNext(mapping, form, request, response);
		        }
		        if(smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectedPage")){
			         nextPage =stuNumSelectedPageNext(mapping, form, request, response);
		        }
		        if(smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectedPage")){
			         nextPage =cellNumSelectedPageNext(mapping, form, request, response);
		       }
		       if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectedPage")){
  			         nextPage =batchNumSelectedPageNext(mapping, form, request, response);
		       }
		return mapping.findForward(nextPage);
	}
	public ActionForward displayPrevious(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ActionMessages messages = new ActionMessages();
		    SmsQueryForm smsForm = (SmsQueryForm) form;
			String nextPage="";
		    if(smsForm.getCurrentPage().equalsIgnoreCase("batchListPage")){
			      nextPage =batchListPagePrev(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("stuNumSelectedPage")){
			      nextPage =stuNumSelectedPagePrev(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("cellNumSelectedPage")){
			      nextPage =cellNumSelectedPagePrev(mapping, form, request, response);
		    }
		    if(smsForm.getCurrentPage().equalsIgnoreCase("batchNumSelectedPage")){
			      nextPage =batchNumSelectedPagePrev(mapping, form, request, response);
		    }
		return mapping.findForward(nextPage);	
    }
	private int validateStudentNumber(SmsQueryForm smsForm,
			          ActionMessages messages,
			          int numberOfSeachOptionsSet){
	                  if (!smsForm.getSearchStudentNumber().trim().equalsIgnoreCase("")){
                            numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;
                            if (!isInteger(smsForm.getSearchStudentNumber())){
	                               messages.add(ActionMessages.GLOBAL_MESSAGE,
			                          new ActionMessage("message.generalmessage",   
						              "Student/Reference number must be numeric."));
                            }else {
      	                       smsForm.setSearchStudentNumber(String.valueOf(Integer.parseInt(smsForm.getSearchStudentNumber())));
                            }	
                     }
	                  return numberOfSeachOptionsSet; 
    }
	public void  validateSearchOptions(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,ActionMessages messages) throws Exception {
				
		    SmsQueryForm smsForm = (SmsQueryForm) form;
			int numberOfSeachOptionsSet=0;
		    if (!smsForm.getSearchBatchNumber().trim().equalsIgnoreCase("")){
			     numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;
			     if (!isInteger(smsForm.getSearchBatchNumber())){
				         messages.add(ActionMessages.GLOBAL_MESSAGE,
						         new ActionMessage("message.generalmessage",
								  	"Batch Number must be numeric."));
			    }else {
			    	     smsForm.setSearchBatchNumber(String.valueOf(Integer.parseInt(smsForm.getSearchBatchNumber())));
			    }
		   } 
		   if (!smsForm.getSearchCellNumber().trim().equalsIgnoreCase("")){
			            numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;			
		   }
		   numberOfSeachOptionsSet=validateStudentNumber(smsForm,messages,numberOfSeachOptionsSet);
		   if (numberOfSeachOptionsSet==0){
			        messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Enter search data in one of the search option."));
		}
		if (numberOfSeachOptionsSet>1) {			
			        messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Enter search data in one search option only."));
		}
	}
    public  void  validatePersonnnelRespCode(
			                  ActionMapping mapping,
			                  ActionForm form,
			                  HttpServletRequest request,
			                  HttpServletResponse response,ActionMessages messages) throws Exception {
		                         SmsQueryForm smsForm = (SmsQueryForm) form;
	                             if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("") &&
		        	                   !smsForm.getSearchResponsibilityCode().equalsIgnoreCase("")) {
			                               messages.add(ActionMessages.GLOBAL_MESSAGE,
				 	                          new ActionMessage("message.generalmessage",
								                 "The search can only be restricted on the Responsibility code or the Personnel number not both."));
		                         }
		                         if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")){
			                             if (!isInteger(smsForm.getSearchPersonnelNumber())){
				                              messages.add(ActionMessages.GLOBAL_MESSAGE,
						                         new ActionMessage("message.generalmessage",
									             "Personnel number must be numeric."));
			                              }else{
			                    	              smsForm.setSearchPersonnelNumber(String.valueOf((Integer.parseInt(smsForm.getSearchPersonnelNumber()))));
			                              }
			                     }
	   }
	   private  void  validateDate(
               ActionMapping mapping,
               ActionForm form,
               HttpServletRequest request,
               HttpServletResponse response,ActionMessages messages,boolean dateIsRequired) throws Exception {
                      SmsQueryForm smsForm = (SmsQueryForm) form;
                      dateUtil dateutil=new dateUtil();
                      String fromDate=smsForm.getSearchFromDate();
                      String toDate=smsForm.getSearchToDate();
                      if((!fromDate.equals(""))&&(!fromDate.equals(""))){
                    	      boolean isFromDateValid=dateutil.validateDateYYYYMMDD(fromDate);
                    	      boolean isToDateValid=dateutil.validateDateYYYYMMDD(toDate);
                    	      if(!isFromDateValid){
                    	    	        messages.add(ActionMessages.GLOBAL_MESSAGE,
                       					new ActionMessage("message.generalmessage",
                       								" From Date : Invalid date format, date must be in format YYYYMMDD."));	
                    	      }
                    	      if(!isToDateValid){
              	    	        messages.add(ActionMessages.GLOBAL_MESSAGE,
                 					new ActionMessage("message.generalmessage",
                 								" To Date : Invalid date format, date must be in format YYYYMMDD."));	
              	             }
                    	     if(isFromDateValid&&isToDateValid){
                    	        	  modifyDates(smsForm);
                    	     }
                    	}else{
                    		  if(dateIsRequired){
                    			      if(fromDate.equals("")){
                  	    	                   messages.add(ActionMessages.GLOBAL_MESSAGE,
                     					          new ActionMessage("message.generalmessage",
                     								" From date is required."));	
                  	                  }
                  	                  if(toDate.equals("")){
            	    	                        messages.add(ActionMessages.GLOBAL_MESSAGE,
               					                   new ActionMessage("message.generalmessage",
               								       " To date is required."));	
            	                      }
                    		  }else{
                    		         smsForm.setSearchFromDate(dateutil.getPrevDate());
                    		         smsForm.setSearchToDate(dateutil.dateOnly());
                    		  }
                    	}
	   }
	   private boolean  validateInput(ActionMapping mapping,ActionForm form, 
			            HttpServletRequest request,HttpServletResponse response,
			            ActionMessages messages) throws Exception {
		                SmsQueryForm smsForm = (SmsQueryForm) form;
		                if(!smsForm.getCurrentPage().equals("batchNumSelectedPage")){
		                      validateDate(mapping,form,request,response,messages,false);
	                    }
		                validatePersonnnelRespCode(mapping,form,request,response,messages);
		                validateSearchOptions(mapping,form,request,response,messages);
		                if(messages.isEmpty()){
		                	return true;
		                }else{
		                	return false;
		                }
		   
	   }
	   public boolean  validateBatchInput(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response,
				ActionMessages messages,SmsQueryForm smsForm) throws Exception {
			         validateDate(mapping,form,request,response,messages,true);
			         validatePersonnnelRespCode(mapping,form,request,response,messages);
					 if (messages.isEmpty()) {
						  return true;
		             }else{		
			             return false;
			         }
	  }
 }
                    		