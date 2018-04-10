package org.sakaiproject.studymaterial.dao;
import org.sakaiproject.studymaterial.utils.*;
import java.io.InputStream;
import java.net.URL;
public class WebserviceAccess {

	public  InputStream  getStudyInfoDataInXML(String courseId,String academicYear,short semester) throws Exception{
		                      InputStream stream=null;
		          /*            StringBuilder webaddr=new StringBuilder();
		                      webaddr.append(getWebServiceUrl(courseId,academicYear,semester).toString());
 	                          if(Utilities.isTestEnvironment()){
 	                        	      AllTrustingCertVerifier allTrustingCertVerifier=new AllTrustingCertVerifier();
 	                                  stream=allTrustingCertVerifier.getStream(webaddr.toString(),courseId,academicYear,semester);
 	                          }else{
 	                        	      URL url = new URL(webaddr.toString());
                                      stream=url.openStream();
 	                          }
 	                         System.out.println("stram is fine  today");*/
                              return stream;
	 }
	private   String getWebServiceUrl(String courseId,String academicYear,short semester){
          String url=Utilities.currServer();
        return  url;
     }
}
