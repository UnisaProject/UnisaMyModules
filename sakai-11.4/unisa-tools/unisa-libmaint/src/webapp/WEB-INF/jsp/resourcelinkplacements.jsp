<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="resourcelinkplacements"/>
	<html:hidden property="resourcestep" value="step2"/>

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="res.add"/> - <fmt:message key="res.db.link.plc"/>
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
			<logic:iterate name="maintenanceForm" property="placementList" id="value" indexId="i">
				<tr>
					<td>
						<logic:notEqual name="value" property="label" value="..">
							<html:multibox property="selectedPlacements"><bean:write name="value" property="value"/></html:multibox>
							<bean:write name="value" property="label"/>
						</logic:notEqual>
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