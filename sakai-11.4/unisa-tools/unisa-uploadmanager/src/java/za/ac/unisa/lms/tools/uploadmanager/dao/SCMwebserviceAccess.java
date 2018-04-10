package za.ac.unisa.lms.tools.uploadmanager.dao;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.w3c.dom.Document;

import utils.Utilities;

import com.unisa.uploadermanager.jaxb.beans.ModuleInfoRequest;
import com.unisa.uploadermanager.jaxb.beans.StudyMaterialResponse;

public class SCMwebserviceAccess {

	private static Log logger = LogFactory.getLog(SCMwebserviceAccess.class);
	
	public Document  getXMLDoc(String modCode, String year, String period, String lang, String type) throws Exception{
	       
		SCMwebserviceAccess sCMwebserviceAccess=new SCMwebserviceAccess();
        InputStream  studymaterialStream=sCMwebserviceAccess.getStudyMaterial(modCode,year,period);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc =dBuilder.parse(studymaterialStream);
	    return doc;
	 } 
	    
	 public InputStream getStudyMaterial(String courseId,String academicYear,String semester) throws Exception {
	    	
	     StudyMaterialResponse studyMaterialResponse = null;
	     InputStream stream = null;
         String webaddr=webaddr=getWebServiceUrl(courseId,academicYear,semester);

            
         ModuleInfoRequest request = new ModuleInfoRequest();
         request.setAcademicYear(Integer.parseInt(academicYear));
         request.setModuleCode(courseId);
         request.setSemesterPeriod(Integer.parseInt(semester));
        	
         String serverpath = ServerConfigurationService.getServerUrl();
         ResteasyClient client = new ResteasyClientBuilder().build();
        	
         if (Utilities.isPreProd()) {
        	 logger.info("current working on preproduction environment instance");
        	 ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(createAllTrustingClient());
        	 client = new ResteasyClientBuilder().httpEngine(engine).build();
        		//ResteasyWebTarget target = client.target("http://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial");
          }
            
           // if (Utilities.isTestEnvironment()){
           	   // ResteasyClient client = new ResteasyClientBuilder().build();
          ResteasyWebTarget target = client.target(webaddr);
          Response response = target.request().post(Entity.entity(request,MediaType.APPLICATION_XML));
          String repsonseString = response.readEntity(String.class);
        	    //repsonseString = repsonseString.replaceAll("<\\?xml.*\\?>","").trim();
          stream = new ByteArrayInputStream(repsonseString.getBytes());
        	//	 System.out.println("======================================\n\n");
        		 
        	//	System.out.println("repsonseString=="+repsonseString);
        		 
        	//	 System.out.println("======================================\n\n");
        		 
           if (response.getStatus() != 200) {
        	   logger.error("Failed : HTTP error code : "+ response.getStatus());
        	   throw new RuntimeException("Please note that UNISA study materials are not currently available for viewing. Access to the materials should be restored within an hour. UNISA apologizes for any inconvenience caused.");
            }
        		
        	response.close();

           	 

	    	
	    	
	  //  } /*else {*/
	    	
    	/*	ModuleInfoRequest request = new ModuleInfoRequest();
    		request.setAcademicYear(Integer.parseInt(academicYear));
    		request.setModuleCode(courseId);
    		request.setSemesterPeriod(Integer.parseInt(semester));*/
    		
    		/*ClientRequest target = new ClientRequest(webaddr).accept(MediaType.APPLICATION_XML);
    		target.body(MediaType.APPLICATION_XML, request);
    		
    		ClientResponse<StudyMaterialResponse> response = target.post(StudyMaterialResponse.class);		
          
    		 studyMaterialResponse = response.getEntity(StudyMaterialResponse.class);
  
	    	
	    }*/
            return stream;
	    }
	    
		private static DefaultHttpClient createAllTrustingClient() throws GeneralSecurityException {
			  
			SchemeRegistry registry = new SchemeRegistry();
			
			registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

			  TrustStrategy trustStrategy = new TrustStrategy() {
			    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			      return true;
			    }
			  };
			  SSLSocketFactory factory = new SSLSocketFactory(trustStrategy, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
			  registry.register(new Scheme("https", 443, factory));

			  ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			  mgr.setMaxTotal(1000);
			  mgr.setDefaultMaxPerRoute(1000);

			  DefaultHttpClient client = new DefaultHttpClient(mgr, new DefaultHttpClient().getParams());
			  return client;
		}
	    
	    
/*	    public  InputStream  getXmlStream(String courseId,String academicYear,short semester) throws Exception{
            InputStream stream=null;
            String webaddr=webaddr=getWebServiceUrl(courseId,academicYear,Short.toString(semester));
             if(Utilities.isTestEnvironment()){
            	 

         		ModuleInfoRequest request = new ModuleInfoRequest();
         		request.setAcademicYear(2014);
         		request.setModuleCode("AFK1501");
         		request.setSemesterPeriod(1);
         		
         		ClientRequest target = new ClientRequest("http://appsdev.int.unisa.ac.za/sharedservices/courseMaterial").accept(MediaType.APPLICATION_XML);
         		target.body(MediaType.APPLICATION_XML, request);
         		
         		ClientResponse<StudyMaterialResponse> response = target.post(StudyMaterialResponse.class);		
               
         		StudyMaterialResponse studyMaterialResponse1 = response.getEntity(StudyMaterialResponse.class);
             	for(ResourceDTO resourceDTO :studyMaterialResponse1.getResource()) {
             		
             		System.out.println("Barcode---->"+resourceDTO.getBarcode());
             		System.out.println("Date Available"+resourceDTO.getDateAvailable());
             	}
             	
             	
            	 
            	 

            	 
             }else{
           	      URL url = new URL(webaddr.toString());
                    stream=url.openStream();
             }
            return stream;
        }*/
	    private   String getWebServiceUrl(String courseId,String academicYear,String semester){
                                return Utilities.currServer();
	    }

	 public InputStream  getStream(String urlAddress) throws Exception {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
			};
			
			try {

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

			URL url = new URL(urlAddress);
			HttpURLConnection  con = (HttpURLConnection)url.openConnection();
			//System.out.println("status code--->"+con.getResponseCode());
			if (con.getResponseCode() == 500 || con.getResponseCode() == 404) {
				Exception myExcep = new Exception("Please note that UNISA study materials are not currently available for viewing. Access to the materials should be restored within an hour. UNISA apologizes for any inconvenience caused.");
				throw myExcep;
			}
			
			return con.getInputStream();
			} catch (Exception e) {
				throw e; 
			}
			
		}
	
}
