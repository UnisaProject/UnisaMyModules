<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.String"%>


<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>
<sakai:html>
	<style>
		@media Print
		{
			h3 {font-size:10pt;width:100%}
			h2 {font-size:10pt;font-weight:bold;font-family:Verdana,Arial,Helvetica}
			table.itemSummary {font-size:8pt}
			table.listHier {font-size:8pt}	
			h1, h2, h3, h4, h5, h6 { page-break-after:avoid;page-break-inside:avoid }
			table { page-break-inside:avoid }
			.portletBody h3{font-size:10pt}
			.portletBody {width: 100%; margin: 0; float: none;}
			body {background: white}
			#content #container, #container2  {width: 100%; margin: 0; float: none;}
			.pagestart{page-break-before:always;}
		}
	</style>	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<img src="https://www2.unisa.ac.za/aol/image/college of education logo.jpg" style="position:absolute; TOP:15px; LEFT:350px; WIDTH:216.7px; HEIGHT:46.5px"/>
		<sakai:group_table>	
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>				
				<td style="white-space:nowrap;align:right"><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				Tel. No. (012) 429 4200<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				E-mail address : teachprac@unisa.ac.za<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<bean:write name="studentPlacementForm" property="letterDate"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>			 
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				           &nbsp;&nbsp;&nbsp;&nbsp;
					    <bean:write name="studentPlacementForm" property="student.printName"/><br/>
					<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line1">
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        &nbsp;&nbsp;&nbsp;&nbsp;
						<bean:write name="studentPlacementForm" property="student.postalAddress.line1"/><br/>
				       <logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line2">
				             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				             &nbsp;&nbsp;&nbsp;&nbsp;
							<bean:write name="studentPlacementForm" property="student.postalAddress.line2"/><br/>
					  </logic:notEmpty>
					<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line3">
					         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				             &nbsp;&nbsp;&nbsp;&nbsp;
							<bean:write name="studentPlacementForm" property="student.postalAddress.line3"/><br/>
					</logic:notEmpty>
				    <logic:notEmpty name="studentPlacementForm"  property="student.postalAddress.line4">
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				              &nbsp;&nbsp;&nbsp;&nbsp;
							<bean:write name="studentPlacementForm" property="student.postalAddress.line4"/><br/>
					</logic:notEmpty>
					<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line5">
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				            &nbsp;&nbsp;&nbsp;&nbsp;
							<br/><bean:write name="studentPlacementForm" property="student.postalAddress.line5"/><br/>
					</logic:notEmpty>
						<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.line6">
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				             &nbsp;&nbsp;&nbsp;&nbsp;
							<br/><bean:write name="studentPlacementForm" property="student.postalAddress.line6"/><br/>
					</logic:notEmpty>
					<logic:notEmpty name="studentPlacementForm" property="student.postalAddress.postalCode">
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				            &nbsp;&nbsp;&nbsp;&nbsp;
							<bean:write name="studentPlacementForm" property="student.postalAddress.postalCode"/>
					</logic:notEmpty>	
				</logic:notEmpty>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					cc. The Principal<br>
					<bean:write name="studentPlacementForm" property="school.name"/>
					<logic:notEmpty name="studentPlacementForm" property="school.postalAddress1">
						<br/><bean:write name="studentPlacementForm" property="school.postalAddress1"/>
						<logic:notEmpty name="studentPlacementForm" property="school.postalAddress2">
							<br/><bean:write name="studentPlacementForm" property="school.postalAddress2"/>
						</logic:notEmpty>
						<logic:notEmpty name="studentPlacementForm" property="school.postalAddress3">
							<br/><bean:write name="studentPlacementForm" property="school.postalAddress3"/><br/>
						</logic:notEmpty>
						<logic:notEmpty name="studentPlacementForm" property="school.postalCode">
							<br/><bean:write name="studentPlacementForm" property="school.postalCode"/><br/>
						</logic:notEmpty>	
					</logic:notEmpty>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>Dear&nbsp;<bean:write name="studentPlacementForm" property="student.printName"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>PLACEMENT INFORMATION</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
	        	<td>This serves:</td>
	        	<td>&nbsp;</td>
	        </tr>
	        <tr><td>
	        	<sakai:flat_list>
		        	<tr>
		        		<td>a)</td>
		        		<td>To confirm your placement at the school/ECD centre for the duration of the Teaching Practice period as<br/>stipulated below.</td>
					</tr><tr>
						<td>b)</td>
						<td>To advise and draw your attention to the Teaching Practice requirements which must be adhered<br/>to for the duration of your Teaching Practice period.</td>	
					</tr><tr>	
						<td>c)</td>
						<td>To formally inform and agree with the school about your placement (relevant documents will be sent to<br/>the school separately).</td>
					</tr>	
				</sakai:flat_list>
	        </td></tr>
	        <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>	
	        <tr>
				<td><B>1. DETAILS</B></td>
				<td>&nbsp;</td>
			</tr>  
			<tr><td>
			<sakai:flat_list>
				<bean:define id="schoolCom" name="studentPlacementForm" property="communicationSchool"/>
				<tr >
					<td style="white-space:nowrap;align:left"><b><fmt:message key="prompt.column.module"/></b></td>	
					<td style="white-space:nowrap;align:left"><b><fmt:message key="prompt.column.duration"/></b></td>					
					<td style="white-space:nowrap;align:left"><b><fmt:message key="prompt.column.dateOfPlacement"/></b></td>
					<td style="white-space:nowrap;align:left"><b><fmt:message key="prompt.column.supervisor"/></b></td>
					<td style="white-space:nowrap;align:left"><b><fmt:message key="prompt.column.supervisorContactNumber"/></b></td>
				</tr>
				<logic:iterate name="studentPlacementForm" property="listStudentPlacement" id="rec" indexId="index">
					<bean:define id="school" name="rec" property="schoolCode"/>	
					<%if (schoolCom.toString().equalsIgnoreCase(school.toString())){
					%>
					<tr>
						<td style="white-space:nowrap;align:left"><bean:write name="rec" property="module"/></td>
						<td style="white-space:nowrap;align:left"><bean:write name="rec" property="numberOfWeeks"/>&nbsp;weeks</td>
						<td style="white-space:nowrap;align:left"><bean:write name="rec" property="startDate"/>&nbsp;-&nbsp;<bean:write name="rec" property="endDate"/></td>									
						<td style="white-space:nowrap;align:left"><bean:write name="rec" property="supervisorName"/></td>
						<td style="white-space:nowrap;align:left"><bean:write name="rec" property="supervisorContactNumber"/></td>		
					</tr>
					<%}%>
				</logic:iterate>
			</sakai:flat_list>
			</td></tr>
			</sakai:group_table>
			<!-- Page break -->
			<p class='pagestart'/>					
			<!-- END PAGE 1 -->	
			<img src="https://www2.unisa.ac.za/aol/image/college of education logo.jpg" style="position:absolute; TOP:15px; LEFT:350px; WIDTH:216.7px; HEIGHT:46.5px"/>
			<sakai:group_table>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	        <tr>
				<td><B>2. TEACHING PRACTICE REQUIREMENTS</B></td>
				<td>&nbsp;</td>
			</tr>   
			<tr><td>
	       <sakai:flat_list>
	        	<tr>
	        		<td colspan="2">The College of Education's Teaching Practice Unit requires all students to comply with the under-<br/>mentioned requirements:	        		
					</td>
	        	</tr>
	        	<tr>
	        		<td>2.1.</td>
	        		<td>Teaching should at all times occur under the supervision of a qualified teacher (mentor) allocated by<br/>the school. The student is also required to participate in all school-related activities for the full<br/>duration of the Teaching Practice period.</td>
	        	</tr><tr>
	        		<td>2.2.</td>
	        		<td>Standing in for an absent teacher should be kept to a bare minimum, as this could interfere with the<br/>Teaching Practice programme.</td>
				</tr><tr>
	        		<td>2.3.</td>
	        		<td>There should be no payment/remuneration for the services rendered at the placement school.</td>
				</tr><tr>
	        		<td>2.4.</td>
	        		<td>The student may not change schools after placement has been finalised without the approval of the<br/>Teaching Practice Unit.</td>
				</tr><tr>
	        		<td>2.5.</td>
	        		<td>The student should report at the school on a daily basis for the full duration of the Teaching Practice<br/>period.</td>
				</tr><tr>
	        		<td>2.6.</td>
	        		<td>The student may not be excused for private engagements during school time. In the case of illness or<br/>any other official matter, the school and the Teaching Practice Unit should be notified immediately. A<br/>medical certificate must be submitted on return to the school in case of illness.</td>
				</tr><tr>
	        		<td>2.7.</td>
	        		<td>Should the student be absent from the Teaching Practice programme for any reason whatsoever,<br/>s(h)e will be required to make up for the lost time.</td>
				</tr><tr>
	        		<td>2.8.</td>
	        		<td>The student may not take leave from the school.</td>
				</tr><tr>
	        		<td>2.9.</td>
	        		<td>Should the student require permission to write examinations, arrangements with the school and the<br/>Teaching Practice Unit should be made well in advance. The student is also required to supply the<br/>principal with an official examination time-table.</td>
				</tr><tr>
	        		<td>2.10.</td>
	        		<td>The student should at all times adhere to the safety regulations of the school.</td></tr>
			</sakai:flat_list>
			</td></tr>	
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>  
			<tr>
				<td>Yours faithfully</td>
				<td>&nbsp;</td>
			</tr>   
			<tr>
				<td>Unisa Teaching Practice Unit</td>
				<td>&nbsp;</td>
			</tr>   
			<tr>
				<td>College of Education</td>
				<td>&nbsp;</td>
			</tr>         
        </sakai:group_table>
        
		<sakai:actions>
			<input type=button value=Print onclick="window.print()"/>
			<html:submit property="action">
						<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>