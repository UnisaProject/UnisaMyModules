<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="highlightremove"/>
	
	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="high.rem.heading"/>
	</sakai:group_heading>
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<p>
	<fmt:message key="rem.sure"/>
	<p>
	<sakai:flat_list>
		<logic:notEmpty name="maintenanceForm" property="removalList">
			<logic:iterate name="maintenanceForm" property="removalList" id="record" indexId="i">
				<tr>
					<td>
						<bean:write name="record" property="highlight"/>
						<logic:equal name="record" property="inUse" value="true">
							(Warning: Highlight Note in use)
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.remove"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	
</html:form>
</sakai:html>