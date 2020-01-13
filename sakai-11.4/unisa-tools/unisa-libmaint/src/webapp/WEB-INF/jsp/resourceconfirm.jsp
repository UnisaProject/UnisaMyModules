<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="resourceconfirm"/>
	<html:hidden property="resourcestep" value="step4"/>

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="res.add"/>
	</sakai:group_heading>
	</p>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.save"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.back1"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</p>
	
	<table>
		<tr>
			<td><fmt:message key="res.db.name"/> <sakai:required/> </td>
			<td><bean:write name="maintenanceForm" property="resourceName"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.desc"/> <sakai:required/> </td>
			<td><bean:write name="maintenanceForm" property="resourceDesc"/></td>
		</tr>
		<tr>
			<td><fmt:message key="url.oncampus"/> <sakai:required/> </td>
			<td>
				<a href="${maintenanceForm.onCampusURL}" target="_blank"><bean:write name="maintenanceForm" property="onCampusURL"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="url.offcampus"/> <sakai:required/> </td>
			<td>
				<a href="${maintenanceForm.offCampusURL}" target="_blank"><bean:write name="maintenanceForm" property="offCampusURL"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="vendor"/> </td>
			<td><bean:write name="maintenanceForm" property="vendorDesc"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="txt.heading"/> <sakai:required/> </td>
			<td><bean:write name="maintenanceForm" property="textDesc"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.cd"/> </td>
			<td><bean:write name="maintenanceForm" property="cdRom"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.training"/> </td>
			<td>
				<a href="${maintenanceForm.trainingURL}" target="_blank"><bean:write name="maintenanceForm" property="trainingURL"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.refmanagement"/> </td>
			<td>
				<a href="${maintenanceForm.refManagementURL}" target="_blank"><bean:write name="maintenanceForm" property="refManagementURL"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.newstitle"/>  </td>
			<td><bean:write name="maintenanceForm" property="newsTitleDesc"/>			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.newsurl"/> </td>
			<td>
				<a href="${maintenanceForm.newsURL}" target="_blank"><bean:write name="maintenanceForm" property="newsURL"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.note"/>  </td>
			<td><bean:write name="maintenanceForm" property="accessNote"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.loginrequired"/>  </td>
			<td><bean:write name="maintenanceForm" property="passwordExist"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.alert"/>  </td>
			<td>
			<a href="${maintenanceForm.alert}" target="_blank"><bean:write name="maintenanceForm" property="alert"/></a>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="enabled"/>  </td>
			<td><bean:write name="maintenanceForm" property="enabled"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="plc.heading"/> <sakai:required/> </td>
			<logic:notEqual name="maintenanceForm" property="selectedPlacementsStr" value="">
				<td><bean:write name="maintenanceForm" property="selectedPlacementsStr"/></td>
			</logic:notEqual>
		</tr>
		<tr>
			<td><fmt:message key="subj.heading"/> <sakai:required/> </td>
			<logic:notEqual name="maintenanceForm" property="selectedSubjectsStr" value="">
				<td><bean:write name="maintenanceForm" property="selectedSubjectsStr"/></td>
			</logic:notEqual>
			<td>
		</tr>
		
	</table>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.save"/>
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