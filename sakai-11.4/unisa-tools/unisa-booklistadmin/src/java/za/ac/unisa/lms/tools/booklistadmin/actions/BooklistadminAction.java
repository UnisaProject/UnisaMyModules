package za.ac.unisa.lms.tools.booklistadmin.actions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.forms.*;
import za.ac.unisa.lms.tools.booklistadmin.module.*;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.InfoMessagesUtil;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.Utilities;
import za.ac.unisa.lms.tools.booklistadmin.module.view.BookListAdminView;
import za.ac.unisa.lms.booklistadmin.utils.*;
public class BooklistadminAction extends LookupDispatchAction{
	 SessionManager  sessionManager;
	 UserDirectoryService userDirectoryService;
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap();		
		map.put("mainview", "mainview");
		map.put("manageDates","manageDates");
		map.put("manageReleaseDates","manageReleaseDates");
		map.put("button.backtodatesmanagement","manageDates");
		map.put("button.backtoreleasedatesmanagement","manageReleaseDates");
		map.put("handleClosingDateChoosenChkbxAction","handleClosingDateChoosenChkbxAction");
		map.put("editmodule", "editmodule");
		map.put("button.display", "viewbooklist");
		map.put("button.clear", "clear");  
		map.put("button.cancel", "cancel");
		map.put("button.canceldates", "mainview");
		map.put("button.cancelDatesSelection","mainview");
		map.put("button.cancelreleasedates","mainview");
		map.put("button.cancelReleaseDatesSelection","mainview");
		map.put("removeDatesConfirm","removeDatesConfirm");
		map.put("button.removedatesconfirm", "removeDatesConfirm");//implement removeDate
		map.put("button.savedates","saveDates");//implement saveDate
		map.put("button.savereleasedates","saveReleaseDates");
		map.put("button.back", "back");
		map.put("datesremoval","datesRemoval");
		map.put("releaseDatesRemoval","releaseDatesRemoval");
		map.put("button.datesremoval","datesremoval");
		map.put("button.releasedatesremoval","releaseDatesRemoval");
		map.put("button.publishbooklist", "booklistAdminContinue");
		map.put("courseNote", "courseNote");
		map.put("button.submit", "submit");
		map.put("button.remove", "removeBook");
		map.put("button.delete", "deleteBook");
		map.put("button.addnewbook", "addNewBook");
		map.put("addNewBook","addNewBook");
		map.put("searchBook", "searchBook");
		map.put("searchBooks", "searchBooks");
		map.put("selectYearforList", "selectYearforList");
		map.put("button.search", "findBooks");
		map.put("button.continue", "booklistAdminContinue");
		map.put("button.copy", "copyBooks");
		map.put("button.newsearch", "searchBook");
		map.put("bookReuse", "bookReuse");
		map.put("declareNopBooks", "declareNopBooks");
		map.put("button.generateList", "generateList");
		map.put("button.unconfirm", "unConfirmBookList");
		map.put("button.noBooks", "noBooks");
		map.put("button.noItems", "noBooks");
		return map;
	}
	public ActionForward removeDate(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
				return editmodule(mapping, form, request, response);	
	}
	public ActionForward releaseDatesRemoval(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

                     BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
			         ActionMessages messages = new ActionMessages();
                     String toPage="submissiondates";
                         if(!booklistAdminForm.isManageDatesScrEntered()){
		    	                 BookReleaseDate bookReleaseDate=new BookReleaseDate();
		    	                 bookReleaseDate.removeReleaseDate(booklistAdminForm, request);
		                         return mapping.findForward("releasedates");
		                    }else{
		    	                     BookSubmissionDate bookSubmissionDate=new BookSubmissionDate();
		    	                     bookSubmissionDate.removeSubmissionDate(booklistAdminForm, request);
			                         return mapping.findForward("submissiondates");
		                   }
     }
	 public ActionForward removeDatesConfirm(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		                ActionMessages messages = new ActionMessages();
		                String toPage="submissiondates";
		                try{
		                       BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		                        boolean deleteStatus = false;
		                        List dateslist=booklistAdminForm.getBooklist();
		                        Iterator dateslistIterator=dateslist.iterator();
		                        BookSubmissionDate bookSubmissionDate=new BookSubmissionDate();
		                        BookReleaseDate bookReleaseDate=new BookReleaseDate();
		                        if(booklistAdminForm.isManageDatesScrEntered()){
	        	                    	toPage=bookSubmissionDate.confirmDateRemovaL(booklistAdminForm, request, messages);
	           	                }else{
	            	                       toPage=bookReleaseDate.confirmDateRemovaL(booklistAdminForm, request, messages);
	                            }
	                     }catch(Exception ex){
	                                      InfoMessagesUtil.addMessages(messages,"an unexpected  error was encountered");
	                                      addErrors(request,messages);
	                                      return mapping.findForward("submissiondates");
                         }
	                     addErrors(request, messages);
	                     return mapping.findForward(toPage);
	 }
	 public ActionForward datesRemoval(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) {
		           try{
				          BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
			              
			              BookSubmissionDate  bookSubmissionDate=new BookSubmissionDate();
			              bookSubmissionDate.removeSubmissionDate(booklistAdminForm, request);
			              return mapping.findForward("submissiondates");
	               }catch(Exception ex){
	            	        ActionMessages messages = new ActionMessages();
			                InfoMessagesUtil.addMessages(messages,"an unexpected  error was encountered"+ex.getMessage());
	            	        addErrors(request,messages);
		                    return mapping.findForward("submissiondates");
	               }
	    }
		
	public ActionForward manageDates(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		    booklistAdminForm.setManageDatesScrEntered(true);
		    booklistAdminForm.setReleaseDateRemovalScrnEntered(false);
		    BookSubmissionDate  bookSubmissionDate=new BookSubmissionDate();
		    bookSubmissionDate.createDateManagementScrn(booklistAdminForm,request);
		    return mapping.findForward("submissiondates");	
	}
	public ActionForward manageReleaseDates(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		    booklistAdminForm.setManageDatesScrEntered(false);
		    booklistAdminForm.setReleaseDateRemovalScrnEntered(true);
		    BookReleaseDate bookReleaseDate=new BookReleaseDate();
		    bookReleaseDate.createReleaseDateManagementScrn(booklistAdminForm,request);
		    return mapping.findForward("releasedates");	
	}
	//change to saveBookDates
	 public ActionForward saveReleaseDates( 
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		 
		      String nextPage="";
		   try{
	        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		    HttpSession session = request.getSession(true);
		    Session currentSession = sessionManager.getCurrentSession();
		    String userID = currentSession.getUserEid();
		    String  networkId=userID;
		     BookSubmissionDate booksubmissionDate=new BookSubmissionDate();
		     BookReleaseDate bookReleaseDate=new BookReleaseDate();
		    BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		    int academicYear=Integer.parseInt(booklistAdminForm.getAcademicYear());
		    if(booklistAdminForm.isManageDatesScrEntered()){
		    	        booksubmissionDate.saveBookSubmissionDate(booklistAdminForm, networkId, session, academicYear);
		    	        nextPage="submissiondates";
					
		    }else{
		    	         bookReleaseDate.saveBookReleaseDate(booklistAdminForm, session, academicYear);
		    	         nextPage="releasedates";
		    }
		 }catch(Exception ex){
			  nextPage="submissiondates";
		 }
		    return mapping.findForward(nextPage);
}
	public ActionForward handleClosingDateChoosenChkbxAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    HttpSession session = request.getSession(true);
		        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		        if(booklistAdminForm.isClosingdatechoosen()){
		    	        booklistAdminForm.setClosingdatechoosen(false);
		        }else{
		    	       booklistAdminForm.setClosingdatechoosen(true);
		        }
		        session.setAttribute("datesList",booklistAdminForm.getDateslist());
	            session.setAttribute("yearsList",booklistAdminForm.getYearsList());
	            session.setAttribute("levelList",booklistAdminForm.getLevelList());
	            session.setAttribute("monthList",booklistAdminForm.getMonthList());
	            session.setAttribute("dayList",booklistAdminForm.getDaysList());
	        return mapping.findForward("submissiondates");
    }
		public ActionForward mainview (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		        ActionMessages messages = new ActionMessages();
		        BookListAdminView bookListAdminView=new BookListAdminView();
		         sessionManager =(SessionManager)ComponentManager.get(SessionManager.class);
		         BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		         GenericUtil genericUtil=new GenericUtil();
		                  if((booklistAdminForm.getTypeOfBookList()==null)||
		        		          (booklistAdminForm.getTypeOfBookList().equals(""))){
		                           booklistAdminForm.setTypeOfBookList(request.getParameter("TYPE").toString());
		                    }
		                    try{
		                          bookListAdminView.createFirstScreen(request, sessionManager, booklistAdminForm);	
		                    }catch(Exception ex){}
			      booklistAdminForm.setFromButton("");
		        return mapping.findForward("mainview");	
	}
		
		public ActionForward booklistAdminContinue(
			                     ActionMapping mapping,
			                     ActionForm form,
			                     HttpServletRequest request,
			                     HttpServletResponse response) throws Exception{
 		
              		                BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
			                        if (booklistAdminForm.getContinueOption().toUpperCase().equals("SEARCHBOOK")){
		            	                   return addNewBook(mapping, form, request, response);
		                            }else if (booklistAdminForm.getContinueOption().toUpperCase().equals("CONFIRMVIEW")) {
			                                     return adminConfirm(mapping, form, request, response);
		                            }if (booklistAdminForm.getContinueOption().toUpperCase().equals("CONFIRMBYADMIN")) {
			                                     return adminupdate(mapping, form, request, response);
		                            }
		                        return editmodule(mapping, form, request, response);	
	 }
	 public ActionForward viewbooklist(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		
        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		ActionMessages messages = new ActionMessages();
		BookList bookList=new BookList();
		String nextPage="";
		College  college=new  College();
		nextPage=bookList.viewCourseList(college,booklistAdminForm, request, messages, "selectcourse");
		addErrors(request, messages);
		booklistAdminForm.setFromButton("courseList");
		return mapping.findForward(nextPage);	
	}	
	
	public ActionForward editmodule ( 
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		        BookList booklist =new BookList();
				userDirectoryService=(UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
		        booklist.setBookListDataToBean(booklistAdminForm,userDirectoryService);
	    	return mapping.findForward("displaybooklist");	
	}
	
	public ActionForward adminConfirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		ActionMessages messages = new ActionMessages();
		BookList  booklist=new BookList();
		String nextPage=booklist.confirmBookList(booklistAdminForm, messages);
		addErrors(request, messages);
		if(nextPage.equals("confirmbyadmin")){
			return mapping.findForward("confirmbyadmin");
		}else{
			return editmodule(mapping, form, request, response);
		}
				
	}
	public ActionForward adminupdate (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		        ActionMessages messages = new ActionMessages();
	         	BookList booklist =new BookList();
	         	booklist.adminUpdateList(booklistAdminForm, messages);
		        addErrors(request, messages);
			    return mainview(mapping, form, request, response);
			}
	 public ActionForward removeBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		         BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		         ActionMessages messages = new ActionMessages();
		         Book  book=new  Book();
		         String nextPage= book.confirmBookDeletion(booklistAdminForm, messages);
		         addErrors(request, messages);
		         if(nextPage.equals("bookremove")){
		        	 return mapping.findForward("bookremove");
		         }else{
		        	 return editmodule(mapping, form, request, response);
		         }
	}
	
	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
			booklistAdminForm.setStatus("Booklist open for editing by administrator");			
		if (booklistAdminForm.getSubmitOption().toUpperCase().equals("COURSENOTE")){
			return saveCourseNotes(mapping, form, request, response);
		}else if(booklistAdminForm.getSubmitOption().equals("UPDATEBOOK")){
			return updateBookDetails(mapping, form, request, response);
		}else if (booklistAdminForm.getSubmitOption().toUpperCase().equals("NEWBOOK")){
			return savebookDetails(mapping, form, request, response);
		}
		
		return mapping.findForward("selectcourse");	
	}
	public ActionForward savebookDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	    
		     String bookType="";
		     HttpSession session = request.getSession(true);
         	 BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
         	 ActionMessages messages = new ActionMessages();
		     if(booklistAdminForm.getTypeOfBookList().equalsIgnoreCase("E")){
				     bookType = booklistAdminForm.geteReserveType();
		     }else{
			          bookType = "notEreserves";
		     }
		     Utilities.trimEnteredData(bookType,booklistAdminForm);
		 	 Book book=new Book();
		 	 booklistAdminForm.setSearchOption(bookType);
			 book.validateBookData(booklistAdminForm, messages);
			 if(!messages.isEmpty()){
				     addErrors(request, messages);
	                 return addNewBook(mapping, form, request, response);
			 }
			 book.saveBook(booklistAdminForm, bookType);
			return editmodule(mapping, form, request, response);
	}

	public ActionForward deleteBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		       
		          BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		          AuditTrail auditTrail=new AuditTrail();
		          ActionMessages messages = new ActionMessages();
		          Book book=new Book();
		          book.deleteBook(booklistAdminForm, messages);
		          addMessages(request, messages);
		          saveMessages(request, messages);
				return editmodule(mapping, form, request, response);
	}
	public ActionForward noBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		             BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
			         ActionMessages messages = new ActionMessages();
                     BookList bookList =new BookList(); 
                     bookList.isBookListEmpty(booklistAdminForm, messages);
                     Course course=new Course();
                     course.declareNoBooks(booklistAdminForm);
                     booklistAdminForm.setStatus("No Books prescribed for this course");
                    // booklistAdminForm.setFromDeclareNoBookWin(true);
                return editmodule(mapping, form, request, response);
	}
	public ActionForward courseNote(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		       
		          BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		          Course course=new  Course();
		          try {
		        	       String courseNote=course.getCourseNote(booklistAdminForm.getCourseId(), booklistAdminForm.getYear(),booklistAdminForm.getTypeOfBookList());
	                       booklistAdminForm.setCourseNote(courseNote);
		          } catch (Exception e) {
			            e.printStackTrace();
		          }
		         return mapping.findForward("coursenote");
	}
	public ActionForward saveCourseNotes(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

		      BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
			  ActionMessages messages = new ActionMessages();
		      Course  course=new Course();
		      course.validateCourseNotes(booklistAdminForm, messages);
		      if(messages.isEmpty()){
       			     course.saveCourseNote("7", booklistAdminForm,messages);
		      }else{
			         addErrors(request, messages);
		     	      return courseNote(mapping, form, request, response);
		      }
			return editmodule(mapping, form, request, response);
	}
	public ActionForward addNewBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		  BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		  
		if (booklistAdminForm.getSearchOption().equals("searchform")){
			booklistAdminForm.setSearchOption("searchform");
		} else if (booklistAdminForm.getSearchOption().equals("edit")){
			booklistAdminForm.setSearchOption("edit");
		}
        Book  book=new Book();
			String nextPage=book.setDataForAddBookScreen(booklistAdminForm);
		 
		 return mapping.findForward( nextPage);
	}

	public ActionForward selectYearforList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		       BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		       booklistAdminForm.setIncludeYear(true);
		return mapping.findForward("generatelist");
	}
		
	
	public ActionForward generateList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		       BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		       String path = getServlet().getServletContext().getInitParameter("mypath");
		       BookList bookList=new BookList();
		       bookList.generateList(response, booklistAdminForm, path);
			return null;
	}
	
	
	
	public ActionForward searchBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
		BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		booklistAdminForm.setTxtAuthor("");
		booklistAdminForm.setPublisherName("");
		booklistAdminForm.setTxtTitle("");
		booklistAdminForm.setSearchOption("");
		booklistAdminForm.setEbook_pages("");
		return mapping.findForward("searchform");
	}
	public ActionForward searchBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
		BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		 Book  book=new  Book();
		 String nextPage=book.createSearchBookScreen(booklistAdminForm);
		return mapping.findForward(nextPage);
	}
	public ActionForward findBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
	          	   BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
	          	   ActionMessages messages = new ActionMessages();
			       booklistAdminForm.setBooklist(null);
			       String  nextPage="";
			       HttpSession session = request.getSession(true);
				       userDirectoryService=(UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
			           Book book =new Book();
			           if(booklistAdminForm.getSearchOption().equals("searchCourse")){
                            nextPage= book.searchBookByCourseCode(userDirectoryService, booklistAdminForm, messages);
                            if(!messages.isEmpty()){
					               addErrors(request, messages);
					               nextPage="searchcourse";
                            }
                            booklistAdminForm.setListEnteredFrom("course");
					        return mapping.findForward(nextPage);
			          }else if(booklistAdminForm.getSearchOption().equals("publisher")){
			    	            nextPage= book.searchBookByPublisher(booklistAdminForm, messages);
                                if(!messages.isEmpty()){
				                      addErrors(request, messages);
                                }
				                return mapping.findForward(nextPage);
				     }else if(booklistAdminForm.getSearchOption().equals("")){			
					           nextPage= book.searchBookNoOtpion(booklistAdminForm, messages);
                               if(!messages.isEmpty()){
		                               addErrors(request, messages);
                               }
		                       return mapping.findForward(nextPage);
			        }
			       return mapping.findForward("bookfind");
	}
	public ActionForward unConfirmBookList(
			    ActionMapping mapping,
			    ActionForm form,
			    HttpServletRequest request,
			    HttpServletResponse response) throws Exception{
		             BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;		
		             BookList  booklist=new BookList();
				     AuditTrail auditTrail=new AuditTrail();
		             booklist.updateBookListStatus("7", booklistAdminForm.getCourseId(),
		             booklistAdminForm.getYear(),booklistAdminForm.getNetworkId(),booklistAdminForm.getTypeOfBookList());
		             auditTrail.updateAuditTrail(booklistAdminForm, "Admin reOpened the booklist");
		             booklistAdminForm.setStatus("Booklist open for editing by administrator");
		             booklistAdminForm.setFromDeclareNoBookWin(false);
		 return mapping.findForward("displaybooklist");
	}	
	
	public ActionForward copyBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
      	        ActionMessages messages = new ActionMessages();
      	        
      	      if (booklistAdminForm.getCopyExistingBookOption().equalsIgnoreCase("COPYEXISTINGBOOKS")){
      	        Book book=new Book();
      	        String nextPage=book.copyBook(booklistAdminForm, messages);
      	        if (nextPage.equalsIgnoreCase("bookfind")){
	                      return mapping.findForward("bookfind");
                } else if(nextPage.equalsIgnoreCase("bookReuse")){
                           return bookReuse(mapping, form, request, response);
                }
      	        if(!messages.isEmpty()){
 	    	            addMessages(request, messages);
 	  		            saveMessages(request, messages);
 	            }
     
                return editmodule(mapping, form, request, response);
	  
      	       }  else{
      	    	 return copyExistingBook(mapping, form, request, response);
      	       }
      	      
    }
	public ActionForward copyExistingBook(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	      	     
		              BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
          	          Book  book=new Book();
          	          String nextPage=book.copyExistingBook(booklistAdminForm);
          	          if (nextPage.equalsIgnoreCase("bookfind")){
				               return mapping.findForward("bookfind");
			          } else {
			        	        return editmodule(mapping, form, request, response);
			          }
	}	
	public ActionForward bookReuse(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		                     BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
      	                     BookList bookList=new BookList();
      	                     ActionMessages messages = new ActionMessages();
	                         String nextPage=bookList.reUseBookList(booklistAdminForm, messages);
	                         if(nextPage.equalsIgnoreCase("displaybooklist")){
	                        	    addErrors(request, messages);
	                         }
	                         return mapping.findForward(nextPage);
	}
	public ActionForward declareNopBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		         
		          BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
      	          ActionMessages messages = new ActionMessages();
      	          BookList bookList=new BookList();
      	          boolean isListEmpty=bookList.isBookListEmpty(booklistAdminForm, messages);
      	          addErrors(request, messages);
		          if(isListEmpty){
		        	      return mapping.findForward("declarenopbooks");
		          }
		          return editmodule(mapping, form, request, response);
	              
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		             
		             BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		             College  college=new  College();
		             college.setCollege(booklistAdminForm);
		             booklistAdminForm.setNetworkId(booklistAdminForm.getNetworkId());
		             booklistAdminForm.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
		            
   		             if(booklistAdminForm.getCancelOption().equals("editModule")){			
			                    return editmodule(mapping, form, request, response);	
		             }else if(booklistAdminForm.getCancelOption().equals("TOCOURSEVIEW")){
			                    return mainview(mapping, form, request, response);	
		             }else{
		                        return viewbooklist(mapping, form, request, response);	
		             }
   }
   public ActionForward clear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		    BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;	
		    if(booklistAdminForm.getSearchOption().equals("searchCourse")){
			     booklistAdminForm.setSearchOption(booklistAdminForm.getSearchOption());
			    return searchBooks(mapping, form, request, response);
		}
		return searchBooks(mapping, form, request, response);
	}
	
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		        BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;
		        if(booklistAdminForm.getDateRemovalScrnEntered()){	
	        	        BookSubmissionDate bookSubmissionDate=new BookSubmissionDate();
	        	        bookSubmissionDate.createDateManagementScrn(booklistAdminForm,request);
	  		            return mapping.findForward("submissiondates");
                 }
	             if(booklistAdminForm.isReleaseDateRemovalScrnEntered()){	
	        	        BookReleaseDate bookReleaseDate=new BookReleaseDate();
	        	        bookReleaseDate.createReleaseDateManagementScrn(booklistAdminForm,request);
	  		            return mapping.findForward("releasedates");
                }
	             if(booklistAdminForm.getFromButton().equals("course")){
	            	 booklistAdminForm.setFromButton("");
	                 if((booklistAdminForm.getCourselist()==null)||
	            		 (booklistAdminForm.getCourselist().size()==0)){
	            	          return mapping.findForward("adminmenu");
	                }
	             }
			    if(booklistAdminForm.getBackOption().equals("searchBook")){
			         return searchBooks(mapping, form, request, response);
		       }else if(booklistAdminForm.getBackOption().equals("editModule")){
			         return editmodule(mapping, form, request, response);
		       }else if(booklistAdminForm.getBackOption().equals("searchCourse")){
		    	             return mapping.findForward("searchcourse");
			    }else{	
			    	     return viewbooklist(mapping, form, request, response);
		   	    }
  }

	public ActionForward updateBookDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		           BooklistAdminForm booklistAdminForm = (BooklistAdminForm) form;		
                   Book book = new Book();
                   ActionMessages messages = new ActionMessages();
                  // booklistAdminForm.setSearchOption("add");
                   String updateResult="";
                   book.validateBookData(booklistAdminForm, messages);
                   if(!messages.isEmpty()){
 	                      addErrors(request, messages);
		                  return addNewBook(mapping, form, request, response);
                   }
                   updateResult=book.updateBook(booklistAdminForm);
                   if(!updateResult.trim().equals("")){
        	               InfoMessagesUtil.addMessages(messages,updateResult);
        	               addErrors(request, messages);
        	               return mainview(mapping, form, request, response);
                  }
          return editmodule(mapping, form, request, response);
     }
		
}
