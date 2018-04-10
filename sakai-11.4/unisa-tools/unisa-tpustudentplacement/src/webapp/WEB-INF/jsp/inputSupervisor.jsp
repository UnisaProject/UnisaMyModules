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
		<html:hidden property="action" value="supervCountryOnchangeAction"/>			
	</html:form>
</sakai:html>