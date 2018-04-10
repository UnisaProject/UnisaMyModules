<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/supervisorMaintenance">
		<!--<html:hidden property="currentPage" value="editDistrict"/>-->
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
		<sakai:group_heading>
			<fmt:message key="heading.addSupervisorArea"/>
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="supervisorArea.province.code">
						<html:optionsCollection name="studentPlacementForm" property="listProvince" value="code" label="description"/>
					</html:select>                                           
				</td>
				<td>&nbsp;</td>
			</tr>	
			<!-- <tr>
				<td><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="supervisorArea.district.description" size="30" maxlength="28" readonly="true"/>
				<html:submit property="action">                      
					<fmt:message key="button.searchDistrict"/>
				</html:submit>				
			</tr>  -->			
			<tr>
				<td style="white-space:nowrap;align:top"><fmt:message key="prompt.district"/>&nbsp;</td>
				<td><!--<html:text name="studentPlacementForm" property="districtFilter" size="30" maxlength="28"/>-->
					<html:submit property="action">                      
						<fmt:message key="button.searchDistrict"/>
					</html:submit>
				</td>
				<td>		
					<logic:notEmpty name="studentPlacementForm" property="listDistrict">
						<i><fmt:message key="linkSupervisorArea.note1"/></i><br></br> 
						<html:select name="studentPlacementForm" property="districtSelection" multiple="true" size="20">
							<html:optionsCollection name="studentPlacementForm" property="listDistrict" value="value" label="label"/>
						</html:select>
					</logic:notEmpty>
				</td>				
			</tr>    		
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.link"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>						
	</html:form>
</sakai:html>