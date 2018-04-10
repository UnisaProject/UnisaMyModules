<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

     <sakai:html>
     <sakai:messages/>
     <html:form action="deanAuthorisation">     
     <sakai:heading>
    <b><fmt:message key="function.searchmoduleheading"/></b>
    </sakai:heading>	
    <sakai:instruction>
		<fmt:message key="function.searchmoduleinstr0"/><br/>		
	</sakai:instruction>
	
		   <sakai:group_heading>
			    <fmt:message key="function.searchmoduleinstr1"/>
			    <logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklists"/> </logic:equal>
    	        <logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistsforR"/> </logic:equal>
    	        <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistsforE"/> </logic:equal>
		   </sakai:group_heading>
         <sakai:group_table>
			<tr>
				<td><fmt:message key="label.entermodulecode"/><sakai:required/></td>
				<td><html:text property="courseId"></html:text></td>
				</tr>
		 </sakai:group_table>
		 <sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.displayoptions"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
			<html:hidden property="continueOption" value="searchmodule"/>
			<html:hidden property="cancelOption" value="mainview"/>
	</html:form>				
  </sakai:html>
	