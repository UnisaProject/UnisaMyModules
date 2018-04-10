<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.signoff.ApplicationResources"/>
<sakai:html>	
	<html:form action="/signoff.do">
	 <html:hidden property="atStep" value="1"/>	
	<sakai:messages/>
	  <sakai:heading>
	  <tr><td>
	  <fmt:message key="signoff.remove.confirm"/></td>
	  </sakai:heading> 
	 <sakai:group_table>
	 <sakai:flat_list>
	        <th>S.No</th>
	 	   	<th><fmt:message key="signoff.level"/></th>
	 	   	<th><fmt:message key="signoff.name"/></th>	  
			<logic:notEmpty name="signoffForm" property="removePersonList">
			<logic:iterate name="signoffForm" property="removePersonList" id="d" indexId="dindex">
				<tr>
				    <td>   
		    			<bean:write name="d" property="sno"/>
		    		</td>
					<td>   
		    			<bean:write name="d" property="management_level"/>
		    		</td>
		    		<td>   
		    			<bean:write name="d" property="name"/>
		    		</td>
				</tr>
			</logic:iterate>
			</logic:notEmpty>
	</sakai:flat_list>
	</sakai:group_table>
	<sakai:group_table>
	<tr><td>
     <sakai:actions>
     <html:submit styleClass="button" property="act">
	  <fmt:message key="button.removeconfirm"/>
	 </html:submit>
     <html:submit styleClass="button" property="act">
	  <fmt:message key="button.cancel"/>
	 </html:submit>
     </sakai:actions>
     </td></tr>
		 </sakai:group_table>
	</html:form>
</sakai:html>





