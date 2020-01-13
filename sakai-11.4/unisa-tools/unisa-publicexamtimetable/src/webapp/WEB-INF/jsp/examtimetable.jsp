<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.publicexamtimetable.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
 	 <sakai:group_table>
						<tr>
							<td><img src="http://www.unisa.ac.za/static/corporate_web/Design/themes/default/images/unisa%20logo.svg"  WIDTH="216.7px" HEIGHT="46.5px"/></td>
						</tr>
  </sakai:group_table>
	<sakai:heading><fmt:message key="examtimetable.display.heading"/> <bean:write name="examtimetableform" property="examPeriodDesc"/></sakai:heading>
	<html:link href="examtimetable.do?action=inputSubject&examPeriod=6">View the May / June 2006 timetable</html:link><br>
	<html:link href="examtimetable.do?action=inputSubject&examPeriod=10">View the October / November 2006 timetable</html:link>
</sakai:html>
