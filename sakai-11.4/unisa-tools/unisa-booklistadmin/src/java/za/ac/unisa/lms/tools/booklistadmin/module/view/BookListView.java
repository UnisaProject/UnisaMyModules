package za.ac.unisa.lms.tools.booklistadmin.module.view;

import java.util.Iterator;
import java.util.List;
import org.apache.struts.action.ActionMessages;
import javax.servlet.http.HttpServletRequest;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;
import za.ac.unisa.lms.tools.booklistadmin.module.College;

public class BookListView {
	
	public String confirmBookList(BooklistAdminForm booklistAdminForm,ActionMessages messages){
		                Iterator i = booklistAdminForm.getBooklist().iterator();
		                String errorMsg="";
		                boolean bookMarkedForRemoval=false;
	                 	while (i.hasNext()){
			                   BookModule bookDetails = (BookModule) i.next();
			                   if (bookDetails.isRemove()){
	       			                  errorMsg= "Cannot Publish Final Booklist when a book item is checked for Removal.";
	       			                  addErrorMsg(messages,errorMsg,"editModule");
	       			                  bookMarkedForRemoval=true;
	       			                  break;
	       			           }
		                }
	                 	if(bookMarkedForRemoval){
	                 		      return "editModule";
	                 	}
		                if(booklistAdminForm.getBooklist().size()==0){
			                      errorMsg= "Cannot Publish Final Booklist when no book item listed.";
			                      addErrorMsg(messages,errorMsg,"editModule");
			                      return "editModule";
		                 }
		                 errorMsg=  "Are you sure you want to publish the following Book List Information?";
		                 addErrorMsg(messages,errorMsg,"confirmbyadmin");
		                 return "confirmbyadmin";
	}
	public String viewCourseList(College college,BooklistAdminForm booklistAdminForm,HttpServletRequest request,
			            ActionMessages messages,String nextPage )throws Exception{
		                 String status=booklistAdminForm.getStatusOption();
		                 if(booklistAdminForm.getBackOption().equals("adminmenu")){
		                	    String toPage=validateSelectedData(booklistAdminForm,status, messages);
		                	    if(!toPage.equals("")){
		                	    	return toPage;
		                	    }
		                 }
		                 setListStatus(booklistAdminForm,status);
		                 college.setCollege(booklistAdminForm);
		                 List courseList=null;
		                 String courseCount;
		                 Course course=new Course();
				         if(status.equals("-1")){
					              courseList=course.getNoActionCourseList(booklistAdminForm.getCollegeCode(), booklistAdminForm.getYear(),booklistAdminForm.getTypeOfBookList());
					              courseCount =course.getCourselistCount(booklistAdminForm.getCollegeCode(), booklistAdminForm.getYear(),booklistAdminForm.getTypeOfBookList());
					              booklistAdminForm.setCourseCount(courseCount);
				         }else{
					              courseList=course.getCourseListpercollege(booklistAdminForm.getCollegeCode(), booklistAdminForm.getYear(), booklistAdminForm.getStatusOption(),booklistAdminForm.getTypeOfBookList());
					              courseCount =course.getCourselistCount(booklistAdminForm.getCollegeCode(), booklistAdminForm.getYear(), booklistAdminForm.getStatusOption(),booklistAdminForm.getTypeOfBookList());
					              booklistAdminForm.setCourseCount(courseCount);
				         }	
				        return checkCourseListSize(booklistAdminForm,courseList,messages,nextPage);
		
    }
	
	private String validateSelectedData(BooklistAdminForm booklistAdminForm,String status,ActionMessages messages){
	                   	if(booklistAdminForm.getColleg().equals("")){			
			                   String errorMsg="Please select a college from drop-down list";
			                   return addErrorMsg(messages,errorMsg,"mainview" );	
		                }else if(status.equals("")){
			                         String errorMsg= "Please select type of view";
			                         return addErrorMsg(messages,errorMsg,"mainview");	
		               }		
	                   	return "";	
	}
	private String  addErrorMsg(ActionMessages messages,String errorMsg ,String nextPage){
		              InfoMessagesUtil.addMessages(messages,errorMsg);
                      return  nextPage;
   	}
	private String checkCourseListSize(BooklistAdminForm booklistAdminForm,List courseList,ActionMessages messages,String nextPage){
		               String toPage=nextPage;
		               if(courseList.size()==0){
		            	   if(booklistAdminForm.getBackOption().equals("adminmenu")){
		            	                 String errorMsg= "No booklist available for your selection";  
		                                 toPage=addErrorMsg(messages,errorMsg,"mainview" );
		            	   }
		                }else{
		                	      	  courseList.remove(booklistAdminForm.getCourseId());
		                	          booklistAdminForm.setCourselist(courseList);
			           }
		           return  toPage;
	}
	private void setListStatus(BooklistAdminForm booklistAdminForm,String status){
		if(status.equals("5")){
			booklistAdminForm.setStatus("Book list authorized by Dean");			
		}else if(status.equals("4")){
			booklistAdminForm.setStatus("Booklist authorized by School Director");
		}
		else if(status.equals("2")){
			booklistAdminForm.setStatus("Booklist authorized by COD");
		}
		else if(status.equals("1")){
			booklistAdminForm.setStatus("Booklist submitted but not authorized");
		}
		else if(status.equals("0")){
			booklistAdminForm.setStatus("Booklist started but not submitted");
		}else if(status.equals("-1")){
			booklistAdminForm.setStatus("Book list with no activity");
		}else if(status.equals("6")){
			booklistAdminForm.setStatus("Book list published by administrator");
		}else if(status.equals("7")){
			booklistAdminForm.setStatus("Booklist open for editing by administrator");			
		}else if(status.equals("3")){
			booklistAdminForm.setStatus("No Books prescribed for this course");			
		}
	}
	
		

}
