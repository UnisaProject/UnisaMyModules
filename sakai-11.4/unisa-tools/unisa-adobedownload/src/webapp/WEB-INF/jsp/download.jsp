<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.adobedownload.ApplicationResources"/>

<sakai:html>
<sakai:messages/>
<sakai:messages message="true"/>
<html:form action="/addcourse"> 
	
	
	
	<sakai:actions>
					<html:submit property="action">
					<fmt:message key="course.button.cancel" />
				</html:submit>
	</sakai:actions>
	

</html:form>
</sakai:html>