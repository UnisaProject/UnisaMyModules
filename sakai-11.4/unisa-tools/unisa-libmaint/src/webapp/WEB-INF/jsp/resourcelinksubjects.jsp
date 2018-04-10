<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="resourcelinksubjects"/>
	<html:hidden property="resourcestep" value="step3"/>

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="res.add"/> - <fmt:message key="res.db.link.subj"/>
	</sakai:group_heading>
	</p>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back1"/>
		</html:submit>		
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</p>
	<p>
	
			<sakai:group_table>
			<logic:iterate name="maintenanceForm" property="subjectList" id="value" indexId="i">
				<tr>
					<td>
						<html:multibox property="selectedSubjects"><bean:write name="value" property="value"/></html:multibox>
						<bean:write name="value" property="label"/>
					</td>
				</tr>
			</logic:iterate>
		</sakai:group_table>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back1"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>