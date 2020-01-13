<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.publicprescribedbooks.ApplicationResources"/>
<sakai:html>
	<sakai:messages />
	<sakai:heading><fmt:message key="prescribedbooks.display.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="prescribedbooks.input.instruction1"/><br/><fmt:message key="prescribedbooks.input.instruction2"/>
	</sakai:instruction>
	<html:form action="/prescribedbooks">
		<sakai:group_table>
			
			<tr>
				<td><fmt:message key="prescribedbooks.label.year"/></td>
				<td>
					<html:select property="academicYear">
						<html:options collection ="years" property ="value" labelProperty ="label"></html:options>
					</html:select>	
				</td>
			</tr>
				
			<tr>
				<td colspan="2"><fmt:message key="prescribedbooks.label.studycodes"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<html:text property="unitCode1" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode2" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode3" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode4" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode5" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode6" maxlength="7" size="7"/><br><br>
					<html:text property="unitCode7" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode8" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode9" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode10" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode11" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode12" maxlength="7" size="7"/>&nbsp;
									
				</td>
			</tr>			
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.clear"/></html:submit>
		</sakai:actions>
	</html:form>	
</sakai:html>

