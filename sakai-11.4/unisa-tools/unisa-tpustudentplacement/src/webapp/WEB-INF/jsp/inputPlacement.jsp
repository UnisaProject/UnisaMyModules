<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/managementInfo">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.managementInfo"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputPlacementList.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="acadYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="semester">
						<html:optionsCollection name="studentPlacementForm" property="listSemester" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="placementFilterCountry" onchange="submit();">>
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>		
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="placementFilterProvince">
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
					<logic:notEmpty name="studentPlacementForm" property="listFilterPlacementDistrict">
						<html:select name="studentPlacementForm" property="placementFilterDistrictValue">
							<html:optionsCollection name="studentPlacementForm" property="listFilterPlacementDistrict" value="value" label="label"/>
						</html:select> 
					</logic:notEmpty>
				</td>					
			  </tr>		
			  <tr>
				<td><fmt:message key="prompt.module"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="placementFilterModule">
						<html:options name="studentPlacementForm" property="listPracticalModules"/>
					</html:select>                                           
				</td>
			  </tr>	
			<tr>
				<td><fmt:message key="prompt.school"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="placementFilterSchoolDesc" size="40" maxlength="60" readonly="true"/>
				<html:submit property="action">                      
					<fmt:message key="button.searchSchool"/>
				</html:submit>			
				<html:submit property="action">                      
					<fmt:message key="button.clearSchool"/>
				</html:submit>			
			</tr> 
			<tr>
				<td><fmt:message key="prompt.supervisor"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="placementFilterSupervisorDesc" size="30" maxlength="28" readonly="true"/>
				<html:submit property="action">                      
					<fmt:message key="button.searchSupervisor"/>
				</html:submit>	
				<html:submit property="action">                      
					<fmt:message key="button.clearSupervisor"/>
				</html:submit>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.sortOn"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>		
				<td>
				    <logic:equal name="studentPlacementForm"  property="placementFilterCountry"  value="1015">	
					     <html:radio name="studentPlacementForm" property="placementSortOn" value="District"><fmt:message key="prompt.district"/></html:radio>
					</logic:equal>   
					<html:radio name="studentPlacementForm" property="placementSortOn" value="Module"><fmt:message key="prompt.module"/></html:radio>
					<html:radio name="studentPlacementForm" property="placementSortOn" value="School"><fmt:message key="prompt.school"/></html:radio>
					<html:radio name="studentPlacementForm" property="placementSortOn" value="Student"><fmt:message key="prompt.student"/></html:radio>
					<html:radio name="studentPlacementForm" property="placementSortOn" value="Supervisor"><fmt:message key="prompt.supervisor"/></html:radio>
				</td>	
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.prelimPlacements"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>	
		<html:hidden property="action" value="placementCountryOnchangeAction"/>			
	</html:form>
</sakai:html>