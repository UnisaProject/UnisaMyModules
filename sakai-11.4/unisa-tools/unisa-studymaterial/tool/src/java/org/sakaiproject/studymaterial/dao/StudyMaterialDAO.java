package org.sakaiproject.studymaterial.dao;
import org.sakaiproject.studymaterial.utils.*;
import java.io.InputStream;
import java.net.URL;
public class StudyMaterialDAO {

	public  InputStream  getStudyInfoDataInXML(String courseId,String academicYear,short semester) throws Exception{
		                      InputStream stream=null;
		                      StringBuilder webaddr=new StringBuilder();
		                      webaddr.append(Utilities.scmServerUrl().toString());
 	                        // if(Utilities.isTestEnvironment()){
 	                        	      AllTrustingCertVerifier allTrustingCertVerifier=new AllTrustingCertVerifier();
 	                                  stream=allTrustingCertVerifier.getStream(webaddr.toString(),courseId,academicYear,semester);
 	                         /* }else{
 	                        	      URL url = new URL(webaddr.toString());
                                      stream=url.openStream();
 	                         }*/
                              return stream;
	 }
	private   String getWebServiceUrl(String courseId,String academicYear,short semester){
          String url=Utilities.currServer()+"/sharedservices/scmservice?module="+courseId+
              "&year="+academicYear+"&period="+semester;
        return  url;
     }
}
