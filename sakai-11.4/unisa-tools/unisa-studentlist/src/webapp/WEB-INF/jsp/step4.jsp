<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.studentlist.ApplicationResources"/>

<sakai:html>
	<sakai:heading><fmt:message key="page.general.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step4.instruction"/>
		<fmt:message key="page.step4.instruction1"/>
		
	</sakai:instruction>
		
	<sakai:group_heading><fmt:message key="page.step4.groupheading"/></sakai:group_heading>
	
	<html:form action="studentlistaction.do?action=generatelist">
		<sakai:group_table>
	
		<tr>
		<td><fmt:message key="page.step4.details.studentList"/></td>
		<td>
		<html:select property="confineStudentList">
		<html:options collection="confineStudentList" property="value" labelProperty="label"/>
		</html:select>
		</td>
		</tr>   		
		
			<tr>
				<td><fmt:message key="page.step4.details.gender"/></td>
				<td>
					<html:select property="confineGender">
						<html:options collection="confineGenderList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step4.disabilityTypeLabel"/></td>
				<td>
					<html:select property="disability">
						<html:options collection="disabilityTypeList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>		
	<%--	<tr>
				<td><fmt:message key="page.step4.details.hasemail"/></td>
				<td>
					<html:select property="confineEmailAddress">
						<html:options collection="confineHasEmailList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr> --%>
			<tr>
				<td><fmt:message key="page.step4.details.correspondenceLanguage"/></td>
				<td>
					<html:select property="confineCorrespondenceLanguage">
						<html:options collection="confineCorrespondencLanguage" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>			
			
			<tr>
				<td><fmt:message key="page.step4.details.homelanguage"/></td>
				<td>
					<html:select property="confineHomeLanguage">
						<html:options collection="homeLanguages" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
						
			<tr>
				<td><fmt:message key="page.step4.details.country"/></td>
				<td>
					<html:select property="confineCountry">
						<html:options collection="countries" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step4.details.region"/></td>
				<td>
					<html:select property="confineStudentsregion">
						<html:options collection="regions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td><fmt:message key="page.step4.details.resregion"/></td>
				<td>
					<html:select property="confineResidentialRegion">
						<html:options collection="resRegions" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<td><fmt:message key="page.step4.details.province"/></td>
				<td>
					<html:select property="confineProvince">
						<html:options collection="provinces" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<!--
			<tr>
				<td><fmt:message key="page.step4.details.magisterialdistrict"/></td>
				<td>
					<html:select property="confineDistrict">
						<html:options collection="districts" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			-->
			<tr>
				<td><fmt:message key="page.step4.details.race"/></td>
				<td>
					<html:select property="confineRace">
						<html:options collection="races" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step4.details.onlineuser"/></td>
				<td>
					<html:select property="confineOnlineUser">
						<html:options collection="confineOnlineUserList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.step4.details.groupedstudent"/></td>
				<td>
					<html:select property="confineGroupedStudent">
						<html:options collection="confineGroupedList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
			
			<tr>
				<sakai:actions>
					<td colspan="4">
						<html:submit value="Display" disabled="${studentlistform.format != 'weblist'}" property="listOutput"/>
						<html:submit value="Download list" disabled="${studentlistform.format == 'weblist'}" property="listOutput"/>
						<html:submit value="Back" property="button"/>
						<html:cancel value="Cancel" titleKey="button:cancel"/>
					</td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>
</sakai:html>