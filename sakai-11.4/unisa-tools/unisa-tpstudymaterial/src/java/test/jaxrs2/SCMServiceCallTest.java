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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.unisa.uploadermanager.jaxb.beans.ModuleInfoRequest;

public class SCMServiceCallTest {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, GeneralSecurityException {
		postExample();
	}


	private static void postExample() throws ParserConfigurationException,
			SAXException, IOException, GeneralSecurityException {

		ModuleInfoRequest request = new ModuleInfoRequest();
		request.setAcademicYear(2015);
		request.setModuleCode("CIP2601");
		request.setSemesterPeriod(2);

		/*
		 * ResteasyClient client = new ResteasyClientBuilder().build();
		 * ResteasyWebTarget target = client.target(
		 * "https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial"
		 * );
		 */

		ResteasyClient client = new ResteasyClientBuilder().build();

		 ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(createAllTrustingClient());
		 client = new ResteasyClientBuilder().httpEngine(engine).build();

		// client.getSslContext();
		// https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial
		ResteasyWebTarget target = client
				.target("https://lmkn-ealb01sv.int.unisa.ac.za/sharedservices/courseMaterial");

		// ClientRequest target = new
		// ClientRequest("").accept(MediaType.APPLICATION_XML);
		// target.body(MediaType.APPLICATION_XML, request);

		Response response = target.request().post(
				Entity.entity(request, MediaType.APPLICATION_XML));

		// org.jboss.resteasy.client.ClientResponse response = target.post();

		// String studyMaterialResponse =
		// target.post(StudyMaterialResponse.class);

		// System.out.println("studyMaterialResponse:"+studyMaterialResponse);
		String repsonseString = response.readEntity(String.class);
		// repsonseString =
		// repsonseString.replaceAll("<\\?xml.*\\?>","").trim();
		System.out.println(repsonseString);

		InputStream stream = new ByteArrayInputStream(repsonseString.getBytes());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stream);
		// NodeList nList = doc.getElementsByTagName("resource");
		NodeList nList = doc.getElementsByTagName("Resource");
		// String filefullPath = "";
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;

			System.out.println("itembarcode-->"
					+ eElement.getAttribute("barcode"));
			
			System.out.println("dateAvailable--->"+eElement.getAttribute("dateAvailable"));

		}

		response.close();

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
