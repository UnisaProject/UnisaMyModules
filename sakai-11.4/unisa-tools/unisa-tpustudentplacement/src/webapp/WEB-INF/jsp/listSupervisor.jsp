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
			<fmt:message key="heading.supervisorMaintenance"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputSupervisor.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="supervisorFilterCountry" onchange="submit();">
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="supervisorFilterProvince">
						<html:optionsCollection name="studentPlacementForm" property="listFilterProvince" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="districtFilter" size="30" maxlength="28" />
					<html:submit property="action">                      
						<fmt:message key="button.searchDistrict"/>
					</html:submit>
					<logic:notEmpty name="studentPlacementForm" property="listFilterSupervisorDistrict">
						<html:select name="studentPlacementForm" property="supervisorFilterDistrictValue">
							<html:optionsCollection name="studentPlacementForm" property="listFilterSupervisorDistrict" value="value" label="label"/>
						</html:select> 
					</logic:notEmpty>
				</td>		
			</tr>
			<tr>
				<td> <fmt:message key="prompt.column.contract"/>&nbsp;</td>
				<td>				
					     <html:radio property="contractStatus"  value="All"><fmt:message key="prompt.all"/></html:radio><br>
					      <html:radio property="contractStatus"  value="Active"><fmt:message key="prompt.active"/></html:radio><br>
					      <html:radio property="contractStatus"  value="Expired"><fmt:message key="prompt.expired"/></html:radio><br>
					      <html:radio property="contractStatus"  value="Expires within"><fmt:message key="prompt.expireswithin"/></html:radio>
					      <html:text name="studentPlacementForm" property="daysToExpiry" size="10" maxlength="10" ></html:text>
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.supervisorFilter"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisorFilter" size="20" maxlength="20"/><i><fmt:message key="filter.note"/></i></td>
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>			
		<hr/>
		<sakai:flat_list>
		     <tr>
		     <logic:equal name="studentPlacementForm"  property="supervisorFilterCountry"  value="1015">
		         <td colspan="8" width="100%"   align="right">
				     <html:link href="supervisorMaintenance.do?action=extractFile">
						<fmt:message key="link.extractFile"/>						
					 </html:link>	
				</td>
			</logic:equal>	
			<logic:notEqual name="studentPlacementForm"  property="supervisorFilterCountry"  value="1015">
		         <td colspan="7">&nbsp;</td>	
				 <td colspan="1" align="right">
					<html:link href="supervisorMaintenance.do?action=extractFile">
						<fmt:message key="link.extractFile"/>						
					</html:link>	
				</td>
			</logic:notEqual>
			</tr>
			<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.name"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.cellNumber"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.emailAddress"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.contractStart"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.contractEnd"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.studentsAllowed"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.studentsAllocated"/></th>	
				<logic:equal name="studentPlacementForm"  property="supervisorFilterCountry"  value="1015">				
				        <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				        <th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.districts"/></th>
				</logic:equal>        	
			</tr>
			<logic:iterate name="studentPlacementForm" property="listSupervisor" id="rec" indexId="index">
				<tr>
					<td style="white-space:nowrap;align:left">	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="name"/>
					</td>
					<td><bean:write name="rec" property="cellNumber"/></td>
					<td><bean:write name="rec" property="emailAddress"/></td>		
					<td><bean:write name="rec" property="contractStart"/></td>									
					<td  width="5%"><bean:write name="rec" property="contractEnd"/></td>
					<td  width="5%"><bean:write name="rec" property="studentsAllowed"/></td>
					<td><bean:write name="rec" property="studentsAllocated"/></td>						
					<logic:equal name="studentPlacementForm"  property="supervisorFilterCountry"  value="1015">			
					       <td><bean:write name="rec" property="province"/></td>
					       <td style="white-space: nowrap">
						         <span title="<bean:write name="rec" property="district"/>"><bean:write name="rec" property="shortDistrict"/></span>
					       </td>
					</logic:equal>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.edit"/>
			</html:submit>		
			<logic:notEqual name="studentPlacementForm" property="supervisorCalledFrom" value="inputStudentPlacement">		
				<html:submit property="action">
						<fmt:message key="button.select"/>
				</html:submit>		
			</logic:notEqual>
			<html:submit property="action">
					<fmt:message key="button.emaillog"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>	
			<html:hidden property="action" value="supervCountryOnchangeAction"/>		
		</sakai:actions>
	</html:form>
</sakai:html>