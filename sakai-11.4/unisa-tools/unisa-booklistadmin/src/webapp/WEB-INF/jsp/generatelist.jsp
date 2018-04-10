<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources"/>

<sakai:html>
<html:form action="booklistadmin">
<h3><fmt:message key="admin.heading"/></h3>
	<hr/>
	<script type="text/javascript">
			function makeChoice()
			{						
			if( document.booklistAdminForm.includeYear[0].checked)
			{		
			document.booklistAdminForm.listyear.disabled=false;			
			}
			else
			{
			document.booklistAdminForm.listyear.disabled=true;			
			}			
			}				
		</script>
	<sakai:instruction><b><fmt:message key="label.generatelistinstr"/>
	</b></sakai:instruction>
	<sakai:group_heading>		
			<fmt:message key="label.chooseyearinstr"/>
		</sakai:group_heading>
		<sakai:group_table>
		<tr><td><fmt:message key="label.selectbooklistyear"/></td>
		<td><html:select property="year">
		<html:option value="${booklistAdminForm.nextYear}">
				<bean:write name="booklistAdminForm" property="nextYear"/>
			</html:option><html:option value="${booklistAdminForm.currentYear}">
				<bean:write name="booklistAdminForm" property="currentYear"/>
			</html:option></html:select></td></tr>
		
			 <logic:equal name="booklistAdminForm" property="typeOfBookList" value="P">
		  <tr><td><fmt:message key="label.includestudentsinreport"/></td>
				<td><html:radio name="booklistAdminForm" property="includeYear" value="true"
						onclick="makeChoice()"> <fmt:message key="label.includestudentsyes"/></html:radio>
						<html:radio name="booklistAdminForm" property="includeYear" value="false"
						onclick="makeChoice()"> <fmt:message key="label.includestudentsno"/></html:radio>
					</td></tr>
					<tr><td><fmt:message key="label.selectYearforExport"/></td>
					<td>
					<html:select name="booklistAdminForm" property="listyear">					
		        	<html:option value="${booklistAdminForm.currentYear}">
				  <bean:write name="booklistAdminForm" property="currentYear"/></html:option>
				  <html:option value="${booklistAdminForm.lastYear}">
			     	<bean:write name="booklistAdminForm" property="lastYear"/>
			       </html:option>
				  </html:select>
						</td></tr>
		</logic:equal>
	</sakai:group_table> 
	<input type="hidden" name="cancelOption" value="TOCOURSEVIEW">
	<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.generateList"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
	</sakai:html>