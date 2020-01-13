<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/supervisorMaintenance">
	    	  <sakai:tool_bar>
	    	   <logic:equal name="studentPlacementForm"  property="coordinatorActive"  value="Y">
	    	       <logic:equal name="studentPlacementForm" property="coordinatorForProv" value="Y">
	    	                 <html:link href="supervisorMaintenance.do?action=adjustStudentAllocationAllowed">
			                    <fmt:message key="link.adjustStudentAllocation"/>
			                 </html:link>
			       </logic:equal>
			     </logic:equal>
			     <logic:notEqual name="studentPlacementForm" property="coordinatorForProv" value="Y">
			             <u style="color:grey"><fmt:message key="link.adjustStudentAllocation"/></u>
			     </logic:notEqual>
			     
		      </sakai:tool_bar>
		    
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading>
			<logic:equal name="studentPlacementForm" property="supervisorAction" value="Add">
				<fmt:message key="heading.supervisorAdd"/>
			</logic:equal>
			<logic:equal name="studentPlacementForm" property="supervisorAction" value="Edit">
				<fmt:message key="heading.supervisorEdit"/>
			</logic:equal>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:flat_list>		
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.title"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.title" size="12" maxlength="10"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.initials"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="supervisor.initials" size="20" maxlength="20"/></td>
			</tr>					
			<tr>
				<td><fmt:message key="prompt.surname"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="supervisor.surname" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.occupation"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.occupation" size="40" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="supervisor.countryCode"  disabled="true">
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>	
		</sakai:flat_list>
		<logic:equal name="studentPlacementForm" property="supervisorFilterCountry"  value="1015">
		    <hr/>
			<sakai:group_heading>
				<fmt:message key="editSupervisor.instruction.areas"/> 
			</sakai:group_heading>
			<sakai:flat_list>
			<tr >					
				<th style="white-space:nowrap;align:left;width:200px;""><fmt:message key="prompt.column.province"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.district"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.remove"/></th>
			</tr>
			<logic:iterate name="studentPlacementForm" property="supervisor.listArea" id="rec" indexId="index">
				<tr>
					<td><bean:write name="rec" property="province.description"/></td>
					<td><bean:write name="rec" property="district.description"/></td>
					<td><html:multibox property="indexNrSupAreaSelected"><bean:write name="index"/></html:multibox></td>
				</tr>
			</logic:iterate>
		   </sakai:flat_list>
		   <sakai:flat_list>
			 <tr><td>
				<sakai:actions>
					<html:submit property="action">
							<fmt:message key="button.addArea"/>
					</html:submit>
					<html:submit property="action">
							<fmt:message key="button.removeArea"/>
					</html:submit>
				</sakai:actions>
			  </td></tr>		
		    </sakai:flat_list>
		  </logic:equal>						
		  <hr/>
		<sakai:group_heading>
				<fmt:message key="editSupervisor.instruction.contract"/> 
		</sakai:group_heading>
		<sakai:flat_list>
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.contract"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>		
				<td>
					<html:radio name="studentPlacementForm" property="supervisor.contract" value="Y"><fmt:message key="prompt.Yes"/></html:radio>
					<html:radio name="studentPlacementForm" property="supervisor.contract" value="N"><fmt:message key="prompt.No"/></html:radio>
				</td>	
			</tr>	
			<tr>
				<td><fmt:message key="prompt.contractStart"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.contractStart" size="30" maxlength="28"/><fmt:message key="editSupervisor.note2"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="prompt.contractEnd"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.contractEnd" size="30" maxlength="28"/><fmt:message key="editSupervisor.note2"/></td>
			</tr>
			</sakai:flat_list>				
			<hr/>
			<sakai:group_heading>
				<fmt:message key="editSupervisor.instruction.contact"/> 
			</sakai:group_heading>	
			<sakai:flat_list>		
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.contactCellNr"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="supervisor.cellNr" size="30" maxlength="28"/><fmt:message key="editSupervisor.note1"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactLandLineNr"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.landLineNr" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactEmailAddress"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.emailAddress" size="40" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.faxNumber"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisor.faxNr" size="30" maxlength="28"/></td>
			</tr>
			</sakai:flat_list>
			<hr/>
			<sakai:group_heading>
				<fmt:message key="editSupervisor.instruction.address"/> 
			</sakai:group_heading>			
		<sakai:group_table>
		<tr>
			<th><fmt:message key="prompt.physicalAddress"/>&nbsp;</th>
			<th><fmt:message key="prompt.postalAddress"/>&nbsp;</th>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress1" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress1" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress2" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress2" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress3" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress3" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress4" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress4" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress5" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress5" size="30" maxlength="28"/></td>
		</tr>
		<tr>
			<td><html:text name="studentPlacementForm" property="supervisor.physicalAddress6" size="30" maxlength="28"/></td>
			<td><html:text name="studentPlacementForm" property="supervisor.postalAddress6" size="30" maxlength="28"/></td>
		</tr>
		<logic:equal name="studentPlacementForm" property="supervisorFilterCountry"  value="1015">
		    <tr>
			    <td>&nbsp;</td>
			    <td><fmt:message key="prompt.postalCode"/>&nbsp;
				<html:text name="studentPlacementForm" property="supervisor.postalCode" size="6" maxlength="4" readonly="true"/>
				<html:submit property="action">
					<fmt:message key="button.searchPostalCode"/>
				</html:submit></td>
		    </tr>
		</logic:equal>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.saveSupervisor"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>						
	</html:form>
</sakai:html>