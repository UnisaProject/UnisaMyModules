<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>


<sakai:html>
  	<html:form action="/satbookAdmin.do">
  	<fmt:message key="adminvenuesdelete.instruction1"/>
  	
  	<p>
  	<bean:write name="adminForm" property="regionDesc"/>
  	  	<!--  bean:write name="classroomForm" property="regionDesc"/--> 
  		
 <sakai:actions>
  	<html:submit property="action">
	        <fmt:message key="adminvenues.button.delete"/>
	    </html:submit>
	<html:submit property="action">
	     <fmt:message key="adminvenues.button.cancel"/>
	   </html:submit>
  </sakai:actions> 	
  	</html:form>
</sakai:html>