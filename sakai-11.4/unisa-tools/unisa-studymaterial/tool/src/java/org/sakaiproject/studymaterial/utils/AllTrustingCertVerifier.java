package org.sakaiproject.studymaterial.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;


import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.sakaiproject.component.cover.ServerConfigurationService;

import com.unisa.uploadermanager.jaxb.beans.ModuleInfoRequest;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class AllTrustingCertVerifier {
	
	private static Log logger = LogFactory.getLog(AllTrustingCertVerifier.class);
	
	public InputStream  getStream(String urlAddress,String courseId,String academicYear,short semester) throws Exception {
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
		
	 	InputStream stream = null;
		 
    	ModuleInfoRequest request = new ModuleInfoRequest();
		request.setAcademicYear(Integer.parseInt(academicYear));
		request.setModuleCode(courseId);
		request.setSemesterPeriod(((Short)semester).intValue());
		String serverpath = ServerConfigurationService.getServerUrl();
		ResteasyClient client = new ResteasyClientBuilder().build();
		
		if(serverpath.contains("myqa")) {
        	
    		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(createAllTrustingClient());
    		client = new ResteasyClientBuilder().httpEngine(engine).build();
    		//ResteasyWebTarget target = client.target("http://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial");
    	}
		
		//ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(urlAddress);
		Response response = target.request().post(Entity.entity(request,MediaType.APPLICATION_XML));
		String repsonseString = response.readEntity(String.class);
	//repsonseString = repsonseString.replaceAll("<\\?xml.*\\?>","").trim();
		 stream = new ByteArrayInputStream(repsonseString.getBytes());
		// logger.info("repsonseString=="+repsonseString);
		 logger.info("response status--->"+response.getStatus());
		 
		 if (response.getStatus() != 200) {
			 logger.error("Failed : HTTP error code : "+ response.getStatus());
			 throw new RuntimeException("Oracle SCM service is down");
		  }
		
		 response.close();

	//	URL url = new URL(urlAddress);
	//	URLConnection con = url.openConnection();
		//return con.getInputStream();
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
}