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
     <html:form action="authorisation">     
     <sakai:heading>
    <b><fmt:message key="function.authsdheading"/></b>
    </sakai:heading>	
    <sakai:instruction>
		<fmt:message key="function.instructiontext"/><br/>
		<fmt:message key="function.instructiontext1"/>
	</sakai:instruction>
	
		   <sakai:group_heading>
			    <fmt:message key="label.allbooklists"/>
		   </sakai:group_heading>
				<sakai:flat_list> 
		 <tr><td><html:link href="authorisation.do?action=allmoduleslist"><fmt:message key="link.authorizeavailablemodules"/></html:link></td>
		</tr></sakai:flat_list><br>OR
	
	   <sakai:group_heading>
			    <fmt:message key="label.selectbooklisttypemsg"/>
		   </sakai:group_heading>
	<sakai:instruction>
			<html:radio name="bookMenuForm"  property="typeOfBookList" value="P"><fmt:message key="function.updatepbooksmsg"/></html:radio><br/>
			<html:radio name="bookMenuForm"  property="typeOfBookList" value="R"><fmt:message key="function.recbooksmsg"/></html:radio><br/>
			<html:radio property="typeOfBookList" value="E"><fmt:message key="function.earticlesmsg"/></html:radio><p/>			
			<p/>
		</sakai:instruction>	
	<p/>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.display"/></html:submit>
		</sakai:actions>

		
		<logic:notEqual name="bookMenuForm" property="typeOfBookList" value="">		 
		<sakai:group_heading>
		<fmt:message key="label.selectoptions"/>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklists"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistsforR"/> </logic:equal>
    	<logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistsforE"/> </logic:equal>
    	</sakai:group_heading>	
    	<sakai:flat_list>   
		<tr> <td>1. <html:link href="authorisation.do?action=displayoptions"><fmt:message key="link.displaylists"/></html:link> </td><tr>
    	 <tr><td>2. <html:link href="authorisation.do?action=authorizepermodule"><fmt:message key="link.authorizemodules"/></html:link></td></tr>
         </tr>	</sakai:flat_list>
		 </logic:notEqual>
	</html:form>				
  </sakai:html>
	