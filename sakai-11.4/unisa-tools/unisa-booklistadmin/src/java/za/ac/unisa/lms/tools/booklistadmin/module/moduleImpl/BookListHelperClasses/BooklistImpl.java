package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookListHelperClasses;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookListDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrail;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
import za.ac.unisa.lms.tools.booklistadmin.module.Course;

public class BooklistImpl  extends BookListUpdateUtil {
	
	
	BookListGetterUtil bookListDataGetter;
	BookListDAO bookListDAO;
	BookListReUseUtil bookListReUseUtil;
	BookListValidator bookListValidator;
	public BooklistImpl(BookListDAO bookListDAO,AuditTrail auditTrail,Course course){
		          super(auditTrail,bookListDAO);
		 	      this.bookListDAO= bookListDAO;
		 	      bookListDataGetter=new BookListGetterUtil(auditTrail,bookListDAO,course);
		 	      bookListReUseUtil=new BookListReUseUtil(bookListDAO);
		 	      bookListValidator=new  BookListValidator(bookListDataGetter);
	}
	public void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {

		int w = in.read();

		while (w != -1) {
			out.write(w);
			w = in.read();
		}

		out.flush();
		out.close();
		in.close();
	}
	public void saveToServer(String path, String filename,BooklistAdminForm booklistAdminForm) throws Exception {

		File fileobject = new File(path + filename);
		FileWriter fw = new FileWriter(fileobject);
	
		String seperatewith = "";
		String type  = booklistAdminForm.getTypeOfBookList();
		
		ArrayList list= bookListDAO.getBooklistExport(booklistAdminForm.getYear(),booklistAdminForm.getTypeOfBookList(),booklistAdminForm.getListyear());
		seperatewith = "~";
		List booklist = (List)list;	
		if(booklistAdminForm.getTypeOfBookList().equals("P")){	
			if(booklistAdminForm.isIncludeYear()==true){
		fw.write("Course"+seperatewith+" myUnisa number "+seperatewith+" Title "+seperatewith+" First Author "+seperatewith+" Other Authors "+seperatewith+" Published Year "+seperatewith+" Edition "+seperatewith+" ISBN "+seperatewith+" Publisher "+seperatewith+" Book Note "+seperatewith+" Course Note "+seperatewith+" Year Semister "+seperatewith+" EngStudents_Sem1 "+seperatewith+" EngStudents_Sem2 "+seperatewith+" EngStudents_Year "+seperatewith+"AfrStudents_Sem1 "+seperatewith+"EngStudents_Sem2 "+seperatewith+"AfrStudents_Year "+seperatewith+"AfrStudents_Sem2 "+seperatewith+" \n");
			}else{
		fw.write("Course "+seperatewith+" myUnisa number "+seperatewith+" Title "+seperatewith+" First Author "+seperatewith+" Other Authors "+seperatewith+" Published Year "+seperatewith+" Edition "+seperatewith+" ISBN "+seperatewith+" Publisher "+seperatewith+" Book Note "+seperatewith+" Course Note "+seperatewith+" Year Semister "+seperatewith+" \n");
			}
		}else if(booklistAdminForm.getTypeOfBookList().equals("R")){
		fw.write("Course "+seperatewith+" BIBNUMBER "+seperatewith+" myUnisa number "+seperatewith+" Title "+seperatewith+" First Author "+seperatewith+" Other Authors "+seperatewith+" Published Year "+seperatewith+" Edition "+seperatewith+" ISBN "+seperatewith+" Publisher "+seperatewith+" Language "+seperatewith+" Is_Published "+seperatewith+" Ebook "+seperatewith+" Course Notes "+seperatewith+" Library Notes "+seperatewith+" \n");
		}else if(booklistAdminForm.getTypeOfBookList().equals("E")){
			fw.write("Course "+seperatewith+" BIBNUMBER "+seperatewith+" myUnisa number "+seperatewith+" Title "+seperatewith+" First Author "+seperatewith+" Other Authors "+seperatewith+" VOLUME "+seperatewith+" PAGES "+seperatewith+" Published Year "+seperatewith+" Publication "+seperatewith+" URL "+seperatewith+" Master Copy "+seperatewith+" Master Format "+seperatewith+" Course Notes "+seperatewith+"  Library Notes "+seperatewith+" eReserve Type "+seperatewith+"  \n");	
		}			
		for(int i=0; i < booklist.size(); i++) {
			if(type.equals("P")){
			fw.write(((BookModule)booklist.get(i)).getCourse()+seperatewith);	
			fw.write(((BookModule)booklist.get(i)).getBookId()+seperatewith);	
			fw.write(((BookModule)booklist.get(i)).getTxtTitle()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtOtherAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtYear()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtEdition()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtISBN()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtPublisher()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtBookNote()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getCourseNote()+seperatewith);			
			fw.write(((BookModule)booklist.get(i)).getAcademicStatus()+seperatewith);		
			if(booklistAdminForm.isIncludeYear()==true){
				fw.write(((BookModule)booklist.get(i)).getEngSem1Count()+seperatewith);
				fw.write(((BookModule)booklist.get(i)).getEngSem2Count()+seperatewith);
				fw.write(((BookModule)booklist.get(i)).getEngYearCount()+seperatewith);
				fw.write(((BookModule)booklist.get(i)).getAfrSem1Count()+seperatewith);
				fw.write(((BookModule)booklist.get(i)).getAfrSem2Count()+seperatewith);
				fw.write(((BookModule)booklist.get(i)).getAfrYearCount()+seperatewith);}	
			fw.write(" "+((char)13)+((char)10));
		}else if(type.equals("R")){
			fw.write(((BookModule)booklist.get(i)).getCourse()+seperatewith);		
			fw.write(((BookModule)booklist.get(i)).getBibliographicnr()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getBookId()+seperatewith);	
			fw.write(((BookModule)booklist.get(i)).getTxtTitle()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtOtherAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtYear()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtEdition()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtISBN()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtPublisher()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getCourseLang()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getIsPublished()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getAvailableAsEbook()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getCourseNote()+seperatewith);			
			fw.write(((BookModule)booklist.get(i)).getNoteToLibrary()+seperatewith);
			fw.write(" "+((char)13)+((char)10));
		}else if(type.equals("E")){
			fw.write(((BookModule)booklist.get(i)).getCourse()+seperatewith);		
			fw.write(((BookModule)booklist.get(i)).getBibliographicnr()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getBookId()+seperatewith);	
			fw.write(((BookModule)booklist.get(i)).getTxtTitle()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtOtherAuthor()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getEbookVolume()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getEbook_pages()+seperatewith);			
			fw.write(((BookModule)booklist.get(i)).getTxtYear()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getTxtPublisher()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getUrl()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getMasterCopy()+seperatewith);			
			fw.write(((BookModule)booklist.get(i)).getMasterCopyFormat()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).getCourseNote()+seperatewith);	
			fw.write(((BookModule)booklist.get(i)).getNoteToLibrary()+seperatewith);
			fw.write(((BookModule)booklist.get(i)).geteReserveType()+seperatewith);
			fw.write(" "+((char)13)+((char)10));
		}
		}
		fw.flush();
		fw.close();
	}
	public void generateList(HttpServletResponse response,BooklistAdminForm booklistAdminForm,String path )  throws  Exception{
		// path = "C:\\var\\";
		String filename = "";
		
			if(booklistAdminForm.getNetworkId()!= null) {
				filename = booklistAdminForm.getNetworkId() + ".txt";
				//System.out.println("filename is : "+filename);
			}else {
				filename =  "booklist.txt";
			}

	    	saveToServer(path, filename,booklistAdminForm);
			File file = new File(path + filename);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setDateHeader("Expires", 0);
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/octet-stream");
			response.setContentLength((int)file.length());
			response.addHeader("Content-Disposition", "attachment;filename=" + filename );

			saveToClient(in, out);
		}
	   public List getBookList(BooklistAdminForm booklistAdminForm)throws Exception{
                       return bookListDataGetter.getBookList(booklistAdminForm);
        }
	    public List getBookCopyList (String subject, String acadyear,String typeOfbooklist) throws Exception {
			            return  bookListDataGetter.getBookCopyList(subject, acadyear, typeOfbooklist);
		}
	    public List getBookCopyList (BooklistAdminForm booklistAdminForm) throws Exception {
	    	return  bookListDataGetter.getBookCopyList( booklistAdminForm);
	   }
	    public String reUseBookList(BooklistAdminForm booklistAdminForm,ActionMessages messages)throws Exception{
	    	               return bookListReUseUtil.reUseBookList(booklistAdminForm, messages);
        }
	    public boolean isBookListEmpty(BooklistAdminForm booklistAdminForm,ActionMessages messages) throws Exception{
	    	              return bookListValidator.isBookListEmpty(booklistAdminForm, messages);
        }
	    public void setBookListDataToBean(BooklistAdminForm booklistAdminForm,UserDirectoryService userDirService)throws Exception{
	    	              bookListDataGetter.setBookListDataToBean(booklistAdminForm, userDirService);
	    }
}
