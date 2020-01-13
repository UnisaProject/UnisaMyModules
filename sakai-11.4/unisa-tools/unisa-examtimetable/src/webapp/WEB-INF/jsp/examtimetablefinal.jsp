<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.examtimetable.ApplicationResources"/>

<sakai:html>		
 	<html:form action="/displayExamTimetable">
 	<%@page import="java.util.*" %>
		<% String urlString="";
			if (request.getAttribute("urlString")==null)
				urlString="";
			else
				urlString=(String)request.getAttribute("urlString");
		%>		
		<html:hidden property="currentPage" value="displayFinal"/>
	<!--place alert message beneath tool heading-->
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>		
	<sakai:group_table>	
	<tr>
		<td><fmt:message key="page.examYear"/>&nbsp;</td>
		<td><html:text property="examYear" size="6" maxlength="4"/></td>
	</tr><tr>
		<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
		<td><html:select property="examPeriod">
					<html:options collection="examPeriods" property="code" labelProperty="engDescription"/>
					</html:select>
		</td>
	</tr><tr>
		<td><fmt:message key="page.practicalType"/>&nbsp;</td>
		<td><html:select property="practicalType">
					<html:options collection="examPracTypes" property="code" labelProperty="engDescription"/>
					</html:select>
		</td>
	</tr><tr>
		<td><fmt:message key="page.studentNumber"/>&nbsp;</td>
		<logic:equal name="examTimetableDisplayForm" property="studentUser" value="false">
			<td><html:text property="student.number" size="10" maxlength="8"/></td>
		</logic:equal>
		<logic:equal name="examTimetableDisplayForm" property="studentUser" value="true">
			<td><bean:write name="examTimetableDisplayForm" property="student.number"/></td>
		</logic:equal>
	</tr>	
	</sakai:group_table>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.display"/>
		</html:submit>			
	</sakai:actions>	
	<hr/>
	<table>
		<tr>
			<td><strong><fmt:message key="page.studentNumber"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="student.number"/></td>
		</tr><tr>
			<td><strong><fmt:message key="page.studentName"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="student.title"/>&nbsp;
			<bean:write name="examTimetableDisplayForm" property="student.initials"/>&nbsp;
			<bean:write name="examTimetableDisplayForm" property="student.name"/>&nbsp;</td>
		</tr><tr>
			<td><strong><fmt:message key="page.timetableStatus"/></strong></td>
			<td><bean:write name="examTimetableDisplayForm" property="timetableStatusDesc"/></td>
		</tr>		
	</table>
        <sakai:actions>
                <logic:equal name="examTimetableDisplayForm" property="studentUser" value="true">
                        <html:submit property="action">
                                <fmt:message key="button.sendStudentEmail"/>
                        </html:submit>
                </logic:equal>
                        
        </sakai:actions>
	<!-- Admission: Start -->
	<logic:equal  name="examTimetableDisplayForm" property="admissionFlag" value="Y">
		<p>
			<logic:iterate name="examTimetableDisplayForm" property="parText4130" id="record">			
				<bean:write name="record"/>
			</logic:iterate>
		</p>	
		<sakai:flat_list>		
		<logic:iterate name="examTimetableDisplayForm" property="admissionList" id="record">
			<tr>					
			 	<td><bean:write name="record" property="studyUnitDesc"/></td>
			 	<td><bean:write name="record" property="studyUnit"/></td>
			 	<td><bean:write name="record" property="examDate"/></td>
			</tr>
		</logic:iterate>
		</sakai:flat_list>
		<logic:iterate name="examTimetableDisplayForm" property="admParTextList" id="record">
			<p>			
				<logic:iterate name="record" id="parline">			
					<bean:write name="parline"/>
				</logic:iterate>
			</p>
		</logic:iterate>			
	</logic:equal>
	<!-- Admission: End -->
	<!-- No Admission: Start -->	
	<logic:equal  name="examTimetableDisplayForm" property="noAdmissionFlag" value="Y">
		<logic:equal  name="examTimetableDisplayForm" property="admissionFlag" value="Y">
		<p>
			<logic:iterate name="examTimetableDisplayForm" property="parText4137" id="record">			
				<bean:write name="record"/>
			</logic:iterate>
		</p>
		</logic:equal>
		<logic:notEqual  name="examTimetableDisplayForm" property="admissionFlag" value="Y">
		<p>
			<logic:iterate name="examTimetableDisplayForm" property="parText4136" id="record">			
				<bean:write name="record"/>
			</logic:iterate>
		</p>
		<p>
			<logic:iterate name="examTimetableDisplayForm" property="parText4138" id="record">			
				<bean:write name="record"/>
			</logic:iterate>
		</p>
		</logic:notEqual>
		<logic:iterate name="examTimetableDisplayForm" property="noAdmissionList" id="record">
			&nbsp;&nbsp;&nbsp;<bean:write name="record"/><br>
		</logic:iterate>
		<p></p>	
	</logic:equal>
	<!-- No Admission: End -->	
	<!-- FC: Start -->	
	<logic:equal  name="examTimetableDisplayForm" property="fcFlag" value="Y">
		<p>
			<logic:iterate name="examTimetableDisplayForm" property="parText4125" id="record">			
				<bean:write name="record"/>
			</logic:iterate>
		</p>		
		<logic:iterate name="examTimetableDisplayForm" property="fcList" id="record">
			&nbsp;&nbsp;&nbsp;<bean:write name="record"/><br>
		</logic:iterate>	
		<p></p>		
	</logic:equal>
	<!-- FC: End -->	
	<!-- Venue - Only if student have admission to examination for at least one course: Start -->		
	<logic:equal name="examTimetableDisplayForm" property="admissionFlag" value="Y">
		<sakai:instruction>
			<a href="http://www.unisa.ac.za/contents/courses/images/toele.pdf"><strong><fmt:message key="page.instruction.examlink"/></strong></a>
		</sakai:instruction>
		<BR>
		<logic:notEmpty name="examTimetableDisplayForm" property="invigTelephone">
			<fmt:message key="page.invigTelephone"/>&nbsp;<bean:write name="examTimetableDisplayForm" property="invigTelephone"/>
		</logic:notEmpty>
		<sakai:flat_list>			
			<tr>
		  		<th colspan="2"><fmt:message key="page.venue.heading"/></th>	  	
			</tr>			
			<tr>
				<td><bean:write name="examTimetableDisplayForm" property="venueCode"/>&nbsp;</td>
				<td>
					<bean:write name="examTimetableDisplayForm" property="venueDesc"/>&nbsp;
					<logic:notEmpty name="examTimetableDisplayForm" property="latitude">
						<logic:notEmpty name="examTimetableDisplayForm" property="longitude">
							(latitude:&nbsp;<bean:write name="examTimetableDisplayForm" property="latitude"/>;&nbsp;&nbsp;&nbsp;longitude:&nbsp;<bean:write name="examTimetableDisplayForm" property="longitude"/>)
							<!--<html:link href="displayExamTimetable.do?action=displayMap">
								<fmt:message key="page.linkDisplayMapText"/>
							</html:link>
							<html:link href="<%=urlString%>">
								<fmt:message key="page.linkDisplayMapText"/>
							</html:link>-->
						</logic:notEmpty>
					</logic:notEmpty>
				</td>
			</tr>			
			<tr>
				<td>&nbsp;</td>
				<td>
					<logic:iterate name="addresslines" id="record" indexId="i">			
							<bean:write name="record" />
					</logic:iterate>
				</td>						
			</tr>				
		</sakai:flat_list>
	</logic:equal>
	<!-- Venue: End -->	
   	</html:form>     	
</sakai:html>