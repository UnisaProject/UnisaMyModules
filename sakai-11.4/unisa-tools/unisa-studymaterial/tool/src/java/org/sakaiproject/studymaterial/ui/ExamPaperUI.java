package org.sakaiproject.studymaterial.ui;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.sakaiproject.studymaterial.impl.Email;
import org.sakaiproject.studymaterial.module.ExamPaperModel;
import org.sakaiproject.studymaterial.utils.MetaDataUtils;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIOutput;

public class ExamPaperUI {
	public  void examPaperHeading(UIContainer tofill){
		 UIOutput.make(tofill,"color3");
        UIOutput.make(tofill,"descriptionexam","Description");
        UIOutput.make(tofill,"sizeexam","File Size");
        UIOutput.make(tofill,"icon1");
        UIOutput.make(tofill,"img1");
		}
	public void  displayExamPaper(UIContainer tofill,ExamPaperModel examPaperModel){
		          UIBranchContainer urlBranch1 = UIBranchContainer.make(tofill, "url2-row:");
		          String linkText="Examination Question Paper "+examPaperModel.getUnitNumber()+
		        		  " "+examPaperModel.getPeriodDesc()+" "+examPaperModel.getYear()+"("+examPaperModel.getLanguage()+")";
	              UILink.make(urlBranch1,"url2",linkText,examPaperModel.getFilepasthInServer()); 
                  UIOutput.make(urlBranch1, "sizeng",examPaperModel.getConveFileSize()+" "+"KB");
   	}
	public void   displayExampPapers(UIContainer tofill,String siteId,List examPaperList,Log log){
		               String moduleCode=siteId.substring(0, 7);
	                   UIOutput.make(tofill,"coursecode",moduleCode);
                       int totValidExamPapers=0;
	                   Iterator examListIter=examPaperList.iterator();
                       String[] exam = null;
		               if((examPaperList !=null)&&examPaperList.size()>0){
		                    for(int i=0; examListIter.hasNext() ; i++){
			                      ExamPaperModel examPaperModel = (ExamPaperModel)examListIter.next();
				                  boolean fileExistInServer=examPaperModel.isFileExist();
			                    if(fileExistInServer){
				                     if (i==0){
				                          examPaperHeading(tofill);
				                     }
		   		                       displayExamPaper(tofill,examPaperModel);
		   		                    totValidExamPapers++;
		                        }else{
				                       Email email=new  Email(siteId);
				                       email.sendEmail(examPaperModel,log);
		                        }
		                    }
		             }
		             if((totValidExamPapers==0)||(examPaperList ==null)||(examPaperList.size()==0)){
		                   	 displayNoExamPapersErrorMsg(tofill);
		             }
	}
		          	
    private void displayNoExamPapersErrorMsg(UIContainer tofill){
	               UIOutput.make(tofill,"color3");
	               UIOutput.make(tofill,"descriptionexam","Description");
	               UIOutput.make(tofill,"sizeexam","File Size");
	               UIOutput.make(tofill,"icon1");
	               UIOutput.make(tofill,"img1");
	}
		     		

}
