<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
<h3><fmt:message key="admin.heading"/></h3>
	<hr/><br/>
	<sakai:messages/>
	<html:form action="booklistadmin">
	
			 <sakai:instruction>	
		<b><fmt:message key="function.viewinstructiontext"/>  <bean:write name="booklistAdminForm" property="year"/><br>Status:
		 <bean:write name="booklistAdminForm" property="status"/><br>
		 
		 College: <bean:write name="booklistAdminForm" property="college"/></b>
		 <p/>
		 <fmt:message key="function.viewinstructioncount"/> <b><bean:write name="booklistAdminForm" property="courseCount"/></b><p/>
	    <logic:notEqual name="booklistAdminForm" property="typeOfBookList" value="E">
		<fmt:message key="function.viewinstructiontext1"/></logic:notEqual>
		<logic:equal name="booklistAdminForm" property="typeOfBookList" value="E">
		<fmt:message key="function.viewinstructiontext1forE"/></logic:equal>
	     </sakai:instruction>
	  
	  	     <sakai:flat_list>
	  	      <sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions><p/>
	     <logic:iterate  name="booklistAdminForm" property="courselist" id="record" indexId="index">   
	     <tr>									
		 <td><b><html:link href="booklistadmin.do?action=editmodule"
								paramName="record" paramProperty="value" paramId="courseId">View</html:link> <bean:write name="record" property="value"/></td>
		</tr>
	     </logic:iterate>
	     </sakai:flat_list><br>
	     <input type="hidden" name="cancelOption" value="TOCOURSEVIEW">
	     <sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		

		</html:form>
		</sakai:html>
		