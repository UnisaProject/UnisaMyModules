<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/schoolMaintenance">
		<!--<html:hidden property="currentPage" value="editDistrict"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:equal name="studentPlacementForm" property="schoolAction" value="Add">
				<fmt:message key="heading.schoolAdd"/>
			</logic:equal>
			<logic:equal name="studentPlacementForm" property="schoolAction" value="Edit">
				<fmt:message key="heading.schoolEdit"/>
			</logic:equal>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>	
		<sakai:flat_list>		
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.schoolName"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="school.name" size="60" maxlength="100"/></td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.inUse"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>		
				<td>
					<html:radio name="studentPlacementForm" property="school.inUse" value="Y"><fmt:message key="prompt.Yes"/></html:radio>
					<html:radio name="studentPlacementForm" property="school.inUse" value="N"><fmt:message key="prompt.No"/></html:radio>
				</td>	
			</tr>
			<tr>
				<td><fmt:message key="prompt.type"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="school.type">
						<html:optionsCollection name="studentPlacementForm" property="listSchoolType" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.category"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="school.category">
						<html:optionsCollection name="studentPlacementForm" property="listSchoolCategory" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="school.countryCode" disabled="true">
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>	
			<logic:equal name="studentPlacementForm"  property="school.countryCode"  value="1015">
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="school.district.province.code">
						<html:optionsCollection name="studentPlacementForm" property="listProvince" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><!--<html:text name="studentPlacementForm" property="districtFilter" size="30" maxlength="28"/>-->
				<html:submit property="action">                      
					<fmt:message key="button.searchDistrict"/>
				</html:submit>		
				<logic:notEmpty name="studentPlacementForm" property="listDistrict">
						<html:select name="studentPlacementForm" property="school.districtValue">
							<html:optionsCollection name="studentPlacementForm" property="listDistrict" value="value" label="label"/>
						</html:select> 
				</logic:notEmpty>					
			</tr> 
			</logic:equal>
			<tr>
				<td><fmt:message key="prompt.town"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="school.town" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.suburb"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.suburb" size="30" maxlength="28"/></td>
			</tr>	                                                
			<tr>
				<td><fmt:message key="prompt.agreement"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>		
				<td>
					<html:radio name="studentPlacementForm" property="school.agreement" value="Y"><fmt:message key="prompt.Yes"/></html:radio>
					<html:radio name="studentPlacementForm" property="school.agreement" value="N"><fmt:message key="prompt.No"/></html:radio>
				</td>	
			</tr>
			</sakai:flat_list>	
			<hr/>
			<sakai:group_heading>
				<fmt:message key="editSchool.instruction.contact"/> 
			</sakai:group_heading>	
			<sakai:flat_list>	
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.contactName"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.contactName" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactCellNr"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.cellNr" size="30" maxlength="28"/><fmt:message key="editSchool.note1"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactLandLineNr"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.landLineNr" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactEmailAddress"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.emailAddress" size="60" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.faxNumber"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="school.faxNr" size="30" maxlength="28"/></td>
			</tr>
			</sakai:flat_list>				
			<hr/>
			<sakai:group_heading>
				<fmt:message key="editSchool.instruction.address"/> 
			</sakai:group_heading>			
		<sakai:group_table>
		<tr>
			<th><fmt:message key="prompt.physicalAddress"/>&nbsp;</th>
			<th><fmt:message key="prompt.postalAddress"/>&nbsp;</th>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress1" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress1" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress2" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress2" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress3" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress3" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress4" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress4" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress5" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress5" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="school.physicalAddress6" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="school.postalAddress6" size="30" maxlength="28"/></td>
		</tr>
		<logic:equal name="studentPlacementForm"  property="school.countryCode"  value="1015">
		   <tr>
			  <td>&nbsp;</td>
			  <td><fmt:message key="prompt.postalCode"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				  <html:text name="studentPlacementForm" property="school.postalCode" size="6" maxlength="4" readonly="true"/>
				  <html:submit property="action">
					 <fmt:message key="button.searchPostalCode"/>
				  </html:submit></td>
		    </tr>
		</logic:equal>
		</sakai:group_table>		
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.save"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>						
	</html:form>
</sakai:html>