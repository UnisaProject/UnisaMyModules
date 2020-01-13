<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/districtMaintenance">
		<!--<html:hidden property="currentPage" value="displayDistrictList"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.districtMaintenance"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputDistrict.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table>				
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="districtFilterProvince">
						<html:optionsCollection name="studentPlacementForm" property="listFilterProvince" value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>		
			<tr>
				<td><fmt:message key="prompt.districtFilter"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="districtFilter" size="20" maxlength="20"/></td>
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
		<html:select  name="studentPlacementForm" property="listDistrict">
		 	<html:optionsCollection name="studentPlacementForm" property="listDistrict" value="value" label="label"/>		
		</html:select>		
		<sakai:actions>
			<!--<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.edit"/>
			</html:submit>-->		
			<html:submit property="action">
					<fmt:message key="button.select"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>			
		</sakai:actions>				
	</html:form>
</sakai:html>