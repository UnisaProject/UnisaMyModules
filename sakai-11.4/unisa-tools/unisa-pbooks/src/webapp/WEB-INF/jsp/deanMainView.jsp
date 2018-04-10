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
		<fmt:message key="function.deaninstructiontext"/> <b><bean:write name="bookMenuForm" property="college"/></b><br/><p/>
		<fmt:message key="function.deaninstructiontext1"/> <br/></sakai:instruction><p/>
		
		<sakai:group_table>  
		<tr> <td> <fmt:message key="function.deandepartments" /></td>
     <td><html:select property="department" >
     	           <html:option value="-1">Select a Department</html:option>
        		    <html:options  collection="deanDepartments" property="value" labelProperty="label" />
    	</html:select>   
	 	</td></tr> 
	 	</sakai:group_table>
	 	
	 		
		<sakai:flat_list>
			<html:hidden property="cancelOption" value="mainview"/>
  <tr><td><sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions></td></tr>
		      
			</sakai:flat_list>
		

</html:form>
 </sakai:html> 