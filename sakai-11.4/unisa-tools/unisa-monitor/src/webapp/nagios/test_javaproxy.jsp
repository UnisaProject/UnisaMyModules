<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="za.ac.unisa.lms.tools.monitor.dao.StudentSystemQueryDAO" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="za.ac.unisa.exceptions.JavaProxyExceptionListener" %>
<%@ page import="Saaal40j.Abean.Saaal40jGetStudentCourses" %>
<%@ page import="za.ac.unisa.utils.CoursePeriodLookup" %>

<html>
<body>

<pre>
<%
	InputStream is = this.getClass().getResourceAsStream("/commcfg.properties");
	int b = is.read();
	while (b > 0) {
		out.print((char) b);
		b = is.read();
	}
	String hostname = java.net.InetAddress.getLocalHost().getHostName();
%>
</pre>

<p>Courses for 41256557 <%=hostname%></p>
<ul>

<%
	Saaal40jGetStudentCourses op = new Saaal40jGetStudentCourses();
	JavaProxyExceptionListener exception = new JavaProxyExceptionListener();
	op.addExceptionListener(exception);
	op.clear();
	op.setAsStringInWsStudentAnnualRecordMkStudentNr("41256557");
	op.execute();
	
	if (exception.getException() != null)
		throw exception.getException();
	if (op.getExitStateType() < 3)
		throw new Exception(op.getExitStateMsg());

	for (int i = 0; i < op.getOutStusunCount(); i++) {
		String course = op
				.getOutGWsStudentStudyUnitMkStudyUnitCode(i)
				.toUpperCase();
		String period = CoursePeriodLookup.getCourseTypeAsString(op
				.getOutGWsStudentStudyUnitSemesterPeriod(i));
		String acadyear = new Short(op
				.getOutGWsStudentAnnualRecordMkAcademicYear(i))
				.toString();
		acadyear = acadyear.substring(2);

		String coursecode = course + "-" + acadyear + "-" + period;
		
		%><li><%= coursecode %></li><%
	}
%>

	</ul>

	<p>NAGIOS_TEST_SUCCEEDED</p>
	
</body>
</html>

