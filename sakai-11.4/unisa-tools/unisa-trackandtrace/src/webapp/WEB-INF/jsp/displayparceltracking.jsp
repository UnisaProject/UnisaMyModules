<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.trackandtrace.ApplicationResources"/>

<sakai:html>

	<sakai:messages/>
	<sakai:messages message="true"/>
<html:form method="GET" action="/displayParcelTracking">

	
	<H3><fmt:message key="function.heading"/></h3>
	<br>

	
		<fmt:message key="function.instructiontext"/>
		<fmt:message key="function.instructiontext1"/>
		<fmt:message key="function.instructiontext2"/>
		<fmt:message key="function.instructiontext3"/>
		<fmt:message key="function.instructiontext4"/>
		<fmt:message key="function.instructiontext5"/>
		<fmt:message key="function.instructiontext6"/>
		<fmt:message key="function.instructiontext7"/>
		<fmt:message key="function.instructiontext8"/>
		<fmt:message key="function.instructiontext9"/>
		<fmt:message key="function.instructiontext11"/>
		<fmt:message key="function.instructiontext15"/>		
		<fmt:message key="function.instructiontext14"/>
		<fmt:message key="function.instructiontext12"/>
		<logic:notEmpty name="records">
	<logic:equal name="parcelTrackingDisplayForm" property="studentuser" value="false">
			<br>
		<fmt:message key="function.studno"/>
		<html:text property="student.number" size="8"></html:text><br>
		
		
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clearstno"/></html:submit>			
		</sakai:actions>	
		<hr>
		<sakai:heading>
			<fmt:message key="function.student"/>
		   <bean:write name="parcelTrackingDisplayForm" property="student.number"/> : 
		   <bean:write name="parcelTrackingDisplayForm" property="student.name"/>
		</sakai:heading>
   
	   <br>
	</logic:equal>
	<sakai:heading>
	     <fmt:message key="function.instructiontext13"/>
	</sakai:heading>
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="function.date"/></th>
					<th align="left"><fmt:message key="fucnction.agent"/></th>
				<th align="left"><fmt:message key="function.trackno"/></th>
			
			</tr>
			<logic:present name="records">
				<logic:iterate name="records" id="record" indexId="i">
					<tr>
						<td><bean:write name="record" property="trackTraceDate"/>&nbsp;</td>
						<td><bean:write name="record" property="trackTraceAgent"/>&nbsp;</td>						
						<td><bean:write name="record" property="trackTraceNumber" filter="false"/>&nbsp;</td>
					</tr>
				</logic:iterate>		
			</logic:present>
		</sakai:flat_list>			
	</logic:notEmpty>
	<logic:empty name="records">
		<logic:equal name="parcelTrackingDisplayForm" property="studentuser" value="false">
		<br>
		<fmt:message key="function.studno"/>
		<html:text property="student.number" size="8"></html:text><br>
		
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clearstno"/></html:submit>			
		</sakai:actions>	
		<hr>
		<sakai:heading>
			<fmt:message key="function.student"/>
		   <bean:write name="parcelTrackingDisplayForm" property="student.number"/> : 
		   <bean:write name="parcelTrackingDisplayForm" property="student.name"/>
		</sakai:heading>
   
	   <br>
	</logic:equal>
	</logic:empty>
	
	
</html:form>
</sakai:html>
