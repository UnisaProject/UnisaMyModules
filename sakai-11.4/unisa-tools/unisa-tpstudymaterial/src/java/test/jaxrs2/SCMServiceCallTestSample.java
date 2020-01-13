package test.jaxrs2;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.xml.sax.SAXException;

import com.unisa.uploadermanager.jaxb.beans.ModuleInfoRequest;
import com.unisa.uploadermanager.jaxb.beans.ResourceDTO;
import com.unisa.uploadermanager.jaxb.beans.StudyMaterialResponse;

import za.ac.unisa.lms.tools.tpstudymaterial.dto.StudyMaterialDTO;
import za.ac.unisa.lms.tools.tpstudymaterial.util.ServerConfig;

public class SCMServiceCallTestSample {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, GeneralSecurityException {
		postExample();
	}


	private static void postExample() throws ParserConfigurationException,
			SAXException, IOException, GeneralSecurityException {

		ModuleInfoRequest request = new ModuleInfoRequest();
		request.setAcademicYear(2014);
		request.setModuleCode("COS3711");
		request.setSemesterPeriod(1);
//production url
		//https://apps.unisa.ac.za/sharedservices/courseMaterial
    //http://appsdev.int.unisa.ac.za/sharedservices/courseMaterial
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client
				.target("https://apps.unisa.ac.za/sharedservices/courseMaterial");
        Response response = target.request().post(
				Entity.entity(request, MediaType.APPLICATION_XML));
    	String repsonseString1 = response.readEntity(String.class);
    	System.out.println(repsonseString1);
		StudyMaterialResponse repsonseString = response.readEntity(StudyMaterialResponse.class);
		
   
		 repsonseString = response.readEntity(StudyMaterialResponse.class);

		  Collections.sort(repsonseString.getResource());
		  
		  List<StudyMaterialDTO> studyMaterialDTOList = new ArrayList<StudyMaterialDTO>();
		  
		for (ResourceDTO resourceDTO :repsonseString.getResource()) {
			StudyMaterialDTO studyMaterialDTO = new StudyMaterialDTO();
			studyMaterialDTO.setModuleCode("COS3711");
			
			
			String itemDisplayName = setItemDisplayName(resourceDTO.getDocumentType(),resourceDTO.getUnitNumber(),
					getlanguage(resourceDTO.getShortDescription()),studyMaterialDTO.getModuleCode());
			
			System.out.println("itemDisplayName-->"+itemDisplayName);
		      
			System.out.println("Unit Number ---->"+resourceDTO.getUnitNumber());
			System.out.println("Short Description ---->"+resourceDTO.getShortDescription());
			
			System.out.println("Available Date"+getDBDateFormat(toDate(resourceDTO.getDateAvailable())));
			System.out.println("language---->"+getlanguage(resourceDTO.getShortDescription()));
			
			
			
			System.out.println("itembarcode-->"+resourceDTO.getBarcode());
			System.out.println("Available Date-->"+resourceDTO.getDateAvailable());
			
			
		}

	/*	codesConverter.convertCode(documentType)+"  "+unitNumber+" ("+
        codesConverter.convertCode(language)+")"+ " for "+module;*/

		response.close();

	}
	

/*    public void setFilePath(){
   	            filePath= Utilities.getFilepath(filename, module,documentType);
     }*/

public static String getFilepath(String filename,String module,String docType){
	    String filePath=ServerConfig.getPathToMaterialOnServer()+module+"/"+docType+"/"+filename;
	    
	    File pdfFile = new File(filePath);
        if (!pdfFile.exists()) {
      	    filePath=ServerConfig.getPathToMaterialOnServer()+"collect"+"/"+module+"/"+docType+"/"+filename;
         }

     return filePath;
}
	
	public static Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }
	
	public static String getDBDateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    
            String d = format.format(date);
            System.out.println(""+d);
            Date d1 = null;
            try {
                d1 = format.parse(d);
                System.out.println("d1-->"+d1.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            return d;
        
	}
	
    public static String setItemDisplayName(String documentType,String unitNumber,
    		String language,String module){
        StudyMaterialCodesConverter codesConverter=new StudyMaterialCodesConverter();
        String itemDisplayName=codesConverter.convertCode(documentType)+"  "+unitNumber+" ("+
                   codesConverter.convertCode(language)+")"+ " for "+module;
        return itemDisplayName;
}
    
	public static long getFileSize(String filename,String module,String docType) {
	    
		long fileSize=0;
        
	    try {
        	String filePath = getFilepath(filename,module,docType);
        	File pdfFile = new File(filePath);
            fileSize = (pdfFile.length()/1000);
         }catch (Exception ex) {
	      	  return 0;
	      }
	    return fileSize;
    }
    
	 public  static String getlanguage(String itemShortDesdc){
	     	
	   	    int lastIndexOff_ = itemShortDesdc.lastIndexOf("_")+1;
	   	   	return  itemShortDesdc.substring(lastIndexOff_).trim().toUpperCase();
	   }

	private static DefaultHttpClient createAllTrustingClient()
			throws GeneralSecurityException {

		SchemeRegistry registry = new SchemeRegistry();

		registry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));

		TrustStrategy trustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				return true;
			}
		};
		SSLSocketFactory factory = new SSLSocketFactory(trustStrategy,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", 443, factory));

		ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(
				registry);
		mgr.setMaxTotal(1000);
		mgr.setDefaultMaxPerRoute(1000);

		DefaultHttpClient client = new DefaultHttpClient(mgr,
				new DefaultHttpClient().getParams());
		return client;
	}

}
