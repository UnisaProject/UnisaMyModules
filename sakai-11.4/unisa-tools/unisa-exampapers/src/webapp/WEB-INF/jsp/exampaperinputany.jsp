<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>


<sakai:html>
	<logic:equal name="examPaperCoverDocketForm" property="fromSystem" value="xxx">
		<script>
			window.location="https://mydev.unisa.ac.za/unisa-findtool/default.do?sharedTool=unisa.exampaperonline"
		</script>
	</logic:equal>
	<sakai:tool_bar>
		<html:link href="managementInfo.do?action=displayInput">
			<fmt:message key="link.managementInfo"/>
		</html:link>
	</sakai:tool_bar>
	<html:form action="/examPaperCoverDocket">
		<sakai:heading><fmt:message key="heading.coverDocket"/></sakai:heading>
		<html:hidden name="examPaperCoverDocketForm" property="currentPage" value="input"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="initialAny.instruction"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.examYear"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="exampaper.examYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
				<td>
					<html:select name="examPaperCoverDocketForm" property="exampaper.examPeriod">
					<html:optionsCollection name="examPaperCoverDocketForm" property="examPeriodCodes" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="exampaper.studyUnit" size="10" maxlength="7"/></td>								
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="exampaper.paperNo" size="2" maxlength="1"/></td>
			</tr>
		</sakai:group_table>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>			
		</sakai:actions>			
	</html:form>
</sakai:html>