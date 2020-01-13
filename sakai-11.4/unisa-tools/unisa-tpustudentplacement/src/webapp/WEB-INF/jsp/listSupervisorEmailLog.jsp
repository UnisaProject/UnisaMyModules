<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/supervisorMaintenance">
			<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.supervisorEmailLog"/>
		</sakai:heading>	
		<sakai:group_table>	
			<tr><br><br>
				<td colspan="2"> <b><fmt:message key="prompt.acadYear"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <html:text name="studentPlacementForm" property="acadYear" size="30" maxlength="28" /> </b>                                     
				</td>
			</tr>
			<tr>
				<td colspan="2">
				       <b><fmt:message key="prompt.supervisor"/>&#58;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				       <bean:write name="studentPlacementForm" property="supervisor.supervisorFullName"/></b>                                        
				</td>
			</tr>	
			<tr>
				<td colspan="2">
				     <b><fmt:message key="prompt.supervisorEmail"/>&#58;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				     <bean:write name="studentPlacementForm" property="supervisor.emailAddress"/></b>
				</td>		
			</tr>
			<tr>
				<td> <b><fmt:message key="prompt.currentAllocatedStudents"/>&nbsp;				
					    <bean:write name="studentPlacementForm" property="supervisor.supervisorFullName"/>&#58;&nbsp;	
					    <bean:write name="studentPlacementForm" property="supervisor.studentsAllocated"/></b>
					     
				</td>
			</tr>	
			<tr>
				<td colspan="2">
				     <b><fmt:message key="emaillog.instruction.emailsentMessageFirstPart"/>
				     <bean:write name="studentPlacementForm" property="supervisor.supervisorFullName"/>
				     <fmt:message key="emaillog.instruction.emailsentMessageLastPart"/>&#58;</b>
				</td>
			   </tr>					
		     </sakai:group_table>
		
		     <sakai:flat_list>
		   		<tr >
				      <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.datesent"/></th>		
				      <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.email"/></th>
				      <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.studentsAllocatedOnEmail"/></th>	
				</tr>
			    <logic:iterate name="studentPlacementForm" property="supervisorEmailLogList" id="rec" indexId="index">
				     <tr>
					     <td><bean:write name="rec" property="dateSent"/></td>
					     <td><bean:write name="rec" property="emailAddress"/></td>
					     <td><bean:write name="rec" property="body"/></td>		
					</tr>
			  </logic:iterate>
		    </sakai:flat_list>
		    <sakai:actions>
		   
		    <html:submit property="action">
					<fmt:message key="button.emaillog"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.sendemail"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>