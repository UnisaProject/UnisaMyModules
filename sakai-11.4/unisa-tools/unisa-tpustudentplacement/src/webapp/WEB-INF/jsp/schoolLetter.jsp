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
			.normalSize{font-size:11pt;font-family:Arial}	
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
		<img src="https://www2.unisa.ac.za/aol/image/college of education logo.jpg" style="position:absolute; TOP:15px; LEFT:300px; WIDTH:216.7px; HEIGHT:46.5px"/>
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
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan=2 class='normalSize'>
				    <b>The Principal</b><br/>
				    <b><bean:write name="studentPlacementForm" property="school.name"/></b><br/>
				    <b><bean:write name="studentPlacementForm" property="letterDate"/></b><br/>
				 </td>
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
				<td colspan=2   class='normalSize'><b>Dear Sir/Madam</b><br><br><br>
				Thank you for considering this student to be placed at your school to fulfil his/ her<BR> teaching practical component of the qualification.
				<br><b>The Teaching Practice Office confirms the placement details of the following student at your school:</b></td>
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
			  <td colspan="2" class='normalSize'>
					<sakai:flat_list>
						<bean:define id="schoolCom" name="studentPlacementForm" property="communicationSchool"/>
						<tr><td><b>Name of student</b></td><td><b><bean:write name="studentPlacementForm" property="student.printName"/></b></td></tr>
						<tr><td><b>Student Number</b></td><td><b><bean:write name="studentPlacementForm" property="student.number"/></b></td></tr>
						<tr><td><b>Programme</b></td><td><b><bean:write name="studentPlacementForm" property="student.qual.shortDesc"/></b></b></td></tr>
							<logic:iterate name="studentPlacementForm" property="listStudentPlacement" id="rec" indexId="index">	
									<bean:define id="school" name="rec" property="schoolCode"/>									
									<%if (schoolCom.toString().equalsIgnoreCase(school.toString())){
									%>
									<tr>
										<td><b>Teaching Practice Module</b></td> <td><b><bean:write name="rec" property="module"/></b></td>
									</tr>						
									 <tr>
									     <td><b>Duration</b></td>
							             <td><b><bean:write name="rec" property="startDate"/>&nbsp;-&nbsp;<bean:write name="rec" property="endDate"/></b></td>		
									</tr>
									<%}%>	
								</logic:iterate>
						<tr class='normalSize'><td><b>Subjects</b></td><td><b>The student will provide  the information</b></td></tr>
					</sakai:flat_list>
				</td>
				
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			
			</sakai:group_table>
				<!-- Page break -->
			<p class='pagestart'/>				
			<!-- END PAGE 1 -->	
			<img src="https://www2.unisa.ac.za/aol/image/college of education logo.jpg" style="position:absolute; TOP:15px; LEFT:300px; WIDTH:216.7px; HEIGHT:46.5px"/>
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
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			   <tr>
				   <td>&nbsp;</td>
				   <td>&nbsp;</td>
			   </tr>
			   
			  <tr>
				<td colspan="2"  class='normalSize'><p>During the teaching practice period, the student might be visited by a UNISA Supervisor
	                              to support and assess one lesson, in addition to the assessment and support provided by
                                  the Mentor Teacher<br></p>
                                 <p>Please note that students will remain under your jurisdiction for the full period of the
                                     teaching practice and have to abide by the school Code of Conduct and policies. Students are
                                     expected to execute all tasks that you and Mentor Teachers give them. For example, if the school
                                     starts at 07H30, then the student should be at school on time until end of the school day.</p> 
                               <p>Please take note of the following Teaching Practice Office requirements:</p>  
                               <OL>               
                               <LI>Teaching should at all times occur under the supervision of a qualified teacher (Mentor) allocated by the school. 
                                         The student is also required to participate in all school-related activities for the full duration of the Teaching Practice period.</LI>
                                 <LI>Standing in for an absent Teacher should be kept to a bare minimum, as this could interfere with the Teaching Practice programme.</LI>
                               <LI> Schools are not obligated to pay/remunerate the student teacher for the services rendered during the teaching practicals.</LI>
                                <LI>The student may not change schools after placement has been finalised without the approval of the Teaching Practice Office.</LI>
                                 <LI>The student should report at the school on a daily basis for the full duration of the Teaching Practice period.</LI>
                                 <LI>The student may not take leave from the school.</LI>
                                 <LI>The student may not be excused for private engagements during school time. In the case of illness or any other official matter,
                                the school and the Teaching Practice Office should be notified immediately. A medical certificate must be submitted on return to the school
                                in case of illness.</LI>
                                 <LI>Should the student be absent from the Teaching Practice programme for any reason whatsoever, s(h)e will be required to make up for
                                the lost time.</LI>
                                <LI>Should the student require permission to write examinations, arrangements with the school and the Teaching Practice Office
                                should be made well in advance. The student is also required to supply the Principal with an official examination time-table.</LI>
                                <LI>The student should at all times adhere to the safety regulations of the school.</LI>
                               </OL>  
				 </td>
			</tr>
			  
			<tr>
				<td colspan="2" class='normalSize'>Thank you for your contribution to the training of teachers.<td>
			</tr>
			<tr>
				<td colspan="2" class='normalSize'>For any other  placement queries, please contact Teaching Practice office<td>
			</tr>
			<tr>
				<td colspan="2" class='normalSize'> Teaching Practice Workstation Coordinator<br>
				     <bean:write name="studentPlacementForm" property="user.name"/><br>
				     Tel No: <bean:write name="studentPlacementForm" property="user.contactNumber"/><br>
				     Email: <bean:write name="studentPlacementForm" property="user.emailAddress"/>
				</td>
			</tr>  
			
			<tr>
				<td></td>
				<td>&nbsp;</td>
			</tr>  
			<tr>
				<td colspan="2" class='normalSize'>Yours Faithfully<br><br>UNISA Teaching Practice Office<br><br>Teaching Practice Manager<br><br>Prof MW Mndawe</td>
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