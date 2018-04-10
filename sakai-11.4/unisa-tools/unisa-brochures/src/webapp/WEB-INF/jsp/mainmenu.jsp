<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.brochures.ApplicationResources"/>

<sakai:html>
	
	<html:form action="/brochures.do">	

	<sakai:messages/>
	<sakai:group_heading>Brochure Management</sakai:group_heading>
	<sakai:group_table>
	<tr>
	<td><html:link href="brochures.do?act=myChoiceType&type=a">myChoice</html:link></td>
	</tr><tr>
	<td><html:link href="brochures.do?act=myChoiceType&type=b">myChoice M&D</html:link></td>
	</tr><tr>
	<td><html:link href="brochures.do?act=myRegistration&type=m">myRegistration</html:link></td>
	</tr><tr><tr>
	<td><html:link href="brochures.do?act=myRegistration&type=n">myRegistration M&D</html:link></td>
	</tr>
	<td><html:link href="brochures.do?act=myModules">myModules</html:link></td>
	</tr>
	<tr>
	<td><html:link href="brochures.do?act=auditReport">Audit Reports</html:link></td>
	</tr>
	</sakai:group_table>  
	</html:form>
</sakai:html>





