package org.sakaiproject.studymaterial.dao;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.sakaiproject.studymaterial.utils.Utilities;
import org.w3c.dom.Document;

public class ExamPaperDAO {
	public Document getXMLDocument(String courseName,Log log) {
		InputStream stream = null;
		Document doc=null;
	     try{
	          String webServiceUrl = Utilities.currServer() + "/rcmclient/Default.aspx?coursecode=" + courseName;
			  log.info("Official Study Material: examXmlConvert "+courseName);
			
			URL url = new URL(webServiceUrl);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    stream = (InputStream) url.openStream();
		    doc = docBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			stream=null;
	    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
	} finally {
		     Utilities.closeStream(stream);
	 }
			return doc;
  }
	
	
}
