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
	   <fmt:message key="profile.update.confirm.heading"/>
	</sakai:heading>
	<sakai:messages/>
	<sakai:instruction>
	   <fmt:message key="profile.update.confirm.instruction"/>
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
				 <logic:equal name="telecentreForm" property="telUpdateNameCheckBox" value="true">
			     	<td><b><bean:write name ="telecentreForm" property="telecentreName"/></b></td>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateNameCheckBox" value="false">
			     	<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
			    		<td><bean:write name ="updateId" property="centre"/></td>
			    	</logic:iterate>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateEmailCheckBox" value="true">
			     	<td><b><bean:write name ="telecentreForm" property="telecentreEmail"/></b></td>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateEmailCheckBox" value="false">
			     	<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
			    		<td><bean:write name ="updateId" property="email"/></td>
			    	</logic:iterate>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateProvinceCheckBox" value="true">
			     	<td><b><bean:write name ="telecentreForm" property="province"/></b></td>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateProvinceCheckBox" value="false">
			     	<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
			    		<td><bean:write name ="updateId" property="province"/></td>
			    	</logic:iterate>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateActiveCheckBox" value="true">
			     	<td><b><bean:write name ="telecentreForm" property="telecentreStatus"/></b></td>
			     </logic:equal>
			     <logic:equal name="telecentreForm" property="telUpdateActiveCheckBox" value="false">
			     	<logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
			    		<td><bean:write name ="updateId" property="status"/></td>
			    	</logic:iterate>
			     </logic:equal>
		     	 <logic:iterate name="telecentreForm" property="telecentreInfo" id="updateId">
		    		<td><bean:write name ="updateId" property="code"/></td>
		    	 </logic:iterate>
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
			              <fmt:message key="profile.update.confirm.submitBtn"/>
			            </html:submit>
		            </sakai:actions>
		          </td>	
			  </tr>
	</sakai:group_table>
	</html:form >
</sakai:html>   