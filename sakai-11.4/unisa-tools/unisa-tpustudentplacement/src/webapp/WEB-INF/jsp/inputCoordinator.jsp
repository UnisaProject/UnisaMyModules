<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/coordinatorMaintenance">
			<sakai:messages/>
		    <sakai:messages message="true"/>
            <sakai:heading>
			     <fmt:message key="heading.addWScoordinator"/> 
		    </sakai:heading>
		    <sakai:group_table>	
		    <tr>
				  <td><fmt:message key="prompt.compulsoryMessage"/></td>
				  <td>&nbsp;</td><td>&nbsp;</td>
			</tr>	
			<tr>
				  <td><fmt:message key="prompt.column.persno"/><fmt:message key="prompt.compulsory"/></td>
				  <td colspan="2"><html:text name="studentPlacementForm" property="coordinator.personnelNumber"  size="20" maxlength="20"  onchange="submit()"/>
				                   <html:button  value="Get Staff Info" property="staffInfoStatus" onclick="submit()"></html:button>
				                   <logic:equal  name="studentPlacementForm" property="staffInfoStatus" value="StaffInfoSet">
				                        <fmt:message key="prompt.openingBracket"/>
				                        <bean:write name="studentPlacementForm" property="coordinator.name"/>
				                        <fmt:message key="prompt.closingBracket"/>
				                   </logic:equal>
				 </td>
			</tr>	
			<tr>
			       <td><fmt:message key="prompt.column.networkcode"/></td>
				   <td><bean:write name="studentPlacementForm" property="coordinator.networkCode"/></td>
				   <td>&nbsp;</td>
			</tr>	
			<tr>
			       <td><fmt:message key="prompt.column.contactNumber"/></td>
				   <td><html:text name="studentPlacementForm" property="coordinator.contactNumber" size="20" maxlength="20" /></td>
			</tr>
			<tr>
			       <td><fmt:message key="prompt.column.email"/></td>
				   <td><html:text name="studentPlacementForm" property="coordinator.emailAddress" size="20"  /></td>
			</tr>
			<tr>   
			       <td><fmt:message key="prompt.column.sadecIntIndicator"/></td>
			       <td colspan="2">
			            <html:radio name= "studentPlacementForm"  property="coordinator.sadecInt"   value="Y" ><fmt:message key="prompt.yes" /></html:radio>
			            <html:radio name= "studentPlacementForm"  property="coordinator.sadecInt"   value="N" ><fmt:message key="prompt.no"/></html:radio>
			       </td>
					
			</tr>	
		    <tr>
		           <td><fmt:message key="prompt.column.workstation"/></td>
				    <td><html:select name="studentPlacementForm" property="coordinator.workStationCode" >
						<html:optionsCollection name="studentPlacementForm" property="listProvince" value="value" label="label"/>
					</html:select>                                           
				</td>	
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.save"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>	
		<html:hidden property="action" value="staffInfoBtnOnClickAction"/>			
	</html:form>
</sakai:html>