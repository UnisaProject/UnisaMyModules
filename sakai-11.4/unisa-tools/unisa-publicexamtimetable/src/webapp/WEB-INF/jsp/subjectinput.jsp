<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.publicexamtimetable.ApplicationResources"/>
<sakai:html>
 	 <sakai:group_table>
						<tr>
							<td><img src="http://www.unisa.ac.za/static/corporate_web/Design/themes/default/images/unisa%20logo.svg"  WIDTH="216.7px" HEIGHT="46.5px"/></td>
						</tr>
  </sakai:group_table>
	<sakai:messages />
	<sakai:heading><fmt:message key="examtimetable.display.heading"/></sakai:heading>
	<sakai:instruction>
		<fmt:message key="examtimetable.input.instruction1"/><br/><fmt:message key="examtimetable.input.instruction2"/>
	</sakai:instruction>
	<html:form action="examtimetable">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="examtimetable.label.date"/></td>
				<td><bean:write name="examtimetableform" property="listDate"/></td>
			</tr>
			<tr>
				<td><fmt:message key="examtimetable.label.period"/></td>
				<td>
				<html:select property="examPeriod">
						<html:options collection ="periods" property ="value" labelProperty ="label"></html:options>						
					</html:select>	
				</td>
			</tr>
			<tr>
				<td colspan="2"><fmt:message key="examtimetable.label.studycodes"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<html:text property="unitCode1" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode2" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode3" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode4" maxlength="7" size="7"/>&nbsp;
					<html:text property="unitCode5" maxlength="7" size="7"/>
				</td>
			</tr>			
		</sakai:group_table>
   		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.clear"/></html:submit>
		</sakai:actions>
	</html:form>	
</sakai:html>
