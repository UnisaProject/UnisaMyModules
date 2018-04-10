<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="resource"/>
	<html:hidden property="resourcestep" value="step1"/>
	

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="res.add"/>
	</sakai:group_heading>
	</p>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<table>
		<tr>
			<td valign="top"><fmt:message key="res.db.name"/> <sakai:required/> </td>
			<td><!-- >html:text property="resourceName" size="255" maxlength="255"/ -->
			<html:textarea property="resourceName"  style="width: 500px; height:85px"/>
			</td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.desc"/> <sakai:required/> </td>
			<td><html:textarea property="resourceDesc"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="url.oncampus"/> <sakai:required/> </td>
			<td><html:textarea property="onCampusURL"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="url.offcampus"/> <sakai:required/> </td>
			<td><html:textarea property="offCampusURL"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td><fmt:message key="vendor"/> </td>
			<td>
				<html:select name="maintenanceForm" property='vendorId'>
					<html:options collection="vendorOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="txt.heading"/> <sakai:required/> </td>
			<td>
				<html:select name="maintenanceForm" property='textId'>
					<html:options collection="txtOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.cd"/> </td>
			<td><html:textarea property="cdRom"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.training"/> </td>
			<td><html:textarea property="trainingURL"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.refmanagement"/> </td>
			<td><html:textarea property="refManagementURL"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.newstitle"/>  </td>
			<td>
				<html:select property="newsTitle">
					<html:options collection="newsTitleOptions" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.newsurl"/> </td>
			<td><html:textarea property="newsURL"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.note"/>  </td>
			<td><html:textarea property="accessNote"  style="width: 500px; height:160px"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.loginrequired"/>  </td>
			<td>
				<html:select property="passwordExist">
					<html:options collection="enabledOptionList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.login"/>  </td>
			<td><html:text property="login" size="20" maxlength="20"/></td>
		</tr>
		<tr>
			<td><fmt:message key="res.db.password"/>  </td>
			<td><html:text property="password" size="20" maxlength="20"/></td>
		</tr>
		<tr>
			<td><fmt:message key="high.note"/>  </td>
			<td><html:select property="highlight">
					<html:options collection="highlightOptions" property="value" labelProperty="label"/>
				</html:select></td>
		</tr>
		<tr>
			<td valign="top"><fmt:message key="res.db.alert"/>  </td>
			<td><html:textarea property="alert"  style="width: 500px; height:85px"/></td>
		</tr>
		<tr>
			<td><fmt:message key="enabled"/>  </td>
			<td>
				<html:select property="enabled">
					<html:options collection="enabledOptionList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</table>
	
	</p>
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.continue"/>
		</html:submit>
		<html:submit property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>