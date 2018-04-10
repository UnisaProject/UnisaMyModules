<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.bulkemail.ApplicationResources"/>
<sakai:html>
	<sakai:heading>
		<fmt:message key="page.generalheading"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step2.instruction"/>
	</sakai:instruction>
	<sakai:group_heading>
		<fmt:message key="page.step2.groupheading"/>
	</sakai:group_heading>
	
	<html:form action="bulkEmailAction.do?action=step3">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.step2.details.gender"/></td>
				<td>
					<html:select property="confineGender">
						<html:options collection="confineGenderList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step2.details.homelanguage"/></td>
				<td>
					<html:select property="confineHomeLanguage">
						<html:options collection="homeLanguages" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step2.details.country"/></td>
				<td>
					<html:select property="confineCountry">
						<html:options collection="countries" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step2.details.province"/></td>
				<td>
					<html:select property="confineProvince">
						<html:options collection="provinces" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td><fmt:message key="page.step2.details.regregion"/></td>
				<td>
					<html:select property="confineStudentsregion">
						<html:options collection="regions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step2.details.resregion"/></td>
				<td>
					<html:select property="confineStudentsResidentialregion">
						<html:options collection="resregions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step2.details.race"/></td>
				<td>
					<html:select property="confineRace">
						<html:options collection="races" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<sakai:actions>
					<td><html:submit value="Continue" property="button"/></td>
					<td><html:cancel value="Cancel" titleKey="button:cancel"/></td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>
	
	
</sakai:html>