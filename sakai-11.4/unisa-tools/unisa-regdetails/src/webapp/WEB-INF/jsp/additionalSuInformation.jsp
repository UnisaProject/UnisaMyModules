<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<html:form action="/additions" >
	<html:hidden property="goto" value="save"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		<br/>
		<sakai:group_heading><fmt:message key="page.additional.su.information"/></sakai:group_heading>
		<sakai:group_table>
		
		 <logic:equal name="regDetailsForm" property="wil" value="true">
		<tr>
			<td><strong><fmt:message key="page.wil"/></strong></td>
		</tr><tr>
			<td><fmt:message key="page.wil.information1"/></td>
		</tr>
		<logic:iterate name="regDetailsForm" property="additionalStudyUnits" id="additionalStudyUnits" indexId="index">
			 <logic:present name="additionalStudyUnits" property="code">
			 <logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].wil" %>' value="true">
			 	<tr>
			 		<td><bean:write name="additionalStudyUnits" property="code"/>&nbsp;&nbsp;
			 			<html:select property='<%= "additionalStudyUnits[" + index + "].wilAnswer" %>'>
			 					<html:option value="E">Select an option</html:option>
			 					<html:option value="Y">Yes, I have a place where I can get the experience needed to complete this module.</html:option>
								<html:option value="N">No, I need a place to get the experience that I need.</html:option>
								<html:option value="U">Uncertain - I am not sure right now.</html:option>
						</html:select>&nbsp;
					</td>
	          </tr>
			</logic:equal>
			</logic:present>
		</logic:iterate>
		</logic:equal>
		
		 <logic:equal name="regDetailsForm" property="odl" value="true">
		   <logic:equal name="regDetailsForm" property="wil" value="true">
		   <tr><td>
		     	<hr/>
		    </td></tr>
		    </logic:equal>
		<tr>
			<td><strong><fmt:message key="page.tutor"/></strong></td>
		</tr><tr>
			<td><fmt:message key="page.tutor.information1"/></td>
		</tr>
		<logic:iterate name="regDetailsForm" property="additionalStudyUnits" id="additionalStudyUnits" indexId="index">
			 <logic:present name="additionalStudyUnits" property="code">
			 <logic:equal name="regDetailsForm" property='<%= "additionalStudyUnits[" + index + "].odl" %>' value="true">
			 	<tr>
			 		<td><bean:write name="additionalStudyUnits" property="code"/>&nbsp;&nbsp;
			 			<html:select property='<%= "additionalStudyUnits[" + index + "].odlAnswer" %>'>
			 					<html:option value="E">Select an option</html:option>
			 					<html:option value="F">Face to face</html:option>
								<html:option value="O">Online</html:option>
								<html:option value="N">I do not need support</html:option>
						</html:select>&nbsp;
					</td>
	          </tr>
			</logic:equal>
			</logic:present>
		</logic:iterate>
		</logic:equal>

</sakai:group_table>


	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.save"/></html:submit></td>
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
</html:form>

</sakai:html>