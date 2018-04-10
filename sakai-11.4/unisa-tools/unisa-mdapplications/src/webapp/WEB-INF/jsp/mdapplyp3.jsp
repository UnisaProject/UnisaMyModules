<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="step3"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:heading><fmt:message key="md.heading"/></sakai:heading>

		<sakai:group_heading><fmt:message key="md.subheading3"/></sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td colspan="2"><strong><fmt:message key="md.page3.instruction"/>&nbsp;<sakai:required/></strong></td>
		</tr><tr>
			<td colspan="2"><fmt:message key="md.page3.sharedetails"/>&nbsp;<sakai:required/></td>
		</tr><tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<html:radio property="student.shareContactDetails" value="Y"/><fmt:message key="md.page3.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.shareContactDetails" value="N"/><fmt:message key="md.page3.no"/>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.examcentre"/>&nbsp;<sakai:required/></td>
			<td colspan="2">
				<html:select property="selectedExamCentre">
 					<html:options collection="examlist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.homelanguage"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedHomeLanguage">
 					<html:options collection="languagelist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
<!--			<td><fmt:message key="md.page3.twin"/></td>
			<td>
				<html:radio property="student.twinflag" value="Y"/><fmt:message key="md.page3.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.twinflag" value="N"/><fmt:message key="md.page3.no"/>
			</td>
		</tr><tr> -->
			<td colspan="2"><strong><fmt:message key="md.page3.statistic"/></strong></td>
		</tr><tr>
			<td><fmt:message key="md.page3.nationality"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedNationality">
 					<html:options collection="nationalitylist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.population"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedPopulationGroup">
 					<html:options collection="populationlist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.occupation"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedOccupation">
 					<html:options collection="occupationlist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.econsector"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedEconomicSector">
 					<html:options collection="economicsectorlist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
			<td><fmt:message key="md.page3.activity"/>&nbsp;<sakai:required/></td>
			<td>
				<html:select property="selectedPrevActivity">
 					<html:options collection="prevactivitylist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr><tr>
<!--			<td colspan="2"><fmt:message key="md.page3.last"/></td>
			<td>
				<html:radio property="student.lastStatus" value="Y"/><fmt:message key="md.page3.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.lastStatus" value="N"/><fmt:message key="md.page3.no"/>
			</td>	
		</tr><tr>
			<td><fmt:message key="md.page3.training"/></td>
			<td>
				<html:radio property="student.computerTraining" value="Y"/><fmt:message key="md.page3.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.computerTraining" value="N"/><fmt:message key="md.page3.no"/>
			</td>-->	
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>