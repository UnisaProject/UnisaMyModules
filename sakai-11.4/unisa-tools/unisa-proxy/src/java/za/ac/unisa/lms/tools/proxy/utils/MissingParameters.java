package za.ac.unisa.lms.tools.proxy.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MissingParameters {

	public static void printMissingParameters(HttpServletRequest request, HttpServletResponse response, String reqParms
	) throws ServletException, IOException {
		
		String head = request.getAttribute("sakai.html.head").toString();
		String bodyOnLoad = request.getAttribute("sakai.html.body.onload").toString();
		
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/html");
		
		out.println("<html>");
		out.println("<head>"+head+"</head>");
		out.println("<body onload=\""+bodyOnLoad+"\" >");
		out.println("<div class=\"portletBody\"> ");
		out.println("<p>This tool has not yet been set up correctly by the site administrator.</p>");
		out.println("<!-- "+reqParms+"-->");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}