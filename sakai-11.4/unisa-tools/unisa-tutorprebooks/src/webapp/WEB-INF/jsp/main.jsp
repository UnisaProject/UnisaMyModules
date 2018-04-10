<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorprebooks.ApplicationResources"/>

<sakai:html>
<sakai:messages/>
		

	<sakai:group_heading>
		<fmt:message key="tutorprebooks.title"/> 
	</sakai:group_heading>
	
	<p>
		
	<fmt:message key="info.required"/> <sakai:required/>
		<html:form action="/tutorMain">
	 <sakai:group_table>
		
		<tr>	
			<td><fmt:message key="main.acadyear"/> <sakai:required/> </td>
			<td>
				<html:select name="mainForm" property="year">
					<html:options collection="yearOptions" property="value" labelProperty="label"/>
				</html:select>				
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td><fmt:message key="main.acadperiod"/> <sakai:required/> </td>
			
			<td>
				<html:select name="mainForm" property="acadPeriod">
					<html:options collection="periodOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		
		<tr>
		   </tr>
	
		  <tr> <td>
	 <sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.search" />
				</html:submit>
				<html:submit property="action">
					<fmt:message key="button.clear" />
				</html:submit>
				<html:submit property="action">
					<fmt:message key="button.report" />
				</html:submit>
    </sakai:actions>
    </td> </tr>

		  <logic:notEmpty name="mainForm" property="courseList">
		  	<th><fmt:message key="tutorprebooks.course"/></th>
	 	   	<th><fmt:message key="tutorprebooks.numberoftutors"/></th>
	 	   	<th><fmt:message key="tutorprebooks.lastmodifiedby"/></th>
		  <logic:iterate name="mainForm" property="courseList" id="c" indexId="cindex">
			<tr>
			<td><bean:write name="c" property="coursecode"/></td>
			<td> <html:text name="mainForm" property='<%="recordIndexed["+cindex+"].numberOfTutors"%>' size="10" maxlength="999"/></td>
			<td><bean:write name="c" property="lastModifiedBy"/></td>
			</tr>
		</logic:iterate>
	</tr>
	<tr> <td>
	
	 <sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.submit" />
				</html:submit>
				<html:submit property="action">
					<fmt:message key="button.clear2" />
				</html:submit>
			
    </sakai:actions>
    </td> </tr>
		</logic:notEmpty>						

 </sakai:group_table>

 </html:form>
</sakai:html>