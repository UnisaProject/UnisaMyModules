package org.sakaiproject.studymaterial.impl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.sakaiproject.studymaterial.dao.ExamPaperDAO;
import org.sakaiproject.studymaterial.module.ExamPaperModel;
import org.sakaiproject.studymaterial.ui.ExamPaperUI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import uk.org.ponder.rsf.components.UIContainer;

public class ExamPaper {
	public List getExamPaperMetaData(String courseName,Log log) {
		          ExamPaperDAO examPaperDAO=new ExamPaperDAO();
		          Document doc=examPaperDAO.getXMLDocument(courseName,log);
			      NodeList listOfStudentDocuments = doc.getElementsByTagName("exampaper");
			if(listOfStudentDocuments.getLength() != 0) {
				 List examPaperList=new  ArrayList();
				for (int s = 0; s < listOfStudentDocuments.getLength(); s++) {

					Node firstDocTypeNode = listOfStudentDocuments.item(s);

					if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {

						Element firstElement = (Element) firstDocTypeNode;
                        ExamPaperModel examPaperModule=new ExamPaperModel(); 
                        examPaperModule.setModule(getNodeValue(firstElement,"coursecode"));
                        examPaperModule.setYear(getNodeValue(firstElement,"examyear"));
                        examPaperModule.setPeriod(getNodeValue(firstElement,"period"));
                        examPaperModule.setLanguage(getNodeValue(firstElement,"language"));
                        examPaperModule.setUnitNumber(getNodeValue(firstElement,"papernumber"));
                        examPaperModule.setFileSizeInBytes(getNodeValue(firstElement,"filesize"));
                        examPaperModule.setFilePath(getNodeValue(firstElement,"link"));
						examPaperList.add(examPaperModule);

					}
         	    }
				   return examPaperList;
			} else {
				log.info("Official Study Material: webServiceUrl returns empty list for "+courseName);
				return null;
			}
	}
	private String getNodeValue(Element element,String dataField){
		              NodeList nodelistFromFirstElem = element.getElementsByTagName(dataField);
		              Element subElement = (Element) nodelistFromFirstElem.item(0);
		              NodeList nodelist = subElement.getChildNodes();
		              String  dataFieldValue= ((Node) nodelist.item(0)).getNodeValue().trim();
		           return  dataFieldValue;
   }
	public void   displayExampPapers(UIContainer tofill,String siteId,Log log){
        String module = siteId.substring(0,7);
        ExamPaper examMetaData=new ExamPaper();  
        List examPaperList= examMetaData.getExamPaperMetaData(module, log);
        ExamPaperUI  examPaperUI=new  ExamPaperUI();
        examPaperUI.displayExampPapers(tofill, siteId, examPaperList, log);
}


}
