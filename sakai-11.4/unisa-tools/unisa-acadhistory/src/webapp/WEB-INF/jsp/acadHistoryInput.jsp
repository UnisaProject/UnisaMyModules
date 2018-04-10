<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.acadhistory.ApplicationResources"/>
<sakai:html>
	<html:form action="/displayAcadHistory">
	      <logic:empty name="acadHistoryDisplayForm" property="student">
	             <sakai:instruction><font color="red"><fmt:message key="page.nullstudenterror"/></font></sakai:instruction>
	     </logic:empty>
	      <logic:notEmpty name="acadHistoryDisplayForm" property="student">
	               <sakai:messages/>
	               <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	               <sakai:instruction><fmt:message key="page.input.instruction"/></sakai:instruction>
	               <sakai:group_table>
	                   <tr>
		                    <td><fmt:message key="page.studentNumber"/>&nbsp;</td>
		                    <td> <html:text   property="student.number" size="10" maxlength="8"/></td>
		               </tr>
	               </sakai:group_table>
	               <sakai:actions>
	                     <html:hidden property="action" value="displayAcadHistory"/>
		                <html:submit titleKey="button.display"><fmt:message key="button.display"/></html:submit>
	                </sakai:actions>
        </logic:notEmpty>
    </html:form>
</sakai:html>

