<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>

<sakai:tool_bar>
	<html:link href="managementInfo.do?action=displayInput">
		<fmt:message key="link.managementInfo"/>
	</html:link>
</sakai:tool_bar>
<sakai:html>
	<html:form action="/examPaperCoverDocket">
	<sakai:heading><fmt:message key="heading.coverDocket"/></sakai:heading>
		<sakai:messages/>
		<sakai:messages message="true"/>
	<html:hidden name="examPaperCoverDocketForm" property="currentPage" value="input"/>
		<sakai:instruction>
			<fmt:message key="initial.instruction"/>			
		</sakai:instruction>
		<logic:notEmpty name="examPaperCoverDocketForm" property="exampapers">
		<sakai:group_table>	
			<logic:iterate name="examPaperCoverDocketForm" property="exampapers" id="papers" indexId="index">
				<tr>
				<td><html:radio property="selectedExampaper" value='<%= "" + index.toString() + "" %>'></html:radio></td>
				<td><bean:write name="papers"/></td>
				</tr>			 
			</logic:iterate>
		</sakai:group_table>
		</logic:notEmpty>			
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>