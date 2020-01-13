package test.jaxrs2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;


import com.unisa.uploadermanager.jaxb.beans.*;

import org.jboss.resteasy.client.ClientRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Demo_JAXRS_2_Example 
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException 
	{
        
	//	getExample_one();
	//	getExample_two();
		postExample();
	}

	private static void getExample_one() 
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/RESTEasyApplication/user-management/users");
        Response response = target.request().get();
        //Read output in string format
        String value = response.readEntity(String.class);
        System.out.println(value);
        response.close();  
	}
	
	private static void getExample_two()
	{
/*		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/RESTEasyApplication/user-management/users");
        Response response = target.request().get();
        //Read the entity
        Users users = response.readEntity(Users.class);
        for(User user : users.getUsers()){
        	System.out.println(user.getId());
        	System.out.println(user.getLastName());
        }
        response.close();  */
	}
	
	private static void postExample() throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException 
	{
/*		User user = new User();
		user.setFirstName("john");
		user.setLastName("Maclane");
		
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/RESTEasyApplication/user-management/users");
        Response response = target.request().post(Entity.entity(user, "application/vnd.com.demo.user-management.user+xml;charset=UTF-8;version=1"));
        //Read output in string format
        System.out.println(response.getStatus());
        response.close();  */
		
		ModuleInfoRequest request = new ModuleInfoRequest();
		request.setAcademicYear(2015);
		request.setModuleCode("COS3711");
		request.setSemesterPeriod(1);
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(createAllTrustingClient());
		 client = new ResteasyClientBuilder().httpEngine(engine).build();
		
	//	client.getSslContext();
//		https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial
		ResteasyWebTarget target = client.target("https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial");
		
		//ClientRequest target = new ClientRequest("").accept(MediaType.APPLICATION_XML);
		//target.body(MediaType.APPLICATION_XML, request);
		
		Response response = target.request().post(Entity.entity(request,MediaType.APPLICATION_XML));
		
		//org.jboss.resteasy.client.ClientResponse response = target.post();		
		
	//	String studyMaterialResponse = target.post(StudyMaterialResponse.class);

		//System.out.println("studyMaterialResponse:"+studyMaterialResponse);
		String repsonseString = response.readEntity(String.class);
		//repsonseString = repsonseString.replaceAll("<\\?xml.*\\?>","").trim();
		System.out.println(repsonseString);
		
		InputStream stream = new ByteArrayInputStream(repsonseString.getBytes());
		
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc =dBuilder.parse(stream);
       // NodeList nList = doc.getElementsByTagName("resource");
    	NodeList nList = doc.getElementsByTagName("Resource");
		// String filefullPath = "";
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			
			System.out.println("itembarcode-->"+eElement.getAttribute("barcode"));
			
			
		}
		
		response.close();
		
		
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
	

	
}
