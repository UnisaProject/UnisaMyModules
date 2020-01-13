<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>


<sakai:html>
  	<html:form action="/satbookDaily.do">
  	<fmt:message key="dailyview.confirm.delete"/>
  	<p>
  	  	<bean:write name="satbookDailyForm" property="bkngHeading"/> 
  		
 <sakai:actions>
  	<html:submit property="action">
	        <fmt:message key="dailyview.button.confirmdelete"/>
	    </html:submit>
	<html:submit property="action">
	     <fmt:message key="dailyview.button.cancel"/>
	   </html:submit>
  </sakai:actions> 	
  	</html:form>
</sakai:html>

			
  	