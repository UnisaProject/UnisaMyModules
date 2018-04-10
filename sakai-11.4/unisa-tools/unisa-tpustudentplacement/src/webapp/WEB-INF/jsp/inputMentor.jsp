<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/mentorMaintenance">
	    
	   <sakai:messages/>
	   <sakai:messages message="true"/>	
	  		  <sakai:heading>
				<fmt:message key="heading.mentorAdd"/>
	          </sakai:heading>	
	     <sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:flat_list>		
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.title"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorModel.title" size="12" maxlength="10"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.initials"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="mentorModel.initials" size="20" maxlength="20"/></td>
			</tr>					
			<tr>
				<td><fmt:message key="prompt.surname"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="mentorModel.surname" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.occupation"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorModel.occupation" size="40" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.country"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="mentorModel.countryCode"  >
						<html:optionsCollection name="studentPlacementForm" property="listCountry" value="code" label="description"/>
					</html:select> &nbsp;
				<fmt:message key="prompt.mentorTrained"/>
				<html:select name="studentPlacementForm" property="mentorModel.trained" >
							<html:optionsCollection name="studentPlacementForm" property="listMentorTrained" value="value" label="label"/>
					</html:select>
				</td>
				<td>&nbsp;</td>		
			 </tr> 
			</sakai:flat_list>
		<hr/>
			<sakai:flat_list>		
			<tr>
				<td colspan="2"><fmt:message key="prompt.school"/><html:text name="studentPlacementForm" property="mentorModel.schoolName" size="30" maxlength="28"/>
				          <sakai:actions>
		                            <html:submit property="action">
					                        <fmt:message key="button.searchSchool"/>
			                        </html:submit>
						            <html:submit property="action">                      
				                        <fmt:message key="button.clearSchool"/>
				                    </html:submit>
	                      </sakai:actions>
	              <td>
	               </td>
	              </td>
	             </tr>
			<logic:equal name="studentPlacementForm" property="addMentorScreen"  value="N">
	               <tr><td><sakai:actions>
		                            <html:submit property="action">
					                        <fmt:message key="button.linktoSchool"/>
			                        </html:submit>
			                        <html:submit property="action">
					                        <fmt:message key="button.removeFromSchool"/>
			                        </html:submit>
			               </sakai:actions>
	                  </td><td>&nbsp;</td>
		           </tr>
		     </logic:equal>
			</sakai:flat_list>
			<hr/>
			<sakai:group_heading>
				<fmt:message key="editMentor.instruction.contact"/> 
			</sakai:group_heading>	
			<sakai:flat_list>		
			<tr>
				<td style="width:200px;"><fmt:message key="prompt.contactCellNr"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="mentorModel.cellNumber" size="30" maxlength="28"/><fmt:message key="addMentor.note1"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactLandLineNr"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorModel.phoneNumber" size="30" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.contactEmailAddress"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorModel.emailAddress" size="40" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.faxNumber"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="mentorModel.faxNumber" size="30" maxlength="28"/></td>
			</tr>
			</sakai:flat_list>
			<hr/>
	<sakai:actions>
		      
			     <html:submit property="action">
					<fmt:message key="button.saveMentor"/>
			     </html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
	</sakai:actions>	
	<html:hidden property="action" value="mentorCountryOnchangeAction"/>						
 </html:form>
</sakai:html>