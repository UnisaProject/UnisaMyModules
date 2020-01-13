package org.sakaiproject.studymaterial.tool.producers;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import javax.servlet.http.HttpServletResponse;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import uk.org.ponder.rsf.content.ContentTypeInfoRegistry;
import uk.org.ponder.rsf.view.DataView;

import org.sakaiproject.studymaterial.impl.PdfCreater;
import org.sakaiproject.studymaterial.impl.PdfCreater;
import org.sakaiproject.studymaterial.tool.params.StudyMaterialParams;

public class PdfProducer  implements  DataView , ViewParamsReporter{

	    public ViewParameters getViewParameters() {
		      return new StudyMaterialParams();
	    }
	    private HttpServletResponse httpServletResponse;
		public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
			this.httpServletResponse = httpServletResponse;
		}
  	    public static final String VIEW_ID = "Pdf";
	    public String getViewID() {
       		    return VIEW_ID;
	    }
	    public String getContentType() {
		        return ContentTypeInfoRegistry.CUSTOM;
	    }
	    public Object getData(ViewParameters viewparams) {
 			                 StudyMaterialParams studyMaterialparams = (StudyMaterialParams) viewparams;
	                         String filepath= studyMaterialparams.filepath;
	                         String filename = studyMaterialparams.itemShortDesc;
	                         httpServletResponse.setContentType("application/pdf");
	                         httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + filename );
	                         try{
	                               return  PdfCreater.getStreamOfFileInWeb(filepath);
	                         }catch(Exception ex){
	                        	    return "The file was not found";
	                         }
	    }
}
