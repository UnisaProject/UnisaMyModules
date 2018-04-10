<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/districtMaintenance">
		<!--<html:hidden property="currentPage" value="editDistrict"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:equal name="studentPlacementForm" property="districtAction" value="Add">
				<fmt:message key="heading.districtAdd"/>
			</logic:equal>
			<logic:equal name="studentPlacementForm" property="districtAction" value="Edit">
				<fmt:message key="heading.districtEdit"/>
			</logic:equal>
		</sakai:heading>	
		<sakai:group_table>		
			<tr>
				<td><fmt:message key="prompt.districtName"/>&nbsp;</td>
				<td><html:text name="studentPlacementForm" property="districtName" size="20" maxlength="20"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.inUse"/>&nbsp;</td>		
				<td>
					<html:radio name="studentPlacementForm" property="districtInUse" value="Y"><fmt:message key="prompt.Yes"/></html:radio>
					<html:radio name="studentPlacementForm" property="districtInUse" value="N"><fmt:message key="prompt.No"/></html:radio>
				</td>	
			</tr>
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="districtProvince">
						<html:optionsCollection name="studentPlacementForm" property="listProvince" value="code" label="description"/>
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
	</html:form>
</sakai:html>