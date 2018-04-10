<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<sakai:heading>
	   <fmt:message key="profile.create.confirm.heading"/>
	</sakai:heading>
	<sakai:messages/>
	<sakai:instruction>
	   <fmt:message key="profile.create.confirm.instruction"/>
	</sakai:instruction>
	<html:form action="/telecentre.do">	
	<sakai:flat_list>
			<tr>
			     <th><fmt:message key="profile.create.confirm.name"/></th>
			     <th><fmt:message key="profile.create.confirm.email"/></th>
			     <th><fmt:message key="profile.create.confirm.province"/></th>
			     <th><fmt:message key="profile.create.confirm.active"/></th>
			     <th><fmt:message key="profile.create.confirm.telecode"/></th>
			</tr>
			<tr>
			     <td><bean:write name ="telecentreForm" property="telecentreName"/></td>
			     <td><bean:write name ="telecentreForm" property="telecentreEmail"/></td>
			     <td><bean:write name ="telecentreForm" property="province"/></td>
			     <td><bean:write name ="telecentreForm" property="telecentreStatus"/></td>
			     <td><bean:write name ="telecentreForm" property="telecode"/></td>
			</tr>
	</sakai:flat_list>
	<sakai:group_table>
	         <tr>
	             <td>
	             	<sakai:actions>
	             		<html:submit styleClass="button" property="action">
			                  <fmt:message key="telecentre.main"/>
			            </html:submit>	             
			            <html:submit styleClass="button" property="action">
			              	  <fmt:message key="profile.create.confirm.submitBtn"/>
			            </html:submit>
		             </sakai:actions>
		         </td>	
			  </tr>
	</sakai:group_table>
	</html:form >
</sakai:html>   