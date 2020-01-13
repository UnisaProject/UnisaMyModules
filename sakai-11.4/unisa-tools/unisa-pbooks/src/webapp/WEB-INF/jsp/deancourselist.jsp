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
	    <sakai:instruction>
	    <b><fmt:message key="function.selecteddept"/> <b><bean:write name="bookMenuForm" property="deptName"/></b><br/>
		<b><fmt:message key="function.selectedstatus"/> <b><bean:write name="bookMenuForm" property="status"/></b><br/>	
		<b><fmt:message key="function.selectedbooklisttype"/> 
			<logic:equal name="bookMenuForm" property="typeOfBookList" value="P">	<fmt:message key="function.booklists"/> </logic:equal>
    	    <logic:equal name="bookMenuForm" property="typeOfBookList" value="R">	<fmt:message key="function.booklistsforR"/> </logic:equal>
    	    <logic:equal name="bookMenuForm" property="typeOfBookList" value="E">	<fmt:message key="function.booklistsforE"/> </logic:equal></b><br/>
    	    <b><fmt:message key="booklist.datelabel"/> <b><bean:write name="bookMenuForm" property="listDate"/></b><br/>
      </sakai:instruction><p/>
      
	  	 <sakai:flat_list>	  	     
	     <logic:iterate  name="bookMenuForm" property="courselist" id="record" indexId="index">   
	     <tr>									
		 <td><b><bean:write name="record" property="value"/></b></td>
		  <td><b><bean:write name="record" property="label"/></b></td>
		</tr>
	     </logic:iterate>
	     </sakai:flat_list><br>
	     
	     <input type="hidden" name="cancelOption" value="prevstep">
	     <sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
		

		</html:form>
		</sakai:html>