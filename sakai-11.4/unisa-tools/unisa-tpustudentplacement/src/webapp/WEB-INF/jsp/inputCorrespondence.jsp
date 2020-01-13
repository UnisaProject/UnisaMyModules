<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.correspondence"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>	
		<sakai:group_table>		
			<td><fmt:message key="prompt.recipient"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td>
					<html:radio name="studentPlacementForm" property="communicationTo" value="Student"><fmt:message key="prompt.student"/></html:radio>
					<html:radio name="studentPlacementForm" property="communicationTo" value="School"><fmt:message key="prompt.school"/></html:radio>
				</td>		
			<tr>
				<td><fmt:message key="prompt.school"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="communicationSchool">
						<html:optionsCollection name="studentPlacementForm" property="communicationList" value="value" label="label"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				<td>&nbsp;<!--<fmt:message key="prompt.contactDetails"/>-->					
				</td>
				<td>
					<html:submit property="action">
						<fmt:message key="button.getContactDetails"/>
					</html:submit>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.emailAddress"/>					
				</td>
				<td>
					<!--<html:submit property="action">
						<fmt:message key="button.getEmailAdress"/>
					</html:submit>-->
					<html:text name="studentPlacementForm" property="communicationEmailAddress" size="60" maxlength="60"/>
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.cellNumber"/>					
				</td>
				<td>
					<!--<html:submit property="action">
						<fmt:message key="button.getCellNumber"/>
					</html:submit>-->
					<html:text name="studentPlacementForm" property="communicationCellNumber" size="28" maxlength="28" readonly="true"/>
				</td>
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.sendEmail"/>
			</html:submit>			
			<html:submit property="action">
					<fmt:message key="button.viewLetter"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.SendSMS"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>						
	</html:form>
</sakai:html>