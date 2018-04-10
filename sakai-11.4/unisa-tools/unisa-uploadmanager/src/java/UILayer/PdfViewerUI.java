package UILayer;

import impl.MetaData;
import impl.PdfViewer;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import  za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
public class PdfViewerUI {
	
	
	public  String createViewStudyMaterialScreen(UploadManagerForm uploadManagerForm,ActionMessages messages) throws Exception{
		                MetaData metData=new MetaData();
		                String errorMsg=metData.setMetaDataToFormBean(uploadManagerForm);
                        if(!errorMsg.equals("")){
                        	if (errorMsg.split(":").length > 1) {
      	                    messages.add(ActionMessages.GLOBAL_MESSAGE,
					               new ActionMessage(errorMsg.split(":")[1], errorMsg.split(":")[0]));
                        	} else {
                        		
                        		  messages.add( ActionMessages.GLOBAL_MESSAGE,
 		                                 new ActionMessage("errors.message",errorMsg));
                        	}
      	                                  return "mainfilter";
                       }
                       return "viewstudymaterial";
    }
	public void outPutDataToClient(HttpServletResponse response, String pdffullPath) throws IOException{
		              PdfViewer pdfViewer=new PdfViewer();
		              pdfViewer.outPutDataToClient(response, pdffullPath);
   }


}
